<template>
  <div class="custom-container">
    <!-- reading-header -->
    <div class="row">
      <img class="book-image col-2" :src="selectedReading.book.bookImage" alt="" v-if="selectedReading.book.bookImage">
      <img class="book-image col-2" src="http://placehold.jp/300x200.png?text=sample" alt="" v-else>
      <div class="col-10 py-2 d-flex flex-column justify-content-between">
        <div>
          <div class="d-flex justify-content-start align-items-center mb-2">
            <h3 class="mb-0 font-weight-bold">{{ selectedReading.name }}</h3>
            <span class="badge mb-0 ml-2 reading-closed-true" v-if="selectedReading.closed">종료</span>
            <span class="badge mb-0 ml-2 reading-closed-false" v-else>예정</span>
          </div>
          <p class="text-left">{{ selectedReading.book.bookTitle }}</p>
        </div>
        
        <div>
          <div class="d-flex justify-content-between">
            <div class="d-flex flex-column align-items-start justify-content-end">
              <p class="mb-0"><i class="fas fa-map-marker-alt"></i> {{ selectedReading.place }}</p>
              <p class="mb-0">{{ selectedReading.dateTime.slice(0, 10) }}</p>
            </div>
            <div class="d-flex justify-content-end align-items-end">
              <button class="btn btn-secondary mr-2">리딩 설정</button>
              <button class="btn btn-warning mr-2">참가 신청</button>
              <button class="btn btn-primary mr">모임 입장</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <hr>

    <!-- reading-members -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">리딩 멤버({{ selectedReading.participantCnt }})</h4>
      <div class="d-flex justify-content-start">
        <div class="profile-container pointer" v-for="member in selectedReading.members" :key="member.id">
          <img class="rounded-circle image" :src="member.proflieImg" alt="" v-if="member.profileImg">
          <img class="rounded-circle image" src="http://placehold.jp/150x150.png?text=profile" alt="" v-else>
          <div class="overlay rounded-circle">
            <div class="text">{{ member.userName }}</div>
          </div>
        </div>
      </div>
    </div>

    <hr>

    <!-- reading-description -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">리딩 설명</h4>
      <p class="text-left px-2">{{ selectedReading.description }}</p>
    </div>

    <hr>

    <!-- reading-question -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">질문지</h4>
      <ul class="ml-4">
        <li class="text-left" v-for="question in selectedReading.questions" :key="question.id">{{ question.question }}</li>
      </ul>
    </div>

    <hr>
    <!-- reading-posts -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">멤버의 책 리뷰</h4>
      
      <div class="row rows-cols-1 row-cols-md-3">
        <div class="col-12 col-sm-4 mb-4 pointer" v-for="post in selectedReading.memberPosts" :key="post.id">
          <div class="card h-100">
            <div style="max-height:70px;overflow:hidden;">
              <img class="bg-image" :src="`${ selectedReading.book.bookImage }`" v-if="selectedReading.book.bookImage">
              <h5 
                class="card-img-top color-light-black px-5 post-user" 
                alt="book"
                v-if="post.author.userName" 
              >
                {{ post.author.userName }}
              </h5>
            </div>
            <div class="card-body bg-light-ivory d-flex flex-column">
              <div class="mt-auto">
                <div class="w-100">
                  <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
                  <p class="card-text" style="word-break:keep-all;">{{ post.onelineReview }}</p>
                  <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
                </div>
              </div>
              <div class="text-left bg-light-ivory pt-0 mt-auto">
                <span class="mr-3"><i class="fas fa-heart mr-2"></i>{{ post.likeUsers.length }}</span>
              </div>
            </div>
          </div>
        </div>            
      </div>
      
      <div class="no-content d-flex justify-content-center align-items-center" v-if="!selectedReading.memberPosts">
        <p class="mb-0">아직 멤버의 책 리뷰가 없습니다 ㄴ(°0°)ㄱ</p>
      </div>
    </div>

    <hr>

    <!-- reading-reviews -->
    <h4 class="text-left font-weight-bold mb-3">리딩 기록</h4>
    <div class="no-content d-flex justify-content-center align-items-center" v-if="!selectedReading.reviews">
        <p class="mb-0">아직 리딩 기록이 없습니다 ㄴ(°0°)ㄱ</p>
      </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ReadingDetail',
  data() {
    return {
      params: {
        clubId: this.$route.params.clubId,
        readingId: this.$route.params.readingId
      }
    }
  },
  computed: {
    ...mapState('clubStore', ['selectedReading'])
  },
  methods: {
    ...mapActions('clubStore', ['findReading'])
  },
  created() {
    this.findReading(this.params)
  }
}
</script>

<style scoped>
  .book-image {
    border-radius: 25px;
    padding: 8px;
  }

  .reading-closed-false {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    padding: 5px;
  }

  .reading-closed-true {
    background-color: #707070; 
    color: #F8F8F8;
    text-align: center;
    padding: 5px;
  }

  .profile-container {
    position: relative;
  }

  .overlay {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100%;
    width: 100%;
    opacity: 0;
    transition: .5s ease;
    background-color: #3e3f3f;
  }

  .profile-container:hover .overlay {
    opacity: 0.8;
  }

  .image {
    display: block;
    width: 100%;
    height: auto;
  }

  .text {
    color: white;
    font-size: 20px;
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    text-align: center;
    width: 80%;
    font-weight: bold;
  }
  
  .no-content {
    height: 8rem;
  }

.bg-image {
  position: static;
  width: 100%;
  max-height: initial; 
  margin-top: 0%;
  filter: blur(5px);
}

.post-user {
  position: absolute;
  top: 10%;
  left: 0;
  text-shadow: 1px 1px 2px white;
  font-weight: 900;
  word-break: keep-all;
}

</style>