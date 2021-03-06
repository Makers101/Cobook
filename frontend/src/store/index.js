import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import accountStore from '@/store/modules/accountStore'
import bookStore from '@/store/modules/bookStore'
import clubStore from '@/store/modules/clubStore'
import onedayEventStore from '@/store/modules/onedayEventStore'
import postStore from '@/store/modules/postStore'
import profileStore from '@/store/modules/profileStore'
import searchStore from '@/store/modules/searchStore'

import router from '@/router'
import axios from 'axios'
import cookies from 'vue-cookies'
import SERVER from '@/api/api'
import Swal from 'sweetalert2'

export default new Vuex.Store({
  state: {
    authToken: cookies.get('auth-token'),
    genres: null,
    myaccount: null,
    books: null,
    users: null,
    notis: null,
  },
  getters: {
    config: state => ({ headers: { jwt : state.authToken}}),
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token
      cookies.set('auth-token', token)
    },
    SET_GENRES(state, genres) {
      state.genres = genres
    },
    SET_MY_ACCOUNT(state, user) {
      state.myaccount = user
    },
    SET_BOOKS(state, books) {
      state.books = books
    },
    SET_USERS(state, users) {
      state.users = users
    },
    SET_NOTIS(state, notis) {
      state.notis = notis
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
    findMyAccount({ rootGetters, commit}) {
      axios.post(SERVER.URL + SERVER.ROUTES.myaccount, null, rootGetters.config)
        .then(res => {
            commit('SET_MY_ACCOUNT', res.data)
        })
        .catch(err => console.log(err.response.data))
    },
    fetchBooks({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.books)
        .then(res => {
          commit('SET_BOOKS', res.data)
        })
        .catch(err => console.log(err.response.data))
    },
    fetchUsers({ commit }) {
      axios.get(SERVER.URL + '/users/')
        .then(res => {
          commit('SET_USERS', res.data)
        })
        .catch(err => console.log(err.response.data))
    },
    createNoti({ getters }, notiData) {
      axios.post(SERVER.URL + SERVER.ROUTES.noti, notiData, getters.config)
        .catch(err => console.log(err.response.data))
    },
    deleteNoti({ getters }, notiId) {
      axios.delete(SERVER.URL + SERVER.ROUTES.noti + '/' + notiId, getters.config)
        .catch(err => console.log(err.response.data))
    },
    logout({ commit }) {
          commit('SET_TOKEN', null)
          cookies.remove('auth-token')
          // commit('SET_INIT')
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
              }
           })
           Toast.fire({
            icon: 'success',
            title: '로그아웃되었습니다.'
          })
          router.push({ name: 'Login' })
    },
  },

  modules: {
    accountStore: accountStore,
    bookStore: bookStore,
    clubStore: clubStore,
    onedayEventStore: onedayEventStore,
    postStore: postStore,
    profileStore: profileStore,
    searchStore: searchStore,
  }
})