import SERVER from '@/api/api'

import axios from 'axios'

import router from '@/router'


const accountStore = {
    namespaced: true,
    state: {
    },
    getters: {
        config: state => 
        ({headers: { Authorization: `Token ${state.authToken}`}}),
    },
    mutations: {
        
    },
    actions: {
        postAuthData({ dispatch, commit }, info) {
            axios.post(SERVER.URL + info.location, info.data)
                .then(res => {
                    commit('SET_TOKEN', res.data, { root: true })
                    dispatch('findMyAccount', null, { root: true })
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
        },
        
        findPassword(email) {
            console.log('여기')
            console.log(email)
            axios.post(SERVER.URL + SERVER.ROUTES.password, email)
                .then (res => {
                    console.log(res)
                    console.log(email)
                    router.push({ name: 'PasswordFindEmail'})
                })
                .catch (err =>{
                    console.log(err.response)
                    console.log(email)
                })
        },
        
    },
}

export default accountStore