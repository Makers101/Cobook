<template>
  <div class="mt-3" style="padding-bottom:100px">
    <div class="row rows-cols-1 row-cols-md-3" v-if="this.feeds.length">
      <div 
        class="col-12 col-sm-6 col-lg-4 mb-4 pointer"
        v-for="feed in feedList"
        :key="feed.id"
        @click="selectFeed(feed.id)">
        <div class="card m-0 ">
          <div class="additional d-flex justify-content-start px-1">
            <div class="user-card">
              <div class="points center">
                <i class="fas fa-heart mr-1"></i> {{ feed.likeUsers.length }}
              </div>
              <img class="mt-5" :src="feed.book.bookImg">  
            </div>
            <div class="more-info d-flex flex-column justify-content-around">
              <p class="book-title my-2">{{ feed.book.title }}</p>
              <div class="text-left">
                <div class="coords">
                  <p>
                    <!-- <mark>작가</mark> -->
                    <img class="mr-2" src="https://user-images.githubusercontent.com/25967949/89915180-00042000-dc31-11ea-8777-4cc7df56b4b4.png" width="20px">
                    <span>{{ feed.book.author }}</span>
                  </p>
                </div>
                <div class="coords">
                  <p>
                    <!-- <mark>출판사</mark> -->
                    <img class="mr-2" src="https://user-images.githubusercontent.com/25967949/89915533-71dc6980-dc31-11ea-96c5-139fa8877eb2.png" width="20px">
                    <span>{{ feed.book.publisher }}</span>
                  </p>
                </div>
                <div class="coords">
                  <p>
                    <!-- <mark>출판일</mark> -->
                    <img class="mr-2" src="https://user-images.githubusercontent.com/25967949/89915335-304bbe80-dc31-11ea-9dbc-06270437603f.png" width="20px">
                    <span>{{ feed.book.pubDate | moment('YYYY-MM-DD') }}</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div class="general d-flex flex-column justify-content-between">
            <div class="w-100 h-100 d-flex flex-column justify-content-around">
              <div class="mb-2">
                <span class="mb-3 star-container" v-for="index in feed.rank" :key="index"><i class="fas fa-star" style="color:yellow"></i></span>
              </div>
              <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
              <p class="card-text px-3" style="word-break:keep-all;">{{ feed.onelineReview }}</p>
              <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
            </div>
            <div class="more">
              <span class="text-black-50"><small>{{ feed.createdAt | moment('YYYY-MM-DD')}}</small></span>
            </div>
          </div>
        </div>
      </div>
      <infinite-loading class="col-12" @infinite="infiniteHandler" :identifier="feeds" spinner="waveDots"></infinite-loading>
    </div>
    <div v-else>
      <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
      <h3 class="mt-3">현재 <strong>{{ profile.nickName }}</strong>님이 작성한 피드가 없습니다. </h3>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import InfiniteLoading from 'vue-infinite-loading'

export default {
  name: 'ProfileFeed',
  components: {
    InfiniteLoading
  },
  data() {
    return {
      feedList: [], 
      page: 6
    }
  },
  computed: {
    ...mapState('profileStore',['feeds', 'profile']),
    ...mapState(['myaccount'])
  },
  methods: {
    ...mapActions('profileStore', ['fetchFeeds', 'findProfile']),
    infiniteHandler($state) {
      setTimeout(() => {
        if (this.feeds.slice(this.page, this.page + 6).length) {
          this.feedList = this.feedList.concat(this.feeds.slice(this.page, this.page + 6))
          $state.loaded();
          this.page += 6
        } else {
          $state.complete();
        }
      }, 700)
    },
    selectFeed(id) {
      this.$router.push({name: 'PostDetail', params: {postId: id}})
    }
  },
  watch: {
    feeds() {
      this.feedList = this.feeds.slice(0, 6)
    }
  },
  created() {
    this.fetchFeeds(this.$route.params.userId)
  },
  beforeRouteUpdate (to, from, next) {
    this.fetchFeeds(to.params.userId)
    next();
  },
}
</script>

<style scoped>
.bg-image {
  position: static;
  width: 100%;
  max-height: initial; 
  margin-top: 0%;
  filter: blur(5px);
}

.book-title {
  color: white;
  padding-left: 5px;
  padding-right: 8px;
  text-shadow: 1px 1px 2px rgb(0,0,0,0.7);
  font-weight: 700;
  word-break: keep-all;
  overflow: hidden;
  white-space: normal;
  word-wrap: break-word;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2; 
  -webkit-box-orient: vertical;
  height: 3rem;
}

/* card */

  /* card css 여기! */

.center {
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
}

.card {
  height: 250px;
  background-color: #fff;
  background: linear-gradient(#f8f8f8, #fff);
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
  border-radius: 6px;
  overflow: hidden;
  position: relative;
  margin: 1.5rem;
}

.card:hover {
  height: 250px;
  background-color: #fff;
  background: linear-gradient(#f8f8f8, #fff);
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.8);
  border-radius: 6px;
  overflow: hidden;
  position: relative;
  margin: 1.5rem;
}

.card h1 {
  text-align: center;
}

.card .additional {
  position: absolute;
  width: 33%;
  height: 100%;
  background: linear-gradient(#88A498, #88A487);
  transition: width 0.4s;
  overflow: hidden;
  z-index: 2;
}

.card.green .additional {
  background: linear-gradient(#92bCa6, #A2CCB6);
}


.additional:hover {
  width: 100%;
  border-radius: 0 5px 5px 0;
}


.additional:hover.user-card {
  position: absolute;
  left: 0;
}

.card .additional .user-card {
  width: 120px;
  height: 100%;
  position: relative;
  float: left;
}

.card .additional .user-card::after {
  content: "";
  display: block;
  position: absolute;
  top: 10%;
  right: -2px;
  height: 80%;
}

.card .additional .user-card .level,
.card .additional .user-card .points {
  top: 15%;
  color: #fff;
  text-transform: uppercase;
  font-size: 0.75em;
  font-weight: bold;
  background: rgba(0,0,0,0.15);
  padding: 0.125rem 0.75rem;
  border-radius: 100px;
  white-space: nowrap;
}

.card .additional .user-card .level {
  white-space: pre-wrap;
  width: 100%;
  word-break: keep-all;
}

.card .additional .user-card .points {
  top: 85%;
}

.card .additional .user-card img {
  /* top: 10%; */
  margin-top: 30%;
  max-width: 80%;
  /* left: 14%; */
  border-radius: 5%;
  /* position: absolute; */
}

.card .additional .more-info {
  width: 58%;
  float: left;
  position: absolute;
  left: 120px;
  height: 100%;
}

.card .additional .more-info h1 {
  color: #fff;
  margin-bottom: 0;
}

.card.green .additional .more-info h1 {
  color: #224C36;
}

.card .additional .coords {
  /* margin: 0 1rem; */
  padding-left: 8px;
  padding-right: 8px;
  margin-bottom: 8px;
  color: #fff;
  font-size: 1rem;
}

.card.green .additional .coords {
  color: #325C46;
}

.card .additional .coords span + span {
  float: right;
}

.card .additional .stats {
  font-size: 2rem;
  display: flex;
  position: absolute;
  bottom: 1rem;
  left: 1rem;
  right: 1rem;
  top: auto;
  color: #fff;
}

.card.green .additional .stats {
  color: #325C46;
}

.card .additional .stats > div {
  flex: 1;
  text-align: center;
}

.card .additional .stats i {
  display: block;
}

.card .additional .stats div.title {
  font-size: 0.75rem;
  font-weight: bold;
  text-transform: uppercase;
}

.card .additional .stats div.value {
  font-size: 1.5rem;
  font-weight: bold;
  line-height: 1.5rem;
}

.card .additional .stats div.value.infinity {
  font-size: 2.5rem;
}

.card .general {
  width: 66%;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1;
  box-sizing: border-box;
  padding: 1rem;
}

.card p {
  margin: 0;
  padding-left: 10px;
  padding-right: 10px;
}

mark {
  background-color: #fff7f7;
  color: black;
  border-radius: 10px;
}

.star-container {
  text-shadow: 1px 1px 2px rgb(0, 0, 0, 0.7);
}

.fa-heart {
  color: red;
  text-shadow: 1px 1px 2px rgb(0, 0, 0, 0.7);
}

</style>