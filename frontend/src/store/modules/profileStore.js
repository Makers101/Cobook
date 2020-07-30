import axios from 'axios'
import SERVER from '@/api/api'
import router from '@/router'

const profileStore = {
  namespaced: true,
  state: {
    genres: null,
    profile: null,
    feeds: null,
    bookmarks: null,
    clubs: null,
    readings: null,
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
    SET_READINGS(state, readings) {
      state.readings = readings
    },
    SET_FOLLOWINGLIST(state, followingList) {
      state.followingList = followingList
    },
    SET_FOLLOWERLIST(state, followerList) {
      state.followerList = followerList
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
     fetchFollowerList({ rootGetters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.follower, rootGetters.config)
      .then(res => {
        commit('SET_FOLLOWERLIST', res.data)
      })
      .catch(err => {
        console.log(err.response)
      })
     },
    fetchFeeds({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.feed)
      .then(res => {
        commit('SET_FEEDS', res.data)
      })
      .catch(err => {
        console.log(err.response.data)
      })
    },
    fetchBookmarks({ commit }, userId) {
      console.log(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.bookmark)
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.bookmark)
        .then(res => {
          commit('SET_BOOKMARKS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchClubs({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.club)
        .then(res => {
          commit('SET_CLUBS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchReadings({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.reading)
        .then(res => {
          commit('SET_READINGS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    updateProfile({ rootState, rootGetters, dispatch }, profileUpdateData) {
      axios.put(SERVER.URL + SERVER.ROUTES.profile, profileUpdateData.basicData, rootGetters.config )
        .then(res => {
          const userId = res.data.id
          axios.post(SERVER.URL + SERVER.ROUTES.profile + '/images', profileUpdateData.profileImgFormData,
            {
              'jwt': rootState.authToken,
              'Content-Type': 'multipart/form-data'
            })
              .then(() => {
                dispatch('findMyAccount', null, { root: true })
                router.push({ name: 'Profile', params: { userId: userId }})
              })
              .catch(err => {
                console.log(err.response.data)
              })
        })
        .catch(err => {
          console.log(err.response.data)
        })
    }
   },
   
}

export default profileStore