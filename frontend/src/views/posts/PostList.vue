<template>
  <v-app class="custom-container">

    <!-- posts-banner -->
    <div class="posts-banner">
      <img
        class="posts-banner-img"        
        :src="bannerImages[2]"
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

    <div v-if="myaccount.likeGenres.length">
      <div class="m-0 my-5">
        <h5 class="feed-titles">#인기 리뷰</h5>
        <div class="row scroll-sect book-reviews" id="scroll-area-popularity">
          <div class="row-inner">
            <div class="tile pointer" v-for="post in postsByPopularity" :key="`post_${post.id}`"  @click="postDetail(post.id)">
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
          </div>
        </div>
      </div>

      <div class="m-0 my-5">
        <h5 class="feed-titles">#팔로잉 리뷰</h5>
        <div class="row scroll-sect book-reviews" id="scroll-area-follow" v-if="postsByFollow.length">
          <div class="row-inner">
            <div class="tile pointer" v-for="post in postsByFollow" :key="`post_${post.id}`"  @click="postDetail(post.id)">
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
          </div>
        </div>
        <div class="d-flex flex-column justify-content-center align-items-center" style="min-height: 200px" v-else >
          <h3>아직 팔로우 하는 유저가 없습니다 ㄴ(°0°)ㄱ</h3>
          <p>새로운 유저를 팔로우 하고 리뷰를 받아보세요 :)</p>
        </div>
      </div>

      <div class="m-0 my-5" v-for="postSet in postsByGenre" :key="postSet.genre">
        <h5 class="feed-titles">#{{ postSet.genre }} 리뷰</h5>
        <div class="row scroll-sect book-reviews" :id="'scroll-area-' + postSet.genre" v-if="postSet.posts.length">
          <div class="row-inner">
            <div class="tile pointer" v-for="post in postSet.posts" :key="`post_${post.id}`"  @click="postDetail(post.id)">
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
          </div>
        </div>
        <div class="d-flex flex-column justify-content-center align-items-center" style="min-height: 200px" v-else >
          <h3>아직 {{ postSet.genre }} 장르의 리뷰가 없습니다 ㄴ(°0°)ㄱ</h3>
          <p>{{ postSet.genre }} 장르의 첫 번째 리뷰를 작성해보세요 :)</p>
        </div>
        
      </div>
      <!-- <infinite-loading class="col-12" @infinite="infiniteHandler" spinner="waveDots"></infinite-loading> -->
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
import { mapState, mapActions } from 'vuex'
// import InfiniteLoading from 'vue-infinite-loading'
import router from '@/router'

export default {
  name: 'PostList',
  // components: {
  //   InfiniteLoading
  // },
  data() {
    return {
      bannerImages: [
        'https://user-images.githubusercontent.com/57381062/90099601-d6a0dc80-dd75-11ea-82b9-841824eb5e91.jpg',
        'https://user-images.githubusercontent.com/57381062/90129061-ee428a00-dda2-11ea-9e31-f6cfe006a951.jpg',
        'https://user-images.githubusercontent.com/57381062/90130466-7a55b100-dda5-11ea-9960-e061294dbd34.jpg',
        'https://user-images.githubusercontent.com/57381062/90130484-85104600-dda5-11ea-9167-7dcd85b5e810.jpg',
        'https://user-images.githubusercontent.com/57381062/90130506-8b9ebd80-dda5-11ea-8354-7ce8458fb3f8.jpg'
      ],
      // postList: [], 
      // page: 3,
      // limit: 0,
      // loading: false,
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
    ...mapState('postStore', ['postsByPopularity', 'postsByFollow', 'postsByGenre']),
  },
  methods: {
    ...mapActions(['findMyAccount']),
    ...mapActions('profileStore', ['updateProfile']),
    ...mapActions('postStore', ['fetchPostsByPopularity', 'fetchPostsByFollow', 'fetchPostsByGenre', 'createLike', 'createBookmark']),
    clubDetail(clubId) {
      this.$router.push({ name: 'ClubDetail', params: { clubId: clubId }})
    },
    postDetail(postId) {
      this.$router.push({ name: 'PostDetail', params: { postId: postId }})
    },
    clickUpdateProfile() {
      
      this.updateProfile(this.profileUpdateData)
      
      this.dialog = close
    },
    // getPosts() {
    //   if (this.posts.slice(this.page, this.page + 3).length) {
    //     this.postList = this.postList.concat(this.posts.slice(this.page, this.page + 3))
    //     this.page += 3
    //   }
    // },
    // infiniteHandler($state) {
    //   setTimeout(() => {
    //     if (this.posts.slice(this.page, this.page + 3).length) {
    //       this.postList = this.postList.concat(this.posts.slice(this.page, this.page + 3))
    //       $state.loaded();
    //       this.page += 3
    //     } else {
    //       $state.complete();
    //     }
    //   }, 1000)
    // },
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
  // watch: {
  //   posts() {
  //     this.postList = this.posts.slice(0, 3)
  //   }
  // },
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

.row {
  overflow: scroll;
  width: 100%;
}

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
  /* height: auto; */
  border-radius: 50%;
}

.feed-titles {
  text-shadow: 2px 2px 2px rgb(206, 206, 206);
  margin-bottom: 1rem;
  text-align: left;
  font-weight: bold;
}

.v-card .row {
  overflow: hidden;
}
</style>