<template>
  <div class="custom-container mt-3">
    <div class="post mb-5">
      <div class="post-header d-flex justify-content-between mb-2">
        <div>
          <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
          clubname1
          <span class="badge bg-green">Club</span>
        </div>
        <div class="color-beige small-text">
          <i class="fas fa-heart mr-2"></i><small class="mr-3">{{ selectedPost.likeUsers.length }}</small>
          <i class="fas fa-comments mr-2"></i><small class="mr-3">{{ 23 }}</small>
          <i class="fas fa-bookmark"></i>
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
        <div class="row">
          <div class="col-12 mt-3">
            <div class="w-100 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="mr-5 mb-2">{{ selectedPost.onelineReview }}</div>
              <div class="ml-5">두줄 평은 이렇습니다.</div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
          <div class="col-12 mt-3">
            <div class="w-100 color-black">
              <p class="text-left">{{ selectedPost.review }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="post-footer text-left mt-2">
        <span
          class="badge bg-green rounded-pill px-3 py-2 mr-2"
          v-for="tag in selectedPost.tags"
          :key="`tag-${tag.id}`"
          >{{ tag.name }}</span>
      </div>
    </div>

    <div class="comment mb-5">
      <h5 class="text-left">댓글</h5>
      <div class="input-group row mb-5">
        <textarea class="col-11" @keyup.enter="createComment(commentCreateData)" v-model="commentCreateData.content" type="content" placeholder="댓글을 작성하세요 :)" rows="2" ></textarea>
        <button class="btn btn-green col-1" @click="createComment(commentCreateData)">작성</button>
      </div>
      <div
        class="row"
        v-for="comment in comments"
        :key="`comment-${comment.id}`"
      >
        <div class="col-12 text-left">
          <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            clubname1
          <span class="badge bg-green">Club</span>
        </div>
        <div class="col-12 text-left">
          <div>{{ comment.content }}</div>
        </div>
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
        postId: this.$route.params['postId'],
        content: null
      }
    }
  },
  computed: {
    ...mapState('postStore', ['selectedPost', 'comments']),
  },
  methods: {
    ...mapActions('postStore', ['findPost', 'fetchComments', 'createComment']),
  },
  created() {
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