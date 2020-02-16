import request from './request'

export function register (data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function login (data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getUserInfo () {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export function getAllMiaoshaGoods () {
  return request({
    url: '/miaoshaGoods',
    method: 'GET'
  })
}

export function getMiaoshaGoods (goodsId) {
  return request({
    url: `/miaoshaGoods/${goodsId}`,
    method: 'GET'
  })
}
export function getVerifyCode (goodsId) {
  return request({
    url: '/verifyCode',
    method: 'GET',
    params: {
      goodsId: goodsId
    }
  })
}
export function getMiaoshaPath (goodsId, verifyCode) {
  return request({
    url: '/miaoshaPath',
    method: 'GET',
    params: {
      goodsId: goodsId,
      verifyCode: verifyCode
    }
  })
}
export function seckill (path, goodsId) {
  return request({
    url: `/${path}/seckill`,
    method: 'GET',
    params: {
      goodsId: goodsId
    }
  })
}

export function getMiaoshaResult (goodsId) {
  return request({
    url: '/miaoshaResult',
    method: 'GET',
    params: {
      goodsId: goodsId
    }
  })
}
export function getOrder (orderId) {
  return request({
    url: '/order',
    method: 'GET',
    params: {
      orderId: orderId
    }
  })
}
