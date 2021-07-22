import Vue from 'vue'
import VueRouter from 'vue-router'
import config from "@/router/config";
Vue.use(VueRouter);

let router = new VueRouter(config);

export default router;

