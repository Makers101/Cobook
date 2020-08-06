<template>

  <div>
    <div id="wrapper">
      <div id="container">
        <section class="open-book">
          <header>
          </header>
          <article>
            <!-- 페이지1 -->
            <div class="page1 d-flex flex-column w-100" style="height:750px;">
                <div class="w-100">
                  <!-- 한줄평-->
                  <h2 class="chapter-title book-title pt-0">{{ selectedPost.onelineReview }}</h2>
                  <!-- 책 이미지 및 정보 -->
                  <div class="row no-gutters">
                    <div class="col-3">
                      <img style="height: 15vh;" :src="selectedPost.book.bookImg" alt="책 이미지">
                    </div>
                    <div class="col-9 text-left pr-3 align-self-center">
                      <p><mark>{{ selectedPost.book.title }}</mark></p>
                      <p><img class="mr-2" src="https://user-images.githubusercontent.com/25967949/88953039-4a9da800-d2d3-11ea-8f6b-5792b4f87c45.png" width="20px"> {{ selectedPost.book.author }} </p>
                      <p><img class="mr-2" src="https://user-images.githubusercontent.com/25967949/88953045-4b363e80-d2d3-11ea-8f26-0502556bf651.png" width="20px"> {{ selectedPost.book.publisher }}</p>
                      <p><img class="mr-2" src="https://user-images.githubusercontent.com/25967949/88953046-4bced500-d2d3-11ea-8a79-23e48bd595f1.png" width="20px"> {{ selectedPost.book.pubDate.slice(0,4) }}년 {{ selectedPost.book.pubDate.slice(5,7) }}월 {{ selectedPost.book.pubDate.slice(8,10) }}일</p>
                    </div>
                  </div>
                  <!-- 작성자 정보 및 좋아요/ 북마크 -->
                  <div class="d-flex justify-content-between mt-3">
                    <!-- 작성자 정보 -->
                    <div class="m-0">
                      <span class="rounded-circle">
                        <img
                          v-if="!selectedPost.user.profileImg"
                          class="img-fluid feed-profile-img mr-1" 
                          src="@/assets/anonymous.png" 
                          alt="유저 프로필 사진">
                        <img 
                          v-else
                          class="img-fluid feed-profile-img mr-1" 
                          :src="selectedPost.user.profileImg" alt="작성자 프로필 사진">
                      </span>
                      <strong>
                        <span class="pointer" @click="selectUser(selectedPost.user.id)">{{ selectedPost.user.nickName }}님</span>
                      </strong>
                    </div>
                    <div class="m-0 d-flex justify-content-between">
                      <!-- 좋아요 -->
                      <div class="pointer m-0" @click="clickLike(selectedPost)">
                        <p v-if="checkHeart(selectedPost)">
                          <span 
                            v-if="selectedPost.likeUsers.length -1 > 0" 
                            :tooltip="myaccount.nickName + '님 외 ' + (selectedPost.likeUsers.length -1 )+ '명이 좋아합니다.'" 
                            flow="down">
                            <i class="fas fa-heart mr-2 heartselected"></i>
                          </span>
                          <span 
                            v-else 
                            :tooltip="myaccount.nickName+`님이 좋아합니다.`" 
                            flow="down">
                              <i class="fas fa-heart mr-2 heartselected"></i>
                          </span>
                        </p>
                        <p v-else>
                          <span :tooltip="selectedPost.likeUsers.length+`명이 좋아합니다.`" flow="down">
                            <i class="fas fa-heart mr-2"></i>
                          </span>
                        </p>
                      </div>
                      <!-- 북마크 -->
                      <p class="pointer" @click="clickBookmark(selectedPost)">
                        <span v-if="checkBookmark(selectedPost)">
                          <span :tooltip="selectedPost.bookmarkUsers.length + '명이 북마크하였습니다.'" flow="down">
                            <i class="fas fa-bookmark mr-2 color-green" id="bookmark"></i>
                          </span>
                        </span>
                        <span v-else>
                          <span :tooltip="selectedPost.bookmarkUsers.length + `명이 북마크하였습니다.`" flow="down">
                            <i class="fas fa-bookmark mr-2" id="bookmark"></i>
                          </span>
                        </span>
                      </p>
                    </div>
                    
                  </div>
                  <div class="px-3 py-1 mt-3 rounded post-review" style="height:400px; border: 1px solid black">
                    <!-- 상세 리뷰 -->
                    <div class="mx-0 w-100" v-if="selectedPost.review">
                      <div class="review" v-html="selectedPost.review"></div>
                    </div>
                    <div v-else>
                      <img src="https://user-images.githubusercontent.com/25967949/89524240-2e43c300-d81f-11ea-9a05-b1b45d70172f.png">
                      <h5>작성된 상세 리뷰가 없습니다.</h5>
                    </div>
                  </div>
                  <!-- 뱃지 -->
                  <div class="w-100 mt-3">
                    <div class="post-footer pt-2 d-flex justify-content-between">
                      <!-- 뱃지 -->
                      <div>
                        <span
                        class="badge bg-green rounded-pill px-3 py-2 mr-2"
                        v-for="tag in selectedPost.tags"
                        :key="`tag-${tag.id}`"
                        >#{{ tag.name }}</span>
                      </div>
                    </div>
                  </div>
                </div>
                
                
            </div>
            <!-- 페이지2 -->
            <div class="page2 d-flex flex-column " style="height:750px;"> 
              <!-- <div class="w-100 page2-header">
                <p class="text-left">
                  <span class="rounded-circle">
                    <img
                      v-if="!selectedPost.user.profileImg"
                      class="img-fluid feed-profile-img mr-1" 
                      src="@/assets/anonymous.png" 
                      alt="유저 프로필 사진">
                    <img 
                      v-else
                      class="img-fluid feed-profile-img mr-1" 
                      :src="selectedPost.user.profileImg" alt="작성자 프로필 사진">
                  </span>
                  <strong>
                    <span class="pointer" @click="selectUser(selectedPost.user.id)">{{ selectedPost.user.nickName }}님</span>
                  </strong>
                </p>
              </div> 
              <hr style="background-color:#efefef"> -->
              <!-- 댓글 -->
              <h5 class="text-left">댓글</h5>
              <!-- 댓글 리스트 -->
              <div class="comment-list w-100" style="height:700px">
                <div v-if="comments.length">
                  <div
                  v-for="comment in comments"
                  :key="`comment-${comment.id}`">
                    <div class="d-flex justify-content-between">
                      <div class="m-0">
                        <span class="rounded-circle">
                          <img
                            v-if="!selectedPost.user.profileImg"
                            class="img-fluid feed-profile-img" 
                            src="@/assets/anonymous.png" 
                            alt="유저 프로필 사진">
                          <img 
                            v-else
                            class="img-fluid feed-profile-img" 
                            :src="comment.user.profileImg" alt="작성자 프로필 사진">
                        </span>
                        <span class="ml-2 pointer" @click="selectUser(comment.user.id)">{{ comment.user.nickName }}</span>
                        <span v-if="comment.isClub" class="badge bg-green">Club</span>
                      </div>
                      <div class="m-0">
                          <!-- @click="deleteComment({post.id, comment.id" -->
                        <div
                          class="btn text-danger btn-sm"
                          v-if="comment.user.id === myaccount.id"
                          @click="deleteComment({ postId: commentCreateData.postId, commentId: comment.id })"
                        > 삭제
                        </div>
                      </div>
                    </div>
                    <div class="col-12 text-left wrapping py-0 mb-3">
                      <div>{{ comment.content }}</div>
                      <div><small style="color:#979797">{{ comment.createdAt | moment("from", "now") }}</small></div>
                    </div>
                  </div>
                </div>
                <div v-else class="d-flex align-items-center">
                  <div style="margin-top:120px;">
                    <img src="https://user-images.githubusercontent.com/25967949/89524240-2e43c300-d81f-11ea-9a05-b1b45d70172f.png">
                    <h5>작성된 댓글이 없습니다.</h5>
                  </div>

                </div>
              </div>
        
              <hr class="mt-1" style="background-color:#efefef">
              
              <div class="comment mt-auto w-100" id="comment">
                <!-- 댓글 작성 -->
                <div class="input-group row no-gutters commentSection" style="height:70px;">
                  <textarea
                    class="col-11 textareaSection p-3" 
                    @keyup.enter="enterComment" 
                    @input="activeBtn"
                    v-model="commentCreateData.content" 
                    type="content" 
                    placeholder="댓글을 작성하세요 :)" 
                    rows="1" 
                    autofocus
                  ></textarea>
                  <button 
                    :class="{ 'btn-green': btnActive, 'pointer': btnActive }"
                    class="btn col-1 commentBtn"
                    :disabled="!btnActive"
                    @click="clickComment"
                  >
                  작성</button>
                </div>

              </div>
            </div>
              
          </article>
          <footer>
            <ol id="page-numbers">
              <li>1</li>
              <li>2</li>
            </ol>
          </footer>
        </section>
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
      dialog: false,
      rating: null,
      readonly: true,
      dense: true,
      color: 'yellow lighten-1',
      bgColor: 'grey lighten-1',
      size:32,
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('postStore', ['selectedPost', 'comments']),
  },
  methods: {
    ...mapActions('postStore', ['findPost', 'fetchComments', 'createComment', 'createLike', 'createBookmark', 'deleteComment', 'deletePost']),
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
    },
    falseDialog(commentCreateData, comment) {
      this.dialog = false
      this.deleteComment({ postId: commentCreateData.postId, commentId: comment.id })
    },
    clickUpdatePost(postId) {
      router.push({ name: 'PostUpdate', params: { postId: postId }})
    },
    clickDeletePost(postId) {
      if (confirm('게시물을 삭제하시겠습니까?') === true) {
        this.deletePost(postId)
      } else {
        return false
      }
    }
  },
  created() {
    this.commentCreateData.postId = this.$route.params['postId']
    this.findPost(this.commentCreateData.postId)
    this.fetchComments(this.commentCreateData.postId)
    
  },
  mounted() {
    this.rating = this.selectedPost.rank
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

<style scoped>

.feed-profile-img {
  height: 25px;
  width: 25px;
  border-radius: 50%;
  border: 1px solid black;
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

.setting-btn:focus {
  background-color: #88A498 !important;
  color: #F7F7F7 !important;
  outline: none;
}

.setting-btn:active {
  background-color: #88A498 !important;
  color: #F7F7F7 !important;
  outline: none;
}

@import url(https://fonts.googleapis.com/css?family=Crimson+Text:400,700,900,400italic,700italic,900italic|Playfair+Display:400,700,900,400italic,700italic,900italic|Rock+Salt:400);

body {
    background-color: #1d1f20;
    color: #e5e5e5;
    font: 16px/1.25 'Crimson Text', sans-serif;
    margin: 0;
}

#wrapper {
    margin-left: auto;
    margin-right: auto;
    max-width: 80em;
}

#container {
    float: left;
    padding: 1em;
    width: 100%;
}



/*** OPEN BOOK ***/
.open-book {
    background: #fff;
    box-shadow: rgba(0,0,0,0.5) 0 1em 3em;
    color: #000;
    padding: 2em;
}

.open-book * {
    position: relative;
}

/* Highlight */
.open-book *::-moz-selection {
    background: rgba(222,255,0,0.75);
}

.open-book *::selection {
    background: rgba(222,255,0,0.75);
}

/* Header/Footer */
.open-book header {
    padding-bottom: 1em;
}

.open-book header *,
.open-book footer * {
    font: 700 1em/1.25 'Playfair Display', sans-serif;
    letter-spacing: 0.125em;
    margin: 0;
}

.open-book header * {
    font-size: 0.75em;
    text-transform: uppercase;
}

.open-book footer {
    padding-top: 1em;
}

.open-book footer #page-numbers {
    display: none;
    list-style: none;
    padding: 0;
    text-align: left;
}

.open-book footer #page-numbers > li:last-child {
    text-align: right;
}

/* Chapter Title */
.open-book .chapter-title {
    background: url(data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjxzdmcgdmVyc2lvbj0iMS4xIiBpZD0iTGF5ZXJfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiDQoJIHZpZXdCb3g9IjAgMCA2NCA2NCIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNjQgNjQ7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCiAgICA8Zz4NCiAgICAJPHBhdGggZD0iTTAsMzJMMzIsMGwzMiwzMkwzMiw2NEwwLDMyeiBNOCwzMmwyNCwyNGwyNC0yNEwzMiw4TDgsMzJ6IE0xNiwzMmwxNi0xNmwxNiwxNkwzMiw0OEwxNiwzMnogTTI0LDMybDgsOGw4LThsLTgtOEwyNCwzMnoiIC8+DQogICAgPC9nPg0KPC9zdmc+) bottom center no-repeat;
    background-size: 0.5em 0.5em;
    font: 700 7vw/1.25 'Playfair Display', sans-serif;
    font-size: 1.7em !important;
    letter-spacing: 0.125em;
    margin: 0 0 1em 0;
    padding: 1em 0;
    position: relative;
    text-align: center;
    text-transform: uppercase;
}

.open-book .chapter-title:before,
.open-book .chapter-title:after {
    border: solid 0 #000;
    border-width: 0.05em 0;
    bottom: calc((0.125em / 2) * 3);
    content: '';
    height: 0.15em;
    position: absolute;
    width: calc(50% - (1em / 2));
}

.open-book .chapter-title:before {
    left: 0;
}

.open-book .chapter-title:after {
    right: 0;
}

/* Body Copy */
.open-book article {
    line-height: 1.5;
}

.open-book article *:not(.chapter-title):not(hr):not(dl) {
    margin: 0 auto;
    max-width: 28.125em;
}


.open-book .chapter-title + p:first-of-type {
    text-indent: 0;
}

.open-book .chapter-title + p:first-of-type:first-letter {
    float: left;
    font: 700 3em/0.65 'Playfair Display', sans-serif;
    padding: 0.15em 0.05em 0 0;
    text-transform: uppercase;
}

.open-book article > ul,
.open-book article > ol {
    padding-left: 3em;
}

.open-book article > ul ul {
    padding-left: 1em;
}

.open-book a {
    background: url(data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjxzdmcgdmVyc2lvbj0iMS4xIiBpZD0iTGF5ZXJfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiDQoJIHZpZXdCb3g9IjAgMCA4MDAgNTAiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDgwMCA1MDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KCTxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+DQoJCS5zdDB7ZmlsbDojMDAwMENDO30NCgk8L3N0eWxlPg0KCTxnIGlkPSJZMkJjMjcudGlmIj4NCgkJPGc+DQoJCQk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDMwLjksNDkuMmMtMSwwLTIsMC0zLDBjLTAuMS0wLjMtMC4yLTAuOC0wLjQtMC44Yy0xLjgtMC40LTQtMC4zLTUuNC0xLjNjLTEuOC0xLjQtNC4zLDAuMS01LjgtMS45Yy00LjMtMS04LjUtMi4zLTEyLjgtMi45Yy0zLjQtMC41LTYuOS0wLjEtMTAuMy0wLjFjLTAuMywwLjgtMC41LDEuNC0wLjcsMmMtMC41LTAuMy0wLjktMC42LTEuNC0xYy0wLjIsMS4yLTAuMywyLjEtMC42LDMuN2MtMS0xLjMtMS41LTIuMS0xLjktMi42Yy0yLjgsMC01LjMtMC41LTcuNCwwLjFjLTIuMiwwLjctMy43LDAuOC01LjktMC4xYy0yLjItMC45LTIuOS0yLjYtNC00Yy0yLjUsMS4yLTUuMSwxLjEtNy41LDAuMmMtMS4yLTAuNS0yLTAuNi0zLjEsMC4xYy0xLjIsMC43LTIuNiwxLTMuOSwxLjRjLTAuMiwwLjEtMC42LTAuMi0wLjgtMC4zYy0xLjItMS4zLDIuNC0yLjMsMC0zLjdjLTIuNiwxLjYtNS4yLDMuMy04LDVjLTAuOCwwLTEuOSwwLjMtMi43LTAuMWMtMi40LTEuMS00LjgtMi4zLTYuOS0zLjljLTEuOS0xLjUtMy45LTIuMi02LjItMmMtMSwwLjEtMiwwLTIuOCwwYy0wLjgtMS4xLTEuNC0yLTIuNy0zLjljMCwyLjMsMCwzLjYsMCw0LjljLTMuNywwLjgtNywxLjYtMTAuMywyLjNjLTEuNC0xLjgtMi42LTMuMi0zLjktNC45Yy0wLjQsMC44LTAuNiwxLjItMC43LDEuNmMtMC44LDMuMi0xLDMuMS00LjIsMi4zYy0yLjktMC43LTUuNC0yLjQtOC40LTMuNmMtMS42LDItMy4yLDQtNS4yLDYuNWMtMy44LTIuMi03LjgsMi0xMS45LDBjLTAuNCwwLjMtMC44LDAuNS0xLjIsMC44Yy0xLjQtMC45LTIuNy0xLjgtMy44LTIuNWMtMC44LTIuNy0xLjYtNS4yLTIuNC04LjJjLTEuMiwxLjMtMS45LDIuMi0zLDMuNGMwLDEsMCwyLjUsMCwzLjljLTUuMSwwLjYtOS45LDIuMS0xNS4xLDAuOWMwLTEsMC0xLjcsMC0yLjljLTEuMSwwLjUtMS45LDAuOS0yLjcsMS4zYy0xLjYtMC42LTMuMS0xLjItNC44LTEuOWMtMC42LDAuOC0xLjIsMS44LTEuOSwyLjhjLTAuNC0wLjktMC44LTEuNy0xLjItMi40YzAuMi0wLjQsMC40LTAuOSwxLjEtMi4yYy0zLDEuNS01LDIuNS03LjEsMy42Yy0xLTMtMy40LTQuNS02LTUuN2MtMC41LDAuNi0xLjEsMS4xLTEuNSwxLjZjMC41LDAuOSwwLjgsMS41LDEuMSwyLjJjLTAuOCwwLTEuMSwwLTEuNywwYy0wLjItMS0wLjQtMi0wLjctMy41Yy0xLjMsMC45LTIuMywxLjYtMy4zLDIuM2MtMC4yLTAuNi0wLjUtMS4yLTAuOC0yYy0xLjEsMS41LTIuMSwyLjktMyw0LjJjLTUuMywwLjQtNS45LTUtOS4xLTYuOWMtMC43LDEtMS40LDItMiwzYy0xLjgtMC40LTMuMy0wLjgtNC44LTEuMWMtMC4yLDAtMC40LDAuMy0wLjYsMC41YzAuMywwLjMsMC42LDAuNiwxLDFjLTAuOCwwLjYtMS42LDEuMS0yLjUsMS43Yy0wLjUtMS0wLjgtMS42LTEuMi0yLjVjLTEuNSwwLjgtMi44LDEuNi0zLjgsMi4yYy0wLjcsMS43LTEuMiwyLjktMS42LDQuMWMtMS40LTEuNy0yLjYtMy4yLTQuMS01YzAtMC40LDAtMS4yLDAtMS45Yy0xLjcsMC0zLjEsMC0zLjksMGMtMy4xLDEuOS01LjYsMy40LTguMiw1LjFjLTAuNC0wLjgtMC43LTEuNS0xLjEtMi4yYy0wLjYsMC0xLjMsMC0yLjEsMGMtMC42LDAuNi0xLjMsMS4zLTIsMS45Yy0wLjUtMC40LTEtMC43LTEuOC0xLjNjLTEsMS40LTEuOSwyLjctMi44LDMuOWMtMC4xLDAuMi0wLjQsMC4xLTAuNywwLjJjLTAuMi0xLTAuNC0xLjktMC42LTMuMmMtMC41LDEtMC44LDEuNy0xLjUsMy4zYzAtMi4yLDAtMy40LDAtMy41Yy0xLjktMS45LTMuMS0zLjEtNC4zLTQuM2MtMy41LDEuMS0zLDUuNC00LjgsNy42Yy00LjgsMC41LTkuMywwLjktMTMuOCwxLjRjLTAuMS0xLjEtMC4yLTEuOS0wLjMtMi45Yy0xLjUsMC0yLjksMC00LjIsMGMtMS40LDAuNy0yLjgsMS4zLTQuMiwyYy0wLjMtMC45LTAuNi0xLjQtMC45LTIuM2MtNi43LDAuNi0xMi41LDQuNC0xOSw2LjNjLTAuOS0xLjItMS45LTIuMy0yLjYtMy4zYy0zLDEuOC01LjYsMy4xLTguNywxLjJjLTAuNSwxLTEuMSwxLjktMS42LDIuN2MtMi43LDAuOC0zLjMtMS4yLTQtMi43Yy0yLjIsMC40LTQuMiwwLjctNS45LDFjLTAuOS0wLjktMS41LTEuNS0yLjEtMi4xYy0xLjIsMC42LTUuOSwxLjEtNi44LDEuMmMtMi41LDAuMi00LjksMC42LTguMSwxYy0wLjIsMC40LTAuNywxLjMtMS4xLDJjLTItMC42LTMuNS0xLjEtNS4zLTEuNmMtMC42LDAuOC0xLjEsMS42LTIuMSwyLjljLTAuNy0xLjktMS4yLTMuMi0xLjctNC44Yy0xLDEuMi0xLjgsMi40LTIuMywzYy0xLjgsMC0zLDAtNCwwYy0wLjEtMS0wLjEtMS44LTAuMi0yLjNjLTIsMC4zLTQuMSwwLTUuNSwwLjljLTIuMywxLjMtNC44LDEuMS02LjcsMC41Yy0yLjMtMC44LTQuNi0xLjMtNi41LTAuM2MtMi4xLDEuMS00LjctMC41LTYuNCwxLjVjMCwwLjEtMC41LTAuMy0xLTAuNWMwLjQtMSwwLjktMS45LDEuMy0yLjljLTAuOS0wLjctMS42LTEuMi0yLjgtMi4xYy0xLjYsMy4xLTMsNS44LTQuNCw4LjVjLTEsMC0yLDAtMywwYy0wLjEtMC4zLTAuMi0wLjgtMC40LTAuOGMtMS44LTAuNC00LTAuMy01LjQtMS4zYy0xLjgtMS4zLTQuMywwLTUuOC0xLjlDMTAuOSw0NC4xLDUuNiw0Mi45LDAsNDEuN2MwLTguMSwwLTE2LjYsMC0yNS4yYzEtMC45LDIuMy0xLjcsMS44LTMuOGMtMC42LTAuNy0wLjYtMS41LTEuNS0yLjZDMS45LDguMSwyLjYsNiw0LjQsMy44YzEuMywwLjYsMi41LDEuMSwzLjcsMS42QzcuNSw4LDcuOCwxMC4yLDkuMywxMi4yYzAuNiwwLDEuMywwLDIuNCwwYzAuNy0wLjgsMS42LTIsMi43LTMuNGMwLjQsMS4xLDAuNiwxLjcsMC44LDIuMmMyLjYsMCw1LjEsMCw3LjksMGMwLTEuNCwwLTIuNSwwLTMuNWMxLTAuNCwxLjgtMC43LDIuOC0xLjJjMi4yLDEuOSw0LjYsMy45LDYuNyw1LjhjMS4yLDAsMi4xLDAsMi41LDBjMS4zLTEuNiwyLjMtMi43LDMuMy0zLjljMC41LDAuOSwwLjksMS42LDEuNiwyLjZjMS0xLjUsMS42LTIuNSwyLjMtMy41YzIuOSwwLDMtMC4xLDUuNSwxLjJjNC4xLDIuMiw4LjYsMi40LDEyLjcsMS4zYzMuMi0wLjgsNi4xLTAuMyw5LjItMC42YzMuMi0wLjQsNi4zLDAuMiw5LjQsMS41YzIuNywxLjEsNS41LDEsNy43LTAuOWMyLjctMi4zLDUuNC00LjQsOC42LTUuM2MxLjIsMC40LDIuMywwLjcsNCwxLjJjMC42LDEuNiwxLjQsMy40LDIuMiw1LjVjMC42LTEsMS4zLTIsMS43LTIuN2MxLjUtMC41LDIuOS0wLjksNC4xLTEuM2MwLjYsMS40LDEuMiwyLjcsMiw0LjRjMi42LTYuMiw4LTYuOSwxMy4yLTguNWMyLjEsMy40LDQuMiw2LjksNi42LDEwLjdjMS0xLjksMS42LTMuMywyLjMtNC42YzAuNywwLDEuNCwwLDIsMGMtMC4zLTAuMy0wLjYtMC42LTAuOS0xYzAuOC0wLjEsMS43LTAuMywyLjUtMC40Yy0wLjEtMC4yLTAuMy0wLjQtMC40LTAuNWMtMS41LDAtMi45LDAtNCwwYy0wLjgtMC43LTEuMy0xLjEtMS40LTEuMmMwLjQtMS44LDAuNy0zLjMsMS00LjdjMC45LDAsMS43LDAsMi40LDBjMC4yLDAuNywwLjQsMS4zLDAuNSwxLjZjMi4xLDAuNiwzLjksMS4xLDUuNSwxLjZjMC4yLDAuNywwLjUsMS4yLDAuNywxLjhjMS4yLTAuNSwyLjQtMC43LDMuMi0xLjRjMS40LTEuMiwyLjUtMi42LDMuOS00LjJjMC42LDIuNCwxLjEsNC4zLDEuNiw2LjNjLTIuNCwwLjEtNC4zLDAuMi02LjMsMC40YzAuNiwwLjcsMC45LDEsMS4xLDEuNWMwLjIsMC40LDAuMiwwLjksMC4zLDEuM2MxLjYtMC4xLDMsMC40LDQuMy0wLjljMC43LTAuNywyLTAuOCwzLTFjMC40LTAuMSwwLjksMC4yLDEuMiwwLjJjMC4yLDEuMSwwLjMsMS45LDAuNiwzLjRjMS4zLTEuMSwyLjItMS44LDMuMy0yLjdjMC44LDEuMSwxLjcsMi4yLDIuMSwyLjhjMy40LDEsMy44LTEuMyw0LjktMy40YzAuOSwxLjMsMS40LDIsMi4xLDMuMWMxLTAuOSwxLjgtMS43LDIuNi0yLjRjMC4zLDAuNywwLjcsMS40LDEsMi4xYzEuNC0wLjQsMi41LTEsMi40LTIuNmMxLjYsMC4xLDEuOC0xLjUsMi42LTIuN2MwLjQsMS4xLDAuNiwxLjcsMC43LDJjMi4xLTEuMiw0LjEtMi40LDYuMi0zLjdjMC4zLDAuMywwLjcsMC42LDEuMiwxYzAuNy0xLDEuMy0yLDEuOS0zYzEuNywwLDMuMywwLDUuMiwwYzAuNSwwLjksMS4xLDEuOCwxLjYsMi42YzUtMS42LDkuOC0wLjUsMTQuNSwxLjdjMC41LDEuNSwxLjEsMywxLjgsNS4yYzEuNy0zLjYsMy4yLTYuNiw0LjYtOS41YzIsMS42LDQsMC42LDUuOSwxYzIsMC40LDMuOSwwLjgsNiwxLjNjMCwxLjQsMCwyLjUsMCwzLjljMS41LTEuMSwyLjgtMiw0LTIuOWMwLjQtMC4yLDAuOS0wLjIsMS4yLTAuM2MwLjYsMS4xLDEuMiwyLDEuOCwzYzEuOSwwLDQsMC42LDUuNS0wLjFjMi45LTEuMyw1LjgtMC42LDktMWMwLjQsMC41LDAuOSwxLjIsMS41LDEuN2MwLjcsMC42LDEuNSwxLDIuOCwxLjljMC0yLjYsMC00LjUsMC02LjVjMS44LDAsMy4zLDAsNC41LDBjMi44LDEuNCwwLjgsNC4xLDIuMiw2LjJjMS44LTAuOCwzLjYtMS42LDUuMi0yLjNjMC4yLTEuNCwwLjQtMi4yLDAuNS0yLjljMy42LDAuNiw2LjgsMS40LDEwLjEsMS43YzIuMSwwLjIsNC4xLDAuMyw1LjgsMS43YzEuMiwxLDIuNiwxLjYsNC4xLDIuNWM1LjgtMS44LDEwLjYtNi43LDE2LjktNi4xYzEuOCwxLjMsMy4yLDIuMiw0LjYsMy4zYzEuNCwxLjEsMi42LDIuMyw0LjQsMy44YzAsMC4yLDAsMSwwLDEuOWMxLjgtMC44LDIuNC0yLjcsNC4zLTMuMWMxLjYtMC4zLDIuOS0xLjgsNC41LTIuOWMxLjIsMC4xLDIuNywwLjIsNC4zLDAuM2MtMC41LDEuMS0wLjgsMS45LTEuMywzLjFjMi0wLjksMy40LTEuNSw0LjctMi4xYzAuMywwLjgsMC41LDEuMywwLjgsMmMwLjktMC40LDEuNS0wLjcsMi4yLTEuMWMwLjIsMC42LDAuNCwxLjIsMC43LDEuOWMxLjgtMC4zLDMuNywwLjQsNS4zLTAuOGMwLjMsMC4zLDAuNiwwLjUsMC45LDAuOGMwLjctMSwxLjQtMi4xLDIuMS0zLjFjMC4yLDAsMC40LTAuMSwwLjUsMGMwLjUsMC4zLDEuMSwwLjcsMSwwLjZjMi45LTAuMyw1LjItMC41LDcuNS0wLjhjMC4yLDEuMywwLjQsMi4yLDAuNiwzLjRjMy01LjgsMTAuNS01LjksMTMuOC0zLjZjMC44LDEuMiwxLjUsMi4zLDIuMSwzLjFjMS41LDAsMi42LDAsMy44LDBjLTAuNy0xLjMtMS4zLTIuMy0xLjktMy40YzEuMS0wLjUsMi4yLTEuMSwzLjYtMS43YzAuMywxLjMsMC41LDIuMywwLjYsMi45YzAuOSwwLjUsMS40LDAuNywyLDFjLTAuNiwxLjEtMSwxLjktMS41LDNjMS40LDAuOSwyLjcsMS42LDQuMSwyLjRjMC43LTEuMSwxLjItMiwxLjgtMi45YzAuMiwxLjYsMCwzLjYtMC41LDQuOWMtMC4xLDAuMiwwLjIsMC42LDAuMywwLjljMC4xLDAuMSwwLjMsMC4yLDAuNCwwLjJjMC4zLDAsMC42LTAuMSwwLjktMC4yYzAuNi0yLjksMC42LTIuOSwzLjMtNC41Yy0wLjQtMS0wLjctMS45LTEuMy0zLjNjMS4xLDAsMS43LTAuMSwyLjQsMGMwLjYsMC4xLDEuMiwwLjMsMS44LDAuNGM0LjgsMC44LDkuNCw0LDE0LjcsMS4yYzEsMS4yLDEuOSwyLjQsMi43LDMuM2MyLjYsMC4yLDMuOS0wLjcsMy43LTMuM2MtMC42LTAuOC0xLjQtMS43LTIuMy0yLjhjMS43LTIuMiwzLjMtNC4xLDUtNi4yYzEuMywwLjYsMi4zLDEsMi44LDEuM2MwLjcsMi44LDEuMiw1LDEuNyw3YzMuMSwwLjksMy43LTIsNS42LTIuN2MwLjMsMC43LDAuNSwxLjMsMC42LDEuN2MyLjYsMCw1LjEsMCw3LjcsMGMwLTEuMywwLTIuNCwwLTMuNGMxLTAuNSwxLjgtMC45LDMtMS41YzIuMSwxLjksNC40LDQsNi42LDZjMSwwLDIsMCwyLjYsMGMxLjItMS41LDIuMi0yLjcsMy4xLTMuOWMwLjgsMC45LDEuMywxLjUsMS45LDIuMmMwLjktMS40LDEuNS0yLjQsMi4zLTMuN2MxLjgsMC42LDMuNiwwLjgsNSwxLjZjNC4xLDIuNCw4LjUsMi4yLDEyLjgsMS42YzYuNi0wLjksMTIuNS0xLjQsMTcuMiwwLjJjMi4zLDAuOCw0LjYsMS4yLDYuNiwwLjljNC0yLjUsNy42LTQuOCwxMC42LTYuOGMyLDAuNiwzLjIsMC45LDQuNSwxLjNjMC43LDEuOCwxLjUsMy42LDIuMyw1LjZjMC43LTEuMSwxLjItMiwxLjgtMi45YzEuNC0wLjQsMi44LTAuOSw0LjEtMS4yYzAuNiwxLjUsMS4xLDIuOCwxLjgsNC41YzIuNy02LjMsOC4yLTcuMSwxMy41LTguNWMyLDMuNCw0LDYuOCw2LjQsMTAuN2MwLjktMS45LDEuNi0zLjMsMi4xLTQuM2MxLjQtMC42LDIuNi0xLjIsNC44LTIuMWMtMy0wLjgtNS41LDAuNi02LjctMS40YzAuNC0xLjgsMC43LTMuMywxLTQuOWMwLjgsMCwxLjYsMCwyLjUsMGMwLjEsMC41LDAuMiwxLDAuMywxLjZjMS44LDAuNSwzLjgsMS4xLDUuNywxLjZjMC4zLDAuNiwwLjUsMS4zLDEsMi4zYzIuNi0yLDQuOC0zLjcsNy01LjRjMC41LDEuOSwwLjksMy43LDEuNSw1LjljLTIuMywwLTQuMywwLTYuMiwwYzAuNSwxLjEsMC45LDEuOSwxLjMsMi44YzIuNCwwLjYsNC4zLTAuMyw2LjEtMS43YzAuNS0wLjQsMS41LTAuMSwyLjMtMC4xYzAuMiwxLjIsMC40LDIuMSwwLjcsMy40YzEuMy0wLjksMi4yLTEuNiwzLjctMi43YzAuOSwxLDEuOSwyLjEsMywzLjNjMS45LDAuMiwyLjctMS4xLDMuMS0yLjdjMC0wLjIsMC42LTAuMiwxLjEtMC4zYzAuMiwwLjMsMC40LDAuNiwwLjcsMWMwLjMsMC40LDAuNiwwLjksMS40LDIuMWMwLjktMS4zLDEuNC0yLjEsMi0zYzAsMCwwLjIsMC4xLDAuMywwLjJjMC4yLDAuNiwwLjQsMS4yLDAuNiwxLjhjMS43LTAuOSwzLjctMi43LDUuMy00LjljMC4yLDAuNiwwLjUsMS4xLDAuNiwxLjZjMi4xLTEuMiw0LjItMi40LDYuMi0zLjdjMC40LDAuMywwLjcsMC42LDEuMiwxYzAuNi0xLjEsMS4yLTIsMS43LTIuOWMxLjgsMCwzLjYsMCw1LjQsMGMwLjYsMSwxLjEsMS45LDEuNCwyLjNjNS4yLTEsMTAtMC42LDE0LjcsMi4xYzAuNSwxLjMsMSwyLjksMS43LDUuMWMxLjctMy41LDMuMS02LjUsNC42LTkuNmMwLjQsMC4zLDAuNiwwLjcsMC44LDAuN2MyLjQsMC4yLDQuOSwwLjQsNi44LDAuNWMxLjcsMC41LDIuOCwwLjksNCwxLjNjMC4xLDEuMiwwLjMsMi40LDAuNCwzLjdjMS45LTEuMiwzLjQtMi4zLDUtMy4zYzAuOCwxLjMsMS41LDIuMywyLjIsMy40YzQuOC0wLjUsOS41LTEsMTMuNy0xLjRjMS43LDEuNCwyLjksMi41LDQuNyw0LjFjMC4xLTIuNCwwLjEtNCwwLjItNS42YzAtMC40LDAuMi0wLjksMC40LTEuM2MxLjUsMCwyLjgsMCw0LjEsMGMwLjUsMC41LDEsMSwxLjYsMS42YzAsMS4zLDAsMi43LDAsNC4yYzIuNSwwLjYsNC4xLTAuNiw1LjctMi4xYzAuMi0wLjksMC4zLTEuOCwwLjUtMi44YzQuNSwxLjIsOC44LDIuNCwxMy4yLDJjMi4zLDEuNCw0LjQsMi43LDcsNC4yYzIuMy0xLjEsNS0yLjQsNy42LTMuN2MzLTEuNSw2LTIuOSw5LjQtMi41YzIuOSwyLjIsNS43LDQuNSw4LjUsNi43YzAuMSwwLjgsMC4yLDEuNSwwLjMsMmMyLjktMiw1LjctMy44LDguMy01LjZjMS41LDAsMywwLDQuNiwwYy0wLjEsMC43LTAuMiwxLjEtMC40LDEuNmMtMC4xLDAuMy0wLjQsMC42LTEuNCwxLjljMi42LTEuMiw0LTEuOCw1LjQtMi41YzAuMiwwLjcsMC40LDEuMywwLjcsMi4xYzAuOC0wLjUsMS41LTAuOCwyLjItMS4yYzAuMiwwLjYsMC40LDEuMSwwLjYsMS43YzIuMiwxLjEsNC4zLTEsNi42LDBjMC42LTEsMS4zLTIsMi0zYzAuOSwwLjUsMS42LDAuOSwyLjIsMS4zYzIuMy0xLjYsNC42LTEuMSw3LjEtMC45YzAsMS4xLDAsMS45LDAsMi43YzEuMi0zLDMuNi0zLjcsNi40LTMuOWMyLjctMi43LDUsMC42LDcuMywwLjJjMC45LDEuNSwxLjcsMi43LDIuMywzLjdjMS40LDAsMi41LDAsMy43LDBjLTAuNi0xLjMtMS4yLTIuNC0xLjgtMy42YzEuMy0wLjUsMi40LTEsMy42LTEuNWMwLjIsMS4zLDAuMywyLjIsMC41LDIuOWMwLjgsMC4zLDEuMywwLjUsMi4xLDAuOGMtMC41LDEuMS0wLjksMS45LTEuNSwzYzEuMywwLjksMi42LDEuNywzLjksMi41YzAuNy0xLDEuMy0yLDEuOS0yLjljMC4yLDAuMSwwLjQsMC4yLDAuNiwwLjNjLTAuMywxLjgtMC42LDMuNi0xLDUuNWMwLjUsMC4xLDAuOSwwLjEsMS40LDAuMmMwLjQtMS4xLDAuNS0yLjMsMS4xLTMuMWMwLjYtMC44LDEuNi0xLjIsMi41LTEuN2MtMC41LTEuMS0wLjktMS45LTEuMy0yLjljMy4xLTEsNS41LDAuNyw4LjEsMS4xYzEuOCwwLjMsMy41LDEuNCw1LjMsMS41YzEuNywwLjEsMy40LTAuNiw1LjEtMC45YzEuNywwLjgsMi4yLDMuNCw0LjcsM2MwLDguNywwLDE3LjMsMCwyNmMtMi4zLDAtNC42LDAtNi43LDBjLTAuMywwLjktMC41LDEuNC0wLjcsMmMtMC41LTAuNC0wLjgtMC43LTEuNC0xLjFjLTAuMiwxLjEtMC4zLDItMC40LDIuN2MtMC42LTAuNi0xLjMtMS4zLTEuOC0xLjhjLTMsMC4yLTUuNSwwLTcuOCwwLjVjLTUuMSwxLjItNy4xLDAuNS05LjUtNC4xYy0yLjcsMS01LjIsMS4xLTcuOSwwLjFjLTAuNy0wLjMtMS42LTAuNS0yLjMtMC4zYy0xLjUsMC41LTIuOCwxLjMtNC4yLDEuOWMtMC4zLDAuMS0wLjktMC4yLTEuNC0wLjNjMC40LTEuMiwwLjgtMi4zLDEuNS00LjRjLTMuNywyLjMtNi41LDMuOS05LjYsNS45Yy00LjctMC40LTguMy0zLjktMTIuOC02LjJjLTEuOCwwLTMuOCwwLTUuMywwYy0wLjgtMS4xLTEuNS0yLTIuNy0zLjZjMCwyLjEsMCwzLjIsMCw0LjNjLTMuOCwxLjYtNy42LDIuNC0xMC44LDIuM2MtMS4xLTEuMy0yLjMtMi43LTMuNi00LjNjLTEsMS44LTAuNCw0LjItMy40LDQuNWMtMi45LTEuMi02LjMtMi43LTkuOS00LjJjLTEuNiwyLTMuMiw0LTUsNi4zYy00LTEuNS04LDEuOC0xMi4xLDAuMmMtMC41LDAuMy0wLjksMC41LTEuMywwLjdjLTEuNC0xLTIuNy0xLjgtMy42LTIuNWMtMC45LTIuOC0xLjctNS4yLTIuNS03LjljLTEuMiwxLjItMiwyLTMsM2MwLDEuMywwLDIuOCwwLDQuMWMtNSwwLjUtOS43LDItMTQuNiwwLjljLTAuMi0wLjktMC4zLTEuOC0wLjYtM2MtMS4xLDAuNS0xLjksMC45LTIuNiwxLjNjLTEuNy0wLjYtMy4yLTEuMi00LjktMS44Yy0wLjUsMC45LTEuMSwxLjgtMS43LDIuOGMtMC40LTAuOS0wLjktMS44LTEuMi0yLjRjMC4yLTAuNywwLjMtMS4xLDAuNy0yLjFjLTIuNywxLjQtNC43LDIuNC02LjcsMy40Yy0xLjgtMy4zLTQuOC02LjEtNi4xLTUuNmMtMC40LDAuNC0wLjksMC45LTEuMywxLjVjMC4zLDAuNywwLjYsMS4zLDEuMSwyLjNjLTAuOC0wLjEtMS4zLTAuMS0xLjgtMC4xYy0wLjItMS4yLTAuMy0yLTAuNi0zLjVjLTEuNCwxLjEtMi4zLDEuOC0zLjMsMi41Yy0wLjMtMC44LTAuNS0xLjMtMC44LTIuMmMtMS4xLDEuNi0yLjEsMi45LTMuMSw0LjRjLTIuMy0wLjgtNC43LTAuOC01LjYtMy44Yy0wLjUtMS40LTIuMS0yLjMtMy4xLTMuNGMtMC44LDEuMS0xLjUsMi4xLTIuMiwzLjFjLTEuOS0wLjUtMy41LTAuOS01LjUtMS40YzAuNSwxLDAuNywxLjQsMSwyYy0wLjgsMC41LTEuNiwwLjktMi40LDEuM2MtMC40LTEtMC43LTEuNi0xLjEtMi40Yy0xLjUsMC44LTIuNywxLjQtMy44LDJjLTAuNywxLjYtMS4yLDMtMS44LDQuM2MtMS40LTEuOC0yLjYtMy4zLTMuOC00LjhjLTAuMS0wLjctMC4xLTEuNS0wLjItMi4xYy0xLjgsMC0zLjMsMC00LDBjLTMuMSwxLjktNS42LDMuNC04LjMsNWMtMC40LTAuOC0wLjctMS41LTEuMy0yLjZjLTEuNCwwLjctMi43LDEuNC00LjEsMi4xYy0wLjMtMC4yLTAuOS0wLjUtMS42LTFjLTEsMS40LTEuOSwyLjctMi44LDMuOWMtMC4xLDAuMS0wLjQsMC4xLTAuNywwLjFjLTAuMi0xLTAuMy0xLjgtMC41LTMuMWMtMC41LDEuMS0wLjgsMS43LTEuNiwzLjNjMC0yLjIsMC0zLjUsMC00LjdjLTAuMywwLjItMC42LDAuMy0wLjgsMC41Yy0xLjEtMS4yLTIuMy0yLjMtMy42LTMuNmMtMy4zLDEuMi0yLjksNS40LTQuNyw3LjZjLTQuOSwwLjUtOS4zLDAuOS0xMy44LDEuNGMtMC4xLTEuMi0wLjItMi0wLjMtMi45Yy0xLjUsMC0yLjksMC00LjIsMGMtMS41LDAuNy0yLjksMS4zLTQuMiwyYy0wLjMtMC45LTAuNi0xLjUtMC45LTIuNGMtNi43LDEtMTIuNSw0LjQtMTkuMSw2LjZjLTAuOS0xLjItMS45LTIuNC0yLjYtMy40Yy0yLjksMS45LTUuNSwyLjktOC43LDEuMmMtMC41LDAuOS0xLjEsMS45LTEuNiwyLjhjLTAuNywwLTEuMywwLTIuMiwwYy0wLjYtMC45LTEuMy0yLTEuOC0yLjhjLTIuMSwwLjMtNC4xLDAuNy01LjgsMC45Yy0wLjktMC45LTEuNS0xLjUtMi4xLTIuMWMtMS4zLDAuNi01LjgsMS4xLTYuOCwxLjJjLTIuNSwwLjItNC45LDAuNi04LjEsMWMtMC4yLDAuNC0wLjYsMS4zLTEsMmMtMi0wLjYtMy41LTEuMS01LjMtMS42Yy0wLjYsMC44LTEuMSwxLjYtMiwzYy0wLjctMi0xLjItMy4zLTEuNy00LjdjLTEsMS4xLTEuOSwyLjItMi40LDIuOGMtMS45LDAtMi45LDAtNCwwYy0wLjEtMS0wLjEtMS44LTAuMi0yLjRjLTIsMC4zLTQuMSwwLTUuNiwwLjljLTIuMywxLjMtNC44LDEuMS02LjcsMC41Yy0yLjMtMC44LTQuNS0xLjMtNi41LTAuM2MtMi4xLDEuMS00LjctMC41LTYuNCwxLjVjMCwwLjEtMC41LTAuMy0xLTAuNWMwLjQtMC45LDAuOS0xLjksMS4zLTIuOWMtMC45LTAuNy0xLjYtMS4yLTIuOC0yLjFDNDMzLjgsNDMuOCw0MzIuNCw0Ni41LDQzMC45LDQ5LjJ6Ii8+DQoJCTwvZz4NCgk8L2c+DQo8L3N2Zz4=) bottom repeat-x;
    background-size: auto 0.25em;
    color: inherit;
    text-decoration: none;
}

.open-book s,
.open-book strike {
    background: url(data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjxzdmcgdmVyc2lvbj0iMS4xIiBpZD0iTGF5ZXJfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiDQoJIHZpZXdCb3g9IjAgMCA4MDAgNTAiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDgwMCA1MDsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KCTxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+DQoJCS5zdDB7ZmlsbDojQ0MwMDAwO30NCgk8L3N0eWxlPg0KCTxnIGlkPSJZMkJjMjcudGlmIj4NCgkJPGc+DQoJCQk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDMwLjksNDkuMmMtMSwwLTIsMC0zLDBjLTAuMS0wLjMtMC4yLTAuOC0wLjQtMC44Yy0xLjgtMC40LTQtMC4zLTUuNC0xLjNjLTEuOC0xLjQtNC4zLDAuMS01LjgtMS45Yy00LjMtMS04LjUtMi4zLTEyLjgtMi45Yy0zLjQtMC41LTYuOS0wLjEtMTAuMy0wLjFjLTAuMywwLjgtMC41LDEuNC0wLjcsMmMtMC41LTAuMy0wLjktMC42LTEuNC0xYy0wLjIsMS4yLTAuMywyLjEtMC42LDMuN2MtMS0xLjMtMS41LTIuMS0xLjktMi42Yy0yLjgsMC01LjMtMC41LTcuNCwwLjFjLTIuMiwwLjctMy43LDAuOC01LjktMC4xYy0yLjItMC45LTIuOS0yLjYtNC00Yy0yLjUsMS4yLTUuMSwxLjEtNy41LDAuMmMtMS4yLTAuNS0yLTAuNi0zLjEsMC4xYy0xLjIsMC43LTIuNiwxLTMuOSwxLjRjLTAuMiwwLjEtMC42LTAuMi0wLjgtMC4zYy0xLjItMS4zLDIuNC0yLjMsMC0zLjdjLTIuNiwxLjYtNS4yLDMuMy04LDVjLTAuOCwwLTEuOSwwLjMtMi43LTAuMWMtMi40LTEuMS00LjgtMi4zLTYuOS0zLjljLTEuOS0xLjUtMy45LTIuMi02LjItMmMtMSwwLjEtMiwwLTIuOCwwYy0wLjgtMS4xLTEuNC0yLTIuNy0zLjljMCwyLjMsMCwzLjYsMCw0LjljLTMuNywwLjgtNywxLjYtMTAuMywyLjNjLTEuNC0xLjgtMi42LTMuMi0zLjktNC45Yy0wLjQsMC44LTAuNiwxLjItMC43LDEuNmMtMC44LDMuMi0xLDMuMS00LjIsMi4zYy0yLjktMC43LTUuNC0yLjQtOC40LTMuNmMtMS42LDItMy4yLDQtNS4yLDYuNWMtMy44LTIuMi03LjgsMi0xMS45LDBjLTAuNCwwLjMtMC44LDAuNS0xLjIsMC44Yy0xLjQtMC45LTIuNy0xLjgtMy44LTIuNWMtMC44LTIuNy0xLjYtNS4yLTIuNC04LjJjLTEuMiwxLjMtMS45LDIuMi0zLDMuNGMwLDEsMCwyLjUsMCwzLjljLTUuMSwwLjYtOS45LDIuMS0xNS4xLDAuOWMwLTEsMC0xLjcsMC0yLjljLTEuMSwwLjUtMS45LDAuOS0yLjcsMS4zYy0xLjYtMC42LTMuMS0xLjItNC44LTEuOWMtMC42LDAuOC0xLjIsMS44LTEuOSwyLjhjLTAuNC0wLjktMC44LTEuNy0xLjItMi40YzAuMi0wLjQsMC40LTAuOSwxLjEtMi4yYy0zLDEuNS01LDIuNS03LjEsMy42Yy0xLTMtMy40LTQuNS02LTUuN2MtMC41LDAuNi0xLjEsMS4xLTEuNSwxLjZjMC41LDAuOSwwLjgsMS41LDEuMSwyLjJjLTAuOCwwLTEuMSwwLTEuNywwYy0wLjItMS0wLjQtMi0wLjctMy41Yy0xLjMsMC45LTIuMywxLjYtMy4zLDIuM2MtMC4yLTAuNi0wLjUtMS4yLTAuOC0yYy0xLjEsMS41LTIuMSwyLjktMyw0LjJjLTUuMywwLjQtNS45LTUtOS4xLTYuOWMtMC43LDEtMS40LDItMiwzYy0xLjgtMC40LTMuMy0wLjgtNC44LTEuMWMtMC4yLDAtMC40LDAuMy0wLjYsMC41YzAuMywwLjMsMC42LDAuNiwxLDFjLTAuOCwwLjYtMS42LDEuMS0yLjUsMS43Yy0wLjUtMS0wLjgtMS42LTEuMi0yLjVjLTEuNSwwLjgtMi44LDEuNi0zLjgsMi4yYy0wLjcsMS43LTEuMiwyLjktMS42LDQuMWMtMS40LTEuNy0yLjYtMy4yLTQuMS01YzAtMC40LDAtMS4yLDAtMS45Yy0xLjcsMC0zLjEsMC0zLjksMGMtMy4xLDEuOS01LjYsMy40LTguMiw1LjFjLTAuNC0wLjgtMC43LTEuNS0xLjEtMi4yYy0wLjYsMC0xLjMsMC0yLjEsMGMtMC42LDAuNi0xLjMsMS4zLTIsMS45Yy0wLjUtMC40LTEtMC43LTEuOC0xLjNjLTEsMS40LTEuOSwyLjctMi44LDMuOWMtMC4xLDAuMi0wLjQsMC4xLTAuNywwLjJjLTAuMi0xLTAuNC0xLjktMC42LTMuMmMtMC41LDEtMC44LDEuNy0xLjUsMy4zYzAtMi4yLDAtMy40LDAtMy41Yy0xLjktMS45LTMuMS0zLjEtNC4zLTQuM2MtMy41LDEuMS0zLDUuNC00LjgsNy42Yy00LjgsMC41LTkuMywwLjktMTMuOCwxLjRjLTAuMS0xLjEtMC4yLTEuOS0wLjMtMi45Yy0xLjUsMC0yLjksMC00LjIsMGMtMS40LDAuNy0yLjgsMS4zLTQuMiwyYy0wLjMtMC45LTAuNi0xLjQtMC45LTIuM2MtNi43LDAuNi0xMi41LDQuNC0xOSw2LjNjLTAuOS0xLjItMS45LTIuMy0yLjYtMy4zYy0zLDEuOC01LjYsMy4xLTguNywxLjJjLTAuNSwxLTEuMSwxLjktMS42LDIuN2MtMi43LDAuOC0zLjMtMS4yLTQtMi43Yy0yLjIsMC40LTQuMiwwLjctNS45LDFjLTAuOS0wLjktMS41LTEuNS0yLjEtMi4xYy0xLjIsMC42LTUuOSwxLjEtNi44LDEuMmMtMi41LDAuMi00LjksMC42LTguMSwxYy0wLjIsMC40LTAuNywxLjMtMS4xLDJjLTItMC42LTMuNS0xLjEtNS4zLTEuNmMtMC42LDAuOC0xLjEsMS42LTIuMSwyLjljLTAuNy0xLjktMS4yLTMuMi0xLjctNC44Yy0xLDEuMi0xLjgsMi40LTIuMywzYy0xLjgsMC0zLDAtNCwwYy0wLjEtMS0wLjEtMS44LTAuMi0yLjNjLTIsMC4zLTQuMSwwLTUuNSwwLjljLTIuMywxLjMtNC44LDEuMS02LjcsMC41Yy0yLjMtMC44LTQuNi0xLjMtNi41LTAuM2MtMi4xLDEuMS00LjctMC41LTYuNCwxLjVjMCwwLjEtMC41LTAuMy0xLTAuNWMwLjQtMSwwLjktMS45LDEuMy0yLjljLTAuOS0wLjctMS42LTEuMi0yLjgtMi4xYy0xLjYsMy4xLTMsNS44LTQuNCw4LjVjLTEsMC0yLDAtMywwYy0wLjEtMC4zLTAuMi0wLjgtMC40LTAuOGMtMS44LTAuNC00LTAuMy01LjQtMS4zYy0xLjgtMS4zLTQuMywwLTUuOC0xLjlDMTAuOSw0NC4xLDUuNiw0Mi45LDAsNDEuN2MwLTguMSwwLTE2LjYsMC0yNS4yYzEtMC45LDIuMy0xLjcsMS44LTMuOGMtMC42LTAuNy0wLjYtMS41LTEuNS0yLjZDMS45LDguMSwyLjYsNiw0LjQsMy44YzEuMywwLjYsMi41LDEuMSwzLjcsMS42QzcuNSw4LDcuOCwxMC4yLDkuMywxMi4yYzAuNiwwLDEuMywwLDIuNCwwYzAuNy0wLjgsMS42LTIsMi43LTMuNGMwLjQsMS4xLDAuNiwxLjcsMC44LDIuMmMyLjYsMCw1LjEsMCw3LjksMGMwLTEuNCwwLTIuNSwwLTMuNWMxLTAuNCwxLjgtMC43LDIuOC0xLjJjMi4yLDEuOSw0LjYsMy45LDYuNyw1LjhjMS4yLDAsMi4xLDAsMi41LDBjMS4zLTEuNiwyLjMtMi43LDMuMy0zLjljMC41LDAuOSwwLjksMS42LDEuNiwyLjZjMS0xLjUsMS42LTIuNSwyLjMtMy41YzIuOSwwLDMtMC4xLDUuNSwxLjJjNC4xLDIuMiw4LjYsMi40LDEyLjcsMS4zYzMuMi0wLjgsNi4xLTAuMyw5LjItMC42YzMuMi0wLjQsNi4zLDAuMiw5LjQsMS41YzIuNywxLjEsNS41LDEsNy43LTAuOWMyLjctMi4zLDUuNC00LjQsOC42LTUuM2MxLjIsMC40LDIuMywwLjcsNCwxLjJjMC42LDEuNiwxLjQsMy40LDIuMiw1LjVjMC42LTEsMS4zLTIsMS43LTIuN2MxLjUtMC41LDIuOS0wLjksNC4xLTEuM2MwLjYsMS40LDEuMiwyLjcsMiw0LjRjMi42LTYuMiw4LTYuOSwxMy4yLTguNWMyLjEsMy40LDQuMiw2LjksNi42LDEwLjdjMS0xLjksMS42LTMuMywyLjMtNC42YzAuNywwLDEuNCwwLDIsMGMtMC4zLTAuMy0wLjYtMC42LTAuOS0xYzAuOC0wLjEsMS43LTAuMywyLjUtMC40Yy0wLjEtMC4yLTAuMy0wLjQtMC40LTAuNWMtMS41LDAtMi45LDAtNCwwYy0wLjgtMC43LTEuMy0xLjEtMS40LTEuMmMwLjQtMS44LDAuNy0zLjMsMS00LjdjMC45LDAsMS43LDAsMi40LDBjMC4yLDAuNywwLjQsMS4zLDAuNSwxLjZjMi4xLDAuNiwzLjksMS4xLDUuNSwxLjZjMC4yLDAuNywwLjUsMS4yLDAuNywxLjhjMS4yLTAuNSwyLjQtMC43LDMuMi0xLjRjMS40LTEuMiwyLjUtMi42LDMuOS00LjJjMC42LDIuNCwxLjEsNC4zLDEuNiw2LjNjLTIuNCwwLjEtNC4zLDAuMi02LjMsMC40YzAuNiwwLjcsMC45LDEsMS4xLDEuNWMwLjIsMC40LDAuMiwwLjksMC4zLDEuM2MxLjYtMC4xLDMsMC40LDQuMy0wLjljMC43LTAuNywyLTAuOCwzLTFjMC40LTAuMSwwLjksMC4yLDEuMiwwLjJjMC4yLDEuMSwwLjMsMS45LDAuNiwzLjRjMS4zLTEuMSwyLjItMS44LDMuMy0yLjdjMC44LDEuMSwxLjcsMi4yLDIuMSwyLjhjMy40LDEsMy44LTEuMyw0LjktMy40YzAuOSwxLjMsMS40LDIsMi4xLDMuMWMxLTAuOSwxLjgtMS43LDIuNi0yLjRjMC4zLDAuNywwLjcsMS40LDEsMi4xYzEuNC0wLjQsMi41LTEsMi40LTIuNmMxLjYsMC4xLDEuOC0xLjUsMi42LTIuN2MwLjQsMS4xLDAuNiwxLjcsMC43LDJjMi4xLTEuMiw0LjEtMi40LDYuMi0zLjdjMC4zLDAuMywwLjcsMC42LDEuMiwxYzAuNy0xLDEuMy0yLDEuOS0zYzEuNywwLDMuMywwLDUuMiwwYzAuNSwwLjksMS4xLDEuOCwxLjYsMi42YzUtMS42LDkuOC0wLjUsMTQuNSwxLjdjMC41LDEuNSwxLjEsMywxLjgsNS4yYzEuNy0zLjYsMy4yLTYuNiw0LjYtOS41YzIsMS42LDQsMC42LDUuOSwxYzIsMC40LDMuOSwwLjgsNiwxLjNjMCwxLjQsMCwyLjUsMCwzLjljMS41LTEuMSwyLjgtMiw0LTIuOWMwLjQtMC4yLDAuOS0wLjIsMS4yLTAuM2MwLjYsMS4xLDEuMiwyLDEuOCwzYzEuOSwwLDQsMC42LDUuNS0wLjFjMi45LTEuMyw1LjgtMC42LDktMWMwLjQsMC41LDAuOSwxLjIsMS41LDEuN2MwLjcsMC42LDEuNSwxLDIuOCwxLjljMC0yLjYsMC00LjUsMC02LjVjMS44LDAsMy4zLDAsNC41LDBjMi44LDEuNCwwLjgsNC4xLDIuMiw2LjJjMS44LTAuOCwzLjYtMS42LDUuMi0yLjNjMC4yLTEuNCwwLjQtMi4yLDAuNS0yLjljMy42LDAuNiw2LjgsMS40LDEwLjEsMS43YzIuMSwwLjIsNC4xLDAuMyw1LjgsMS43YzEuMiwxLDIuNiwxLjYsNC4xLDIuNWM1LjgtMS44LDEwLjYtNi43LDE2LjktNi4xYzEuOCwxLjMsMy4yLDIuMiw0LjYsMy4zYzEuNCwxLjEsMi42LDIuMyw0LjQsMy44YzAsMC4yLDAsMSwwLDEuOWMxLjgtMC44LDIuNC0yLjcsNC4zLTMuMWMxLjYtMC4zLDIuOS0xLjgsNC41LTIuOWMxLjIsMC4xLDIuNywwLjIsNC4zLDAuM2MtMC41LDEuMS0wLjgsMS45LTEuMywzLjFjMi0wLjksMy40LTEuNSw0LjctMi4xYzAuMywwLjgsMC41LDEuMywwLjgsMmMwLjktMC40LDEuNS0wLjcsMi4yLTEuMWMwLjIsMC42LDAuNCwxLjIsMC43LDEuOWMxLjgtMC4zLDMuNywwLjQsNS4zLTAuOGMwLjMsMC4zLDAuNiwwLjUsMC45LDAuOGMwLjctMSwxLjQtMi4xLDIuMS0zLjFjMC4yLDAsMC40LTAuMSwwLjUsMGMwLjUsMC4zLDEuMSwwLjcsMSwwLjZjMi45LTAuMyw1LjItMC41LDcuNS0wLjhjMC4yLDEuMywwLjQsMi4yLDAuNiwzLjRjMy01LjgsMTAuNS01LjksMTMuOC0zLjZjMC44LDEuMiwxLjUsMi4zLDIuMSwzLjFjMS41LDAsMi42LDAsMy44LDBjLTAuNy0xLjMtMS4zLTIuMy0xLjktMy40YzEuMS0wLjUsMi4yLTEuMSwzLjYtMS43YzAuMywxLjMsMC41LDIuMywwLjYsMi45YzAuOSwwLjUsMS40LDAuNywyLDFjLTAuNiwxLjEtMSwxLjktMS41LDNjMS40LDAuOSwyLjcsMS42LDQuMSwyLjRjMC43LTEuMSwxLjItMiwxLjgtMi45YzAuMiwxLjYsMCwzLjYtMC41LDQuOWMtMC4xLDAuMiwwLjIsMC42LDAuMywwLjljMC4xLDAuMSwwLjMsMC4yLDAuNCwwLjJjMC4zLDAsMC42LTAuMSwwLjktMC4yYzAuNi0yLjksMC42LTIuOSwzLjMtNC41Yy0wLjQtMS0wLjctMS45LTEuMy0zLjNjMS4xLDAsMS43LTAuMSwyLjQsMGMwLjYsMC4xLDEuMiwwLjMsMS44LDAuNGM0LjgsMC44LDkuNCw0LDE0LjcsMS4yYzEsMS4yLDEuOSwyLjQsMi43LDMuM2MyLjYsMC4yLDMuOS0wLjcsMy43LTMuM2MtMC42LTAuOC0xLjQtMS43LTIuMy0yLjhjMS43LTIuMiwzLjMtNC4xLDUtNi4yYzEuMywwLjYsMi4zLDEsMi44LDEuM2MwLjcsMi44LDEuMiw1LDEuNyw3YzMuMSwwLjksMy43LTIsNS42LTIuN2MwLjMsMC43LDAuNSwxLjMsMC42LDEuN2MyLjYsMCw1LjEsMCw3LjcsMGMwLTEuMywwLTIuNCwwLTMuNGMxLTAuNSwxLjgtMC45LDMtMS41YzIuMSwxLjksNC40LDQsNi42LDZjMSwwLDIsMCwyLjYsMGMxLjItMS41LDIuMi0yLjcsMy4xLTMuOWMwLjgsMC45LDEuMywxLjUsMS45LDIuMmMwLjktMS40LDEuNS0yLjQsMi4zLTMuN2MxLjgsMC42LDMuNiwwLjgsNSwxLjZjNC4xLDIuNCw4LjUsMi4yLDEyLjgsMS42YzYuNi0wLjksMTIuNS0xLjQsMTcuMiwwLjJjMi4zLDAuOCw0LjYsMS4yLDYuNiwwLjljNC0yLjUsNy42LTQuOCwxMC42LTYuOGMyLDAuNiwzLjIsMC45LDQuNSwxLjNjMC43LDEuOCwxLjUsMy42LDIuMyw1LjZjMC43LTEuMSwxLjItMiwxLjgtMi45YzEuNC0wLjQsMi44LTAuOSw0LjEtMS4yYzAuNiwxLjUsMS4xLDIuOCwxLjgsNC41YzIuNy02LjMsOC4yLTcuMSwxMy41LTguNWMyLDMuNCw0LDYuOCw2LjQsMTAuN2MwLjktMS45LDEuNi0zLjMsMi4xLTQuM2MxLjQtMC42LDIuNi0xLjIsNC44LTIuMWMtMy0wLjgtNS41LDAuNi02LjctMS40YzAuNC0xLjgsMC43LTMuMywxLTQuOWMwLjgsMCwxLjYsMCwyLjUsMGMwLjEsMC41LDAuMiwxLDAuMywxLjZjMS44LDAuNSwzLjgsMS4xLDUuNywxLjZjMC4zLDAuNiwwLjUsMS4zLDEsMi4zYzIuNi0yLDQuOC0zLjcsNy01LjRjMC41LDEuOSwwLjksMy43LDEuNSw1LjljLTIuMywwLTQuMywwLTYuMiwwYzAuNSwxLjEsMC45LDEuOSwxLjMsMi44YzIuNCwwLjYsNC4zLTAuMyw2LjEtMS43YzAuNS0wLjQsMS41LTAuMSwyLjMtMC4xYzAuMiwxLjIsMC40LDIuMSwwLjcsMy40YzEuMy0wLjksMi4yLTEuNiwzLjctMi43YzAuOSwxLDEuOSwyLjEsMywzLjNjMS45LDAuMiwyLjctMS4xLDMuMS0yLjdjMC0wLjIsMC42LTAuMiwxLjEtMC4zYzAuMiwwLjMsMC40LDAuNiwwLjcsMWMwLjMsMC40LDAuNiwwLjksMS40LDIuMWMwLjktMS4zLDEuNC0yLjEsMi0zYzAsMCwwLjIsMC4xLDAuMywwLjJjMC4yLDAuNiwwLjQsMS4yLDAuNiwxLjhjMS43LTAuOSwzLjctMi43LDUuMy00LjljMC4yLDAuNiwwLjUsMS4xLDAuNiwxLjZjMi4xLTEuMiw0LjItMi40LDYuMi0zLjdjMC40LDAuMywwLjcsMC42LDEuMiwxYzAuNi0xLjEsMS4yLTIsMS43LTIuOWMxLjgsMCwzLjYsMCw1LjQsMGMwLjYsMSwxLjEsMS45LDEuNCwyLjNjNS4yLTEsMTAtMC42LDE0LjcsMi4xYzAuNSwxLjMsMSwyLjksMS43LDUuMWMxLjctMy41LDMuMS02LjUsNC42LTkuNmMwLjQsMC4zLDAuNiwwLjcsMC44LDAuN2MyLjQsMC4yLDQuOSwwLjQsNi44LDAuNWMxLjcsMC41LDIuOCwwLjksNCwxLjNjMC4xLDEuMiwwLjMsMi40LDAuNCwzLjdjMS45LTEuMiwzLjQtMi4zLDUtMy4zYzAuOCwxLjMsMS41LDIuMywyLjIsMy40YzQuOC0wLjUsOS41LTEsMTMuNy0xLjRjMS43LDEuNCwyLjksMi41LDQuNyw0LjFjMC4xLTIuNCwwLjEtNCwwLjItNS42YzAtMC40LDAuMi0wLjksMC40LTEuM2MxLjUsMCwyLjgsMCw0LjEsMGMwLjUsMC41LDEsMSwxLjYsMS42YzAsMS4zLDAsMi43LDAsNC4yYzIuNSwwLjYsNC4xLTAuNiw1LjctMi4xYzAuMi0wLjksMC4zLTEuOCwwLjUtMi44YzQuNSwxLjIsOC44LDIuNCwxMy4yLDJjMi4zLDEuNCw0LjQsMi43LDcsNC4yYzIuMy0xLjEsNS0yLjQsNy42LTMuN2MzLTEuNSw2LTIuOSw5LjQtMi41YzIuOSwyLjIsNS43LDQuNSw4LjUsNi43YzAuMSwwLjgsMC4yLDEuNSwwLjMsMmMyLjktMiw1LjctMy44LDguMy01LjZjMS41LDAsMywwLDQuNiwwYy0wLjEsMC43LTAuMiwxLjEtMC40LDEuNmMtMC4xLDAuMy0wLjQsMC42LTEuNCwxLjljMi42LTEuMiw0LTEuOCw1LjQtMi41YzAuMiwwLjcsMC40LDEuMywwLjcsMi4xYzAuOC0wLjUsMS41LTAuOCwyLjItMS4yYzAuMiwwLjYsMC40LDEuMSwwLjYsMS43YzIuMiwxLjEsNC4zLTEsNi42LDBjMC42LTEsMS4zLTIsMi0zYzAuOSwwLjUsMS42LDAuOSwyLjIsMS4zYzIuMy0xLjYsNC42LTEuMSw3LjEtMC45YzAsMS4xLDAsMS45LDAsMi43YzEuMi0zLDMuNi0zLjcsNi40LTMuOWMyLjctMi43LDUsMC42LDcuMywwLjJjMC45LDEuNSwxLjcsMi43LDIuMywzLjdjMS40LDAsMi41LDAsMy43LDBjLTAuNi0xLjMtMS4yLTIuNC0xLjgtMy42YzEuMy0wLjUsMi40LTEsMy42LTEuNWMwLjIsMS4zLDAuMywyLjIsMC41LDIuOWMwLjgsMC4zLDEuMywwLjUsMi4xLDAuOGMtMC41LDEuMS0wLjksMS45LTEuNSwzYzEuMywwLjksMi42LDEuNywzLjksMi41YzAuNy0xLDEuMy0yLDEuOS0yLjljMC4yLDAuMSwwLjQsMC4yLDAuNiwwLjNjLTAuMywxLjgtMC42LDMuNi0xLDUuNWMwLjUsMC4xLDAuOSwwLjEsMS40LDAuMmMwLjQtMS4xLDAuNS0yLjMsMS4xLTMuMWMwLjYtMC44LDEuNi0xLjIsMi41LTEuN2MtMC41LTEuMS0wLjktMS45LTEuMy0yLjljMy4xLTEsNS41LDAuNyw4LjEsMS4xYzEuOCwwLjMsMy41LDEuNCw1LjMsMS41YzEuNywwLjEsMy40LTAuNiw1LjEtMC45YzEuNywwLjgsMi4yLDMuNCw0LjcsM2MwLDguNywwLDE3LjMsMCwyNmMtMi4zLDAtNC42LDAtNi43LDBjLTAuMywwLjktMC41LDEuNC0wLjcsMmMtMC41LTAuNC0wLjgtMC43LTEuNC0xLjFjLTAuMiwxLjEtMC4zLDItMC40LDIuN2MtMC42LTAuNi0xLjMtMS4zLTEuOC0xLjhjLTMsMC4yLTUuNSwwLTcuOCwwLjVjLTUuMSwxLjItNy4xLDAuNS05LjUtNC4xYy0yLjcsMS01LjIsMS4xLTcuOSwwLjFjLTAuNy0wLjMtMS42LTAuNS0yLjMtMC4zYy0xLjUsMC41LTIuOCwxLjMtNC4yLDEuOWMtMC4zLDAuMS0wLjktMC4yLTEuNC0wLjNjMC40LTEuMiwwLjgtMi4zLDEuNS00LjRjLTMuNywyLjMtNi41LDMuOS05LjYsNS45Yy00LjctMC40LTguMy0zLjktMTIuOC02LjJjLTEuOCwwLTMuOCwwLTUuMywwYy0wLjgtMS4xLTEuNS0yLTIuNy0zLjZjMCwyLjEsMCwzLjIsMCw0LjNjLTMuOCwxLjYtNy42LDIuNC0xMC44LDIuM2MtMS4xLTEuMy0yLjMtMi43LTMuNi00LjNjLTEsMS44LTAuNCw0LjItMy40LDQuNWMtMi45LTEuMi02LjMtMi43LTkuOS00LjJjLTEuNiwyLTMuMiw0LTUsNi4zYy00LTEuNS04LDEuOC0xMi4xLDAuMmMtMC41LDAuMy0wLjksMC41LTEuMywwLjdjLTEuNC0xLTIuNy0xLjgtMy42LTIuNWMtMC45LTIuOC0xLjctNS4yLTIuNS03LjljLTEuMiwxLjItMiwyLTMsM2MwLDEuMywwLDIuOCwwLDQuMWMtNSwwLjUtOS43LDItMTQuNiwwLjljLTAuMi0wLjktMC4zLTEuOC0wLjYtM2MtMS4xLDAuNS0xLjksMC45LTIuNiwxLjNjLTEuNy0wLjYtMy4yLTEuMi00LjktMS44Yy0wLjUsMC45LTEuMSwxLjgtMS43LDIuOGMtMC40LTAuOS0wLjktMS44LTEuMi0yLjRjMC4yLTAuNywwLjMtMS4xLDAuNy0yLjFjLTIuNywxLjQtNC43LDIuNC02LjcsMy40Yy0xLjgtMy4zLTQuOC02LjEtNi4xLTUuNmMtMC40LDAuNC0wLjksMC45LTEuMywxLjVjMC4zLDAuNywwLjYsMS4zLDEuMSwyLjNjLTAuOC0wLjEtMS4zLTAuMS0xLjgtMC4xYy0wLjItMS4yLTAuMy0yLTAuNi0zLjVjLTEuNCwxLjEtMi4zLDEuOC0zLjMsMi41Yy0wLjMtMC44LTAuNS0xLjMtMC44LTIuMmMtMS4xLDEuNi0yLjEsMi45LTMuMSw0LjRjLTIuMy0wLjgtNC43LTAuOC01LjYtMy44Yy0wLjUtMS40LTIuMS0yLjMtMy4xLTMuNGMtMC44LDEuMS0xLjUsMi4xLTIuMiwzLjFjLTEuOS0wLjUtMy41LTAuOS01LjUtMS40YzAuNSwxLDAuNywxLjQsMSwyYy0wLjgsMC41LTEuNiwwLjktMi40LDEuM2MtMC40LTEtMC43LTEuNi0xLjEtMi40Yy0xLjUsMC44LTIuNywxLjQtMy44LDJjLTAuNywxLjYtMS4yLDMtMS44LDQuM2MtMS40LTEuOC0yLjYtMy4zLTMuOC00LjhjLTAuMS0wLjctMC4xLTEuNS0wLjItMi4xYy0xLjgsMC0zLjMsMC00LDBjLTMuMSwxLjktNS42LDMuNC04LjMsNWMtMC40LTAuOC0wLjctMS41LTEuMy0yLjZjLTEuNCwwLjctMi43LDEuNC00LjEsMi4xYy0wLjMtMC4yLTAuOS0wLjUtMS42LTFjLTEsMS40LTEuOSwyLjctMi44LDMuOWMtMC4xLDAuMS0wLjQsMC4xLTAuNywwLjFjLTAuMi0xLTAuMy0xLjgtMC41LTMuMWMtMC41LDEuMS0wLjgsMS43LTEuNiwzLjNjMC0yLjIsMC0zLjUsMC00LjdjLTAuMywwLjItMC42LDAuMy0wLjgsMC41Yy0xLjEtMS4yLTIuMy0yLjMtMy42LTMuNmMtMy4zLDEuMi0yLjksNS40LTQuNyw3LjZjLTQuOSwwLjUtOS4zLDAuOS0xMy44LDEuNGMtMC4xLTEuMi0wLjItMi0wLjMtMi45Yy0xLjUsMC0yLjksMC00LjIsMGMtMS41LDAuNy0yLjksMS4zLTQuMiwyYy0wLjMtMC45LTAuNi0xLjUtMC45LTIuNGMtNi43LDEtMTIuNSw0LjQtMTkuMSw2LjZjLTAuOS0xLjItMS45LTIuNC0yLjYtMy40Yy0yLjksMS45LTUuNSwyLjktOC43LDEuMmMtMC41LDAuOS0xLjEsMS45LTEuNiwyLjhjLTAuNywwLTEuMywwLTIuMiwwYy0wLjYtMC45LTEuMy0yLTEuOC0yLjhjLTIuMSwwLjMtNC4xLDAuNy01LjgsMC45Yy0wLjktMC45LTEuNS0xLjUtMi4xLTIuMWMtMS4zLDAuNi01LjgsMS4xLTYuOCwxLjJjLTIuNSwwLjItNC45LDAuNi04LjEsMWMtMC4yLDAuNC0wLjYsMS4zLTEsMmMtMi0wLjYtMy41LTEuMS01LjMtMS42Yy0wLjYsMC44LTEuMSwxLjYtMiwzYy0wLjctMi0xLjItMy4zLTEuNy00LjdjLTEsMS4xLTEuOSwyLjItMi40LDIuOGMtMS45LDAtMi45LDAtNCwwYy0wLjEtMS0wLjEtMS44LTAuMi0yLjRjLTIsMC4zLTQuMSwwLTUuNiwwLjljLTIuMywxLjMtNC44LDEuMS02LjcsMC41Yy0yLjMtMC44LTQuNS0xLjMtNi41LTAuM2MtMi4xLDEuMS00LjctMC41LTYuNCwxLjVjMCwwLjEtMC41LTAuMy0xLTAuNWMwLjQtMC45LDAuOS0xLjksMS4zLTIuOWMtMC45LTAuNy0xLjYtMS4yLTIuOC0yLjFDNDMzLjgsNDMuOCw0MzIuNCw0Ni41LDQzMC45LDQ5LjJ6Ii8+DQoJCTwvZz4NCgk8L2c+DQo8L3N2Zz4=) center repeat-x;
    background-size: auto 0.5em;
    color: rgba(0,0,0,0.5);
    text-decoration: none;
}

.open-book sup {
    color: #cc0000;
    font-family: 'Rock Salt', cursive;
    left: 0;
    margin-top: -1em!important;
    max-width: 100%!important;
    position: absolute;
    text-align: center;
    width: 100%;
}

.open-book mark {
    background: linear-gradient(to bottom, rgba(222,255,0,1) 0%,rgba(222,255,0,0.5) 60%,rgba(222,255,0,1) 100%);
}

.open-book mark.pink {
    background: linear-gradient(to bottom, rgba(255,69,190,1) 0%,rgba(255,107,203,0.5) 60%,rgba(255,107,203,1) 100%);
}

.open-book mark.blue {
    background: linear-gradient(to bottom, rgba(73,179,255,1) 0%,rgba(107,193,255,0.5) 60%,rgba(107,193,255,1) 100%);
}

.open-book mark.green {
    background: linear-gradient(to bottom, rgba(67,226,15,1) 0%,rgba(39,229,54,0.5) 60%,rgba(39,229,54,1) 100%);
}

.open-book mark.orange {
    background: linear-gradient(to bottom, rgba(255,134,9,1) 0%,rgba(255,177,34,0.5) 60%,rgba(255,177,34,1) 100%);
}

.open-book hr {
    background-color: #000;
    border: 0;
    height: 2px;
    margin: 1em 0;
}

.open-book dl {
    border: solid 0 #ccc;
    border-width: 0.0625em 0;
    break-inside: avoid-column;
    margin: 1em auto;
    padding: 1em 0;
}



/*** MEDIA QUERIES ***/
@media only screen and ( min-width: 50em ) {

    .open-book {
        margin: 1em;
        position: relative;
    }

    .open-book:before {
        background-color: #8B4513;
        border-radius: 0.25em;
        bottom: -1em;
        content: '';
        left: -1em;
        position: absolute;
        right: -1em;
        top: -1em;
        z-index: -1;
    }

    .open-book:after {
        background: linear-gradient(to right, transparent 0%,rgba(0,0,0,0.2) 46%,rgba(0,0,0,0.5) 49%,rgba(0,0,0,0.6) 50%,rgba(0,0,0,0.5) 51%,rgba(0,0,0,0.2) 52%,transparent 100%);
        bottom: -1em;
        content: '';
        left: 50%;
        position: absolute;
        top: -1em;
        transform: translate(-50%,0);
        width: 4em;
        z-index: 1;
    }

    .open-book > * {
        column-count: 2;
        column-gap: 6em;
        position: relative;
        z-index: 1;
    }

    /* Header/Footer */
    .open-book header:before,
    .open-book header:after,
    .open-book footer:before,
    .open-book footer:after {
        background: #fff;
        border-radius: 25%;
        content: '';
        height: 2em;
        position: absolute;
        z-index: -1;
        width: calc(50% + 2em);
    }

    .open-book header:before,
    .open-book footer:before,
    .open-book footer:after {
        border-top-left-radius: 0;
    }

    .open-book header:after,
    .open-book footer:before,
    .open-book footer:after {
        border-top-right-radius: 0;
    }

    .open-book header:before,
    .open-book header:after,
    .open-book footer:after {
        border-bottom-right-radius: 0;
    }

    .open-book header:before,
    .open-book header:after,
    .open-book footer:before {
        border-bottom-left-radius: 0;
    }

    .open-book header:before,
    .open-book header:after {
        top: -2.65em;
    }

    .open-book header:before,
    .open-book footer:before {
        right: 50%;
    }

    .open-book header:before {
        transform: rotate(-2deg);
    }

    .open-book header:after,
    .open-book footer:after {
        left: 50%;
    }

    .open-book header:after {
        transform: rotate(2deg);
    }

    .open-book footer:before,
    .open-book footer:after {
        bottom: -2.65em;
    }

    .open-book footer:before {
        transform: rotate(2deg);
    }

    .open-book footer:after {
        transform: rotate(-2deg);
    }

    .open-book header > *:last-child,
    .open-book footer > *:last-child {
        text-align: right;
    }

    .open-book footer #page-numbers {
        display: block;
    }

    /* Chapter Title */
    .open-book .chapter-title {
        font-size: 3em;
    }

    .open-book .chapter-title:before,
    .open-book .chapter-title:after {
        height: 0.125em;
    }

    /* Body Copy */
    .open-book article > ul,
    .open-book article > ol {
        padding-left: 4em;
    }

}
/* tooltip */
[tooltip] {
  position: relative; /* opinion 1 */
}
/* START TOOLTIP STYLES */
[tooltip] {
  position: relative; /* opinion 1 */
}
/* Applies to all tooltips */
[tooltip]::before,
[tooltip]::after {
  text-transform: none; /* opinion 2 */
  font-size: .8em; /* opinion 3 */
  line-height: 1;
  user-select: none;
  pointer-events: none;
  position: absolute;
  display: none;
  opacity: 0;
}
[tooltip]::before {
  content: '';
  border: 5px solid transparent; /* opinion 4 */
  z-index: 1001; /* absurdity 1 */
}
[tooltip]::after {
  content: attr(tooltip); /* magic! */
  
  /* most of the rest of this is opinion */
  font-family:  'Noto Sans KR', Helvetica, sans-serif;
  text-align: center;
  
  /* 
    Let the content set the size of the tooltips 
    but this will also keep them from being obnoxious
    */
  min-width: 3em;
  max-width: 21em;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding: 1ch 1.5ch;
  border-radius: .3ch;
  box-shadow: 0 1em 2em -.5em rgba(0, 0, 0, 0.35);
  background: #333;
  color: #fff;
  z-index: 1000; /* absurdity 2 */
}
/* Make the tooltips respond to hover */
[tooltip]:hover::before,
[tooltip]:hover::after {
  display: block;
}
/* don't show empty tooltips */
[tooltip='']::before,
[tooltip='']::after {
  display: none !important;
}
/* FLOW: RIGHT */
[tooltip][flow^="right"]::before {
  top: 50%;
  border-left-width: 0;
  border-right-color: #333;
  right: calc(0em - 5px);
  transform: translate(.5em, -50%);
}
[tooltip][flow^="right"]::after {
  top: 50%;
  left: calc(100% + 5px);
  transform: translate(.5em, -50%);
}


/* FLOW: DOWN */
[tooltip][flow^="down"]::before {
  top: 100%;
  border-top-width: 0;
  border-bottom-color: #333;
}
[tooltip][flow^="down"]::after {
  top: calc(100% + 5px);
}
[tooltip][flow^="down"]::before,
[tooltip][flow^="down"]::after {
  left: 50%;
  transform: translate(-50%, .5em);
}


/* KEYFRAMES */
@keyframes tooltips-vert {
  to {
    opacity: .9;
    transform: translate(-50%, 0);
  }
}
@keyframes tooltips-horz {
  to {
    opacity: .9;
    transform: translate(0, -50%);
  }
}
/* FX All The Things */ 
[tooltip]:not([flow]):hover::before,
[tooltip]:not([flow]):hover::after,
[tooltip][flow^="up"]:hover::before,
[tooltip][flow^="up"]:hover::after,
[tooltip][flow^="down"]:hover::before,
[tooltip][flow^="down"]:hover::after {
  animation: tooltips-vert 300ms ease-out forwards;
}
[tooltip][flow^="left"]:hover::before,
[tooltip][flow^="left"]:hover::after,
[tooltip][flow^="right"]:hover::before,
[tooltip][flow^="right"]:hover::after {
  animation: tooltips-horz 300ms ease-out forwards;
}


.post-review, .comment-list {
  overflow-y: auto;
}

.post-onelinereview, .post-review, .book-title{
  word-break: keep-all;
}

</style>