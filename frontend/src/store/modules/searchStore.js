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
    fetchUsers({commit, rootGetters}, content) {
      console.log()
      axios.get(SERVER.URL + SERVER.ROUTES.search + SERVER.ROUTES.myaccount + '/' + content, rootGetters.config)
        .then(res => {
          commit('SET_USERS', res.data)
        })
    }
  },
   
}

export default searchStore