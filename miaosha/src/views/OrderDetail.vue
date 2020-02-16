<template>
  <el-table
   :data="tableData"
    border
    style="width: 100%">
    <el-table-column
      prop="id"
      label="订单号">
    </el-table-column>
     <el-table-column
      prop="userId"
      label="用户">
    </el-table-column>
    <el-table-column
      prop="goodsName"
      label="商品名称">
    </el-table-column>
    <el-table-column
      prop="goodsPrice"
      label="单价">
    </el-table-column>
    <el-table-column
      prop="goodsCount"
      label="数量">
    </el-table-column>
    <el-table-column
      prop="totalCost"
      label="总额">
    </el-table-column>
    <el-table-column
      prop="createTime"
      label="创建时间"
      width="200">
    </el-table-column>
    <el-table-column
      prop="status"
      label="支付状态">
    </el-table-column>
    <el-table-column>
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row.orderId)" type="text" size="small">支付</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getOrder} from '@/utils/api'
export default {
  name: 'orderDetail',
  data () {
    return {
      tableData: []
    }
  },
  mounted () {
    const orderId = this.$router.currentRoute.params.id
    getOrder(orderId).then(response => {
      const order = response.data.data
      order.totalCost = order.goodsPrice * order.goodsCount
      this.tableData.push(order)
    }).catch(error => {
      console.log(error)
    })
  }

}
</script>

<style>

</style>
