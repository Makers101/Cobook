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
      findReading({ commit }, params) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs + '/' + params.clubId + '/readings/' + params.readingId)
          .then(res => {
            commit('SET_SELECTED_READING', res.data)
          })
          .catch(err => {
            console.log(err.response.data)
          })
      },
      createClub({ rootState, rootGetters, dispatch }, clubCreateData) {
        // console.log(rootGetters.config)
        axios.post(SERVER.URL + SERVER.ROUTES.clubs, clubCreateData.basicData, rootGetters.config)
          .then(res => {
            const newClubId = res.data.id
            // console.log(res.data)
            axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + newClubId + '/images', clubCreateData.clubImgFormData, 
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
      createReading({ rootGetters }, dataContainer) {
        axios.post(SERVER.URL + SERVER.ROUTES.clubs + '/' + dataContainer.clubId + '/readings', dataContainer.readingCreateData, rootGetters.config)
          .then(res => {
            router.push({ name: 'ReadingDetail', params: { clubId: dataContainer.clubId, readingId: res.data.id }})
          })
          .catch(err => {
            console.log(err.response.data)
          })
      }
    }
}

export default clubStore