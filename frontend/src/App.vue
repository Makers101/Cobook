<template>
  <div>
    <div id="app">
      <div id="nav">
        <nav class="navbar navbar-expand-md navbar-light navbar-bg-color">
          <router-link class="navbar-brand" to="/">
            <img 
              class="img-fluid logo-img" 
              src="https://user-images.githubusercontent.com/57381062/88909311-e8728200-d295-11ea-92d4-0a4a805f9afa.png" 
              alt="로고 이미지"
            ><span class="ml-2 logo-text">Co-Book</span>
          </router-link>
          <button 
            class="navbar-toggler" 
            type="button" 
            data-toggle="collapse" 
            data-target="#navbarSupportedContent" 
            aria-controls="navbarSupportedContent" 
            aria-expanded="false" 
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div class="autocomplete" id="search-bar">
              <input 
                type="search" 
                v-model="keyword" 
                @input="searchUser"
                @blur="focusout"
                >
              <ul 
                class="autocomplete-results px-3"
                v-show="searchedUsers"
                >
                <li
                  class="autocomplete-result d-flex"
                  v-for="user in searchedUsers"
                  :key="`search-${user.id}`"
                >
                <img
                  class="img-fluid mr-3"
                  style="width:25px; height:25px; border-radius: 50%" 
                  v-if="user.profileImg"
                  :src="user.profileImg" 
                  alt="">
                <img
                  class="img-fluid mr-3"
                  v-else
                  style="border-radius: 50%"
                  src="https://user-images.githubusercontent.com/57381062/88908958-84e85480-d295-11ea-9637-540f1be674ac.png"
                  width=25px
                  height=25px 
                  >
                <span @click="userDetail(user.id)">
                  {{ user.nickName }}
                </span>
                </li>
              </ul>
            </div>
            <ul class="navbar-nav mr-auto row w-100">
              <li class="nav-item col-3">
                <router-link class="nav-link" :to="{ name: 'PostList' }">
                  <i class="fas fa-home color-green"></i>
                  Feed
                </router-link>
              </li>
              <li class="nav-item col-3">
                <router-link class="nav-link" :to="{ name: 'ClubList' }">
                  <i class="fas fa-users color-green"></i>
                  Club
                </router-link>
              </li>
              <li class="nav-item col-3">
                <router-link class="nav-link" :to="{ name: 'MeetupList' }">
                  <img class="img-fluid club-img" src="https://user-images.githubusercontent.com/57381062/88909365-f7f1cb00-d295-11ea-859e-656c0633bf2e.png" alt="밋업 이미지">
                  Meetup
                </router-link>
              </li>
              <li class="nav-item dropdown col-1 pointer">
                <div 
                  class="nav-link dropdown-toggle" 
                  id="navbarDropdown" 
                  type="button"
                  role="button" 
                  data-toggle="dropdown" 
                  aria-haspopup="true" 
                  aria-expanded="false"
                >
                  <i class="fas fa-bell color-green"></i>
                </div>
                <div class="dropdown-menu py-0 text-center" aria-labelledby="navbarDropdown" v-if="myaccount" >
                  <div
                    class="dropdown-item setting-btn"
                    v-for="noti in notis"
                    :key="`noti-${noti.id}`"
                    @click="toRoute(noti)"
                  >
                    <p v-if="noti.type==='club'">{{ findUsers[noti.from] }}님이 '{{ findClubs[noti.dataId] }}' club에 가입신청했습니다.</p>
                    <p v-if="noti.type==='follow'">{{ findUsers[noti.from] }}님이 팔로우했습니다.</p>
                  </div>
                </div>
              </li>
              <li class="nav-item col-1">
                <router-link class="nav-link" :to="{ name: 'PostCreate' }">
                  <i class="fas fa-plus-circle color-green"></i>
                </router-link>
              </li>
              <li class="nav-item dropdown col-1">
                <div 
                  class="nav-link dropdown-toggle" 
                  id="navbarDropdown" 
                  type="button"
                  role="button" 
                  data-toggle="dropdown" 
                  aria-haspopup="true" 
                  aria-expanded="false"
                >
                  <i class="fas fa-user color-green"></i>
                </div>
                <div class="dropdown-menu py-0 text-center" aria-labelledby="navbarDropdown" v-if="myaccount" >
                  <router-link class="dropdown-item setting-btn menus" :to="{ name: 'Profile', params: {userId: myaccount.id} }">프로필</router-link>
                  <!-- <router-link v-if="myaccount" class="dropdown-item" :to="{ name: 'ProfileUpdate', params: {userId: myaccount.id} }">프로필 수정</router-link> -->
                  <!-- <div class="dropdown-divider"></div> -->
                  <div class="dropdown-item setting-btn pointer menus" @click="logout">로그아웃</div>
                </div>
              </li>
            </ul>
          </div>
        </nav>
      </div>
      <router-view/>
    </div>
    <div id="app2">
      <div class="media-q d-flex flex-column justify-content-center align-items-center">
        <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="250px" class="mt-3">
        <h5>이용에 불편을 드려 죄송합니다.</h5>
        <h5>코북은 좀 더 <strong>큰 창</strong>에서 봐야 제 맛 :)</h5>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'App',
  data() {
    return {
      keyword: null,
      isActive:null,
      searchedUsers: null,
      findUsers: null,
      findClubs: null,
    }
  },
  computed: {
    ...mapState(['genres', 'myaccount', 'books', 'users', 'notis']),
  },
  methods: {
    ...mapActions(['fetchGenres', 'findMyAccount', 'fetchBooks', 'fetchUsers', 'fetchNotis', 'logout']),
    searchUser() {
      if (!this.keyword) {
        this.isActive = false
        this.searchedUsers = null
      } else {
        this.isActive = true
        this.searchedUsers = this.users.filter((user) => {
          return user.nickName.match(this.keyword)
        })
        if (this.searchedUsers.length < 1) {
          this.searchedUsers = null
        }
      }
    },
    focusout() {
      setTimeout(this.isKeywordNull, 100)
    },
    isKeywordNull() {
      this.keyword = null
      this.searchedUsers = null
    },
    userDetail(userId) {
      this.$router.push({ name: 'Profile', params: {userId : userId}})
    },
    toRoute(noti) {
      if (noti.type === 'club') {
        this.$router.push({name: 'ClubCandidates', params: { clubId: noti.dataId }})
      } else if (noti.type === 'follow') {
        this.$router.push({name: 'Profile', params: { userId: noti.dataId }})
      }
    }
  },
  watch: {
    users() {
      const mapData = this.users.map(user => [user.id, user.nickName])
      this.findUsers = Object.fromEntries(mapData)
    },
    myaccount() {
      const mapData = []
      this.myaccount.myClubs.forEach(club => {
        mapData.push([club.id, club.name])
      })
      this.findClubs = Object.fromEntries(mapData)
    }
  },
  created() {
    this.fetchGenres()
    this.findMyAccount()
    this.fetchBooks()
    this.fetchUsers()
    this.fetchNotis()
  }
}
</script>

<style scoped>
@media (max-width: 960px) {
  #app {
    display: none;
  }
}

@media (min-width: 960px) {
  #app2 {
    display: none;
  }
}


#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  max-height: 960px;
}

#nav a {
  font-size: 1.1rem;
  font-weight: 900;
  color: #3A2F15;
}

.navbar-bg-color {
  background: #D6CBBD;
  height: 65px;
}

.logo-img {
  height: 58px;
}

.logo-text {
  font-size: 1.5rem;
  padding: 0;
}

.club-img {
  height: 23px;
}

.dropdown-menu {
  top: 50px;
  left: -70px !important;
}

/* Search bar */
input {
	outline: none;
}
input[type=search] {
	-webkit-appearance: textfield;
	-webkit-box-sizing: content-box;
	font-size: 100%;
}
input::-webkit-search-decoration,
input::-webkit-search-cancel-button {
	display: none; 
}


input[type=search] {
	background: #ededed url(https://static.tumblr.com/ftv85bp/MIXmud4tx/search-icon.png) no-repeat 9px center;
	border: solid 1px #ccc;
	padding: 9px 10px 9px 32px;
	width: 55px;
	
	-webkit-border-radius: 10em;
	-moz-border-radius: 10em;
	border-radius: 10em;
	
	-webkit-transition: all .5s;
	-moz-transition: all .5s;
	transition: all .5s;
}
input[type=search]:focus {
	width: 130px;
	background-color: #fff;
	border-color: #88A498;
	
	-webkit-box-shadow: 0 0 5px #88A498;
	-moz-box-shadow: 0 0 5px #88A498;
	box-shadow: 0 0 5px #88A498;
}


input:-moz-placeholder {
	color: #999;
}
input::-webkit-input-placeholder {
	color: #999;
}

#search-bar input[type=search] {
	width: 15px;
	padding-left: 10px;
	color: transparent;
	cursor: pointer;
}
#search-bar input[type=search]:hover {
	background-color: #fff;
}
#search-bar input[type=search]:focus {
	width: 130px;
	padding-left: 35px;
	color: #000;
	background-color: #fff;
	cursor: auto;
}
#search-bar input:-moz-placeholder {
	color: transparent;
}
#search-bar input::-webkit-input-placeholder {
	color: transparent;
}


/* autocomplete */
.autocomplete {
  position: relative;
  width: 130px;
}

.autocomplete-results {
  position: absolute;
  top: 55px;
  padding: 0;
  margin: 0;
  border: 1px solid #eeeeee;
  background: #F7F4F2;
  height: 120px;
  overflow: auto;
  z-index: 1;
}

.autocomplete-results{
  overflow: hidden;
  
}
.autocomplete-results::-webkit-scrollbar {
  width: 8px; height: 8px; border: 3px solid white; 
  } 
.autocomplete-results::-webkit-scrollbar-button,.autocomplete-results::-webkit-scrollbar-button:END {
  background-color: white;
}
.autocomplete-results::-webkit-scrollbar-button:start:decrement{
}
.autocomplete-results::-webkit-scrollbar-track {
  background: white; 
  -webkit-border-radius: 10px white; 
  border-radius:10px white;
  /* -webkit-box-shadow: inset 0 0 4px rgba(0,0,0,.2) */
  }
.autocomplete-results::-webkit-scrollbar-thumb {
  height: 10px; 
  width: 50px; 
  background: #88A498; 
  -webkit-border-radius: 15px; border-radius: 15px; 
  /* -webkit-box-shadow: inset 0 0 4px rgba(0,0,0,.1) */
  }
.autocomplete-results:hover{
  overflow-y: scroll;
}

.autocomplete-result {
  list-style: none;
  text-align: left;
  padding: 4px 2px;
  cursor: pointer;
	width: 180px;
}

.autocomplete-result:hover {
  background-color: #707070;
  color: white;
}

.media-q {
  width:500px;
  height:500px;
  margin:0 auto;
  position:absolute;
  left:50%;
  top:50%;
  margin-left:-250px;
  margin-top:-250px;
}

.dropdown-item {
  font-weight: 600 !important;
}

.setting-btn:focus {
  background-color: #88A498 !important;
  color: #F7F7F7 !important;
  outline: none;
}

.menus {
  font-size: 17px !important;
}
</style>