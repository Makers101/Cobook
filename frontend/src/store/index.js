import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import accountStore from '@/store/modules/accountStore'
import clubStore from '@/store/modules/clubStore'
import meetupStore from '@/store/modules/meetupStore'
import postStore from '@/store/modules/postStore'
import profileStore from '@/store/modules/profileStore'

export default new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    accountStore: accountStore,
    clubStore: clubStore,
    meetupStore: meetupStore,
    postStore: postStore,
    profileStore: profileStore
  }
})
