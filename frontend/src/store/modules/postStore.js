import axios from 'axios'
import SERVER from '@/api/api'
import router from '@/router'

const postStore = {
  namespaced: true,
  state: {
    postsByPopularity: null,
    postsByFollow: null,
    postsByGenre: null,
    selectedPost: null,
    comments: null,
    tags: null,
    recommendedUsers: null
  },
  getters: {
  },
  mutations: {
    SET_POSTS_BY_POPULARITY(state, postsByPopularity) {
      state.postsByPopularity = postsByPopularity
    },
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
    SET_RECOMMENDED_USERS(state, recommendedUsers) {
      state.recommendedUsers = recommendedUsers
    }
  },
  actions: {
    fetchPostsByPopularity({ commit, rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/popular', rootGetters.config)
        .then(res => {
          commit('SET_POSTS_BY_POPULARITY', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchPostsByFollow({ commit, rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/follows', rootGetters.config)
        .then(res => {
          if (res.data.length) {
            commit('SET_POSTS_BY_FOLLOW', res.data)
          } else {
            axios.get(SERVER.URL + SERVER.ROUTES.posts + '/recommendusers')
              .then(res => {
                commit('SET_POSTS_BY_FOLLOW', [])
                commit('SET_RECOMMENDED_USERS', res.data)
              })
              .catch(err => {
                console.log(err.response.data)
              })
          }
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchPostsByGenre({ commit, rootGetters }) {
      axios.get(SERVER.URL + SERVER.ROUTES.posts + '/genres', rootGetters.config)
        .then(res => {
          const postsByGenre = []
          res.data.forEach(postSet => {
            if (postSet.posts.length) {
              postsByGenre.push(postSet)
            } else {
              axios.get(SERVER.URL + SERVER.ROUTES.posts + '/genres/' + postSet.genreId)
                .then(res => {
                  postSet['books'] = res.data
                  postsByGenre.push(postSet)
                })
                .catch(err => {
                  console.log(err.response.data)
                })
            }       
          });
          commit('SET_POSTS_BY_GENRE', postsByGenre)
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