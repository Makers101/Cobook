<template>
  <div>
    <div id="wrapper">
      <div id="container">
        <section class="open-book">
          <header>
            <p class="text-black-50">Book Info</p>
          </header>
          <article>
            <!-- 페이지1 -->
            <div class="page1 d-flex flex-column" style="height: 580px">
              <!-- 책 제목 -->
              <div class="d-flex align-items-center" style="height:85px;"> 
                <h2 class="chapter-title book-title pt-0">『{{ selectedBook.title }}』</h2>
              </div>
              <div class="d-flex">
                <!-- 책 이미지 -->
                <div class="w-50 mr-3">
                  <img 
                    class="book-image" 
                    :src="selectedBook.bookImg" 
                    alt="책 이미지">
                </div>
                <!-- 책 정보 -->
                <div class="mx-0 mt-3 row no-gutters align-items-center text-left">
                  <!-- pubDate, author, publisher, translators, url -->
                  <div class="row no-gutters">
                    <div class="col-5 col-lg-4"> 
                      <strong>저자</strong>
                      <img class="ml-1 mr-2" src="https://user-images.githubusercontent.com/25967949/88953039-4a9da800-d2d3-11ea-8f6b-5792b4f87c45.png" width="20px"> 
                    </div>
                    <div class="col-7 col-lg-8">
                      <span class="text-left">{{ selectedBook.author }} </span>
                    </div>
                  </div>
                  <div class="row no-gutters align-items-center text-left" v-if="selectedBook.translators.length">
                    <div class="col-5 col-lg-4"> 
                      <strong>번역가</strong>
                      <i class="fas fa-language color-green ml-1 mr-2" width="20px"></i>
                    </div>
                    <div class="col-7 col-lg-8">  
                      <span>{{ selectedBook.translators }}</span>
                    </div>
                  </div>
                  <div class="row no-gutters align-items-center text-left">
                    <div class="col-5 col-lg-4">
                      <strong>출판사</strong>
                      <img class="ml-1" src="https://user-images.githubusercontent.com/25967949/88953045-4b363e80-d2d3-11ea-8f26-0502556bf651.png" width="20px">
                    </div>
                    <div class="col-7 col-lg-8">
                      <span>{{ selectedBook.publisher }}</span>
                    </div>
                  </div>
                  <div class="row no-gutters align-items-center text-left">
                    <div class="col-5 col-lg-4">
                      <strong>출판일</strong>
                      <img class="ml-1" src="https://user-images.githubusercontent.com/25967949/88953046-4bced500-d2d3-11ea-8a79-23e48bd595f1.png" width="20px"> 
                    </div>
                    <div class="col-7 col-lg-8">
                      <span>{{ selectedBook.pubDate | moment('YYYY-MM-DD')}}</span>
                    </div>
                  </div>
                  <div class="row no-gutters align-items-center text-left">
                    <div class="col-5 col-lg-4">
                      <strong>장르</strong>
                      <i class="fas fa-theater-masks color-green ml-2"></i> 
                    </div>
                    <div class="col-7 col-lg-8 pointer">
                      <span @click="searchGenre(selectedBook.genre.name)">{{ selectedBook.genre.name }}</span>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 책 줄거리 -->
              <div class="mt-3 text-left" v-if="selectedBook.contents !== ''">
                <h6 class="mb-2"><strong>줄거리</strong></h6>
                {{ selectedBook.contents }}...
              </div>
              <div class="mt-5" v-else>
                <h6>줄거리가 없습니다.</h6>
              </div>
              <div v-for="genre in selectedBook.Genres" :key="genre.id">
                <p>#{{ genre.name }}</p>
              </div>
              <div class="d-flex justify-content-between mt-auto">
                <button class="btn btn-green m-0" @click="clickPostCreate(selectedBook.id)" v-if="selectedBook.posts.length">리뷰 작성하러 가기</button>
                <button v-else></button>
                <button class="btn btn-green m-0"><a class="url" :href="selectedBook.url" target="_blank">자세히보기</a></button>
              </div>
            </div>
            <!-- 페이지2 -->
            <div class="page2" style="height: 600px">
              <h5 class="mb-4"><strong>코북 유저들의 리뷰</strong></h5>
              <div class="review-list scroll-sect"  v-if="selectedBook.posts.length">
                <div class="mb-3" v-for="post in selectedBook.posts" :key="post.id">
                  
                    <div 
                      class="card-style row no-gutters pointer" 
                      style="height: 100px"
                      @click="postDetail(post.id)"
                      :key="post.id">
                      <div class="col-4 bg-green d-flex align-items-center left-card">
                        <div>
                          <span class="rounded-circle bg-green">
                            <img
                              v-if="!post.profileImg"
                              class="img-fluid feed-profile-img mt-2"
                              style="max-width: 30%; border-radius: 50%;" 
                              src="http://bit.do/anonymouseuser"
                              alt="유저 프로필 사진">
                            <img 
                              v-else
                              class="img-fluid feed-profile-img mt-2"
                              style="max-width: 30%; border-radius: 50%;" 
                              :src="post.profileImg" alt="작성자 프로필 사진">
                          </span>
                          <span class="other-nickName">
                            <p class="mb-2 px-2">{{ post.nickName }}</p>
                          </span>
                        </div>
                      </div>
                      <!-- style="border: 1px solid #88A498;" -->
                      
                      <div class="col-8 d-flex align-items-center">
                        <div>
                          <div class="px-3 large-text text-left"><i class="fas fa-quote-left"></i></div>
                          <div class="px-4 text-center post-onelineReview">{{ post.onelineReview }}</div>               
                          <div class="px-3 large-text text-right"><i class="fas fa-quote-right"></i></div>
                        </div>
                      </div>
                      
                    </div>
                  
                </div>
              </div>
              <div class="review-list d-flex align-items-center" v-else>
                <div>
                  <h5 class="mb-1">아직 다른 유저들이 작성한 리뷰는 없어요 ㅠ_ㅠ</h5>
                  <h5>리뷰... 작성해주시겠어요?</h5>
                  <button class="btn btn-green mt-3" @click="clickPostCreate(selectedBook.id)">리뷰 작성하러 가기</button>
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
'use strict';

export default {
  name: "Book",
  data() {
    return {

    }
  },
  computed: {
    ...mapState('bookStore', ['selectedBook']),
  },
  methods: {
    ...mapActions('bookStore', ['findBook']),
    clickPostCreate(bookId) {
      this.$router.push({ name: 'PostCreate', params: { selectedBookId: bookId }})
    },
    postDetail(postId) {
      this.$router.push({ name: 'PostDetail', params: { postId: postId }})
    },
    searchGenre(content) {
      this.$router.push({ name: 'SearchBook', params: {content: content} })
    }
  },
  mounted() {
    this.findBook(this.$route.params.bookId)
  }
}
</script>

<style scoped>

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

.book-image {
  height: 200px;
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
  border-radius: 6px;
}

.other-nickName {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  line-height: 2em;
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
    font-size: 1.5em !important;
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
    background-size: auto 0.25em;
    color: inherit;
    text-decoration: none;
}

.open-book s,
.open-book strike {

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
        text-align: left;
    }

    .open-book footer #page-numbers {
        display: block;
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

p, h1, h2, h3, h4, h5, h6, div {
  width: 100%;
}

.url:active, .url:link, .url:active {
  text-decoration: none !important;
}

.card-style {
  border-radius: 10px;
  box-shadow: rgba(0,0,0,0.2) 0 0.8em 0.8em;
  width: 95%;
  border: 0.5px solid rgb(136, 164, 152, 0.2);
}

.left-card {
  border-bottom-left-radius: 5px;
  border-top-left-radius: 5px;
}

.post-onelineReview {
  word-break: break-word;
}

.review-list {
  overflow-y: auto;
  height: 550px;
}

/* scroll */
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
  background: #88A498 ; 
  -webkit-border-radius: 15px; border-radius: 15px; 
  /* -webkit-box-shadow: inset 0 0 4px rgba(0,0,0,.1) */
}

.btn-genre {
  background-color: #88A498;
  color: #F8F8F8;
  opacity: 1;
}

.book-genre {
  border: 1px solid #88A498;
  border-radius: 10%;
}
</style>