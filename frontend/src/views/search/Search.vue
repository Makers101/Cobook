<template>
  <div class="row no-gutters">
    <div class="col-3 p-3 text-left search-bar">
      <div class="autocomplete text-left m-0" id="search-bar">
        <input 
          type="search" 
          v-model="keyword" 
          @keyup.enter="search(keyword)"
          >
      </div>
      <h4 class="mt-3"><strong>검색 결과:</strong></h4>
      <p>{{ this.$route.params.content }}</p>
      <hr>
      <h5 class="font-weight-bold">필터</h5>
      <router-link class="m-0" :to="{name: 'SearchUser'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
            <i class="fas fa-user p-2"></i>
          </div>
          <span>유저</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchPost'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
            <i class="fas fa-comment-dots p-2"></i>
          </div>
          <span>리뷰</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchBook'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
              <i class="fas fa-book-open p-2"></i>
          </div>
          <span class="m-0">도서</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchClub'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
              <i class="fas fa-users p-2"></i>
          </div>
          <span class="m-0">북클럽</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchOneDayEvent'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%;">
            <span class="mdi mdi-calendar-star p-2" width="50px"></span>
          </div>
          <span class="m-0">원데이 이벤트</span>
        </div>
      </router-link>
    </div>
    <transition name="slide" mode="out-in">
      <router-view class="col-9"></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'Search',
  data() {
    return {
      keyword: null,
    }
  },
  methods: {
    search(keyword){
      this.$router.push({name: 'Search', params: { content : keyword }})
    }
  },
  created() {
    this.search(this.$route.params.content)
  },
  mounted(){
    if (this.$route.params.content[0]==='#') {
      this.$router.push({ name: 'SearchPost', params: { content: this.$route.params.content.slice(1,)}})
    }
    // this.$router.push({ name: 'SearchUser', params: { content: this.$route.params.content}})
  },

    
    
}
</script>

<style scoped>
a:link, a:active, a:visited {
  color: black;
}
.search-bar {
  height: 100vh;
  position: sticky;
  top: 0px;
  border-right: 1px solid rgb(0, 0, 0, 0.2);
  box-shadow: 5px 5px 10px rgb(0, 0, 0, 0.2)
}

.filter:hover, a:hover {
  background-color: #3c755a ;
  color: #F8F8F8;
  text-decoration: none !important;
}

hr {
  border-top: 2px solid rgba(0,0, 0, 0.1);
}

.router-link-active .filter{
  background-color: #88A498 !important;
  color: white;
}

/* search-bar */
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

.slide-leave-active {
        transition: opacity 0.3s ease;
        opacity: 0;
        animation: slide-out 0.3s ease-out forwards;
    }

    .slide-leave {
        opacity: 1;
        transform: translateX(0);
    }

    .slide-enter-active {
        animation: slide-in 0.3s ease-out forwards;
    }

    @keyframes slide-out {
        0% {
            transform: translateY(0);
        }
        100% {
            transform: translateY(-30px);
        }
    }

    @keyframes slide-in {
        0% {
            transform: translateY(-30px);
        }
        100% {
            transform: translateY(0);
        }
    }
</style>