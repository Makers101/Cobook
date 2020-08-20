import SERVER from '@/api/api'
import axios from 'axios'
import router from '@/router'
import Swal from 'sweetalert2'

// import cookies from 'vue-cookies'

const accountStore = {
  namespaced: true,
  state: {
  },
  getters: {
    config: state =>
      ({ headers: { Authorization: `Token ${state.authToken}` } }),
  },
  mutations: {

  },
  actions: {
    // Signup
    postAuthData1({ commit } , info) {
      let timerInterval
        Swal.fire({
          title: '이메일을 보내는 중입니다.',
          html: '조금만 기다려주세요',
          timer: 4000,
          timerProgressBar: true,
          onBeforeOpen: () => {
            Swal.showLoading()
            timerInterval = setInterval(() => {
              const content = Swal.getContent()
              if (content) {
                const b = content.querySelector('b')
                if (b) {
                  b.textContent = Swal.getTimerLeft()
                }
              }
            }, 100)
          },
          onClose: () => {
            clearInterval(timerInterval)
          }
        })
      axios.post(SERVER.URL + info.location, info.data)
        .then(() => {
          // console.log("SUCCESS", res.data)
          console.log(commit)
          router.push({ name: 'SignupEmail', params: {signupEmail: info.data} })

        })
        .catch(err => {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: false,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
              }
           })
           Toast.fire({
            icon: 'error',
            title: err.response.data.message
          })
        })
    },
    // Login
    postAuthData2({ commit }, info) {
      axios.post(SERVER.URL + info.location, info.data)
        .then(res => {
          commit('SET_TOKEN', res.data, { root: true })
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
              }
           })
           Toast.fire({
            icon: 'success',
            title: "로그인에 성공하였습니다."
          })

          router.push('/')
        })
        .catch(err => {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: false,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
              }
           })
           Toast.fire({
            icon: 'error',
            title: err.response.data.message
          })
        })
    },
    // 비밀번호
    sendPasswordEmail(info) {
      axios.post(SERVER.URL + info.location, info.data)
        .then(() => {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
              }
           })
           Toast.fire({
            icon: 'success',
            title: "메일을 발송했습니다."
          })
          router.push('/')
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    // changePassword(info) {
    //   axios.post(SERVER.URL + info.location, info.data, {
    //     headers: {
    //       'Content-Type': 'application/json',
    //       'jwt': this.$route.query.jwt,
    //       // 'jwt': rootState.route.query.jwt
    //     }
    //   })
    //     .then(() => {
    //       router.push({ name: 'PasswordChangeSuccessful' })
    //     })
    //     .catch(err => {
    //       console.log(err.response)
    //     })
    // },
    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        location: SERVER.ROUTES.signup,
        to: '/signup/email'
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
    // clickChangePassword({ dispatch }, passwordChangeData) {
    //   const info = {
    //     data: passwordChangeData,
    //     location: SERVER.ROUTES.changepassword,
    //   }
    //   dispatch('changePassword', info)
    // },
    // findPassword({ dispatch }, passwordFindData) {
    //     const info = {
    //         data: passwordFindData,
    //         location: SERVER.ROUTES.password,
    //     }
    //     dispatch('sendPasswordEmail', info)
    // },
    findPassword(email) {
      const info = {
        data: email,
      }
      axios.post(SERVER.URL + SERVER.ROUTES.password, info)
        .then (() => {
          router.push({ name: 'PasswordFindEmail'})
        })
        .catch (err =>{
          console.log(err.response)
        })
    },
    // 이메일 재전송
    resendSignupEmail({ commit }, data) {
      let timerInterval
        Swal.fire({
          title: '이메일을 보내는 중입니다.',
          html: '조금만 기다려주세요',
          timer: 4000,
          timerProgressBar: true,
          onBeforeOpen: () => {
            Swal.showLoading()
            timerInterval = setInterval(() => {
              const content = Swal.getContent()
              if (content) {
                const b = content.querySelector('b')
                if (b) {
                  b.textContent = Swal.getTimerLeft()
                }
              }
            }, 100)
          },
          onClose: () => {
            clearInterval(timerInterval)
          }
        })
      const resendData = {
        email: data.data.email,
        type: data.type
      }
      console.log(resendData)
      axios.post(SERVER.URL + SERVER.ROUTES.myaccount + '/resend', resendData)
        .then (() => {
          console.log(commit)
          router.push({ name: 'SignupEmail', params: {signupEmail: data.data} })
        })
        .catch(err => {
          console.log(err)
        })
    },
    resendPasswordEmail({ commit }, data) {
      let timerInterval
        Swal.fire({
          title: '이메일을 보내는 중입니다.',
          html: '조금만 기다려주세요',
          timer: 4000,
          timerProgressBar: true,
          onBeforeOpen: () => {
            Swal.showLoading()
            timerInterval = setInterval(() => {
              const content = Swal.getContent()
              if (content) {
                const b = content.querySelector('b')
                if (b) {
                  b.textContent = Swal.getTimerLeft()
                }
              }
            }, 100)
          },
          onClose: () => {
            clearInterval(timerInterval)
          }
        })
      const resendData = {
        email: data.data.email,
        type: data.type
      }
      console.log(resendData)
      axios.post(SERVER.URL + SERVER.ROUTES.myaccount + '/resend', resendData)
        .then (() => {
          console.log(commit)
          router.push({ name: 'PasswordFindEmail', params: {passwordFindData: data.data} })
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
}

export default accountStore