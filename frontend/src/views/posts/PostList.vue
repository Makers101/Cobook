<template>
  <div class="container">
    <div class="m-0">
      <h2 class="mt-3 text-left"> 팔로워 리뷰</h2>
      <div class="row scroll-sect book-reviews" id="scroll-area">
        <div class="row-inner">
            <div class="tile pointer" v-for="post in posts" :key="`post_${post.id}`"  @click="postDetail(post.id)">
                <div class="tile-media">
                  <div class="content">
                    <div class="content-overlay"></div>
                    <img :src="post.book.bookImg" alt="책 표지 이미지" class="content-image">
                    <div class="content-details fadeIn-top">
                      <p><span v-for="index in post.rank" :key="index"> <i class="fas fa-star" style="color:yellow"></i> </span></p>
                      <div class="text-left p-0 m-0"><i class="fas fa-quote-left quote"></i></div>
                      <p class="m-0" @click="postDetail(post.id)">{{ post.onelineReview }}</p> 
                      <div class="text-right p-0 m-0"><i class="fas fa-quote-right quote"></i></div>
                      <small @click="postDetail(post.id)">{{ post.user.nickName }}</small> 
                    </div>
                  </div>
                </div>
            </div>
        </div>
      </div>

    </div>

    <!-- <infinite-loading class="col-12" @infinite="infiniteHandler" spinner="waveDots"></infinite-loading> -->
  </div>
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
      // postList: [], 
      // page: 3,
      // limit: 0,
      // loading: false,
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('postStore', ['posts']),
  },
  methods: {
    ...mapActions(['findMyAccount']),
    ...mapActions('postStore', ['fetchPosts', 'createLike', 'createBookmark']),
    clubDetail(clubId) {
      this.$router.push({ name: 'ClubDetail', params: { clubId: clubId }})
    },
    postDetail(postId) {
      this.$router.push({ name: 'PostDetail', params: { postId: postId }})
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
    }
  },
  // watch: {
  //   posts() {
  //     this.postList = this.posts.slice(0, 3)
  //   }
  // },
  created() {
    this.fetchPosts()
    this.findMyAccount()
  },

}
document.addEventListener('wheel', (e) => {
    document.getElementById('scroll-area').scrollLeft += e.deltaY;
})

</script>

<style scoped>
.row {
  overflow: scroll;
  width: 100%;
}

.row-inner {
  white-space: nowrap;
  transition: 0.45s all;
  margin: 50px 10px;
  padding-left: 45px;
  padding-right: 60px;
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
}

.row-inner:hover
{
  /*move to the left */
  transform: translateX(calc(250px*(-0.5)/2));
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
  background: #345389; 
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

</style>