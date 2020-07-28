<template>
  <div class="row">
    <div class="d-none custom-d-md-block col-2">
      <div class="myclub border rounded">
        <div class="myclub-header font-weight-extrabold mt-3">내 클럽</div>
        <hr>
        <div class="myclub-content">
          <div class="mb-3">
            <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            clubname1
            <span class="badge bg-green">Club</span>
          </div>
          <div class="mb-3">
            <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            clubname1
            <span class="badge bg-green">Club</span>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 col-md-8 mt-3">
      <div
        class="post mb-5"
        v-for="post in posts"
        :key="`post-${post.id}`"
        >
        <div class="post-header d-flex justify-content-between mb-2">
          <div>
            <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            clubname1
            <span class="badge bg-green">Club</span>
          </div>
          <div class="color-beige small-text">
            <i class="fas fa-heart mr-2"></i><small class="mr-3">{{ post.likeUsers.length }}</small>
            <i class="fas fa-comments mr-2"></i><small class="mr-3">{{ 23 }}</small>
            <i class="fas fa-bookmark"></i>
          </div>
        </div>
        <div class="post-content bg-light-beige row pointer" @click="postDetail(post.id)">
          <div class="col-4 my-5">
            <img style="height: 30vh;" :src="post.book.bookImg" alt="책 이미지">
          </div>
          <div class="col-8 d-flex align-items-center">
            <div class="w-100 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="mr-5 mb-2">{{ post.onelineReview }}</div>
              <div class="ml-5">두줄 평은 이렇습니다.</div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
        </div>
        <div
          class="post-footer text-left mt-2"
          v-for="tag in post.tags"
          :key="`tag-${tag.id}`">
          <span class="badge bg-green rounded-pill px-3 py-2 mr-2">{{ tag.name }}</span>
        </div>
      </div>
    </div>
    <div class="d-none custom-d-md-block col-2">
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
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'PostList',
  computed: {
    ...mapState('postStore', ['posts']),
  },
  methods: {
    ...mapActions('postStore', ['fetchPosts']),
    postDetail(postId) {
      this.$router.push({ name: 'PostDetail', params: { postId: postId }})
    }
  },
  created() {
    this.fetchPosts()
  }
  

}
</script>

<style scoped>
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

@media (min-width: 960px) {
  .custom-d-md-block {
      display: block!important;
  }
}

</style>