import axios from 'axios'
import SERVER from '@/api/api'
// import router from '@/router'


const searchStore = {
  namespaced: true,
  state: {
    users: null,
  },
  getters: {
  },
  mutations: {
    SET_USERS(state, users){
      state.users = users
    }
  },
  actions: {
    fetchUserList({commit, rootGetters}, content) {
      axios.get(SERVER.URL + SERVER.ROUTES.search + '/' + content + SERVER.ROUTES, rootGetters.config)
        .then(res => {
          commit('SET_USERS', res.data)
        })
    }
  },
   
}

export default searchStore