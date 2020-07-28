import axios from 'axios'
import SERVER from '@/api/api'

const postStore = {
  namespaced: true,
  state: {
    posts: null,
    selectedPost: null,
  },
  getters: {
  },
  mutations: {
    SET_POSTS(state, posts) {
      state.posts = posts
    },
    SET_SELECTED_POST(state, post) {
      state.selectedPost = post
    }
  },
  actions: {
    fetchPosts({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts)
        .then(res => {
          commit('SET_POSTS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    }
  },
}

export default postStore