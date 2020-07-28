<template>
  <div class="row">
    <div class="d-none custom-d-md-block col-2">
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
        v-for="post in posts"
        :key="`post-${post.id}`"
        >
        <div class="post-header d-flex justify-content-between py-2">
          <div class="ml-3">
            <span class="rounded-circle">
              <img
                v-if="!post.user.profileImg"
                class="img-fluid feed-profile-img" 
                src="@/assets/anonymous user.png" 
                alt="유저 프로필 사진">
              <img 
                v-else
                class="img-fluid feed-profile-img" 
                :src="post.user.profileImg" alt="작성자 프로필 사진">
            </span>
            {{ post.user.nickName }}
            <span v-if="post.isClub" class="badge bg-green">Club</span>
          </div>
          <div class="color-beige small-text">
            <span class="pointer" @click="clickLike(post)">
              <i class="fas fa-heart mr-2"></i><small class="mr-3">{{ post.likeUsers.length }}</small>
            </span>
            <span class="pointer" @click="postDetail(post.id)">
              <i class="fas fa-comments mr-2"></i><small class="mr-3">{{ post.commentCnt }}</small>
            </span>
            <span class="pointer" @click="clickBookmark(post)">
              <i class="fas fa-bookmark mr-2"></i><small class="mr-3">{{ post.bookmarkUsers.length }}</small>
            </span>
          </div>
        </div>
        <div class="post-content bg-light-beige row no-gutters pointer d-flex justify-content-around" @click="postDetail(post.id)">
          <div class="my-5">
            <img style="height: 30vh;" :src="post.book.bookImg" alt="책 이미지">
          </div>
          <div class="d-flex align-items-center justify-content-center w-50">
            <div class="w-75 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="d-flex justify-content-center"><p class="my-2 w-50">{{ post.onelineReview }}</p></div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
        </div>
        <div class="post-footer text-left pl-3 py-2">
          <span
            class="badge rounded-pill px-3 py-2 mr-2"
            v-for="tag in post.tags"
            :key="`tag-${tag.id}`"
            style="background-color:#907a62; color: white;"
          >
          {{ tag.name }}</span>
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
    }
  },
  created() {
    this.fetchPosts()
  }
  

}
</script>

<style scoped>
.post-header, .post-footer {
  border: 1px solid #D6CBBD ;
}

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
}

.recommend {
  position: fixed;
  top: 200px;
  right: 1%;
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
</style>