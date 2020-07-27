import axios from 'axios'
import SERVER from '@/api/api'

const readingSample = {
  id: 1,
  name: "string",
  participantCnt: 1,
  place: "string",
  closed: null,
  dateTime: "2020-07-24T17:21:41",
  description: "이번 리딩은 이렇게 진행할 것입니다. 벌금은 얼마입니다.",
  book: {
    bookId: 57,
    bookTitle: "내 강아지 마음 상담소",
    bookImage: "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5116719%3Ftimestamp%3D20200723135844"
  },
  members: [
    {
      id: 0,
      profileImg: null,
      userName: "string"
    }
  ],
  memberPosts: [
    {
      bookmarkUsers: [
        0
      ],
      createdAt: "2020-07-26T13:38:31.114Z",
      id: 0,
      likeUsers: [
        0
      ],
      onelineReview: "한 줄 평은 이렇다 호호호호호호호",
      open: true,
      rank: 0,
      updatedAt: "2020-07-26T13:38:31.114Z",

      author: {
        id: 0,
        profileImg: null,
        userName: "string"
      }
    }
  ],
  questions: [
    {
      id: 0,
      question: "책을 읽고 난 후의 전반적인 감상은?"
    },
    {
      id: 1,
      question: "강아지 캐릭터는 왜 귀여운가?"
    }
  ]
}

const clubStore = {
    namespaced: true,
    state: {
        clubs: null,
        selectedClub: null,
        selectedReading: null,
        userGenres: [
          {
            id: 700,
            name: '언어'
          },
          {
            id: 810,
            name: '한국문학'
          },
          {
            id: 600,
            name: '예술'
          }
        ]
    },
    getters: {
    },
    mutations: {
      SET_CLUBS(state, clubs) {
        state.clubs = clubs
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
          })
          .catch(err => console.log(err.response.data))
      },
      findClub({ commit }, clubId) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs + clubId)
          .then(res => {
            commit('SET_SELECTED_CLUB', res.data)
          })
          .catch(err => console.log(err.response.data))
      },
      findReading({ commit }, params) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs + params.clubId + '/readings/' + params.readingId)
          .then(res => {
            commit('SET_SELECTED_READING', res.data)
          })
          .catch(err => {
            console.log(err.response.data)
            commit('SET_SELECTED_READING', readingSample)
          })
      }
    },
}

export default clubStore