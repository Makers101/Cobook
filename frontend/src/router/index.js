import Vue from 'vue'
import VueRouter from 'vue-router'
import PostList from '@/views/posts/PostList.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'PostList',
    component: PostList
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
