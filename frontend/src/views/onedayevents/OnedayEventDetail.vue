<template>
  <div class="custom-container mt-3 mb-5"  v-if="selectedOnedayEvent">
    
    <!-- onedayEvent-detail-header -->
    <div class="row">
      <div class="bookImg-container pointer col-2 p-0">
        <img
          class="book-image img-fluid"
          :src="selectedOnedayEvent.book.bookImg"
          :alt="selectedOnedayEvent.book.title"
          v-if="selectedOnedayEvent.book.bookImg">
        <img 
          class="book-image img-fluid"
          :src="'http://placehold.jp/300x200.png?text=' + selectedOnedayEvent.book.title"
          :alt="selectedOnedayEvent.book.title"
          v-else>
        <div class="book-image overlay p-0">
          <div class="text">{{ selectedOnedayEvent.book.title }}</div>
        </div>
      </div>
      <div class="col-10 py-2 d-flex flex-column justify-content-between">
        <div>
          <div class="d-flex justify-content-start align-items-center mb-2">
            <h3 class="mb-0 font-weight-bold">{{ selectedOnedayEvent.name }}</h3>
            <span class="badge mb-0 ml-2 onedayEvent-closed-true" v-if="selectedOnedayEvent.closed">종료</span>
            <span class="badge mb-0 ml-2 onedayEvent-closed-false" v-else>예정</span>
          </div>
          <div class="d-flex justify-content-start align-items-center">
            <span class="badge badge-genre ml-1">{{ selectedOnedayEvent.book.genre }}</span>
          </div>
        </div>
        <div>
          <div class="d-flex justify-content-between">
            <div class="d-flex flex-column align-items-start justify-content-end">
              <p class="mb-1 ml-1 font-weight-bold"><i class="fas fa-map-marker-alt"></i> {{ selectedOnedayEvent.place }}</p>
              <p class="mb-0 ml-1 font-weight-bold">{{ selectedOnedayEvent.datetime | moment('YYYY년 MM월 DD일 HH시 mm분') }}</p>
            </div>
            <div class="d-flex justify-content-end align-items-end">
              <button
                class="btn btn-secondary dropdown-toggle"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
                v-if="isLeader">
                이벤트 설정
              </button>
              <div class="dropdown-menu py-0">
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="toOnedayEventUpdate(selectedOnedayEvent.id)">
                  이벤트 정보 수정
                </button>
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="clickOnedayEventDelete(selectedOnedayEvent.id)">
                  이벤트 삭제
                </button>
              </div>

              <button
                class="btn btn-warning"
                v-if="!isParticipant && !isLeader && isRecruit"
                @click="clickParticipateOnedayEvent('apply')">
                참가 신청
              </button>
              <button
                class="btn btn-warning"
                v-if="isParticipant && !isLeader"
                @click="clickParticipateOnedayEvent('cancel')">
                참가 취소
              </button>
              <!-- <button class="btn btn-primary mr" v-if="isLeader || isParticipant">모임 입장</button> -->
            </div>
          </div>
        </div>
      </div>
    </div>

    <hr>

    <!-- onedayEvent-detail-members -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">원데이 이벤트 멤버({{ selectedOnedayEvent.participantCnt }}/{{ selectedOnedayEvent.capacity + 1 }})</h4>
      <div class="d-flex justify-content-start">
        <div class="profile-container pointer mr-3" @click="selectUser(selectedOnedayEvent.leader.id)">
          <img
            class="rounded-circle profile-image"
            :src="selectedOnedayEvent.leader.profileImg"
            :alt="selectedOnedayEvent.leader.nickName"
            v-if="selectedOnedayEvent.leader.profileImg">
          <img
            class="rounded-circle profile-image"
            :src="'http://placehold.jp/150x150.png?text=' + selectedOnedayEvent.leader.nickName"
            :alt="selectedOnedayEvent.leader.nickName"
            v-else>
          <div class="overlay rounded-circle">
            <div class="text">{{ selectedOnedayEvent.leader.nickName }}</div>
          </div>
        </div>

        <div
          class="profile-container pointer mr-3"
          v-for="participant in selectedOnedayEvent.participants"
          :key="participant.id"
          @click="selectUser(participant.id)">
          <img
            class="rounded-circle profile-image"
            :src="participant.profileImg"
            :alt="participant.nickName"
            v-if="participant.profileImg">
          <img
            class="rounded-circle profile-image"
            :src="'http://placehold.jp/150x150.png?text=' + participant.nickName"
            :alt="participant.nickName"
            v-else>
          <div class="overlay rounded-circle">
            <div class="text">{{ participant.nickName }}</div>
          </div>
        </div>
      </div>
    </div>

    <hr>

    <!-- onedayEvent-detail-description -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">원데이 이벤트 설명</h4>
      <p class="text-left px-2 description">{{ selectedOnedayEvent.description }}</p>
    </div>

    <hr>

    <!-- onedayEvent-detail-question -->
    <div v-if="isParticipant || isLeader">
      <h4 class="text-left font-weight-bold mb-3">질문지</h4>
      <ul class="ml-4" v-if="selectedOnedayEvent.questions.length !== 0">
        <li
          class="text-left"
          v-for="question in selectedOnedayEvent.questions"
          :key="question.id">
          {{ question.question }}
        </li>
      </ul>
      <div class="no-content d-flex justify-content-center align-items-center" v-else>
        <p class="mb-0">아직 질문지가 없습니다 ㄴ(°0°)ㄱ</p>
      </div>
    </div>

    <hr v-if="isParticipant || isLeader">

    <!-- onedayEvent-detail-posts -->
    <div v-if="isParticipant || isLeader">
      <div class="d-flex justify-content-between align-items-center">
        <h4 class="text-left font-weight-bold mb-3">멤버의 책 리뷰</h4>
        <button class="btn btn-green" @click="toPostCreate(selectedOnedayEvent.book.id)">책 리뷰 작성하기</button>
      </div>
      <div class="d-flex my-2 scroll-sect" id="scroll-area-post" v-if="selectedOnedayEvent.memberPosts.length">
        <div 
          class="pointer px-3"
          v-for="post in selectedOnedayEvent.memberPosts"
          :key="post.id"
          @click="toPostDetail(post.id)"
          style="min-width: 345.59px; max-width: 345.59px">
          <div class="card h-100">
            <div style="max-height:70px;overflow:hidden;">
              <img
                class="bg-image"
                :src="selectedOnedayEvent.book.bookImg"
                :alt="selectedOnedayEvent.book.title"
                v-if="selectedOnedayEvent.book.bookImg">
              <img
                class="bg-image"
                :src="'http://placehold.jp/300x200.png?text=' + selectedOnedayEvent.book.title"
                :alt="selectedOnedayEvent.book.title"
                v-else>
              <h5 class="card-img-top color-light-black px-5 post-user" v-if="post.nickName">
                {{ post.nickName }}
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
      <div class="no-content d-flex justify-content-center align-items-center" v-else>
        <p class="mb-0">아직 멤버의 책 리뷰가 없습니다 ㄴ(°0°)ㄱ</p>
      </div>
    </div>

    <!-- <hr> -->

    <!-- onedayEvent-detail-reviews -->
    <!-- <h4 class="text-left font-weight-bold mb-3">원데이 이벤트 기록</h4>
    <div class="no-content d-flex justify-content-center align-items-center" v-if="!selectedOnedayEvent.reviews">
      <p class="mb-0">아직 원데이 이벤트 기록이 없습니다 ㄴ(°0°)ㄱ</p>
    </div> -->
  </div>
</template>

<script>
import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'OnedayEventDetail',
  computed: {
    ...mapState(['myaccount']),
    ...mapState('onedayEventStore', ['selectedOnedayEvent']),
    isRecruit() {
      if (this.selectedOnedayEvent.participantCnt === (this.selectedOnedayEvent.capacity + 1)) {
        return false
      } else {
        return true
      }
    },
    isParticipant() {
      let result = false
      this.selectedOnedayEvent.participants.forEach(participant => {
        if (participant.id === this.myaccount.id) {
          result = true
        }
      })
      return result
    },
    isLeader() {
      if (this.selectedOnedayEvent.leader.id === this.myaccount.id) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    ...mapActions('onedayEventStore', ['findOnedayEvent', 'participateOnedayEvent', 'deleteOnedayEvent']),
    selectUser(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    },
    clickParticipateOnedayEvent(type) {
      if (type === 'apply') {
        if (confirm('원데이 이벤트에 참가하시겠습니까?') === true) {
          this.participateOnedayEvent(this.$route.params.onedayEventId)
        } else {
          return false
        }
      } else if (type === 'cancel') {
        if (confirm('원데이 이벤트 참가를 취소하시겠습니까?') === true) {
          this.participateOnedayEvent(this.$route.params.onedayEventId)
        } else {
          return false
        }
      }
    },
    toPostDetail(postId) {
      router.push({ name: 'PostDetail', params: { postId: postId }})
    },
    toPostCreate(bookId) {
      router.push({ name: 'PostCreate', params: { selectedBookId: bookId }})
    },
    toOnedayEventUpdate(onedayEventId) {
      router.push({ name: 'OnedayEventUpdate', params: { onedayEventId: onedayEventId }})
    },
    clickOnedayEventDelete(onedayEventId) {
      if (confirm('원데이 이벤트를 삭제하시겠습니까?') === true) {
        this.deleteOnedayEvent(onedayEventId)
      } else {
        return false
      }
    }
  },
  created() {
    this.findOnedayEvent(this.$route.params.onedayEventId)
  },
  mounted() {
    function stopWheel(e){
      if(!e){ e = window.event; } /* IE7, IE8, Chrome, Safari */
      if(e.preventDefault) { e.preventDefault(); } /* Chrome, Safari, Firefox */
      e.returnValue = false; /* IE7, IE8 */
    }

    const scrollAreaClub = document.querySelector('#scroll-area-post')
    scrollAreaClub.addEventListener('wheel', (e) => {
      scrollAreaClub.scrollLeft += e.deltaY;
      stopWheel()
    })
  },
}
</script>

<style scoped>
  .book-image {
    padding: 0;
    display: block;
    width: 100%;
    height: 100%;
    border-radius: 25px;
  }

  .onedayEvent-closed-false {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    padding: 5px;
  }

  .onedayEvent-closed-true {
    background-color: #707070; 
    color: #F8F8F8;
    text-align: center;
    padding: 5px;
  }

  .profile-container {
    position: relative;
  }

  .bookImg-container {
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

  .bookImg-container:hover .overlay {
    opacity: 0.8;
  }

  .profile-image {
    display: block;
    width: 150px;
    height: 150px;
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

  .description {
    white-space: pre-line;
  }

  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .scroll-sect {
    overflow: hidden;
  }

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
    background: #88A498; 
    -webkit-border-radius: 15px; border-radius: 15px; 
    /* -webkit-box-shadow: inset 0 0 4px rgba(0,0,0,.1) */
  }

  .scroll-sect:hover{
    overflow-x: scroll;
  }
</style>