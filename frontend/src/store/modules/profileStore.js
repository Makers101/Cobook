import axios from 'axios'
import SERVER from '@/api/api'

const profileStore = {
  namespaced: true,
  state: {
    genres: null,
    profile: null,
    feeds: null,
    bookmarks: null,
    clubs: null
  },
  getters: {
  },
  mutations: {
    SET_GENRES(state, genres) {
      state.genres = genres
    },
    SET_PROFILE(state, profile) {
      state.profile = profile
    },
    SET_FEEDS(state, feeds) {
      state.feeds = feeds
    },
    SET_BOOKMARKS(state, bookmarks) {
      state.bookmarks = bookmarks
    },
    SET_CLUBS(state, clubs) {
      state.clubs = clubs
    }
  },
  actions: {
    fetchGenres({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.genres)
        .then(res => {
          commit('SET_GENRES', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    findProfile({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId)
        .then(res => {
          console.log(res)
          commit('SET_PROFILE', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchFeeds({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + '/' + SERVER.ROUTES.feeds)
      .then(res => {
        console.log(res)
        commit('SET_FEEDS', res.data)
      })
      .catch(err => {
        console.log(err.response.data)
      })
    },
    fetchBookmarks({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + '/' + SERVER.ROUTES.bookmarks)
        .then(res => {
          console.log(res)
          commit('SET_BOOKMARKS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchClubs({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + '/' + SERVER.ROUTES.clubs)
        .then(res => {
          console.log(res)
          commit('SET_CLUBS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
      }
   },
}

export default profileStore