<template>
 <el-table
   :data="tableData"
    border
    style="width: 100%">
    <el-table-column
      prop="name"
      label="名称"
      width="180">
    </el-table-column>
    <el-table-column
      prop="image"
      label="图片"
      width="180">
      <template slot-scope="scope">
        <el-avatar shape="square" :size="100" :src="scope.row.image"></el-avatar>
      </template>
    </el-table-column>
    <el-table-column
      prop="price"
      label="商品原价">
    </el-table-column>
    <el-table-column
      prop="miaoshaPrice"
      label="秒杀价">
    </el-table-column>
    <el-table-column
      prop="miaoshaStock"
      label="库存量">
    </el-table-column>
    <el-table-column
      prop="detail"
      label="详情">
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row.id)" type="text" size="small">详情</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getAllMiaoshaGoods} from '@/utils/api'
export default {
  name: 'home',
  data () {
    return {
      tableData: []
    }
  },
  methods: {
    handleClick (id) {
      this.$router.push({path: `/goods/${id}`})
    }
  },
  mounted () {
    getAllMiaoshaGoods().then((response) => {
      console.log(response)
      this.tableData = response.data.data
      console.log(response.data.data)
      console.log(this.tableData.data)
    })
  }
}
</script>

<style>

</style>
