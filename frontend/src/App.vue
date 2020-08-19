<template>
  <div>
    <div id="app">
      <div id="nav" v-if="authToken != null">
        <nav class="navbar navbar-expand-md navbar-light navbar-bg-color d-flex justify-content-between">
          <router-link class="navbar-brand" to="/">
            <img 
              class="img-fluid logo-img"
              style="height: 50px"
              src="@/assets/new logo.png" 
              alt="로고 이미지"
            >
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

          <div class="collapse navbar-collapse d-flex justify-content-between" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item">
                <router-link class="nav-link" :to="{ name: 'PostList' }">
                  <!-- <i class="fas fa-home"></i> -->
                  피드
                </router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" :to="{ name: 'ClubList' }">
                  <!-- <i class="fas fa-users"></i> -->
                  북클럽
                </router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" :to="{ name: 'OnedayEventList' }">
                  <!-- <img class="img-fluid club-img" src="https://user-images.githubusercontent.com/57381062/88909365-f7f1cb00-d295-11ea-859e-656c0633bf2e.png" alt="밋업 이미지"> -->
                  원데이이벤트
                </router-link>
              </li>
            </ul>
            <!-- v-if="this.$route.name!=='SearchUser'" -->
            <div class="d-flex justify-content-end">
              <div 
                class="autocomplete text-right" 
                id="search-bar" 
                v-if="this.$route.name!=='SearchUser' && this.$route.name!=='SearchBook' && this.$route.name!=='SearchClub' && this.$route.name!=='SearchOnedayEvent' && this.$route.name!=='SearchPost'" >
              <input 
                type="search" 
                v-model="keyword" 
                @input="searchUser"
                @blur="focusout"
                @keyup.enter="search(keyword)"
                >
              <!-- <ul 
                class="autocomplete-results px-3"
                v-show="searchedUsers"
                >
                <li
                  class="autocomplete-result d-flex"
                  v-for="user in searchedUsers"
                  :key="`search-${user.id}`"
                  @click="userDetail(user.id)"
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
                  <span>
                    {{ user.nickName }}
                  </span>
                </li>
                <li class="autocomplete-result d-flex" @click="search(keyword)">검색결과 더보기 ...</li>
              </ul> -->
            </div>
            <ul class="navbar-nav d-flex align-items-center">
              <li class="nav-item mx-4">
                <router-link class="nav-link" :to="{ name: 'PostCreate' }">
                  <i class="fas fa-pen"></i>
                </router-link>
              </li>
              <li class="nav-item dropdown mr-4">
                <div 
                  class="nav-link dropdown-toggle pointer" 
                  id="navbarDropdown" 
                  type="button"
                  role="button" 
                  data-toggle="dropdown" 
                  aria-haspopup="true" 
                  aria-expanded="false"
                  @click="clickNoti"
                >
                  <i class="fas fa-bell"></i>
                  <small class="badge rounded-circle badge-danger go-left" v-if="notis">{{ notis.length }}</small>
                </div>
                <div class="dropdown-menu dropdown-noti py-0 text-center scroll-sect" aria-labelledby="navbarDropdown" v-if="myaccount" >
                  <div v-if="notis">
                    <div
                      class="dropdown-item setting-btn"
                      v-for="(noti, idx) in notis"
                      :key="`noti-${idx}`"
                    >
                      <div class="row no-gutters">
                        <div class="col-2 pointer" @click="toRoute(noti)">
                          <img
                            v-if="(noti.type === 'approve' || noti.type === 'reject') && findClubImg(noti.dataId)"
                            class="img-fluid noti-profile-img mr-1" 
                            :src="findClubImg(noti.dataId)" alt="클럽 프로필 사진">
                          <img
                            v-else-if="(noti.type === 'approve' || noti.type === 'reject') && !findClubImg(noti.dataId)"
                            class="img-fluid noti-profile-img mr-1" 
                            src="http://bit.do/anonymouseuser" 
                            alt="클럽 프로필 사진">
                          <img
                            v-else-if="!findUsers[noti.from][1]"
                            class="img-fluid noti-profile-img mr-1" 
                            src="http://bit.do/anonymouseuser" 
                            alt="유저 프로필 사진">
                          <img 
                            v-else
                            class="img-fluid noti-profile-img mr-1" 
                            :src="findUsers[noti.from][1]" alt="유저 프로필 사진">
                        </div>
                        <div class="col-9 d-flex align-items-center text-left pl-2 pointer" @click="toRoute(noti)">
                          <span v-if="noti.type==='club'" style="font-size: 14px;">{{ findUsers[noti.from][0] }}님이 '{{ findClubs[noti.dataId] }}' 북클럽에 가입신청했습니다.</span>
                          <span v-if="noti.type==='comment'" style="font-size: 14px;">{{ findUsers[noti.from][0] }}님이 댓글을 작성했습니다.</span>
                          <span v-if="noti.type==='follow'" style="font-size: 14px;">{{ findUsers[noti.from][0] }}님이 팔로우했습니다.</span>
                          <span v-if="noti.type==='like'" style="font-size: 14px;">{{ findUsers[noti.from][0] }}님이 게시물을 좋아합니다.</span>
                          <span v-if="noti.type==='approve'" style="font-size: 14px;">'{{ findClubName(noti.dataId) }}' 북클럽에 가입 승인되었습니다.</span>
                          <span v-if="noti.type==='reject'" style="font-size: 14px;">'{{ findClubName(noti.dataId) }}' 북클럽에 가입 거절되었습니다.</span>
                        </div>
                        <div class="col-1 d-flex align-items-center justify-content-end pointer" @click="deleteNoti(noti.id)">
                          <span class="text-muted" style="font-size:10px">X</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div v-else>
                    <div
                      class="dropdown-item setting-btn"
                    >
                      <div class="row no-gutters">
                        <div class="col-12">
                          <p class="mt-2">알림이 없습니다.</p>
                        </div>
                      </div>
                    
                    </div>
                  </div>
                </div>
              </li>
              <li class="nav-item dropdown">
                <div 
                  class="nav-link dropdown-toggle" 
                  id="navbarDropdown" 
                  type="button"
                  role="button" 
                  data-toggle="dropdown" 
                  aria-haspopup="true" 
                  aria-expanded="false"
                >
                  <i class="fas fa-user"></i>
                </div>
                <div class="dropdown-menu dropdown-profile py-0 text-center" aria-labelledby="navbarDropdown" v-if="myaccount" >
                  <router-link class="dropdown-item setting-btn menus" :to="{ name: 'Profile', params: {userId: myaccount.id} }">프로필</router-link>
                  <div class="dropdown-item setting-btn pointer menus" @click="logout">로그아웃</div>
                </div>
              </li>
            </ul>
            </div>
            
          </div>
        </nav>
      </div>
      <div v-else style="padding-top:50px">
        <img 
          class="img-fluid logo-img"
          style="height: 50px"
          src="@/assets/new logo.png" 
          alt="로고 이미지"
        >
      </div>
      <!-- 위로가기 버튼 -->
      <div>
      <div>
        <button class="btn btn-green btn-up m-0" @click="scrollToTop()" v-if="this.$route.name!=='PostDetail' && this.$route.name!=='BookDetail' &&  authToken != null">
          <i class="fas fa-caret-up" style="color:white;"></i>
        </button>
      </div>
      </div>
      <router-view class="router-view"/>
    </div>
    <!-- footer -->
    <div class="footer" v-if="this.$route.name!=='PostDetail' && this.$route.name!=='BookDetail' && authToken != null">
      <p class="footer-p">© 2020 Copyright: 만든이101 </p>
      <p class="m-0 pb-2">
        <i class="fab fa-github github-color"></i><a href="https://github.com/smhwang0109" target="_blank"> smhwang0109</a>
        | <i class="fab fa-github github-color"></i> <a href="https://github.com/scl2589" target="_blank"> chaelinshin96</a> 
        | <i class="fab fa-github github-color"></i><a href="https://github.com/SunHwan-Park" target="_blank"> SunHwan-Park</a>
        | <i class="fab fa-github github-color"></i><a href="https://github.com/genie97" target="_blank"> genie97</a>
        | <i class="fab fa-github github-color"></i><a href="https://github.com/pandaHun" target="_blank"> pandaHun</a>
      </p>
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
import firebase from 'firebase/app'
import 'firebase/database'

const firebaseConfig = {
  apiKey: "AIzaSyA9KKQn0uKuErmQMJsMbhw25-iG8chHLdI",
  authDomain: "co-book-original.firebaseapp.com",
  databaseURL: "https://co-book-original.firebaseio.com",
  projectId: "co-book-original",
  storageBucket: "co-book-original.appspot.com",
  messagingSenderId: "21513194733",
  appId: "1:21513194733:web:5ac45b8faee796d5b910e4",
  measurementId: "G-YNYKTYY7B8"
};

firebase.initializeApp(firebaseConfig);

export default {
  name: 'App',
  data() {
    return {
      keyword: null,
      isActive:null,
      searchedUsers: null,
      findUsers: null,
      findClubs: null,
      notis: null,
    }
  },
  computed: {
    ...mapState(['genres', 'myaccount', 'books', 'users', 'authToken']),
    ...mapState('clubStore', ['clubs']),
    findClubName() {
      return (clubId) => {
        {
          let name = '이름 미정'
          this.clubs.forEach((club) => {
            if (club.id === clubId) {
              return name = club.name
            }
          })
          return name
        }
      }
    },
    findClubImg() {
      return (clubId) => {
        {
          let img = null
          this.clubs.forEach((club) => {
            if (club.id === clubId) {
              return img = club.clubImg
            }
          })
          return img
        }
      }
    },
  },
  methods: {
    ...mapActions(['fetchGenres', 'findMyAccount', 'fetchBooks', 'fetchUsers', 'logout', 'deleteNoti']),
    ...mapActions('clubStore', ['fetchClubs']),
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
      } else if (noti.type === 'like' || noti.type === 'comment') {
        this.$router.push({name: 'PostDetail', params: { postId: noti.dataId }})
      } else if (noti.type === 'approve' || noti.type === 'reject') {
        this.$router.push({name: 'ClubDetail', params: { clubId: noti.dataId }})
      }
    },
    // Firebase
    clickNoti() {
      firebase.database().ref('noti/' + this.myaccount.id).on('value', data => {
        if (data.val()) {
          this.notis = []
          let dataObject = data.val()
          for (let idx in dataObject) {
            dataObject[idx].id= idx
            this.notis.push(dataObject[idx])
          }
          this.notis.reverse()
        } else {
          this.notis = null
        }
      })
    },
    search(keyword){
      this.$router.push({name: 'SearchPost', params: { content : keyword }})
    },
    scrollToTop() {
      window.scrollTo(0,0)
    }
  },
  watch: {
    users() {
      const mapData = this.users.map(user => [user.id, [user.nickName, user.profileImg]])
      this.findUsers = Object.fromEntries(mapData)
    },
    myaccount() {
      if (this.myaccount) {
        const mapData = []
        this.clickNoti()
        this.myaccount.myClubs.forEach(club => {
          mapData.push([club.id, club.name])
        })
        this.findClubs = Object.fromEntries(mapData)
      }
    }
  },
  created() {
    this.fetchGenres()
    this.findMyAccount()
    this.fetchBooks()
    this.fetchUsers()
    this.fetchClubs()
  },
}
</script>

<style scoped>
@media (max-width: 960px) {
  #app {
    display: none;
  }
}

@media (max-width: 960px) {
  .footer {
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
  min-height: 100vh;
  position: relative;
}

#nav {
  box-shadow: 0px 2px 5px rgb(0, 0, 0, 0.2);
  position: sticky;
  top: 0;
  z-index: 99;
  background-color: white;
}

#nav a, i {
  font-size: 1.1rem;
  font-weight: 900;
  color: #3A2F15;
}

.navbar {
  margin-left: 1vw;
}

.navbar-bg-color {
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
  overflow-y: auto;
}

.dropdown-profile {
  left: -16vh !important;
}

.noti-profile-img {
  max-width: 100%;
  border-radius: 50%
}

.btn-up {
  position: fixed;
  bottom: 120px;
  right: 20px;
  border-radius: 50%;
  z-index: 99;
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
  width: 180px;
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

.dropdown-item {
  white-space: pre-wrap;
}

.dropdown-noti {
  width: 25vw;
  left: -18vw !important;
  max-height: 40vh;
}

.setting-btn:focus {
  background-color: #88A498 !important;
  color: #F7F7F7 !important;
  outline: none;
}

.menus {
  font-size: 17px !important;
}

.dropdown-toggle:after {
  content: none;
}

.go-left {
  margin-left: -2px;
}

.scroll-sect::-webkit-scrollbar {
  width: 8px; height: 8px; border: 3px solid white; 
} 

.scroll-sect::-webkit-scrollbar-button,.scroll-sect::-webkit-scrollbar-button:END {
  background-color: white;
}

.scroll-sect::-webkit-scrollbar-button:start:decrement{
}

.scroll-sect::-webkit-scrollbar-track {
  background: white; 
  -webkit-border-radius: 10px white; 
  border-radius:10px white;
  /* -webkit-box-shadow: inset 0 0 4px rgba(0,0,0,.2) */
}

.scroll-sect::-webkit-scrollbar-thumb {
  height: 10px; 
  width: 50px; 
  background: #88A498; 
  -webkit-border-radius: 15px; border-radius: 15px; 
  /* -webkit-box-shadow: inset 0 0 4px rgba(0,0,0,.1) */
}

.footer {
  text-align: center;
  background-color: rgb(34, 34, 34);
  color: white;
  padding-top: 20px;
  height: 100px;
  left: 0;
  bottom: 0;
  width: 100%;
  z-index: 99;
  position: relative;
}

.footer a, .footer a:link {
  color: white;
  text-decoration: none;
}

.github-color {
  color: white;
}
</style>