import axios from 'axios'
import SERVER from '@/api/api'
import router from '@/router'

const clubStore = {
    namespaced: true,
    state: {
      clubs: null,
      filteredClubs: null,
      selectedClub: null,
      selectedReading: null,
      candidates: null,
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
      SET_SELECTED_READING(state, reading) {
        state.selectedReading = reading
      },
      SET_CANDIDATES(state, candidates) {
        state.candidates = candidates
      }
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
          state.filteredClubs = new_clubs2
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
      findReading({ rootGetters, commit }, params) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/readings/' + params.readingId, rootGetters.config)
          .then(res => {
            commit('SET_SELECTED_READING', res.data)
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
      updateClub({ rootGetters }, clubUpdateData) {
        axios.put(SERVER.URL + SERVER.ROUTES.clubs + '/' + clubUpdateData.clubId, clubUpdateData.basicData, rootGetters.config)
          .then(() => {
            router.push({ name: 'ClubDetail', params: { clubId: clubUpdateData.clubId }})
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
      createReading({ rootGetters }, params) {
        axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/readings', params.readingCreateData, rootGetters.config)
          .then(res => {
            router.push({ name: 'ReadingDetail', params: { clubId: params.clubId, readingId: res.data.id }})
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
      participateReading({ rootGetters, dispatch }, params) {
        axios.post(
          SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/readings/' + params.readingId + '/apply',
          null,
          rootGetters.config
        )
          .then(() => {
            dispatch('findReading', params)
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
      }
    }
}

export default clubStore