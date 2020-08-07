import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueMoment from 'vue-moment'
import VueCookies from 'vue-cookies'

import GAuth from 'vue-google-oauth2'

const gauthOption = {
    clientId: '788261399554-bvj7d8l6jjcttpbfiitachi3l3kuklfh.apps.googleusercontent.com',
    scope: 'email profile',
    prompt: 'consent',
    fetch_basic_profile: true
}

Vue.use(VueCookies)
Vue.use(VueMoment);
Vue.use(GAuth, gauthOption)

Vue.config.productionTip = false

window.Kakao.init("18e2e5a6d3d295aaf4cd23e46868e8ab");

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app')
