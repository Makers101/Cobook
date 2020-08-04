import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueMoment from 'vue-moment'
import VueCookies from 'vue-cookies'

import firebase from 'firebase/app'
import 'firebase/messaging'

const firebaseConfig = {
  apiKey: "AIzaSyA9KKQn0uKuErmQMJsMbhw25-iG8chHLdI",
  authDomain: "co-book-original.firebaseapp.com",
  databaseURL: "https://co-book-original.firebaseio.com",
  projectId: "co-book-original",
  storageBucket: "co-book-original.appspot.com",
  messagingSenderId: "21513194733",
  appId: "1:21513194733:web:5ac45b8faee796d5b910e4",
  measurementId: "G-YNYKTYY7B8"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging()

messaging.usePublicVapidKey('BMxEbAZ8_VAS1w4fZ06Kd-TL8eWPdxlFR4aRmQj1NHpEdAyVwKShOg8YLF_lJbhdwMpuS09jbMbbi5PDGYn_UoA')

// 알림 수신을 위한 사용자 권한 요청
Notification.requestPermission()
  .then((permission) => {
    console.log('permission ', permission)
    if (permission !== 'granted') {
      alert('알림을 허용해주세요')
    }
  })

// TODO: Send token to server for send notification
messaging.getToken()
  .then(console.log)

// Handle received push notification at foreground
messaging.onMessage(payload => {
  console.log(payload)
  alert(payload.data.message)
})


Vue.use(VueCookies)
Vue.use(VueMoment);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
