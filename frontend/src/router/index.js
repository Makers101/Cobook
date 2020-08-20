import Vue from 'vue'
import VueRouter from 'vue-router'
// accounts
import Login from '@/views/accounts/Login'
import Signup from '@/views/accounts/Signup'
import SignupKakao from '@/views/accounts/SignupKakao'
import SignupEmail from '@/views/accounts/SignupEmail'
import SignupSuccessful from '@/views/accounts/SignupSuccessful'
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
import PostUpdate from '@/views/posts/PostUpdate'
// clubs
import ClubList from '@/views/clubs/ClubList'
import ClubDetail from '@/views/clubs/ClubDetail'
import ClubCreate from '@/views/clubs/ClubCreate'
import ClubUpdate from '@/views/clubs/ClubUpdate'
import ClubCandidates from '@/views/clubs/ClubCandidates'
import ClubEventDetail from '@/views/clubs/clubevents/ClubEventDetail'
import ClubEventCreate from '@/views/clubs/clubevents/ClubEventCreate'
import ClubEventUpdate from '@/views/clubs/clubevents/ClubEventUpdate'

// onedayevents
import OnedayEventList from '@/views/onedayevents/OnedayEventList'
import OnedayEventDetail from '@/views/onedayevents/OnedayEventDetail'
import OnedayEventCreate from '@/views/onedayevents/OnedayEventCreate'
import OnedayEventUpdate from '@/views/onedayevents/OnedayEventUpdate'

// books
import BookDetail from '@/views/books/Book'

//pagenotfound
import PageNotFound from '@/views/PageNotFound'

// search
import Search from '@/views/search/Search'
import SearchBook from '@/views/search/SearchBook'
import SearchClub from '@/views/search/SearchClub'
import SearchOneDayEvent from '@/views/search/SearchOneDayEvent'
import SearchPost from '@/views/search/SearchPost'
import SearchUser from '@/views/search/SearchUser'


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
    path: '/signup/kakao',
    name: 'SignupKakao',
    component: SignupKakao
  },
  {
    path: '/signup/email',
    name: 'SignupEmail',
    component: SignupEmail
  },
  {
    path: '/signup/success',
    name: 'SignupSuccessful',
    component: SignupSuccessful
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
    path: '/profile/:userId',
    name: 'Profile',
    component: Profile,
    children: [
      {
        path: 'feed',
        component: ProfileFeed,
        name: 'ProfileFeed'
      },
      {
        path: 'club',
        component: ProfileClub,
        name: 'ProfileClub'
      },
      {
        path: 'bookmark',
        component: ProfileBookmark,
        name: 'ProfileBookmark'
      },
      {
        path: 'overview',
        component: ProfileOverview,
        name: 'ProfileOverview'
      },
    ]
  },
  {
    path: '/profile/:userId/update',
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
  {
    path: '/posts/:postId/update',
    name: 'PostUpdate',
    component: PostUpdate
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
  {
    path: '/clubs/:clubId/update',
    name: 'ClubUpdate',
    component: ClubUpdate
  },
  {
    path: '/clubs/:clubId/candidates',
    name: 'ClubCandidates',
    component: ClubCandidates
  },
  {
    path: '/clubs/:clubId/clubevents/create',
    name: 'ClubEventCreate',
    component: ClubEventCreate
  },
  {
    path: '/clubs/:clubId/clubevents/:clubEventId',
    name: 'ClubEventDetail',
    component: ClubEventDetail
  },
  {
    path: '/clubs/:clubId/clubevents/:clubEventId/update',
    name: 'ClubEventUpdate',
    component: ClubEventUpdate
  },
  // onedayevents
  {
    path: '/onedayevents',
    name: 'OnedayEventList',
    component: OnedayEventList
  },
  {
    path: '/onedayevents/create',
    name: 'OnedayEventCreate',
    component: OnedayEventCreate
  },
  {
    path: '/onedayevents/:onedayEventId',
    name: 'OnedayEventDetail',
    component: OnedayEventDetail
  },
  {
    path: '/onedayevents/:onedayEventId/update',
    name: 'OnedayEventUpdate',
    component: OnedayEventUpdate
  },
  // Books
  {
    path: '/books/:bookId',
    name: 'BookDetail',
    component: BookDetail
  },
  // Search
  {
    path: '/search/:content',
    name: 'Search',
    component: Search,
    children: [
      {
        path: 'user',
        component: SearchUser,
        name: 'SearchUser'
      },
      {
        path: 'review',
        component: SearchPost,
        name: 'SearchPost'
      },
      {
        path: 'book',
        component: SearchBook,
        name: 'SearchBook'
      },
      {
        path: 'club',
        component: SearchClub,
        name: 'SearchClub'
      },
      {
        path: 'onedayevent',
        component: SearchOneDayEvent,
        name: 'SearchOneDayEvent'
      },
    ]
  },
  // 404 Pages
  {
    path: '*',
    name: 'PageNotFound',
    component: PageNotFound

  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['Login', 'Signup', 'SignupEmail', 'SignupKakao', 'SignupSuccessful', 'PasswordFind', 'PasswordFindEmail', 'PasswordChange', 'PasswordChangeSuccessful'] // Login 안해도 됨
  const authPages = ['Login', 'Signup', 'SignupEmail', 'SignupKakao', 'SignupSuccessful', 'PasswordFind', 'PasswordFindEmail', 'PasswordChange', 'PasswordChangeSuccessful'] // Login 되어있으면 안됨
  // const pubicPages = ['Login', 'Signup'] // Login 안해도 됨
  // const authPages = ['Login', 'Signup'] // Login 되어있으면 안됨
  const authRequired = !publicPages.includes(to.name) // 로그인 해야하는 페이지면 true 반환
  const unauthRequired = authPages.includes(to.name)
  const isLoggedIn = Vue.$cookies.isKey('auth-token')

  if (unauthRequired && isLoggedIn){
    next('/')
  }
  
  if (authRequired && !isLoggedIn) {
    next({ name: 'Login' })
  } else {
    next()
  }
})

export default router
