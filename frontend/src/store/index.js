import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const state = {
    token: null
}

const mutations = {
    LOGOUT (state) {
        // 移除token和userInfo
        state.token = null
    },
    LOGIN (state, data) {
        state.token = data
    }
}
const actions = {
}

export default new Vuex.Store({
    state,
    mutations,
    actions
})