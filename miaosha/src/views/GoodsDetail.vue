<template>
  <el-card>
      <div class="image-box">
        <el-image :src="goods.image" class="image"></el-image>
      </div>
      <div class="info-box">
        <ul>
          <li>当前时间：{{currentTime.format('YYYY-MM-DD HH:mm:ss')}}</li>
          <li v-if='seckillStatus === 0'><h4>秒杀倒计时：{{countDownShow}} </h4></li>
          <li v-else-if="seckillStatus === 1"><h4>秒杀进行中！</h4></li>
          <li v-else><h4>秒杀已结束！</h4></li>
          <li><h3>商品名称: {{goods.name}}</h3></li>
          <li><h3>商品原价: {{goods.price}}</h3></li>
          <li><h3>秒杀价: {{goods.miaoshaPrice}}</h3>
          <li><h3>库存数量: {{goods.miaoshaStock}}</h3></li>
          <li>
            <div style="height: 40px;">
              <div style="height: 40px; float: left;">
                <el-input size="small" v-model="verifyCodeInput" style="width: 70px;"></el-input>
              </div>
              <div style=" margin-left: 100px;"><el-image :src="verifyCodeUrl"/></div>
            </div>
          </li>
          <li><el-button type="text" class="button" :disabled="seckillDisabled" @click="handleSeckill">立即秒杀</el-button></li>
        </ul>
      </div>
  </el-card>
</template>

<script>
import moment from 'moment'
import {seckill, getMiaoshaGoods, getMiaoshaPath, getVerifyCode, getMiaoshaResult} from '@/utils/api'
export default {
  name: 'detail',
  data () {
    return {
      goods: {},
      goodsId: -1,
      remainSeconds: -1,
      currentTime: {},
      startTime: {},
      duration: -1,
      timer: {},
      verifyCodeUrl: '',
      verifyCodeInput: ''
    }
  },
  computed: {
    seckillDisabled () {
      return this.countDown > 0 || this.countDown <= -this.duration
    },
    seckillStatus () {
      if (this.countDown > 0) {
        // console.log('countDown：' + this.countDown)
        return 0
      } else if (this.countDown <= 0 && this.countDown > -this.duration) {
        // console.log('countDown：' + this.countDown)
        return 1
      } else {
        // console.log('countDown：' + this.countDown)
        return -1
      }
    },
    countDown () {
      return Math.floor((this.startTime - this.currentTime) / 1000)
    },
    countDownShow () {
      const totalSeconds = this.countDown
      const hours = Math.floor(totalSeconds / 3600)
      const minutes = Math.floor((totalSeconds % 3600) / 60)
      const seconds = totalSeconds % 60
      return hours + ' 时 ' + minutes + ' 分 ' + seconds + ' 秒'
    }
  },
  watch: {
    countDown (val) {
      if (val < -this.duration) {
        clearInterval(this.timer)
      }
    }
  },
  mounted () {
    this.goodsId = this.$router.currentRoute.params.id
    getMiaoshaGoods(this.goodsId).then(response => {
      this.goods = response.data.data.goods
      this.remainSeconds = response.data.data.remainSeconds
      this.duration = response.data.data.duration
      this.currentTime = moment()
      this.startTime = moment().add(this.remainSeconds, 'seconds')
      this.timer = setInterval(() => {
        this.currentTime = moment()
      }, 200)
    }).catch(error => {
      this.$message.error('获取商品信息失败！')
      console.log(error)
    })
    getVerifyCode(this.goodsId).then(response => {
      this.verifyCodeUrl = 'data:image/jpeg;base64,' + response.data.data
      console.log(this.verifyCodeUrl)
    }).catch(error => {
      this.$message.error('获取验证码失败！')
      console.log(error)
    })
  },
  methods: {
    handleSeckill () {
      getMiaoshaPath(this.goodsId, this.verifyCodeInput).then((res) => {
        const path = res.data.data
        seckill(path, this.goodsId).then((resp) => {
          if (resp.data.code === 200) {
            this.$message.success('等待秒杀结果...')
            const resultTimer = setInterval(() => {
              getMiaoshaResult(this.goodsId).then(response => {
                if (response.data.code === 200) {
                  this.$message.success('秒杀成功！')
                  clearInterval(resultTimer)
                  const id = response.data.data
                  this.$router.push({path: `/order/${id}`})
                } else {
                  this.$message.error(response.data.message)
                  this.$router.push({path: '/'})
                }
              }).catch(error => {
                this.$message.error('获取结果失败！')
                console.log(error)
              })
            }, 200)
          } else {
            this.$message.error(resp.data.message)
          }
        }).catch(err => {
          this.$message.error('服务器出错！')
          this.$router.push('/')
          console.log('error: ')
          console.log(err)
        })
      })
    }
  }
}
</script>

<style>
.el-card {
  margin: auto;
  width: 60%;
}
.image-box {
  text-align: center
}
.image {
  width: 350px;
  height: 350px;
}
.info-box {
  text-align: left;
  padding-left: 220px;
}
ul li {
  list-style: none;
}
.button {
  font-size: 18px;
}
h4 {
  color: red
}
</style>
