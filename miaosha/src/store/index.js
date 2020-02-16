import Vue from 'vue'
import Vuex from 'vuex'

import user from './modules/user'
import http from './modules/http'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user,
    http
  }
})
