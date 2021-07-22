import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from "@/router/index";
import axios from "axios";
import store from './store'
Vue.use(ElementUI)

Vue.config.productionTip = false
// Axios挂载到prototype，全局可以使用this.$axios访问
Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://localhost:8080/api'
axios.defaults.withCredentials = true
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')