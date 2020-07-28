import Vue from 'vue'
import VueRouter from 'vue-router'
// accounts
import Login from '@/views/accounts/Login'
import Signup from '@/views/accounts/Signup'
import SignupEmail from '@/views/accounts/SignupEmail'
import PasswordFind from '@/views/accounts/PasswordFind'
import PasswordFindEmail from '@/views/accounts/PasswordFindEmail'
import PasswordChange from '@/views/accounts/PasswordChange'
import PasswordChangeSuccessful from '@/views/accounts/PasswordChangeSuccessful'
// profiles
import Profile from '@/views/profile/Profile'
import ProfileFeed from '@/views/profile/ProfileFeed'
import ProfileClub from '@/views/profile/ProfileClub'
import ProfileBookmark from '@/views/profile/ProfileBookmark'
import ProfileOverview from '@/views/profile/ProfileOverview'
import ProfileUpdate from '@/views/profile/ProfileUpdate'
// posts
import PostList from '@/views/posts/PostList'
import PostCreate from '@/views/posts/PostCreate'
import PostDetail from '@/views/posts/PostDetail'
// clubs
import ClubList from '@/views/clubs/ClubList'
import ClubDetail from '@/views/clubs/ClubDetail'
import ClubCreate from '@/views/clubs/ClubCreate'
import ReadingDetail from '@/views/clubs/readings/ReadingDetail'
import ReadingCreate from '@/views/clubs/readings/ReadingCreate'

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
    path: '/password/email',
    name: 'PasswordFindEmail',
    component: PasswordFindEmail
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
  {
    path: '/profile/update',
    name: 'ProfileUpdate',
    component: ProfileUpdate
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
  {
    path: '/posts/:postId',
    name: 'PostDetail',
    component: PostDetail
  },

  // clubs
  {
    path: '/clubs',
    name: 'ClubList',
    component: ClubList
  },
  {
    path: '/clubs/create',
    name: 'ClubCreate',
    component: ClubCreate
  },
  {
    path: '/clubs/:clubId',
    name: 'ClubDetail',
    component: ClubDetail
  },
  // {
  //   path: '/clubs/:clubId/readings/create',
  //   name: 'ReadingCreate',
  //   component: ReadingCreate
  // },
  {
    path: '/clubs/readings/create',
    name: 'ReadingCreate',
    component: ReadingCreate
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
