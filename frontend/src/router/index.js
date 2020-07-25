import Vue from 'vue'
import VueRouter from 'vue-router'
// accounts
import Login from '@/views/accounts/Login.vue'
import Signup from '@/views/accounts/Signup.vue'
import SignupEmail from '@/views/accounts/SignupEmail.vue'
import PasswordFind from '@/views/accounts/PasswordFind.vue'
import PasswordChange from '@/views/accounts/PasswordChange.vue'
import PasswordChangeSuccessful from '@/views/accounts/PasswordChangeSuccessful.vue'
// profile
import Profile from '@/views/profile/Profile.vue'
import ProfileFeed from '@/views/profile/ProfileFeed.vue'
import ProfileClub from '@/views/profile/ProfileClub'
import ProfileBookmark from '@/views/profile/ProfileBookmark'
import ProfileOverview from '@/views/profile/ProfileOverview'
import PostList from '@/views/posts/PostList.vue'

import ClubList from '@/views/clubs/ClubList.vue'

Vue.use(VueRouter)

  const routes = [

    {
    path: '/login',
    name: 'Login',
    component: Login
  {,
    path: '/',
    name: 'PostList',
    component: PostList
  },
  // clubs
  {
    path: '/clubs',
    name: 'ClubList',
    component: ClubList
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
  },
  // profile
  {
    path: '/profile',
    name: 'Profile',
    component: Profile,
    children: [
      {
        path: 'feed',
        component: ProfileFeed
      },
      {
        path: 'club',
        component: ProfileClub
      },
      {
        path: 'bookmark',
        component: ProfileBookmark
      },
      {
        path: 'overview',
        component: ProfileOverview
      },
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
