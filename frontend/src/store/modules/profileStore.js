import axios from 'axios'
import SERVER from '@/api/api'
import router from '@/router'

const profileStore = {
  namespaced: true,
  state: {
    genres: null,
    profile: null,
    feeds: null,
    bookmarks: null,
    clubs: null,
    clubEvents: null,
    onedayEvents: null,
    overview: null,
    followingList: null,
    followerList: null,
    pieData: null,
    barData: null,
    events: null,
  },
  getters: {
  },
  mutations: {
    SET_GENRES(state, genres) {
      state.genres = genres
    },
    SET_PROFILE(state, profile) {
      state.profile = profile
    },
    SET_FEEDS(state, feeds) {
      state.feeds = feeds
    },
    SET_BOOKMARKS(state, bookmarks) {
      state.bookmarks = bookmarks
    },
    SET_CLUBS(state, clubs) {
      state.clubs = clubs
    },
    SET_CLUB_EVENTS(state, clubEvents) {
      state.clubEvents = clubEvents
    },
    SET_ONEDAY_EVENTS(state, onedayEvents) {
      state.onedayEvents = onedayEvents
    },
    SET_OVERVIEW(state, overview) {
      state.overview = overview
    },
    SET_FOLLOWINGLIST(state, followingList) {
      state.followingList = followingList
    },
    SET_FOLLOWERLIST(state, followerList) {
      state.followerList = followerList
    },
    SET_PIEDATA(state, pieData) {
      state.pieData = pieData
    },
    SET_BARDATA(state, barData) {
      state.barData = barData
    },
    SET_EVENTS(state, events) {
      state.events = events
    }
  },
  actions: {
    fetchGenres({ commit }) {
      axios.get(SERVER.URL + SERVER.ROUTES.genres)
        .then(res => {
          commit('SET_GENRES', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    findProfile({ rootGetters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId, rootGetters.config)
        .then(res => {
          commit('SET_PROFILE', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    clickFollow({ dispatch, rootGetters}, userId){
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.follow, rootGetters.config)
        .then(() => {
          // dispatch('fetchFollowerList', userId)
          // dispatch('fetchFollowingList', userId)
          dispatch('findProfile', userId)
        })
        .catch(err => {
          console.log(err.response)
        })
    },
    clickFollowModal({ rootGetters}, userId){
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.follow, rootGetters.config)
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err.response)
        })
    },
    fetchFollowingList({ rootGetters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.following, rootGetters.config)
      .then(res => {
        commit('SET_FOLLOWINGLIST', res.data)
      })
      .catch(err => {
        console.log(err.response)
      })
     },
     fetchFollowerList({ rootGetters, commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.follower, rootGetters.config)
      .then(res => {
        commit('SET_FOLLOWERLIST', res.data)
      })
      .catch(err => {
        console.log(err.response)
      })
     },
    fetchFeeds({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.feed)
      .then(res => {
        commit('SET_FEEDS', res.data)
      })
      .catch(err => {
        console.log(err.response.data)
      })
    },
    fetchBookmarks({ commit }, userId) {
      console.log(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.bookmark)
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.bookmark)
        .then(res => {
          commit('SET_BOOKMARKS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchClubs({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + SERVER.ROUTES.club)
        .then(res => {
          commit('SET_CLUBS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchClubEvents({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + '/clubevents')
        .then(res => {
          commit('SET_CLUB_EVENTS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    fetchOnedayEvents({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + '/onedayevents')
        .then(res => {
          commit('SET_ONEDAY_EVENTS', res.data)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    collectEvents({ state, commit }) {
      const events = []

      state.clubEvents.forEach(clubEvent => {
        events.push(
          {
            name: clubEvent.name,
            start: clubEvent.datetime.slice(0, 16),
            end: '',
            color: '#b484bf',
            timed: false,
            clubEvent: clubEvent
          }
        )
      })

      state.onedayEvents.forEach(onedayEvent => {
        events.push(
          {
            name: onedayEvent.name,
            start: onedayEvent.datetime.slice(0, 16),
            end: '',
            color: '#7393C2',
            timed: false,
            onedayEvent: onedayEvent
          }
        )
      })

      state.feeds.forEach(post => {
        events.push(
          {
            name: post.book.title,
            start: post.createdAt.slice(0, 10),
            end: '',
            color: '#74a892',
            timed: false,
            post: post
          }
        )
      });

      commit('SET_EVENTS', events)
    },
    findOverview({ commit }, userId) {
      axios.get(SERVER.URL + SERVER.ROUTES.profile + '/' + userId + '/overview')
        .then(res => {
          commit('SET_OVERVIEW', res.data)
          const pieData = {
            hoverBackgroundColor: "red",
            hoverBorderWidth: 10,
            labels: [],
            datasets: [
              {
                label: "장르별 독서량",
                // backgroundColor: [
                //   '#4B5916',
                //   '#69724C',
                //   '#7C3D09',
                //   '#845225',
                //   '#14312A',
                // ],
                // backgroundColor: [
                //   '#BF5B04',
                //   '#CD7E59',
                //   '#457373',
                //   '#DDB247',
                //   '#D9C179'
                // ],
                // backgroundColor: [
                //   '#4B5916',
                //   '#8D9365',
                //   '#A87543',
                //   '#A86326',
                //   '#73303C'
                // ],
                // backgroundColor: [
                //   '#281B24',
                //   '#3C4B4D',
                //   '#F2AB26',
                //   '#D87D0D',
                //   '#890A03'
                // ],
                // backgroundColor: [
                //   '#174029',
                //   '#3D7345',
                //   '#A68F7B',
                //   '#A86326',
                //   '#593622'
                // ],
                // backgroundColor: [
                //   '#574301',
                //   '#A86326',
                //   '#8D9365',
                //   '#D9B29C',
                //   '#8C0303'
                // ],
                // backgroundColor: [
                //   '#027368',
                //   '#034001',
                //   '#0D0D0D',
                //   '#988148',
                //   '#D9A282'
                // ],
                // backgroundColor: [
                //   '#02323A',
                //   '#7F7601',
                //   '#D9B29C',
                //   '#A66249',
                //   '#5E0202'
                // ],
                // backgroundColor: [
                //   '#2F3D30',
                //   '#8C826C',
                //   '#57605C',
                //   '#404654',
                //   '#34273B'
                // ],
                backgroundColor: [
                  '#CB997E',
                  '#DDBEA9',
                  '#FFDEC5',
                  '#A5A58D',
                  '#D7D5CB',
                  '#F0EFEB',
                ],
                data: []
              }
            ]
          }
          if (res.data.genreByPostData.length > 5) {
            for (let i = 0; i < 5; i++) {
              // const genre = res.data.genreByPostData[i]
              pieData.labels.push(res.data.genreByPostData[i].name)
              pieData.datasets[0].data.push(res.data.genreByPostData[i].count)
            }
            
            let etcCount = 0
            for (let i = 5; i < res.data.genreByPostData.length; i++) {
              etcCount += res.data.genreByPostData[i].count
            }
            pieData.labels.push('기타')
            pieData.datasets[0].data.push(etcCount)
            commit('SET_PIEDATA', pieData)

          } else {
            res.data.genreByPostData.forEach(genre => {
              pieData.labels.push(genre.name)
              pieData.datasets[0].data.push(genre.count)
            });
            commit('SET_PIEDATA', pieData)
          }
          
          const barData = {
            labels: [],
            datasets: [
              {
                xAxisID: 0,
                yAxisID: 0,
                label: '월별 독서량',
                backgroundColor: '#88A498',
                data: []
              }
            ]
          }
          res.data.monthByPostData.forEach(month => {
            barData.labels.push(month.period.slice(5, ) + '월')
            barData.datasets[0].data.push(month.count)
          })
          commit('SET_BARDATA', barData)
        })
        .catch(err => {
          console.log(err.response.data)
        })
    },
    updateProfile({ rootState, rootGetters, dispatch }, profileUpdateData) {
      axios.put(SERVER.URL + SERVER.ROUTES.profile, profileUpdateData.basicData, rootGetters.config )
        .then(res => {
          const userId = res.data.userId
          if (profileUpdateData.profileImgFormData) {
            axios.post(SERVER.URL + SERVER.ROUTES.profile + '/images', profileUpdateData.profileImgFormData,
              {  
                headers: {
                  'jwt': rootState.authToken,
                  'Content-Type': 'multipart/form-data'
                }
              })
                .then(() => {
                  dispatch('findMyAccount', null, { root: true })
                  if (router.currentRoute.name === 'ProfileUpdate') {
                    router.push({ name: 'PostList'})
                  } else {
                    router.push({ name: 'Profile', params: { userId: userId }})
                  }
                })
                .catch(err => {
                  console.log(err.response.data)
                })
          } else {
            dispatch('findMyAccount', null, { root: true })
            if (router.currentRoute.name === 'PostList') {
              dispatch('postStore/fetchPostsByGenre', null, { root: true } )
              router.push({ name: 'PostList'})
            } else {
            router.push({ name: 'Profile', params: { userId: userId }})
            }
          }
        })
        .catch(err => {
          console.log(err.response.data)
        })
    }
   },
   
}

export default profileStore