<template>
  <div class="mt-3">
    <div class="row rows-cols-1 row-cols-md-3" v-if="bookmarks.length">
      <div class="col-12 col-sm-4 mb-4" v-for="bookmark in bookmarks" :key="bookmark.id">
        <div class="card h-100" @click="selectFeed(bookmark.id)">
           <div style="max-height:70px;overflow:hidden;">
            <!--  style="position:absolute; clip:rect(0px, 100px, 100px, 0px)" -->
            <img class="bg-image" :src="`${ bookmark.book.bookImg }`" >
            <h5 
              class="card-img-top color-light-black px-5 book-title" 
              alt="book" >
              {{ bookmark.book.title }}</h5>
          </div>
          <div class="card-body bg-light-ivory d-flex flex-column">
            <div class="mt-auto">
              <div class="w-100">
                <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
                <p class="card-text" style="word-break:keep-all;">{{ bookmark.onelineReview }}</p>
                <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
              </div>
            </div>
            <div class="text-left bg-light-ivory pt-0 mt-auto">
              <span class="mr-3"><i class="fas fa-heart mr-2"></i>{{ bookmark.likeUsers.length }}</span>
              <span><i class="fas fa-comment mr-2"></i>{{ bookmark.commentCnt }}</span>
            </div>
          </div>
        </div>
       
      </div>
      
    </div>
    <div v-else>
      <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
      <h3 class="mt-3">현재 <strong>{{ this.profile.nickName }}</strong>님이 북마크한 피드가 없습니다. </h3>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ProfileBookmark',
  data() {
    return {
    }
  },
  computed: {
    ...mapState('profileStore',['bookmarks', 'profile']),
    ...mapState(['myaccount'])
  },
  methods: {
    ...mapActions('profileStore', ['fetchBookmarks']),
    selectFeed(id) {
      this.$router.push({name: 'PostDetail', params: {postId: id}})
    }
  },
  created(){
    this.fetchBookmarks(this.$route.params.userId)
  }
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
  position: absolute;
  top: 3%;
  left: 0;
  text-shadow: 1px 1px 2px white;
  font-weight: 900;
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

</style>