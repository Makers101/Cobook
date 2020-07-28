<template>
  <div id="app">
    <div id="nav">
      <nav class="navbar navbar-expand-md navbar-light navbar-bg-color">
        <router-link class="navbar-brand" to="/">
          <img 
            class="img-fluid logo-img" 
            src="@/assets/logo.png" 
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
          <form id="search-bar">
            <input type="search" placeholder="Search">
          </form>
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
              <router-link class="nav-link" to="/">
                <img class="img-fluid club-img" src="@/assets/meetup.png" alt="클럽 이미지">
                Meetup
              </router-link>
            </li>
            <li class="nav-item col-1">
              <router-link class="nav-link" to="/">
                <i class="fas fa-bell color-green"></i>
              </router-link>
            </li>
            <li class="nav-item col-1">
              <router-link class="nav-link" :to="{ name: 'PostCreate' }">
                <i class="fas fa-plus-circle color-green"></i>
              </router-link>
            </li>
            <li class="nav-item dropdown col-1">
              <router-link 
                class="nav-link dropdown-toggle" 
                to="/" 
                id="navbarDropdown" 
                type="button"
                role="button" 
                data-toggle="dropdown" 
                aria-haspopup="true" 
                aria-expanded="false"
              >
                <i class="fas fa-user color-green"></i>
              </router-link>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <router-link v-if="myaccount" class="dropdown-item" :to="{ name: 'Profile', params: {userId: myaccount.id} }">프로필</router-link>
                <router-link v-if="myaccount" class="dropdown-item" :to="{ name: 'ProfileUpdate'}">프로필 수정</router-link>
                <div class="dropdown-divider"></div>
                <router-link class="dropdown-item" to="/">로그아웃</router-link>
              </div>
            </li>
          </ul>
        </div>
      </nav>
    </div>
    <router-view/>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'App',
  computed: {
    ...mapState(['genres', 'myaccount'])
  },
  methods: {
    ...mapActions(['fetchGenres', 'findMyAccount'])
  },
  created() {
    this.fetchGenres()
    this.findMyAccount()
  }
}
</script>

<style scoped>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
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
  left: -70px;
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
	padding-left: 32px;
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
</style>