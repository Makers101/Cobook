<template>
  <div class="mt-3">
    <div class="row rows-cols-1 row-cols-md-3">
      <div class="col-12 col-sm-4 mb-4" v-for="feed in this.feeds" :key="feed.id">
        <div class="card h-100" @click="selectFeed(feed.id)">
           <div style="max-height:70px;overflow:hidden;">
            <!--  style="position:absolute; clip:rect(0px, 100px, 100px, 0px)" -->
            <img class="bg-image" :src="`${ feed.book.bookImg }`" >
            <h5 
              class="card-img-top color-light-black px-5 book-title" 
              alt="book" >
              {{ feed.book.title }}</h5>
          </div>
          <div class="card-body bg-light-ivory d-flex flex-column">
            <div class="mt-auto">
              <div class="w-100">
                <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
                <p class="card-text" style="word-break:keep-all;">{{ feed.onelineReview }}</p>
                <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
              </div>
            </div>
            <div class="text-left bg-light-ivory pt-0 mt-auto">
              <span class="mr-3"><i class="fas fa-heart mr-2"></i>{{ feed.likeUsers.length }}</span>
              <span><i class="fas fa-comment mr-2"></i>{{ feed.commentCnt }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'ProfileFeed',
  data() {
    return {
    }
  },
  computed: {
    ...mapState('profileStore',['feeds']),
    ...mapState(['myaccount'])
  },
  methods: {
    ...mapActions('profileStore', ['fetchFeeds']),
    selectFeed(id) {
      this.$router.push({name: 'PostDetail', params: {postId: id}})
    }
  },
  created() {
    this.fetchFeeds(this.$route.params.userId)
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

.card {
  cursor: pointer;
}

</style>