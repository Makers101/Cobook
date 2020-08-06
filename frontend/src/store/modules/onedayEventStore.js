import axios from 'axios'
import SERVER from '@/api/api'
// import router from '@/router'

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
			// createOnedayEvent({ rootGetters, dispatch }, ) {
			// 	axios.post(
			// 		SERVER.URL + SERVER.URL + SERVER.ROUTES.onedayevents,
			// 		,
			// 		rootGetters.config
			// 		)
			// 		.then()

			// }
		},
}

export default onedayEventStore