import axios from 'axios'
import SERVER from '@/api/api'

const profileStore = {
  namespaced: true,
  state: {
    genres: null,
    profile: null,
    feeds: null,
    bookmarks: null,
    clubs: null,
    followingList: null,
    followerlist: null,
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
    },
    SET_FOLLOWINGLIST(state, followingList) {
      state.followingList = followingList
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
    findProfile({ rootGetters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId, rootGetters.config)
        .then(res => {
          commit('SET_PROFILE', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    clickFollow({ rootGetters}, userId){
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.follow, rootGetters.config)
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err.response)
        })
    },
    fetchFollowingList({ rootGetters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.following, rootGetters.config)
      .then(res => {
        commit('SET_FOLLOWINGLIST', res.data)
      })
      .catch(err => {
        console.log(err.response)
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