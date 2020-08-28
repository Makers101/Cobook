import axios from 'axios'
import SERVER from '@/api/api'
import WEBEXSERVER from '@/api/webexApi'
import router from '@/router'
import Swal from 'sweetalert2'

const onedayEventStore = {
    namespaced: true,
    state: {
      onedayEvents: null,
      filteredOnedayEvents: null,
      selectedOnedayEvent: null,
      webexToken: { headers: { Authorization : 'Bearer ZTE2NmI0MTUtOTZhNy00YjE1LWIwMjUtY2EwMDNkODc1MTFjNjZhZTNmYzctZThm_P0A1_357e376f-831b-42a4-8c5a-06d11771e9c2'}},
    },
    getters: {
    },
    mutations: {
			SET_ONEDAYEVENTS(state, onedayEvents) {
        state.onedayEvents = onedayEvents
			},
			SET_FILTERED_ONEDAYEVENTS(state, onedayEvents) {
        state.filteredOnedayEvents = onedayEvents
			},
			SET_SELECTED_ONEDAYEVENT(state, onedayEvents) {
        state.selectedOnedayEvent = onedayEvents
      }
    },
    actions: {
			fetchOnedayEvents({ commit }) {
        axios.get(SERVER.URL + SERVER.ROUTES.onedayevents)
          .then(res => {
            commit('SET_ONEDAYEVENTS', res.data)
            commit('SET_FILTERED_ONEDAYEVENTS', res.data)
          })
          .catch(err => console.log(err.response.data))
      },
      findOnedayEvent({ rootGetters, commit }, onedayEventId) {
        axios.get(SERVER.URL + SERVER.ROUTES.onedayevents + '/' + onedayEventId, rootGetters.config)
          .then(res => {
            commit('SET_SELECTED_ONEDAYEVENT', res.data)
          })
          .catch(err => console.log(err.response.data))
      },
			createOnedayEvent({ rootGetters }, onedayEventCreateData) {
        axios.post(SERVER.URL + SERVER.ROUTES.onedayevents, onedayEventCreateData, rootGetters.config)
          .then(res => {
            router.push({ name: 'OnedayEventDetail', params: { onedayEventId: res.data.oneDayEventId }})
          })
          .catch(err => console.log(err.response.data))
      },
      updateOnedayEvent({ rootGetters, dispatch }, dataContainer) {
        axios.put(
          SERVER.URL + SERVER.ROUTES.onedayevents + '/' + dataContainer.onedayEventId,
          dataContainer.onedayEventUpdateData,
          rootGetters.config
        )
          .then(() => {
            dispatch('findOndayEvent', dataContainer.oneDayEventId)
            router.push({ name: 'OnedayEventDetail', params: { oneDayEventId: dataContainer.oneDayEventId }})
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      deleteOnedayEvent({ rootGetters }, onedayEventId) {
        axios.delete(SERVER.URL + SERVER.ROUTES.onedayevents + '/' + onedayEventId, rootGetters.config)
          .then(() => {
            router.push({ name: 'OnedayEventList' })
          })
          .catch(err => console.log(err.response.data))
      },
      participateOnedayEvent({ rootGetters, dispatch }, onedayEventId) {
        axios.post(
          SERVER.URL + SERVER.ROUTES.onedayevents + '/' + onedayEventId + '/apply',
          null,
          rootGetters.config
        )
          .then(() => {
            dispatch('findOnedayEvent', onedayEventId)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      filterOnedayEvents({ state }, filters) {
        let new_onedayEvents1 = []
        if (filters.recruit_filter) {
          state.onedayEvents.forEach(onedayEvent => {
            if (onedayEvent.participantCnt < (onedayEvent.capacity)) {
              new_onedayEvents1.push(onedayEvent)
            }
          })
        } else {
          new_onedayEvents1 = state.onedayEvents
        }

        if (filters.genre_filter) {
          let new_onedayEvents2 = new Set()
          new_onedayEvents1.forEach(onedayEvent => {
            if (filters.genre_filter === onedayEvent.book.genre) {
              new_onedayEvents2.add(onedayEvent)
            }
          })
          state.filteredOnedayEvents = Array.from(new_onedayEvents2)
        } else {
          state.filteredOnedayEvents = new_onedayEvents1
        }
      },
      
      checkPeople({ state, dispatch }, webexData) {
        axios.get(WEBEXSERVER.URL + WEBEXSERVER.ROUTES.createPeople + '?email=' + webexData.emails[0], state.webexToken)
          .then((res) => {
            if (!res.data.items.length) {
              dispatch('createPeople', webexData)
            } else {
              dispatch('createRoom', webexData)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },
      createPeople({ state }, webexData) {
        let timerInterval
        Swal.fire({
          title: '이메일을 보내는 중입니다.',
          html: '조금만 기다려주세요',
          timer: 8000,
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
        axios.post(WEBEXSERVER.URL + WEBEXSERVER.ROUTES.createPeople, webexData, state.webexToken)
          .then(() => {
            alert('이메일에서 승인 절차를 진행해주세요.')
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      createRoom({ state, dispatch }, webexData) {
        let timerInterval
        Swal.fire({
          title: '방을 생성중입니다.',
          html: '조금만 기다려주세요',
          timer: 3000,
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
        let roomData = {
          "title": webexData.selectedOnedayEvent.name,
          "agenda": webexData.selectedOnedayEvent.description,
          "password": "ssafyssafy1",
          "start": webexData.selectedOnedayEvent.datetime + '+09:00',
          "end": webexData.selectedOnedayEvent.datetime + '+08:00',
          "enabledAutoRecordMeeting": false,
          "allowAnyUserToBeCoHost": true,
          "invitees": [
            {
              "email": webexData.emails[0],
              "displayName": webexData.displayName,
              "coHost": true
            }
          ]
        }
        axios.post(WEBEXSERVER.URL + WEBEXSERVER.ROUTES.createMeetings, roomData, state.webexToken)
          .then((res) => {
            webexData.url = res.data.webLink
            dispatch('createRoomUrl', webexData)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      createRoomUrl({ dispatch, rootGetters }, webexData) {
        let urlData = {
          url: webexData.url
        }
        axios.put(
          SERVER.URL + SERVER.ROUTES.onedayevents + '/' + webexData.selectedOnedayEvent.id + '/url',
          urlData,
          rootGetters.config)
          .then(() => {
            dispatch('findOnedayEvent', webexData.selectedOnedayEvent.id)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      }
    },
}

export default onedayEventStore