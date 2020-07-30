<template>
  <div class="custom-container mt-3 custom-offset-lg-0">
    <div class="post mb-4">
      <div class="post-header d-flex justify-content-between p-2 pl-3">
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
          <span class="ml-1 pointer" @click="selectUser(selectedPost.user.id)">
             {{ selectedPost.user.nickName }}
          </span>
          <span v-if="selectedPost.isClub" class="badge bg-green">Club</span>
        </div>
        <div class="d-flex justify-center align-items-center">
          <div class="color-beige small-text">
            <span class="mr-3 color-black">{{ selectedPost.createdAt | moment("from", "now") }}</span>
            <span class="pointer" @click="clickBookmark(selectedPost)">
              <span v-if="checkBookmark(selectedPost)"><i class="fas fa-bookmark mr-2 color-green" id="bookmark"></i><small class="mr-3">{{ selectedPost.bookmarkUsers.length }}</small></span>
              <span v-else><i class="fas fa-bookmark mr-2" id="bookmark"></i><small class="mr-3">{{ selectedPost.bookmarkUsers.length }}</small></span>
            </span>
          </div>
          <div class="btn-group">
            <button class="btn-green dropdown-toggle btn-sm" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            </button>
            <div class="dropdown-menu text-center">
              <p class="dropdown-item m-0 pointer" @click="copyUrl">공유하기</p>
              <div class="dropdown-divider" v-if="myaccount.nickName===selectedPost.user.nickName"></div>
              <p class="dropdown-item m-0 pointer" v-if="myaccount.nickName===selectedPost.user.nickName">수정하기</p>
              <p class="dropdown-item m-0 pointer" v-if="myaccount.nickName===selectedPost.user.nickName">삭제하기</p>
            </div>
          </div>
       </div>
      </div>
        
      <div class="post-content">
        <div class="row no-gutters bg-light-beige">
          <div class="col-3 my-5">
            <img style="height: 20vh;" :src="selectedPost.book.bookImg" alt="책 이미지">
          </div>
          <div class="col-9 my-5 text-left pr-3 wrapping">
            <h5 class="font-weight-bold">{{ selectedPost.book.title }}</h5>
            <p><img class="mr-2" src="https://user-images.githubusercontent.com/25967949/88953039-4a9da800-d2d3-11ea-8f6b-5792b4f87c45.png" width="20px"> {{ selectedPost.book.author }} </p>
            <p><img class="mr-2" src="https://user-images.githubusercontent.com/25967949/88953045-4b363e80-d2d3-11ea-8f26-0502556bf651.png" width="20px"> {{ selectedPost.book.publisher }}</p>
            <p><img class="mr-2" src="https://user-images.githubusercontent.com/25967949/88953046-4bced500-d2d3-11ea-8a79-23e48bd595f1.png" width="20px"> {{ selectedPost.book.pubDate.slice(0,4) }}년 {{ selectedPost.book.pubDate.slice(5,7) }}월 {{ selectedPost.book.pubDate.slice(8,10) }}일</p>
          </div>
        </div>
        <div>
          <div class="offset-2 col-8 py-3">
            <div class="w-100 color-black post-onelineReview">
              <div class="px-3 pt-2 large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="px-3 d-flex justify-content-center"><p class="my-2 w-100">{{ selectedPost.onelineReview }}</p></div>
              <div class="px-3 pb-2 large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
          <!-- <hr class="w-50 d-flex justify-content-center"> -->
          <div class="col-12 py-2 px-5" v-if="selectedPost.review" >
            <div class="w-100 color-black">
              <div class="review" v-html="selectedPost.review"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="post-footer text-right py-2 d-flex justify-content-between">
        <span class="pointer ml-3" @click="clickLike(selectedPost)">
          <span v-if="checkHeart(selectedPost)"><i class="fas fa-heart mr-2 heartselected"></i>
            <small class="mr-3"><span>{{ myaccount.nickName}}님</span>
            <span v-if="selectedPost.likeUsers.length -1 > 0"> 외 {{ selectedPost.likeUsers.length - 1}}명이 좋아합니다.</span>
            <span v-else>이 좋아합니다.</span>
            </small>
          </span>
          <span v-else><i class="fas fa-heart mr-2"></i><small class="mr-3">{{ selectedPost.likeUsers.length }}명이 좋아합니다.</small></span>
        </span>
        <div>
          <span
          class="badge bg-green rounded-pill px-3 py-2 mr-2"
          v-for="tag in selectedPost.tags"
          :key="`tag-${tag.id}`"
          >#{{ tag.name }}</span>
        </div>
        
      </div>
    </div>
    <div class="comment mb-5" id="comment">
      <h5 class="text-left mb-3">댓글</h5>
      <div class="input-group row no-gutters mb-3 commentSection">
        <textarea
          class="col-11 textareaSection p-3" 
          @keyup.enter="enterComment" 
          @input="activeBtn"
          v-model="commentCreateData.content" 
          type="content" 
          placeholder="댓글을 작성하세요 :)" 
          rows="3" 
        ></textarea>
        <button 
          :class="{ 'btn-green': btnActive, 'pointer': btnActive }"
          class="btn col-1 commentBtn"
          :disabled="!btnActive"
          @click="clickComment"
        >
        작성</button>
      </div>
      <div
        v-for="comment in comments"
        :key="`comment-${comment.id}`"
      >
        <div class="col-12 d-flex justify-content-between p-2 pl-3">
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
                :src="comment.user.profileImg" alt="작성자 프로필 사진">
            </span>
            <span class="ml-2 pointer" @click="selectUser(comment.user.id)">{{ comment.user.nickName }}</span>
            <span v-if="comment.isClub" class="badge bg-green">Club</span>
          </div>
          <div>
            <div
              class="btn text-danger btn-sm" 
              v-if="comment.user.id === myaccount.id"
              @click="deleteComment({ postId: commentCreateData.postId, commentId: comment.id })"
            >
            삭제</div>
          </div>
        </div>
        <div class="col-12 text-left wrapping">
          <div>{{ comment.content }}</div>
        </div>
        <hr>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import router from '@/router'
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
    ...mapActions('postStore', ['findPost', 'fetchComments', 'createComment', 'createLike', 'createBookmark', 'deleteComment']),
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
    enterComment() {
      if (this.commentCreateData.content.length === 1){
        this.commentCreateData.content = null
        this.btnActive = false
        alert("댓글을 작성해주세요.")
      } else {
        this.createComment(this.commentCreateData)
        .then(() => {
          this.commentCreateData.content = null
          this.btnActive = false
        })  
      }
    },
    activeBtn() {
      if (this.commentCreateData.content) {
        this.btnActive = true
      } else {
        this.btnActive = false
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
    selectUser(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    },
    copyUrl() {
      const copyText = document.createElement("input");
      copyText.value = `http://localhost:8080/posts/${this.commentCreateData.postId}`
      document.body.appendChild(copyText)
      
      copyText.select();
      document.execCommand("copy");
      document.body.removeChild(copyText)
      alert("주소가 복사 되었습니다.")
    }
  },
  created() {
    this.commentCreateData.postId = this.$route.params['postId']
    this.findPost(this.commentCreateData.postId)
    this.fetchComments(this.commentCreateData.postId)
  },
  beforeRouteLeave(to, from, next) {
    if (this.commentCreateData.content) {
      if (confirm('작성 중인 댓글이 있습니다. 정말 넘어가시겠습니까?') === true) {
        next(to)
      } else {
        next(false)
      }
    }
    next()
  }
}
</script>

<style>
.post-header, .post-content{
  border: 1px solid #D6CBBD ;
  border-bottom-style: none;
}

.post-footer {
  border: 1px solid #D6CBBD;
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
  left: 1%;
  width: 14%;
}

.recommend {
  position: fixed;
  top: 200px;
  right: 1%;
  width: 14%;
}

.wrapping {
  word-break: keep-all;
}

.heartselected {
  color: red !important;
}

.commentSection {
  border: 1px solid #D6CBBD;
  border-radius: 5px;
}

.textareaSection {
  border-right: 1px solid #D6CBBD;
}

.textareaSection:focus {
  outline: 1px solid #88A498;
}

.post-onelineReview {
  background-color: #f3f0ec;
  border-radius: 50px;
}

.dropdown-menu {
  left: -126px !important;
}

.btn-group > .btn-green {
  outline-style: none !important;
}

@media screen and (min-width: 1264px) {
  .custom-offset-lg-0 {
    max-width: 823.46px;
    margin-left: auto !important;
    margin-right: auto !important;
  }
}
</style>