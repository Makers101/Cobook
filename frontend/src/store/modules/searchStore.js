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
    events: null,
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
      state.books = posts
    },
    SET_CLUBS(state, clubs) {
      state.books = clubs
    },
    SET_EVENTS(state, events) {
      state.books = events
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
  },
   
}

export default searchStore