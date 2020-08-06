<template>
  <div class="custom-container mt-3 mb-5"  v-if="selectedClubEvent">
    <div v-if="isLeader || isParticipant">
    
    <!-- clubEvent-detail-header -->
    <div class="row">
      <div class="bookImg-container pointer col-2 p-0">
        <img
          class="book-image img-fluid"
          :src="selectedClubEvent.book.bookImg"
          :alt="selectedClubEvent.book.title"
          v-if="selectedClubEvent.book.bookImg">
        <img 
          class="book-image img-fluid"
          :src="'http://placehold.jp/300x200.png?text=' + selectedClubEvent.book.title"
          :alt="selectedClubEvent.book.title"
          v-else>
        <div class="book-image overlay p-0">
          <div class="text">{{ selectedClubEvent.book.title }}</div>
        </div>
      </div>
      <div class="col-10 py-2 d-flex flex-column justify-content-between">
        <div>
          <div class="d-flex justify-content-start align-items-center mb-2">
            <h3 class="mb-0 font-weight-bold">{{ selectedClubEvent.name }}</h3>
            <span class="badge mb-0 ml-2 clubEvent-closed-true" v-if="selectedClubEvent.closed">종료</span>
            <span class="badge mb-0 ml-2 clubEvent-closed-false" v-else>예정</span>
          </div>
          <div class="d-flex justify-content-start align-items-center">
            <img
              class="club-image pointer"
              :src="selectedClub.clubImg"
              :alt="selectedClub.name"
              @click="toSelectedClub(selectedClub.id)">
            <p
              class="ml-1 mb-0 font-weight-bold club-name pointer"
              @click="toSelectedClub(selectedClub.id)">
              {{ selectedClub.name }}
            </p>
          </div>
        </div>
        <div>
          <div class="d-flex justify-content-between">
            <div class="d-flex flex-column align-items-start justify-content-end">
              <p class="mb-1 font-weight-bold"><i class="fas fa-map-marker-alt"></i> {{ selectedClubEvent.place }}</p>
              <p class="mb-0 font-weight-bold">{{ selectedClubEvent.dateTime | moment('YYYY년 MM월 DD일 HH시 mm분') }}</p>
            </div>
            <div class="d-flex justify-content-end align-items-end">
              <button class="btn btn-secondary" v-if="isLeader">클럽 이벤트 설정</button>
              <button
                class="btn btn-warning"
                v-if="selectedClubEvent.isMember & !isParticipant & !isLeader"
                @click="clickParticipateClubEvent('apply')">
                참가 신청
              </button>
              <button
                class="btn btn-warning"
                v-if="selectedClubEvent.isMember & isParticipant & !isLeader"
                @click="clickParticipateClubEvent('cancel')">
                참가 취소
              </button>
              <!-- <button class="btn btn-primary mr" v-if="isLeader || isParticipant">모임 입장</button> -->
            </div>
          </div>
        </div>
      </div>
    </div>

    <hr>

    <!-- clubEvent-detail-members -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">클럽 이벤트 멤버({{ selectedClubEvent.participantCnt }})</h4>
      <div class="d-flex justify-content-start">
        <div class="profile-container pointer mr-3" @click="selectUser(selectedClubEvent.leader.id)">
          <img
            class="rounded-circle profile-image"
            :src="selectedClubEvent.leader.profileImg"
            :alt="selectedClubEvent.leader.nickName"
            v-if="selectedClubEvent.leader.profileImg">
          <img
            class="rounded-circle profile-image"
            :src="'http://placehold.jp/150x150.png?text=' + selectedClubEvent.leader.nickName"
            :alt="selectedClubEvent.leader.nickName"
            v-else>
          <div class="overlay rounded-circle">
            <div class="text">{{ selectedClubEvent.leader.nickName }}</div>
          </div>
        </div>

        <div
          class="profile-container pointer mr-3"
          v-for="participant in selectedClubEvent.participants"
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

    <!-- clubEvent-detail-description -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">클럽 이벤트 설명</h4>
      <p class="text-left px-2 description">{{ selectedClubEvent.description }}</p>
    </div>

    <hr>

    <!-- clubEvent-detail-question -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">질문지</h4>
      <ul class="ml-4" v-if="selectedClubEvent.questions.length !== 0">
        <li
          class="text-left"
          v-for="question in selectedClubEvent.questions"
          :key="question.id">
          {{ question.question }}
        </li>
      </ul>
      <div class="no-content d-flex justify-content-center align-items-center" v-else>
        <p class="mb-0">아직 질문지가 없습니다 ㄴ(°0°)ㄱ</p>
      </div>
    </div>

    <hr>

    <!-- clubEvent-detail-posts -->
    <div>
      <div class="d-flex justify-content-between align-items-center">
        <h4 class="text-left font-weight-bold mb-3">멤버의 책 리뷰</h4>
        <button class="btn btn-green" @click="toPostCreate(selectedClubEvent.book.id)">책 리뷰 작성하기</button>
      </div>
      <div class="row rows-cols-1 row-cols-md-3" v-if="selectedClubEvent.memberPosts.length !== 0">
        <div 
          class="col-12 col-sm-4 mb-4 pointer"
          v-for="post in selectedClubEvent.memberPosts"
          :key="post.id"
          @click="toPostDetail(post.id)">
          <div class="card h-100">
            <div style="max-height:70px;overflow:hidden;">
              <img
                class="bg-image"
                :src="selectedClubEvent.book.bookImg"
                :alt="selectedClubEvent.book.title"
                v-if="selectedClubEvent.book.bookImg">
              <img
                class="bg-image"
                :src="'http://placehold.jp/300x200.png?text=' + selectedClubEvent.book.title"
                :alt="selectedClubEvent.book.title"
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

    <hr>

    <!-- clubEvent-detail-reviews -->
    <h4 class="text-left font-weight-bold mb-3">클럽 이벤트 기록</h4>
    <div class="no-content d-flex justify-content-center align-items-center" v-if="!selectedClubEvent.reviews">
      <p class="mb-0">아직 클럽 이벤트 기록이 없습니다 ㄴ(°0°)ㄱ</p>
    </div>
    </div>
    <div v-else class="mt-5">
      <img
        src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png"
        width="150px"
        class="mt-3">
      <h3 class="my-3">아쉽지만 클럽 멤버만 접근 가능합니다.</h3>
      <button class="btn btn-green" @click="toSelectedClub(selectedClub.id)">클럽으로 돌아가기</button>
    </div>
  </div>
</template>

<script>
import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ClubEventDetail',
  data() {
    return {
      params: {
        clubId: this.$route.params.clubId,
        clubEventId: this.$route.params.clubEventId
      }
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('clubStore', ['selectedClub', 'selectedClubEvent']),
    isParticipant() {
      let result = false
      this.selectedClubEvent.participants.forEach(participant => {
        if (participant.id === this.myaccount.id) {
          result = true
        }
      })
      return result
    },
    isLeader() {
      if (this.selectedClubEvent.leader.id === this.myaccount.id) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    ...mapActions('clubStore', ['findClub', 'findClubEvent', 'participateClubEvent']),
    selectUser(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    },
    clickParticipateClubEvent(type) {
      if (type === 'apply') {
        if (confirm('클럽 이벤트에 참가하시겠습니까?') === true) {
          this.participateClubEvent(this.params)
        } else {
          return false
        }
      } else if (type === 'cancel') {
        if (confirm('클럽 이벤트 참가를 취소하시겠습니까?') === true) {
          this.participateClubEvent(this.params)
        } else {
          return false
        }
      }
    },
    toPostDetail(postId) {
      router.push({ name: 'PostDetail', params: { postId: postId }})
    },
    toSelectedClub(clubId) {
      router.push({ name: 'ClubDetail', params: { clubId: clubId}})
    },
    toPostCreate(bookId) {
      router.push({ name: 'PostCreate', params: { selectedBookId: bookId }})
    }
  },
  created() {
    this.findClub(this.params.clubId)
    this.findClubEvent(this.params)
  }
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

  .clubEvent-closed-false {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    padding: 5px;
  }

  .clubEvent-closed-true {
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

  .club-image {
    width: 40px;
    height: 40px;
    border-radius: 20px;
  }

  .club-name:hover {
    color: #88A498;
  }
</style>