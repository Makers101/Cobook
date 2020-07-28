import axios from 'axios'
import SERVER from '@/api/api'

const postStore = {
  namespaced: true,
  state: {
    posts: null,
    selectedPost: null,
    comments: null,
    tags: null,
  },
  getters: {
  },
  mutations: {
    SET_POSTS(state, posts) {
      state.posts = posts
    },
    SET_SELECTED_POST(state, post) {
      state.selectedPost = post
    },
    SET_COMMENTS(state, comments) {
      state.comments = comments
    },
    SET_TAGS(state, tags) {
      state.tags = tags
    },
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
    },
    findPost({ commit }, postId) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/' + postId)
        .then(res => {
          commit('SET_SELECTED_POST', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchComments({ commit }, postId) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/' + postId + SERVER.ROUTES.comments)
        .then(res => {
          commit('SET_COMMENTS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    createComment({ dispatch, rootGetters }, commentData) {
      axios.post(SERVER.URL + SERVER.ROUTES.posts + '/' + commentData.postId + SERVER.ROUTES.comments, commentData, rootGetters.config)
        .then(() => {
          dispatch('fetchComments', commentData.postId)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchTags({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/tags')
        .then(res => {
          commit('SET_TAGS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    createLike({ rootGetters }, postId) {
      axios.post(SERVER.URL + SERVER.ROUTES.posts + '/' + postId + '/likes', {postId: postId}, rootGetters.config)
        .catch(err => {
          console.log(err.response.data)
        })
    },
    createBookmark({ rootGetters }, postId) {
      axios.post(SERVER.URL + SERVER.ROUTES.posts + '/' + postId + '/bookmarks', {postId: postId}, rootGetters.config)
        .catch(err => {
          console.log(err.response.data)
        })
    },
  },
}

export default postStore