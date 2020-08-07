import axios from 'axios'
import SERVER from '@/api/api'
import router from '@/router'

const onedayEventStore = {
    namespaced: true,
    state: {
      onedayEvents: null,
      filteredOnedayEvents: null,
      selectedOnedayEvent: null
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
        console.log(dataContainer)
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
            if (onedayEvent.participantCnt < (onedayEvent.capacity + 1)) {
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
          state.filteredOnedayEvents = new_onedayEvents2
        } else {
          state.filteredOnedayEvents = new_onedayEvents1
        }
      }
    },
}

export default onedayEventStore