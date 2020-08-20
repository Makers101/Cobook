<template>
  <div class="mt-5">
    <div class="d-flex justify-content-center" v-if="books.length"> 
      <div class="book-container">
        <div class="row no-gutters row-cols-1 row-cols-md-3">
          <div v-for="book in genreBooks" :key="`genreBooks-${book.id}`">
            <div class="col mb-4 p-3">
              <div class="card h-100 pointer" @click="bookDetail(book.id)">
                <img class="bookImg card-img-top" :src="book.bookImg">
                <div class="overlay">
                  <div class="text">
                    <p class="m-0">{{ book.title }}</p>
                    <small>By. {{book.author}}</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-for="book in books" :key="book.id">
            <div class="col mb-4 p-3">
              <div class="card h-100 pointer" @click="bookDetail(book.id)">
                <img class="bookImg card-img-top" :src="book.bookImg">
                <div class="overlay">
                  <div class="text">
                    <p class="m-0">{{ book.title }}</p>
                    <small>By. {{book.author}}</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="no-result" v-else>
      <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
      <h3 class="mt-3">검색된 키워드의 도서가 없습니다. </h3>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'SearchBook',
  computed: {
     ...mapState('searchStore', ['books', 'genreBooks']),
  },
  methods: {
    ...mapActions('searchStore', ['fetchBooks', 'fetchGenreBooks']),
    bookDetail(bookId) {
      this.$router.push({ name: 'BookDetail', params: { bookId: bookId}})
    },
  },
  created() {
    this.fetchBooks(this.$route.params.content)
    this.fetchGenreBooks(this.$route.params.content)
  },
  beforeRouteUpdate (to, from, next) {
    this.fetchBooks(this.$route.params.content)
    this.fetchGenreBooks(this.$route.params.content)
    next();
  },
}
</script>

<style scoped>
.book-container {
  width: 70%
}

.card {
  box-shadow: 8px 8px 10px 3px rgb(0, 0, 0, 0.2)
}

.image {
  display: block;
  width: 100%;
  height: auto;
}

.card .overlay {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  /* transition: .5s ease; */
  background-color: rgba(0, 0, 0, 0.7);
}

.card:hover .overlay {
  opacity: 1;
  -webkit-transition: all 0.4s ease-in-out 0s;
  -moz-transition: all 0.4s ease-in-out 0s;
  transition: all 0.4s ease-in-out 0s;
}
.card:hover .text {
  top: 50%;
  left: 50%;
  opacity: 1;
}

.text {
  width: 80%;
  color: white;
  font-size: 1em;
  position: absolute;
  padding-left: 0.05em;
  padding-right: 0.05em;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  transition: all 0.3s ease-in-out 0s;
  text-align: center;
  -webkit-line-clamp: 6; 
}

  
.text p {
  color: #fff;
  text-shadow: 1px 1px 2px rgb(0,0,0,0.7);
  font-size: 1em;
  font-weight: 300;
  white-space: pre-wrap;
  word-break: keep-all;
  overflow: hidden;
  white-space: normal;
  display: -webkit-box;
  -webkit-line-clamp: 4; 
  -webkit-box-orient: vertical;
}

.no-result {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -150px 0px 0px -200px;
}
</style>