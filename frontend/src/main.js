import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueMoment from 'vue-moment'
import VueCookies from 'vue-cookies'

Vue.use(VueCookies)
Vue.use(VueMoment);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
