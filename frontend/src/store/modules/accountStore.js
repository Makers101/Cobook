import SERVER from '@/api/api'
import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

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
        // Signup
        postAuthData1( info) {
            axios.post(SERVER.URL + info.location, info.data)
                .then(res => {
                    console.log("SUCCESS", res.data)
                    // commit('SET_TOKEN', res.data, { root: true })
                    router.push({ name: 'SignupEmail' })
                    
                })
                .catch(err => {
                    console.log(err.response.data)
                })
        },
        // Login
        postAuthData2({ commit }, info) {
            axios.post(SERVER.URL + info.location, info.data)
                .then(res => {
                    console.log("SUCCESS")
                    commit('SET_TOKEN', res.data, { root: true })
                    router.push('/')
                })
                .catch(err => {
                    console.log(err.response.data)
                })
        },
        sendPasswordEmail(info){
            console.log(info)
            axios.post(SERVER.URL + info.location, info.data)
                .then(() => {
                    console.log("SUCCESS")
                    router.push('/')
                })
                .catch(err => {
                    console.log(err.response.data)
                })
        },
        changePassword(info) {
            console.log(info.data)
            axios.post(SERVER.URL + info.location, info.data, {
                headers: { 
                    'Content-Type' : 'application/json',
                    'jwt' : this.$route.query.jwt,
                }
            })
                .then (() => {
                    router.push({ name: 'PasswordChangeSuccessful'})
                })
                .catch (err => {
                    console.log(err.response)
                })
        },
        signup({ dispatch }, signupData) {
            const info = {
                data: signupData,
                location: SERVER.ROUTES.signup,
                to: '/password/email'
            }
            dispatch('postAuthData1', info)
        },
        login({ dispatch }, loginData) {
            const info = {
                data: loginData,
                location: SERVER.ROUTES.login,
                to: '/'
            }
            dispatch('postAuthData2', info)
        },
        clickChangePassword({ dispatch }, passwordChangeData) {
            const info = {
                data: passwordChangeData,
                location: SERVER.ROUTES.changepassword,
            }
            dispatch('changePassword', info)
        }
        // findPassword({ dispatch }, passwordFindData) {
        //     const info = {
        //         data: passwordFindData,
        //         location: SERVER.ROUTES.password,
        //     }
        //     dispatch('sendPasswordEmail', info)
        // }
        
        // findPassword(email) {
        //     const info = {
        //         data: email,
        //     }
        //     axios.post(SERVER.URL + SERVER.ROUTES.password, info)
        //         .then (res => {
        //             console.log(res.data)
        //             router.push({ name: 'PasswordFindEmail'})
        //         })
        //         .catch (err =>{
        //             console.log(err.response)
        //         })
        // },
        
    },
}

export default accountStore