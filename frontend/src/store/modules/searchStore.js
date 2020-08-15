import axios from 'axios'
import SERVER from '@/api/api'
// import router from '@/router'


const searchStore = {
  namespaced: true,
  state: {
    users: null,
    books: null,
    posts: null,
    clubs: null,
    onedayevents: null,
  },
  getters: {
  },
  mutations: {
    SET_USERS(state, users) {
      state.users = users
    },
    SET_BOOKS(state, books) {
      state.books = books
    },
    SET_POSTS(state, posts) {
      state.posts = posts
    },
    SET_CLUBS(state, clubs) {
      state.clubs = clubs
    },
    SET_ONEDAYEVENTS(state, onedayevents) {
      state.onedayevents = onedayevents
    },

  },
  actions: {
    fetchUsers({commit, rootGetters}, content) {
      axios.get(SERVER.URL + SERVER.ROUTES.search + SERVER.ROUTES.myaccount + '/' + content, rootGetters.config)
        .then(res => {
          commit('SET_USERS', res.data)
        })
    },
    fetchBooks({commit}, content) {
      axios.get(SERVER.URL + SERVER.ROUTES.search + SERVER.ROUTES.books + '/' + content)
        .then(res => {
          commit('SET_BOOKS', res.data)
        })
        .catch(err => {
          console.log(err.data)
        })
    },
    fetchPosts({commit}, content) {
      axios.get(SERVER.URL + SERVER.ROUTES.search + SERVER.ROUTES.posts + '/' + content)
        .then(res => {
          commit('SET_POSTS', res.data)
        })
        .catch(err => {
          console.log(err.data)
        })
    },
    fetchClubs({commit}, content) {
      axios.get(SERVER.URL + SERVER.ROUTES.search + SERVER.ROUTES.clubs + '/' + content)
        .then(res => {
          commit('SET_CLUBS', res.data)
        })
        .catch(err => {
          console.log(err.data)
        })
    },
    fetchOnedayevents({commit}, content) {
      axios.get(SERVER.URL + SERVER.ROUTES.search + SERVER.ROUTES.onedayevents + '/' + content)
        .then(res => {
          commit('SET_ONEDAYEVENTS', res.data)
        })
        .catch(err => {
          console.log(err.data)
        })
    }
  },
}

export default searchStore