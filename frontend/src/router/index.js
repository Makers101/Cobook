import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/accounts/Login.vue'
import Signup from '@/views/accounts/Signup.vue'
import SignupEmail from '@/views/accounts/SignupEmail.vue'
import PasswordFind from '@/views/accounts/PasswordFind.vue'
import PasswordChange from '@/views/accounts/PasswordChange.vue'
import PasswordChangeSuccessful from '@/views/accounts/PasswordChangeSuccessful.vue'


Vue.use(VueRouter)

  const routes = [

    {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/signup/email',
    name: 'SignupEmail',
    component: SignupEmail
  },
  {
    path: '/password',
    name: 'PasswordFind',
    component: PasswordFind
  },
  {
    path: '/password/change',
    name: 'PasswordChange',
    component: PasswordChange
  },
  {
    path: '/password/success',
    name: 'PasswordChangeSuccessful',
    component: PasswordChangeSuccessful
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
