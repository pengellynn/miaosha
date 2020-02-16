import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Login from '@/views/Login'
import GoodsDetail from '@/views/GoodsDetail'
import OrderDetail from '@/views/OrderDetail'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/goods/:id',
      name: 'goodsDetail',
      component: GoodsDetail
    },
    {
      path: '/order/:id',
      name: 'orderDetail',
      component: OrderDetail
    }
  ]
})
