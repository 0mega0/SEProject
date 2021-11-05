import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login.vue'
import zhuce from '@/components/zhuce.vue'
import admin from '@/components/admin.vue'
import main from '@/components/main.vue'
import personalInfo from '@/components/personalInfo.vue'
import shopmanage from '@/components/shopmanage.vue'
import addgood from '@/components/addgood.vue'
import shopzhuce from '@/components/shopzhuce.vue'
import shopadmin from '@/components/shopadmin.vue'
import goodadmin from '@/components/goodadmin.vue'
import goodinfo from '@/components/goodinfo.vue'
import usershop from '@/components/usershop.vue'
import car from '@/components/car.vue'
import payed from '@/components/payed.vue'
import sendgood from '@/components/sendgood.vue'
import tuikuan from '@/components/tuikuan.vue'
import shouhuo from '@/components/shouhuo.vue'
import horder from '@/components/horder.vue'
import finished from '@/components/finished.vue'
import xiajia from '@/components/xiajia.vue'
import shoplevel from '@/components/shoplevel.vue'
import chongzhi from '@/components/chongzhi.vue'

Vue.use(Router)

const routes = [
  { path: '/login', component: login },
  { path: '/zhuce', component: zhuce },
  { path: '/admin', component: admin },
  { path: '/', component: main },
  { path: '/personalInfo', component: personalInfo },
  { path: '/shopmanage', component: shopmanage },
  { path: '/addgood', component: addgood },
  { path: '/shopzhuce', component: shopzhuce },
  { path: '/shopmanage', component: shopmanage },
  { path: '/shopadmin', component: shopadmin },
  { path: '/goodadmin', component: goodadmin },
  { path: '/goodinfo', component: goodinfo },
  { path: '/usershop', component: usershop },
  { path: '/car', component: car },
  { path: '/payed', component: payed },
  { path: '/sendgood', component: sendgood },
  { path: '/tuikuan', component: tuikuan },
  { path: '/shouhuo', component: shouhuo },
  { path: '/horder', component: horder },
  { path: '/finished', component: finished },
  { path: '/xiajia', component: xiajia },
  { path: '/shoplevel', component: shoplevel },
  { path: '/chongzhi', component: chongzhi }
]

const router = new Router({
  routes: routes
})

export default router
