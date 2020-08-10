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
        .then(res => {
          dispatch('fetchFollowerList', userId)
          dispatch('fetchFollowingList', userId)
          console.log(res)
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
            color: 'indigo',
            timed: false
          }
        )
      })

      state.onedayEvents.forEach(onedayEvent => {
        events.push(
          {
            name: onedayEvent.name,
            start: onedayEvent.datetime.slice(0, 16),
            end: '',
            color: 'green',
            timed: false
          }
        )
      })

      state.feeds.forEach(post => {
        events.push(
          {
            name: post.book.title,
            start: post.createdAt.slice(0, 10),
            end: '',
            color: 'orange',
            timed: false
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
                backgroundColor: [
                  '#41B883', '#E46651', '#00D8FF', '#C28535',
                  '#8AAE56', '#B66C46', '#FCD1B5', '#DE9391',
                  '#96759A', '#455B71', '#28343D', '#A4BBC8',
                  '#556F59', '#9B8C6E', '#CED19A', '#2681A3',
                  '#544542', '#735567', '#A4403D', '#CA7C7C'
                ].sort(function(){return 0.5-Math.random()}),
                data: []
              }
            ]
          }
          res.data.genreByPostData.forEach(genre => {
            pieData.labels.push(genre.name)
            pieData.datasets[0].data.push(genre.count)
          });
          commit('SET_PIEDATA', pieData)
          
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
                  router.push({ name: 'Profile', params: { userId: userId }})
                })
                .catch(err => {
                  console.log(err.response.data)
                })
          } else {
            dispatch('findMyAccount', null, { root: true })
            router.push({ name: 'Profile', params: { userId: userId }})
          }
        })
        .catch(err => {
          console.log(err.response.data)
        })
    }
   },
   
}

export default profileStore