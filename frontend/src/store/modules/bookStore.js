import axios from 'axios'
import SERVER from '@/api/api'
// import router from '@/router'

const bookStore = {
    namespaced : true,
    state: {
      selectedBook: null,
    },
    getters: {

    },
    mutations: {
      SET_SELECTED_BOOK(state, book) {
          state.selectedBook = book
        },
    },
    actions: {
      findBook({ commit }, bookId) {
        if (bookId === 0) {
          commit('SET_SELECTED_BOOK', null)
        } else
          axios.get(SERVER.URL + SERVER.ROUTES.books + '/' + bookId)
            .then(res => {
              commit('SET_SELECTED_BOOK', res.data)
            })
            .catch(err => {
              console.log(err.response.data)
            })
        },
      
    },
}

export default bookStore