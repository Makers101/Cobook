import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import accountStore from '@/store/modules/accountStore'
import clubStore from '@/store/modules/clubStore'
import meetupStore from '@/store/modules/meetupStore'
import postStore from '@/store/modules/postStore'
import profileStore from '@/store/modules/profileStore'

import axios from 'axios'
import SERVER from '@/api/api'

export default new Vuex.Store({
  state: {
    genres: null
  },
  getters: {
  },
  mutations: {
    SET_GENRES(state, genres) {
      state.genres = genres
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
      }
  },

  modules: {
    accountStore: accountStore,
    clubStore: clubStore,
    meetupStore: meetupStore,
    postStore: postStore,
    profileStore: profileStore
  }
})
