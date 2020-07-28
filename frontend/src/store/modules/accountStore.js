import SERVER from '@/api/api'

import cookies from 'vue-cookies'
import axios from 'axios'

import router from '@/router'


const accountStore = {
    namespaced: true,
    state: {
        authToken: null,
    },
    getters: {
    },
    mutations: {
        SET_TOKEN(state, token) {
            state.authToken = token
            cookies.set('auth-token', token)
        }
    },
    actions: {
        postAuthData({ commit }, info) {
            axios.post(SERVER.URL + info.location, info.data)
                .then(res => {
                    commit('SET_TOKEN', res.data)
                    // dispatch('getMyAccount')
                    router.push({ name: 'SignupEmail' })
                })
                .catch(err => {
                    console.log(err.response.data)
                })
        },
        signup({ dispatch }, signupData) {
            const info = {
                data: signupData,
                location: SERVER.ROUTES.signup,
                to: '/password/email'
            }
            dispatch('postAuthData', info)
        },
        login({ dispatch }, loginData) {
            const info = {
                data: loginData,
                location: SERVER.ROUTES.login,
                to: '/'
            }
            dispatch('postAuthData', info)
        }
    },
}

export default accountStore