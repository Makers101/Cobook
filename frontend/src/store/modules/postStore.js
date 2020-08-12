import axios from 'axios'
import SERVER from '@/api/api'
import router from '@/router'

const postStore = {
  namespaced: true,
  state: {
    postsByFollow: null,
    postsByGenre: null,
    selectedPost: null,
    comments: null,
    tags: null,
  },
  getters: {
  },
  mutations: {
    SET_POSTS_BY_FOLLOW(state, postsByFollow) {
      state.postsByFollow = postsByFollow
    },
    SET_POSTS_BY_GENRE(state, postsByGenre) {
      state.postsByGenre = postsByGenre
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
    fetchPostsByFollow({ commit, rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/follows', rootGetters.config)
        .then(res => {
          commit('SET_POSTS_BY_FOLLOW', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchPostsByGenre({ commit, rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/genres', rootGetters.config)
        .then(res => {
          commit('SET_POSTS_BY_GENRE', res.data)
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
    createPost({ rootGetters }, postCreateData) {
      axios.post(SERVER.URL + SERVER.ROUTES.posts, postCreateData, rootGetters.config)
        .then(res => {
          router.push({ name: 'PostDetail', params: { postId: res.data.id }})
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    updatePost({ rootGetters }, postUpdateData) {
      axios.put(SERVER.URL + SERVER.ROUTES.posts + '/' + postUpdateData.postId, postUpdateData.basicData, rootGetters.config)
        .then(() => {
          router.push({ name: 'PostDetail', params: { postId: postUpdateData.postId }})
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    deletePost({ rootGetters }, postId) {
      console.log(postId)
      axios.delete(SERVER.URL + SERVER.ROUTES.posts + '/' + postId, rootGetters.config)
        .then(() => {
          router.push({ name: 'PostList' })
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
    deleteComment({ dispatch, rootGetters }, commentData) {
      axios.delete(SERVER.URL + SERVER.ROUTES.posts + '/' + commentData.postId + SERVER.ROUTES.comments + '/' + commentData.commentId, rootGetters.config)
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