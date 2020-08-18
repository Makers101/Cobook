<template>
  <v-app class="custom-container" style="padding-bottom:100px;">

    <!-- posts-banner -->
    <div class="posts-banner">
      <img
        class="posts-banner-img"        
        src="https://user-images.githubusercontent.com/57381062/90130466-7a55b100-dda5-11ea-9960-e061294dbd34.jpg"
        alt="">
      <div class="posts-banner-text">
        <h3 class="font-weight-bold">피드</h3>
        <p class="mb-0">
          피드는 내가 선호하는 유저와 장르의 책 리뷰를 제공합니다. 
          <br>
          다른 유저의 책 감상을 탐험해보세요!
        </p>
      </div>
    </div>

    <div class="my-3" v-if="myaccount.likeGenres.length">
      <div class="feed-container mt-3">
        <h5 class="feed-titles">#인기 리뷰</h5>
        <carousel
          class="slide-container"
          :autoplay="true"
          :autoplayHoverPause="true"
          :autoplayTimeout="5000"
          :loop="true"
          :navigationEnabled="true"
          navigationNextLabel="<h3 class='navigation'><i class='fas fa-angle-right'></i></h3>"
          navigationPrevLabel="<h3 class='navigation'><i class='fas fa-angle-left'></i></h3>"
          :perPageCustom="[[1, 1], [700, 1], [900, 2], [1100, 3], [1300, 4], [1500, 5]]"
          paginationActiveColor="#3c756a"
          paginationColor="#88A498"
          paginationPadding="4"
          paginationSize="10"
          easing="linear"
          speed="300"
          v-if="postsByPopularity.length">
        
          <slide
            class="custom-slide"
            v-for="post in postsByPopularity"
            :key="`post_${post.id}`">
            <div class="tile pointer mx-auto my-auto" @click="postDetail(post.id)">
              <div class="tile-media">
                <div class="content">
                  <div class="content-overlay"></div>
                  <img :src="post.book.bookImg" alt="책 표지 이미지" class="content-image">
                  <div class="content-details fadeIn-top">
                    <p><span v-for="index in post.rank" :key="index"> <i class="fas fa-star" style="color:yellow"></i> </span></p>
                    <div class="text-left p-0 m-0"><i class="fas fa-quote-left quote"></i></div>
                    <p class="m-0" @click="postDetail(post.id)">{{ post.onelineReview }}</p> 
                    <div class="text-right p-0 m-0"><i class="fas fa-quote-right quote"></i></div>
                    <small @click="postDetail(post.id)">
                      <span v-if="post.user.profileImg"><img class="profile-img mr-2" :src="post.user.profileImg"></span>
                      <span v-else ><img class="profile-img mr-2" src="http://bit.do/anonymouseuser"></span>
                      <span>{{ post.user.nickName }}</span>
                    </small> 
                  </div>
                </div>
              </div>
            </div>
          </slide>
        </carousel>
        <div class="d-flex flex-column justify-content-center align-items-center" style="min-height: 200px" v-else >
          <h3>인기 게시물이 없습니다 ㄴ(°0°)ㄱ</h3>
        </div>
      </div>

      <div class="feed-container">
        <h5 class="feed-titles">#팔로잉 리뷰</h5>
        <carousel
          class="slide-container"
          :autoplay="false"
          :autoplayHoverPause="true"
          :autoplayTimeout="5000"
          :loop="false"
          :navigationEnabled="true"
          navigationNextLabel="<h3 class='navigation'><i class='fas fa-angle-right'></i></h3>"
          navigationPrevLabel="<h3 class='navigation'><i class='fas fa-angle-left'></i></h3>"
          :perPageCustom="[[1, 1], [700, 1], [900, 2], [1100, 3], [1300, 4], [1500, 5]]"
          paginationActiveColor="#3c756a"
          paginationColor="#88A498"
          paginationPadding="4"
          paginationSize="10"
          easing="linear"
          speed="300"
          v-if="postsByFollow.length">
        
          <slide
            class="custom-slide"
            v-for="post in postsByFollow"
            :key="`post_${post.id}`">
            <div class="tile pointer mx-auto my-auto" @click="postDetail(post.id)">
              <div class="tile-media">
                <div class="content">
                  <div class="content-overlay"></div>
                  <img :src="post.book.bookImg" alt="책 표지 이미지" class="content-image">
                  <div class="content-details fadeIn-top">
                    <p><span v-for="index in post.rank" :key="index"> <i class="fas fa-star" style="color:yellow"></i> </span></p>
                    <div class="text-left p-0 m-0"><i class="fas fa-quote-left quote"></i></div>
                    <p class="m-0" @click="postDetail(post.id)">{{ post.onelineReview }}</p> 
                    <div class="text-right p-0 m-0"><i class="fas fa-quote-right quote"></i></div>
                    <small @click="postDetail(post.id)">
                      <span v-if="post.user.profileImg"><img class="profile-img mr-2" :src="post.user.profileImg"></span>
                      <span v-else ><img class="profile-img mr-2" src="http://bit.do/anonymouseuser"></span>
                      <span>{{ post.user.nickName }}</span>
                    </small> 
                  </div>
                </div>
              </div>
            </div>
          </slide>
        </carousel>
        <div class="my-5 pt-1" v-else >
          <div class="d-flex flex-column justify-content-center align-items-center mt-5">
            <h5 class="mb-1 font-weight-bold">아직 팔로우 하는 유저가 없습니다 ㄴ(°0°)ㄱ</h5>
            <small class="mb-1 font-weight-bold">새로운 유저를 팔로우 하고 리뷰를 받아보세요 :)</small>
          </div>
          <div class="d-flex justify-content-center mt-3">
            <div
              class="mx-2"
              v-show="user.id !== myaccount.id"
              v-for="user in recommendedUsers"
              :key="user.id"
              >
              <div class="card profile-card py-4 px-2 d-flex flex-column align-items-center justify-content-center">
                <div class="d-flex justify-content-center profile-image">
                  <img
                    v-if="!user.profileImg"
                    class="img-fluid"
                    src="http://bit.do/anonymouseuser" 
                    alt="유저 프로필 사진">
                  <img 
                    v-else
                    class="img-fluid"
                    :src="user.profileImg" alt="작성자 프로필 사진">
                </div>
                <p class="user-nickName pointer mb-0" @click="selectUser(user.id)"><strong>{{ user.nickName }}</strong></p>
                <div class="d-flex align-items-center">
                  <button v-if="user.isFollow" class="btn btn-following small-btn p-1" @click="clickedFollow(user, 'unfollow')">언팔로잉</button>
                  <button v-else class="btn btn-follow small-btn p-1" @click="clickedFollow(user, 'follow')">팔로우</button>
                </div>
              </div>                
            </div>
          </div>
        </div>
      </div>

      <div class="feed-container" v-for="postSet in postsByGenre" :key="postSet.genre">
        <h5 class="feed-titles">#{{ postSet.genre }} 리뷰</h5>
        <carousel
          class="slide-container"
          :autoplay="false"
          :autoplayHoverPause="true"
          :autoplayTimeout="5000"
          :loop="false"
          :navigationEnabled="true"
          navigationNextLabel="<h3 class='navigation'><i class='fas fa-angle-right'></i></h3>"
          navigationPrevLabel="<h3 class='navigation'><i class='fas fa-angle-left'></i></h3>"
          :perPageCustom="[[1, 1], [700, 1], [900, 2], [1100, 3], [1300, 4], [1500, 5]]"
          paginationActiveColor="#3c756a"
          paginationColor="#88A498"
          paginationPadding="4"
          paginationSize="10"
          easing="linear"
          speed="300"
          v-if="postSet.posts.length">
        
          <slide
            class="custom-slide"
            v-for="post in postSet.posts"
            :key="`post_${post.id}`">
            <div class="tile pointer mx-auto my-auto" @click="postDetail(post.id)">
              <div class="tile-media">
                <div class="content">
                  <div class="content-overlay"></div>
                  <img :src="post.book.bookImg" alt="책 표지 이미지" class="content-image">
                  <div class="content-details fadeIn-top">
                    <p><span v-for="index in post.rank" :key="index"> <i class="fas fa-star" style="color:yellow"></i> </span></p>
                    <div class="text-left p-0 m-0"><i class="fas fa-quote-left quote"></i></div>
                    <p class="m-0" @click="postDetail(post.id)">{{ post.onelineReview }}</p> 
                    <div class="text-right p-0 m-0"><i class="fas fa-quote-right quote"></i></div>
                    <small @click="postDetail(post.id)">
                      <span v-if="post.user.profileImg"><img class="profile-img mr-2" :src="post.user.profileImg"></span>
                      <span v-else ><img class="profile-img mr-2" src="http://bit.do/anonymouseuser"></span>
                      <span>{{ post.user.nickName }}</span>
                    </small> 
                  </div>
                </div>
              </div>
            </div>
          </slide>
        </carousel>

        <div class="my-5 pt-1" v-else>
          <div class="d-flex flex-column justify-content-center align-items-center mt-5">
            <h5 class="mb-1 font-weight-bold">아직 {{ postSet.genre }} 장르의 리뷰가 없습니다 ㄴ(°0°)ㄱ</h5>
            <small class="mb-1 font-weight-bold">{{ postSet.genre }} 장르의 첫 번째 리뷰를 작성해보세요 :)</small>
          </div>

          <div class="d-flex justify-content-center mt-3" style="margin-bottom: 50px;">
            <div
              class="mx-3"
              v-for="book in postSet.books"
              :key="book.id"
              :tooltip="book.title"
              flow="down"
            >
              <div class="recommended-book pointer" @click="bookDetail(book.id)">
                <img class="book-img" :src="book.bookImg">
              </div>
              <!-- <p class="">{{ book.title }}</p> -->
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-else>
      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="600px">
          <v-card>
            <v-card-title>
              <span class="headline">선호하는 책 장르를 선택해주세요.</span>
            </v-card-title>
            <v-card-text>
              <v-form
              v-model="valid"
              :lazy-validation="lazy"
              >
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <v-autocomplete
                        v-model="profileUpdateData.basicData.genres"
                        v-if="genres"
                        :items="genres"
                        chips
                        hide-selected
                        color="blue-grey lighten-2"
                        counter="3"
                        :rules="[
                          v => (v.length !== 0) || '필수항목입니다.',
                          v => (v.length < 4) || '최대 3개의 관심 장르를 고를 수 있습니다.'
                        ]"
                        label="관심 장르"
                        item-text="name"
                        item-value="id"
                        multiple
                        :search-input.sync="searchGenre"
                        @change="isGenreNull()"
                        @keypress.enter="isGenreNull()"
                      >
                        <template v-slot:selection="data">
                          <v-chip
                            v-bind="data.attrs"
                            :input-value="data.selected"
                            close
                            @click="data.select"
                            @click:close="remove(profileUpdateData.basicData.genres, data.item)"
                          >
                            {{ data.item.name }}
                          </v-chip>
                        </template>
                        <template v-slot:item="data">
                          <template v-if="typeof data.item !== 'object'">
                            <v-list-item-content v-text="data.item"></v-list-item-content>
                          </template>
                          <template v-else>
                            <v-list-item-content>
                              <v-list-item-title v-text="data.item.name"></v-list-item-title>
                            </v-list-item-content>
                          </template>
                        </template>
                      </v-autocomplete>
                    </v-col>
                  </v-row>
                </v-container>
              <small>*필수입니다.</small>
              </v-form>
              
            </v-card-text>
            <v-card-actions class="d-flex justify-content-end">
              <v-spacer></v-spacer>
                <v-btn
                  :disabled="!valid"
                  class="btn btn-green"
                  @click="clickUpdateProfile()"
                >
                  장르 추가
                </v-btn>
              </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
    </div>
  </v-app>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel'
import { mapState, mapActions } from 'vuex'
import router from '@/router'

export default {
  name: 'PostList',
  components: {
    Carousel,
    Slide
  },
  data() {
    return {
      // bannerImages: [
      //   'https://user-images.githubusercontent.com/57381062/90099601-d6a0dc80-dd75-11ea-82b9-841824eb5e91.jpg',
      //   'https://user-images.githubusercontent.com/57381062/90129061-ee428a00-dda2-11ea-9e31-f6cfe006a951.jpg',
      //   'https://user-images.githubusercontent.com/57381062/90130466-7a55b100-dda5-11ea-9960-e061294dbd34.jpg',
      //   'https://user-images.githubusercontent.com/57381062/90130484-85104600-dda5-11ea-9167-7dcd85b5e810.jpg',
      //   'https://user-images.githubusercontent.com/57381062/90130506-8b9ebd80-dda5-11ea-8354-7ce8458fb3f8.jpg'
      // ],
      profileUpdateData: {
        basicData: {
          nickName: null,
          description: null,
          genres: null,
        },
        profileImgFormData: null,
      },
      valid: true,
      lazy: false,
      searchGenre: null,
      clicked: false,
      dialog: true
    }
  },
  computed: {
    ...mapState(['myaccount', 'genres']),
    ...mapState('postStore', ['postsByPopularity', 'postsByFollow', 'postsByGenre', 'recommendedUsers']),
  },
  methods: {
    ...mapActions(['findMyAccount']),
    ...mapActions('profileStore', ['updateProfile', 'clickFollow']),
    ...mapActions('postStore', ['fetchPostsByPopularity', 'fetchPostsByFollow', 'fetchPostsByGenre', 'createLike', 'createBookmark']),
    clubDetail(clubId) {
      this.$router.push({ name: 'ClubDetail', params: { clubId: clubId }})
    },
    postDetail(postId) {
      this.$router.push({ name: 'PostDetail', params: { postId: postId }})
    },
    bookDetail(bookId) {
      this.$router.push({ name: 'BookDetail', params: { bookId: bookId}})
    },
    clickUpdateProfile() {
      this.updateProfile(this.profileUpdateData)
      this.dialog = close
    },
    clickedFollow(user, type) {
      if (type === 'unfollow') {
        if (confirm('팔로우를 취소하시겠습니까?') === true) {
          user['isFollow'] = false
        } else {  
          return false
        }
      } else {
        user['isFollow'] = true
      }
      this.clickFollow(user.id)
      this.$forceUpdate();
    },
    selectUser(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    },
    remove (data, item) {
      const index = data.indexOf(item.id)
      if (index >= 0) data.splice(index, 1)
    },
    isGenreNull() {
      this.$nextTick(() => {
        this.searchGenre = null
      })
    },
    validate() {
      this.$refs.form.validate()
    },
  },
  created() {
    this.fetchPostsByPopularity()
    this.fetchPostsByFollow()
    this.findMyAccount()
    this.fetchPostsByGenre()
  },
  beforeUpdate() {
    this.profileUpdateData.basicData.nickName = this.myaccount.nickName;
    this.profileUpdateData.basicData.description = this.myaccount.description;
  },
  updated() {
    function stopWheel(e){
      if(!e){ e = window.event; } /* IE7, IE8, Chrome, Safari */
      if(e.preventDefault) { e.preventDefault(); } /* Chrome, Safari, Firefox */
      e.returnValue = false; /* IE7, IE8 */
    }

    const scrollAreaPopularity = document.querySelector('#scroll-area-popularity')
    if (scrollAreaPopularity) {
      scrollAreaPopularity.addEventListener('wheel', (e) => {
        scrollAreaPopularity.scrollLeft += e.deltaY;
        stopWheel()
      })
    }

    const scrollAreaFollow = document.querySelector('#scroll-area-follow')
    if (scrollAreaFollow) {
      scrollAreaFollow.addEventListener('wheel', (e) => {
        scrollAreaFollow.scrollLeft += e.deltaY;
        stopWheel()
      })
    }

    this.postsByGenre.forEach(postSet => {
      if (document.querySelector(`#scroll-area-${postSet.genre}`)) {
        document.querySelector(`#scroll-area-${postSet.genre}`).addEventListener('wheel', (e) => {
          document.querySelector(`#scroll-area-${postSet.genre}`).scrollLeft += e.deltaY;
          stopWheel()
        })
      }
    });
  },
  beforeRouteUpdate() {
    this.fetchPostsByPopularity()
    this.fetchPostsByFollow()
    this.findMyAccount()
    this.fetchPostsByGenre()
  }
}

</script>



<style scoped>

.posts-banner {
  position: relative;
}

.posts-banner-img {
  width: 100%;
  height: 200px;
  vertical-align: middle;
  filter: brightness(0.7)
}

.posts-banner-text {
  color: #F8F8F8;
  text-align: center;
  text-shadow: 2px 2px 2px rgb(100, 100, 100);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate( -50%, -50% );
}

/* .row {
  overflow: scroll;
  width: 100%;
} */

.row-inner {
  white-space: nowrap;
  transition: 0.45s all;
  /* margin: 50px 10px; */
  /* padding-left: 45px;
  padding-right: 60px; */
}

.tile {
  position: relative;
  display:inline-block;
  margin: 0 5px;
  transition: 0.45s all;
  transform-origin: left center;
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
}

img {
  width:160px;
  height:250px;
  object-fit:cover;
}

.book-reviews {
  margin-bottom: 30px;
  margin-left: 0;
  margin-right: 0;
}

.row-inner:hover
{
  /*move to the left */
  transform: translateX(calc(250px*(-0.5)/2));
  margin: 60px 10px;
  padding-left: 45px;
  padding-right: 60px;
}

.row-inner:hover .tile {
  opacity: .3;
}

.row-inner:hover .tile:hover {
  /*set opacity back to 1 */
  transform: scale(1.5);
  opacity: 1;
}

.tile:hover ~ .tile {
  /* move tiles on the right to the right*/
  transform: translateX(calc(250px * 0.5));
}

.scroll-sect{
  overflow: hidden;
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

.scroll-sect:hover{
  overflow-x: scroll;
}

@media screen and (min-width: 1264px) {
  .custom-offset-lg-0 {
      /* margin-left: 6% !important; */
      max-width: 823.46px;
      margin-left: auto !important;
      margin-right: auto !important;
  }
  .custom-d-md-block {
    display: inline-block!important;
    max-width: 174.6px;
    /* width: 15% */
  }
}

@media screen and (min-width: 600px) {
  .custom-offset-sm-2 {
      margin-left: 16.666667%;
  }
}

.content {
  position: relative;
  max-width: 160px;
  margin: auto;
  overflow: inherit;
}

.content .content-overlay {
  background: rgba(0,0,0,0.7);
  position: absolute;
  width: 100%;
  left: 0;
  top: 0;
  bottom: 0;
  right: 0;
  opacity: 0;
  -webkit-transition: all 0.4s ease-in-out 0s;
  -moz-transition: all 0.4s ease-in-out 0s;
  transition: all 0.4s ease-in-out 0s;
}

.content:hover .content-overlay{
  width: 100%;
  opacity: 1;
}

.content-details {
  position: absolute;
  text-align: center;
  padding-left: 0.3em;
  padding-right: 0.3em;
  width: 160px;
  top: 50%;
  left: 50%;
  opacity: 0;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  -webkit-transition: all 0.3s ease-in-out 0s;
  -moz-transition: all 0.3s ease-in-out 0s;
  transition: all 0.3s ease-in-out 0s;
}

.content:hover .content-details{
  top: 50%;
  left: 50%;
  opacity: 1;
}

.content-details p{
  color: #fff;
  padding-left: 5px;
  font-size: 0.75em;
  font-weight: 300;
  letter-spacing: 0.15em;
  margin-bottom: 0.5em;
  text-transform: uppercase;
  white-space: pre-wrap !important;
  word-break: keep-all !important;
}

.content-details small{
  width: 160px;
  color: #fff;
  font-size: 0.5em;
}

.fadeIn-top{
  top: 20%;
}

.quote {
  color: white;
  font-size: 0.2em;
}

.profile-img {
  width: 25px;
  height: 25px;
  border-radius: 50%;
}


.v-card .row {
  overflow: hidden;
}

.feed-container {
  margin: -20px 0px;
}

.feed-titles {
  text-shadow: 2px 2px 2px rgb(206, 206, 206);
  margin-bottom: 0;
  text-align: left;
  font-weight: bold;
  transform: translateY(80%);
}

.slide-container {
  padding-top: 20px;
  min-height: 370px;
}

.custom-slide {
  position: relative;
  display:inline-block;
  transition: 0.45s all;
  transform: translateY(10%);
  transform-origin: center;
  width: 200px;
  min-height: 290px;
}

.custom-slide:hover {
  transform: scale(1.3);
  transition: 0.45s all;
  transform-origin: center;
  padding: 30px 0px;
}

/* books */
.book-container {
  width: 70%
}

.book-card {
  width: 200px;
  box-shadow: 8px 8px 10px 3px rgb(0, 0, 0, 0.2)
}

.book-image {
  display: block;
  width: 100%;
  height: auto;
}

.book-card .overlay {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  /* transition: .5s ease; */
  background-color: rgba(0, 0, 0, 0.7);
}

.book-card:hover .overlay {
  opacity: 1;
  -webkit-transition: all 0.4s ease-in-out 0s;
  -moz-transition: all 0.4s ease-in-out 0s;
  transition: all 0.4s ease-in-out 0s;
}
.book-card:hover .book-text {
  top: 50%;
  left: 50%;
  opacity: 1;
}

.book-text {
  width: 80%;
  color: white;
  font-size: 1em;
  position: absolute;
  padding-left: 0.05em;
  padding-right: 0.05em;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  transition: all 0.3s ease-in-out 0s;
  text-align: center;
  -webkit-line-clamp: 6; 
}

  
.book-text p {
  color: #fff;
  text-shadow: 1px 1px 2px rgb(0,0,0,0.7);
  font-size: 1em;
  font-weight: 300;
  white-space: pre-wrap;
  word-break: keep-all;
  overflow: hidden;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: 4; 
  -webkit-box-orient: vertical;
}

/* recommendedUsers */
.user {
  border: 1px solid rgb(0, 0, 0, 0.2);
  border-radius: 10px;
  width: 65%;
  box-shadow: 5px 5px 10px 1px rgb(0, 0, 0, 0.2);
}

.profile-card {
  width: 120px;
  border-radius: 15px;
  background-color: #fff;
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
}

.profile-card:hover {
  width: 120px;
  border-radius: 15px;
  background-color: #fff;
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.8);
}

.profile-image img {
  border: 1px solid rgb(0, 0, 0, 0.2);
  border-radius: 50%;
  width: 50px;
  height: 50px;
}

.profile-card p {
  overflow: hidden;
  white-space: normal;
  word-wrap: break-word;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1; 
  -webkit-box-orient: vertical;
  text-align: start;
}

.profile-card p:hover {
  color: #88A498;
}

.user-nickName {
  line-height: 60px;
}

.btn-following {
  background-color: #88A498;
  color: #F8F8F8;
}

.btn-follow {
  border-color: #88A498 !important;
  color: #88A498 !important;
}

.recommended-book {
  perspective: 500px;
  position: relative;
  z-index: 2;
  display: block;
  margin: 0 auto;
  width: 112px;
  height: auto;
  box-shadow: 10px 10px 30px rgba(0, 0, 0, 0.4);
  transform-origin: 0 0;
  transform-style: preserve-3d;
  transform: rotateY(0);
  transition: all 500ms;
}

.recommended-book img {
  max-width: 100%;
}

.recommended-book:after {
  content: "";
  position: absolute;
  display: block;
  top: 2px;
  right: -4px;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAG0lEQVQIW2P8/////w8fPjAICAgwwGhGGggCAOYAMfLhHG5wAAAAAElFTkSuQmCC) repeat;
  width: 4px;
  height: calc(100% - 3px);
  transform-origin: 0 100%;
  transform-style: preserve-3d;
  transform: rotateY(35deg);
  transform-origin: right;
  transition: all 500ms;
}

.recommended-book:hover {
  box-shadow: 20px 10px 50px rgba(0, 0, 0, 0.4);
  transform: rotateY(-20deg);
}

.recommended-book:hover:after {
  right: -15px;
  width: 15px;
}

.book-img {
  width: 112px;
  height: 175px;
}

.small-btn {
  font-size: 0.8rem;
}

.small-btn:hover {
  font-size: 0.8rem;
  background-color: #88A498;
  color: #F8F8F8 !important;
}

/* tooltip */
[tooltip] {
  position: relative; /* opinion 1 */
}
/* START TOOLTIP STYLES */
[tooltip] {
  position: relative; /* opinion 1 */
}
/* Applies to all tooltips */
[tooltip]::before,
[tooltip]::after {
  text-transform: none; /* opinion 2 */
  font-size: .8em; /* opinion 3 */
  line-height: 1;
  user-select: none;
  pointer-events: none;
  position: absolute;
  display: none;
  opacity: 0;
}
[tooltip]::before {
  content: '';
  border: 5px solid transparent; /* opinion 4 */
  z-index: 1001; /* absurdity 1 */
}
[tooltip]::after {
  content: attr(tooltip); /* magic! */
  
  /* most of the rest of this is opinion */
  font-family:  'Noto Sans KR', Helvetica, sans-serif;
  text-align: center;
  
  /* 
    Let the content set the size of the tooltips 
    but this will also keep them from being obnoxious
    */
  min-width: 3em;
  max-width: 21em;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding: 1ch 1.5ch;
  border-radius: .3ch;
  box-shadow: 0 1em 2em -.5em rgba(0, 0, 0, 0.35);
  background: #333;
  color: #fff;
  z-index: 1000; /* absurdity 2 */
}
/* Make the tooltips respond to hover */
[tooltip]:hover::before,
[tooltip]:hover::after {
  display: block;
}
/* don't show empty tooltips */
[tooltip='']::before,
[tooltip='']::after {
  display: none !important;
}
/* FLOW: RIGHT */
[tooltip][flow^="right"]::before {
  top: 50%;
  border-left-width: 0;
  border-right-color: #333;
  right: calc(0em - 5px);
  transform: translate(.5em, -50%);
}
[tooltip][flow^="right"]::after {
  top: 50%;
  left: calc(100% + 5px);
  transform: translate(.5em, -50%);
}


/* FLOW: DOWN */
[tooltip][flow^="down"]::before {
  top: 100%;
  border-top-width: 0;
  border-bottom-color: #333;
}
[tooltip][flow^="down"]::after {
  top: calc(100% + 5px);
}
[tooltip][flow^="down"]::before,
[tooltip][flow^="down"]::after {
  left: 50%;
  transform: translate(-50%, .5em);
}


/* KEYFRAMES */
@keyframes tooltips-vert {
  to {
    opacity: .9;
    transform: translate(-50%, 0);
  }
}
@keyframes tooltips-horz {
  to {
    opacity: .9;
    transform: translate(0, -50%);
  }
}
/* FX All The Things */ 
[tooltip]:not([flow]):hover::before,
[tooltip]:not([flow]):hover::after,
[tooltip][flow^="up"]:hover::before,
[tooltip][flow^="up"]:hover::after,
[tooltip][flow^="down"]:hover::before,
[tooltip][flow^="down"]:hover::after {
  animation: tooltips-vert 300ms ease-out forwards;
}
[tooltip][flow^="left"]:hover::before,
[tooltip][flow^="left"]:hover::after,
[tooltip][flow^="right"]:hover::before,
[tooltip][flow^="right"]:hover::after {
  animation: tooltips-horz 300ms ease-out forwards;
}
</style>