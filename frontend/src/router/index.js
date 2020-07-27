import Vue from 'vue'
import VueRouter from 'vue-router'
// accounts
import Login from '@/views/accounts/Login.vue'
import Signup from '@/views/accounts/Signup.vue'
import SignupEmail from '@/views/accounts/SignupEmail.vue'
import PasswordFind from '@/views/accounts/PasswordFind.vue'
import PasswordChange from '@/views/accounts/PasswordChange.vue'
import PasswordChangeSuccessful from '@/views/accounts/PasswordChangeSuccessful.vue'
// profiles
import Profile from '@/views/profile/Profile.vue'
import ProfileFeed from '@/views/profile/ProfileFeed.vue'
import ProfileClub from '@/views/profile/ProfileClub'
import ProfileBookmark from '@/views/profile/ProfileBookmark'
import ProfileOverview from '@/views/profile/ProfileOverview'
// posts
import PostList from '@/views/posts/PostList'
import PostCreate from '@/views/posts/PostCreate'
// clubs
import ClubList from '@/views/clubs/ClubList.vue'
import ClubDetail from '@/views/clubs/ClubDetail.vue'
import ReadingDetail from '@/views/clubs/readings/ReadingDetail.vue'

Vue.use(VueRouter)

  const routes = [
  // accounts
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

  // posts
  {
    path: '/',
    name: 'PostList',
    component: PostList
  },
  {
    path: '/posts/create',
    name: 'PostCreate',
    component: PostCreate
  },

  // clubs
  {
    path: '/clubs',
    name: 'ClubList',
    component: ClubList
  },
  {
    path: '/clubs/:clubId',
    name: 'ClubDetail',
    component: ClubDetail
  },
  {
    path: '/clubs/:clubId/readings/:readingId',
    name: 'ReadingDetail',
    component: ReadingDetail
  }

  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
