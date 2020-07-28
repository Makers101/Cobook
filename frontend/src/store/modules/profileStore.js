import axios from 'axios'
import SERVER from '@/api/api'

const profileStore = {
  namespaced: true,
  state: {
    genres: null,
    profile: null,
  },
  getters: {
  },
  mutations: {
    SET_GENRES(state, genres) {
      state.genres = genres
    },
    SET_PROFILE(state, profile) {
      state.profile = profile
      console.log(profile)
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
      axios.get(SERVER.URL + SERVER.ROUTES.profile + userId)
        .then(res => {
          console.log(res)
          commit('SET_PROFILE', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    }
  },
}

export default profileStore