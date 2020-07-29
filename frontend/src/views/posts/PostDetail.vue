<template>
  <div class="custom-container mt-3">
    <div class="post mb-5">
      <div class="post-header d-flex justify-content-between mb-2">
        <div>
          <span class="rounded-circle">
            <img
              v-if="!selectedPost.user.profileImg"
              class="img-fluid feed-profile-img" 
              src="@/assets/anonymous user.png" 
              alt="유저 프로필 사진">
            <img 
              v-else
              class="img-fluid feed-profile-img" 
              :src="selectedPost.user.profileImg" alt="작성자 프로필 사진">
          </span>
          {{ selectedPost.user.nickName }}
          <span v-if="selectedPost.isClub" class="badge bg-green">Club</span>
        </div>
        <div class="color-beige small-text">
          <span class="pointer" @click="clickLike(selectedPost)">
            <i class="fas fa-heart mr-2"></i><small class="mr-3">{{ selectedPost.likeUsers.length }}</small>
          </span>
          <!-- <span>
            <i class="fas fa-comments mr-2"></i><small class="mr-3">{{ selectedPost.commentCnt }}</small>
          </span> -->
          <span class="pointer" @click="clickBookmark(selectedPost)">
            <i class="fas fa-bookmark mr-2"></i><small class="mr-3">{{ selectedPost.bookmarkUsers.length }}</small>
          </span>
        </div>
      </div>
      <div class="post-content">
        <div class="row bg-light-beige">
          <div class="col-3 my-5">
            <img style="height: 20vh;" :src="selectedPost.book.bookImg" alt="책 이미지">
          </div>
          <div class="col-9 my-5 text-left">
            <h5 class="font-weight-bold">{{ selectedPost.book.title }}</h5>
            <p>- {{ selectedPost.book.author }} -</p>
            <p>{{ selectedPost.book.publisher }}</p>
            <p>{{ selectedPost.book.pubDate.slice(0,4) }}년 {{ selectedPost.book.pubDate.slice(5,7) }}월 {{ selectedPost.book.pubDate.slice(8,10) }}일</p>
          </div>
        </div>
        <div>
          <div class="col-12 mt-3">
            <div class="w-100 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="d-flex justify-content-center"><p class="my-2 w-50">{{ selectedPost.onelineReview }}</p></div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
          <hr>
          <div class="col-12 my-5">
            <div class="w-100">
              <div v-html="selectedPost.review"></div>
            </div>
          </div>
        </div>
      </div>
      <hr>
      <div class="post-footer text-right mt-2">
        <span
          class="badge bg-green rounded-pill px-3 py-2 mr-2"
          v-for="tag in selectedPost.tags"
          :key="`tag-${tag.id}`"
          >#{{ tag.name }}</span>
      </div>
    </div>
    <hr>
    <div class="comment mb-5">
      <h5 class="text-left">댓글</h5>
      <div class="input-group row mb-5">
        <textarea
          class="col-11" 
          @keyup.enter="clickComment" 
          @input="activeBtn"
          v-model="commentCreateData.content" 
          type="content" 
          placeholder="댓글을 작성하세요 :)" 
          rows="2" 
        ></textarea>
        <button 
          :class="{ 'btn-green': btnActive, 'pointer': btnActive }"
          class="btn col-1"
          :disabled="!btnActive"
          @click="clickComment"
        >
        작성</button>
      </div>
      <div
        v-for="comment in comments"
        :key="`comment-${comment.id}`"
      >
        <div class="col-12 text-left">
          <span class="rounded-circle">
            <img
              v-if="!selectedPost.user.profileImg"
              class="img-fluid feed-profile-img" 
              src="@/assets/anonymous user.png" 
              alt="유저 프로필 사진">
            <img 
              v-else
              class="img-fluid feed-profile-img" 
              :src="comment.user.profileImg" alt="작성자 프로필 사진">
          </span>
          {{ comment.user.nickName }}
          <span v-if="comment.isClub" class="badge bg-green">Club</span>
        </div>
        <div class="col-12 text-left">
          <div>{{ comment.content }}</div>
        </div>
        <hr>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'PostDetail',
  data() {
    return {
      commentCreateData: {
        postId: null,
        content: null
      },
      btnActive: false,
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('postStore', ['selectedPost', 'comments']),
  },
  methods: {
    ...mapActions('postStore', ['findPost', 'fetchComments', 'createComment', 'createLike', 'createBookmark']),
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
    clickComment() {
      this.createComment(this.commentCreateData)
        .then(() => {
          this.commentCreateData.content = null
          this.btnActive = false
        })
    },
    activeBtn() {
      if (this.commentCreateData.content) {
        this.btnActive = true
      } else {
        this.btnActive = false
      }
    }
  },
  created() {
    this.commentCreateData.postId = this.$route.params['postId']
    this.findPost(this.commentCreateData.postId)
    this.fetchComments(this.commentCreateData.postId)
  }
}
</script>

<style>
.feed-profile-img {
  height: 25px;
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
  left: 1%;
  width: 14%;
}

.recommend {
  position: fixed;
  top: 200px;
  right: 1%;
  width: 14%;
}
</style>