<template>
  <div class="row">
    <v-row justify="center" v-if="myaccount.likeGenres">
      <v-dialog v-model="dialog" persistent max-width="600px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="primary"
            dark
            v-bind="attrs"
            v-on="on"
          >
            Open Dialog
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">User Profile</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field label="Legal first name*" required></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field label="Legal middle name" hint="example of helper text only on focus"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field
                    label="Legal last name*"
                    hint="example of persistent helper text"
                    persistent-hint
                    required
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Email*" required></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Password*" type="password" required></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-select
                    :items="['0-17', '18-29', '30-54', '54+']"
                    label="Age*"
                    required
                  ></v-select>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-autocomplete
                    :items="['Skiing', 'Ice hockey', 'Soccer', 'Basketball', 'Hockey', 'Reading', 'Writing', 'Coding', 'Basejump']"
                    label="Interests"
                    multiple
                  ></v-autocomplete>
                </v-col>
              </v-row>
            </v-container>
            <small>*indicates required field</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
            <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <div class="d-none custom-d-md-block col-2" v-if="myaccount">
      <div class="myclub border rounded">
        <div class="myclub-header font-weight-extrabold mt-3">내 클럽</div>
        <hr>
        <div class="myclub-content">
          <div
            class="mb-3 pointer"
            v-for="club in myaccount.myClubs"
            :key="`club-${club.id}`"
            @click="clubDetail(club.id)"
          >
            <div class="row">
              <img class="offset-1 col-3 p-2 border rounded-circle img-fluid" :src="club.clubImg" alt="클럽 프로필 사진">
              <div class="col-8 font-weight-bold text-left">
                <p class="mb-0">{{ club.name }}</p>
              </div>
            </div>
            <hr>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 custom-offset-sm-2 col-sm-8 custom-offset-lg-0 col-lg-8 mt-3">
      <div
        class="post mb-5"
        v-for="post in postList"
        :key="`post-${post.id}`"
        >
        <div class="post-header d-flex justify-content-between py-2">
          <div class="ml-3 pointer" @click="selectUser(post.user.id)">
            <span class="rounded-circle">
              <img
                v-if="!post.user.profileImg"
                class="img-fluid feed-profile-img mr-2" 
                src="https://user-images.githubusercontent.com/57381062/88908958-84e85480-d295-11ea-9637-540f1be674ac.png" 
                alt="유저 프로필 사진">
              <img 
                v-else
                class="img-fluid feed-profile-img mr-2" 
                :src="post.user.profileImg" alt="작성자 프로필 사진">
            </span>
            <span>{{ post.user.nickName }}</span>
            <span v-if="post.isClub" class="badge bg-green">Club</span>
          </div>
          <div class="color-beige small-text">
            <span class="pointer text-center" @click="postDetail(post.id)">
              <i class="fas fa-comments mr-2" id="heart"></i><small>{{ post.commentCnt }}</small>
            </span>
            <span class="pointer text-center" @click="clickBookmark(post)">
              <span v-if="checkBookmark(post)"><i class="fas fa-bookmark mx-2 color-green" id="bookmark"></i><small class="mr-3">{{ post.bookmarkUsers.length }}</small></span>
              <span v-else><i class="fas fa-bookmark mx-2" id="bookmark"></i><small class="mr-3">{{ post.bookmarkUsers.length }}</small></span>
            </span>
          </div>
        </div>
        <div class="post-content bg-light-beige row no-gutters pointer d-flex justify-content-around" @click="postDetail(post.id)">
          <div class="my-5">
            <img style="height: 30vh;" :src="post.book.bookImg" alt="책 이미지" class="rounded">
          </div>
          <div class="d-flex align-items-center justify-content-center w-50">
            <div class="w-75 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="d-flex justify-content-center"><p class="my-2 w-50">{{ post.onelineReview }}</p></div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
        </div>
        <div class="post-footer text-left pl-3 py-2 d-flex justify-content-between">
          <span class="pointer" @click="clickLike(post)">
            <span v-if="checkHeart(post)"><i class="fas fa-heart mr-2 heartselected"></i>
              <small class="mr-3"><span>{{ myaccount.nickName}}님</span>
              <span v-if="post.likeUsers.length -1 > 0"> 외 {{ post.likeUsers.length - 1}}명이 좋아합니다.</span>
              <span v-else>이 좋아합니다.</span>
              </small>
            </span>
            <span v-else><i class="fas fa-heart mr-2"></i><small class="mr-3">{{ post.likeUsers.length }}명이 좋아합니다.</small></span>
          </span>
          <div>
            <span
            class="badge rounded-pill px-3 py-2 mr-2 bg-green"
            v-for="tag in post.tags"
            :key="`tag-${tag.id}`"
            style="background-color:#907a62; color: white;"
          >
          #{{ tag.name }}</span>
          </div>
          
        </div>
      </div>
    </div>
    <!-- <div class="d-none custom-d-md-block col-2">
      <div class="recommend border rounded">
        <div class="recommend-header font-weight-extrabold mt-3">추천하는 책</div>
        <hr>
        <div class="recommend-content">
          <div class="row mb-3">
            <div class="offset-1 col-3">
              <img style="height: 10vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
            </div>
            <div class="col-8">
              <p>작가 : 000</p>
              <p>출판사 : 000</p>
            </div>
          </div>
          <div class="row">
            <div class="offset-1 col-3">
              <img style="height: 10vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
            </div>
            <div class="col-8">
              <p>작가 : 000</p>
              <p>출판사 : 000</p>
            </div>
          </div>
        </div>
      </div>
    </div> -->
    <infinite-loading class="col-12" @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
    <!-- <div id="bottomSensor"></div> -->
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import InfiniteLoading from 'vue-infinite-loading'
import router from '@/router'

export default {
  name: 'PostList',
  components: {
    InfiniteLoading
  },
  data() {
    return {
      postList: [], 
      page: 3,
      limit: 0,
      loading: false,
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('postStore', ['posts']),
  },
  methods: {
    ...mapActions('postStore', ['fetchPosts', 'createLike', 'createBookmark']),
    clubDetail(clubId) {
      this.$router.push({ name: 'ClubDetail', params: { clubId: clubId }})
    },
    postDetail(postId) {
      this.$router.push({ name: 'PostDetail', params: { postId: postId }})
    },
    // postDetailComment(postId) {
    //   this.$router.push('/posts/' + postId + '#comment')
    // },
    clickLike(post) {
      this.createLike(post.id)
      if (post.likeUsers.includes(this.myaccount.id)) {
        const idx = post.likeUsers.indexOf(this.myaccount.id)
        if (idx > -1) post.likeUsers.splice(idx, 1)
      } else {
        post.likeUsers.push(this.myaccount.id)
      }
    },
    clickBookmark(post) {
      this.createBookmark(post.id)
      if (post.bookmarkUsers.includes(this.myaccount.id)) {
        const idx = post.bookmarkUsers.indexOf(this.myaccount.id)
        if (idx > -1) post.bookmarkUsers.splice(idx, 1)
      } else {
        post.bookmarkUsers.push(this.myaccount.id)
      }
    },
    checkHeart(post){
      if (post.likeUsers.includes(this.myaccount.id)) {
        return true;
      }
    },
    checkBookmark(post) {
      if (post.bookmarkUsers.includes(this.myaccount.id)) {
        return true;
      }
    },
    getPosts() {
      if (this.posts.slice(this.page, this.page + 3).length) {
        this.postList = this.postList.concat(this.posts.slice(this.page, this.page + 3))
        this.page += 3
      }
    },
    // onScroll ({ target: { scrollTop, clientHeight, scrollHeight }}) {
    //   console.log(1)
    //   console.log(scrollTop, clientHeight, scrollHeight)
    //   if (scrollTop + clientHeight >= scrollHeight) {
    //     this.getPosts()
    //   }
    // },
    // addScrollWatcher: function() {
    //   const bottomSensor = document.querySelector('#bottomSensor')
    //   const watcher = scrollMonitor.create(bottomSensor)
    //   // watcher가 화면에 들어오면, callback 하겠다.
    //   watcher.enterViewport(() => {
    //     setTimeout(() => {
    //       this.getPosts()
    //     }, 500)
    //   })
    // },
    

    // loadUntilViewportIsFull: function() {
    //   const bottomSensor = document.querySelector('#bottomSensor')
    //   const watcher = scrollMonitor.create(bottomSensor)
    //   if (watcher.isFullyInViewport){
    //     this.getPosts()
    //   }
    // },
    infiniteHandler($state) {
      setTimeout(() => {
        if (this.posts.slice(this.page, this.page + 3).length) {
          this.postList = this.postList.concat(this.posts.slice(this.page, this.page + 3))
          $state.loaded();
          this.page += 3
        } else {
          $state.complete();
        }
      }, 1000)
    },
    // onBottom() {
    //   setTimeout(function() {
    //     const el = document.querySelector("#bottomSensor");
    //     console.log(el.scrollTop)
    //   }, 1000)
    // },
    selectUser(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    }
  },
  watch: {
    posts() {
      this.postList = this.posts.slice(0, 3)
    }
  },
  created() {
    this.fetchPosts()
  },
  // mounted() {
    // window.addEventListener('scroll', this.onScroll(window))
    // const listElm = document.querySelector('#scroll');
    // listElm.addEventListener('scroll', () => {
    //   console.log(1)
    //   if(listElm.scrollTop + listElm.clientHeight >= listElm.scrollHeight) {
    //     this.getPosts();
    //   }
    // });
  //   this.addScrollWatcher()
  //   window.$(window).scroll(function() {
  //     if(window.$(window).scrollTop() + window.$(window).height() == window.$(document).height()) {
  //       console.log(window.$(window).scrollTop())
  //     }
  //   });
  // },
  // updated() {
  //   this.loadUntilViewportIsFull()
  // }

}
</script>

<style scoped>
.post-header, .post-footer {
  border: 1px solid #D6CBBD ;
}

.feed-profile-img {
  height: 25px;
  width: 25px;
  border-radius: 50%;
}

.small-text {
  font-size: 0.8rem;
}

.large-text {
  font-size: 1.3rem;
}

.myclub {
  position: fixed;
  top: 200px;
  left: 3.5%;
}

.recommend {
  position: fixed;
  top: 200px;
  right: 3.5%;
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

.heartselected {
  color: red !important;
}

.club-img {
  border-radius: 50%;
}
</style>