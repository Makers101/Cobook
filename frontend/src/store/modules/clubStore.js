import axios from 'axios'
import SERVER from '@/api/api'
import WEBEXSERVER from '@/api/webexApi'
import router from '@/router'
import Swal from 'sweetalert2'

const clubStore = {
    namespaced: true,
    state: {
      clubs: null,
      filteredClubs: null,
      selectedClub: null,
      selectedClubEvent: null,
      candidates: null,
      webexToken: { headers: { Authorization : 'Bearer ZDc5NGExMWUtNzgzYy00MGY4LTgyZTktYjhhMzc0Yzc5MWViNmQzMzAwZDItZWUx_P0A1_357e376f-831b-42a4-8c5a-06d11771e9c2'}},
    },
    getters: {
    },
    mutations: {
      SET_CLUBS(state, clubs) {
        state.clubs = clubs
      },
      SET_FILTERED_CLUBS(state, clubs) {
        state.filteredClubs = clubs
      },
      SET_SELECTED_CLUB(state, club) {
        state.selectedClub = club
      },
      SET_SELECTED_CLUBEVENT(state, clubEvent) {
        state.selectedClubEvent = clubEvent
      },
      SET_CANDIDATES(state, candidates) {
        state.candidates = candidates
      },
    },
    actions: {
      fetchClubs({ commit }) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs)
          .then(res => {
            commit('SET_CLUBS', res.data)
            commit('SET_FILTERED_CLUBS', res.data)
          })
          .catch(err => console.log(err.response.data))
      },
      filterClubs({ state }, filters) {
        let new_clubs1 = []
        if (filters.recruit_filter) {
          state.clubs.forEach(club => {
            if (club.recruit) {
              new_clubs1.push(club)
            }
          })
        } else {
          new_clubs1 = state.clubs
        }
        if (filters.genre_filter.size !== 0) {
          let new_clubs2 = new Set()
          new_clubs1.forEach(club => {
            let temp = 0
            club.genres.forEach(genre => {
              if (filters.genre_filter.has(genre.id)) {
                temp = temp + 1
              }
            })
            if (filters.genre_filter.size === temp) {
              new_clubs2.add(club)
            }
          })
          state.filteredClubs = Array.from(new_clubs2)
        } else {
          state.filteredClubs = new_clubs1
        }
      },
      findClub({ commit }, clubId) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubId)
          .then(res => {
            commit('SET_SELECTED_CLUB', res.data)
          })
          .catch(err => console.log(err.response.data))
      },
      findClubEvent({ rootGetters, commit }, params) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/clubevents/' + params.clubEventId, rootGetters.config)
          .then(res => {
            commit('SET_SELECTED_CLUBEVENT', res.data)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      createClub({ rootState, rootGetters, dispatch }, params) {
        axios.post(SERVER.URL + SERVER.ROUTES.clubs, params.basicData, rootGetters.config)
          .then(res => {
            const newClubId = res.data.id
            axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + newClubId + '/images', params.clubImgFormData, 
              {
                headers: {
                  'jwt': rootState.authToken,
                  'Content-Type': 'multipart/form-data'
                }
              })
              .then(() => {
                dispatch('findMyAccount', null, { root: true })
                router.push({ name: 'ClubDetail', params: { clubId: newClubId }})
              })
              .catch(err => {
                console.log(err.response.data)
              })
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      updateClub({ rootState, rootGetters, dispatch }, clubUpdateData) {
        axios.put(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubUpdateData.clubId, clubUpdateData.basicData, rootGetters.config)
          .then(() => {
            console.log(clubUpdateData)
            if (clubUpdateData.clubImgFormData) {
              axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubUpdateData.clubId + '/images', clubUpdateData.clubImgFormData, 
                {
                  headers: {
                    'jwt': rootState.authToken,
                    'Content-Type': 'multipart/form-data'
                  }
                })
                .then(() => {
                  dispatch('findMyAccount', null, { root: true })
                  router.push({ name: 'ClubDetail', params: { clubId: clubUpdateData.clubId }})
                })
                .catch(err => {
                  console.log(err.response.data)
                })
            } else {
              router.push({ name: 'ClubDetail', params: { clubId: clubUpdateData.clubId }})
            }
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      deleteClub({ rootGetters }, clubId) {
        axios.delete(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubId, rootGetters.config)
          .then(() => {
            router.push({ name: 'ClubList' })
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      createClubEvent({ rootGetters }, params) {
        axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/clubevents', params.clubEventCreateData, rootGetters.config)
          .then(res => {
            router.push({ name: 'ClubEventDetail', params: { clubId: params.clubId, clubEventId: res.data.id }})
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      updateRecruit({ rootGetters }, clubId) {
        axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubId + '/recruit', null, rootGetters.config)
          .catch(err => {
            console.log(err.response.data)
          })
      },
      applyClub({ rootGetters, dispatch }, clubId) {
        axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubId + '/apply', null, rootGetters.config)
          .then(() => {
            dispatch('findClub', clubId)
          })
          .catch(err => {
            console.log(err.response.data)
            alert(err.response.data.message)
          })
      },
      fetchCandidates({ rootGetters, commit }, clubId) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubId + '/candidates', rootGetters.config)
          .then(res => {
            commit('SET_CANDIDATES', res.data)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      decideClubApply({ rootGetters, dispatch }, params) {
        axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/apply/' + params.clubMemberId + '/' + params.decision, null, rootGetters.config)
          .then(() => {
            dispatch('fetchCandidates', params.clubId)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      participateClubEvent({ rootGetters, dispatch }, params) {
        axios.post(
          SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/clubevents/' + params.clubEventId + '/apply',
          null,
          rootGetters.config
        )
          .then(() => {
            dispatch('findClubEvent', params)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      secedeClub({ rootGetters, dispatch }, clubId) {
        axios.delete(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubId + '/members', rootGetters.config)
          .then(() => {
            dispatch('findClub', clubId)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      updateClubEvent({ rootGetters, dispatch }, dataContainer) {
        axios.put(
          SERVER.URL + SERVER.ROUTES.clubs + '/' + dataContainer.params.clubId + '/clubevents/' + dataContainer.params.clubEventId,
          dataContainer.clubEventUpdateData,
          rootGetters.config)
          .then(() => {
            dispatch('findClubEvent', dataContainer.params)
            router.push({ name: 'ClubEventDetail', params: { clubId: dataContainer.params.clubId, clubEventId: dataContainer.params.clubEventId }})
          })
          .catch(err => {
            console.log(err.response.data)
          })

      },
      deleteClubEvent({ rootGetters }, params) {
        axios.delete(SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/clubevents/' + params.clubEventId, rootGetters.config)
          .then(() => {
            router.push({ name: 'ClubDetail', params: { clubId: params.clubId } })
          })
          .catch(err => {
            console.log(err.response.data)
          })
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
          "title": webexData.selectedClubEvent.name,
          "agenda": webexData.selectedClubEvent.description,
          "password": "ssafyssafy1",
          "start": webexData.selectedClubEvent.datetime + '+09:00',
          "end": webexData.selectedClubEvent.datetime + '+08:00',
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
          SERVER.URL + SERVER.ROUTES.clubs + '/' + webexData.clubId + '/clubevents/' + webexData.clubEventId + '/url',
          urlData,
          rootGetters.config)
          .then(() => {
            dispatch('findClubEvent', {
              clubId: webexData.clubId,
              clubEventId: webexData.clubEventId,
            })
          })
          .catch(err => {
            console.log(err.response.data)
          })
      }
    }
}

export default clubStore