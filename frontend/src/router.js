import Vue from 'vue'
import Router from 'vue-router'
import AddPartner from './components/AddPartner.vue'
import GetPartner from './components/GetPartner.vue'
import App from './App.vue'

Vue.use(Router)

export default new Router({
    routes: [
      {
        path: '/getPartner',
        name: 'getPartner',
        component: GetPartner
      },
      {
        path: '/addPartner',
        name: 'addPartner',
        component: AddPartner
      },
      {
        path: '/home',
        name: 'Home',
        component: App
      }
    ]
  })
  