<template>
  <div class="custom-container mt-3 mb-5"  v-if="selectedClubEvent">
    <div v-if="selectedClubEvent.isMember">
    
      <!-- clubEvent-detail-header -->
      <div class="row">
        <div class="bookImg-container pointer col-2 p-0" @click="toBookDetail(selectedClubEvent.book.id)">
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
              <span class="badge badge-genre ml-1">{{ selectedClubEvent.book.genre }}</span>
            </div>
            <div class="d-flex justify-content-start align-items-center mt-2 ml-1">
              <span>in</span>
              <p class="ml-1 mb-0 font-weight-bold club-name pointer" @click="toSelectedClub(selectedClub.id)">{{ selectedClub.name }}</p>
            </div>
          </div>
          <div>
            <div class="d-flex justify-content-between">
              <div class="d-flex flex-column align-items-start justify-content-end">
                <p class="mb-1 ml-1 font-weight-bold"><i class="fas fa-map-marker-alt"></i> {{ selectedClubEvent.place }}</p>
                <p class="mb-0 ml-1 font-weight-bold">{{ selectedClubEvent.datetime | moment('YYYY년 MM월 DD일 HH시 mm분') }}</p>
              </div>
              <div class="d-flex justify-content-end align-items-end">
                <button
                  type="button"
                  class="btn btn-green mr-2"
                  data-toggle="modal" data-target="#makeRoomModal"
                  v-if="isLeader & !selectedClubEvent.closed & (selectedClubEvent.place === '온라인') & !selectedClubEvent.roomUrl">
                  온라인 만들기
                </button>
                <button
                  class="btn btn-green mr-2"
                  v-else-if="(isLeader || isParticipant) && !selectedClubEvent.closed && (selectedClubEvent.place === '온라인') && selectedClubEvent.roomUrl">
                  <a :href="selectedClubEvent.roomUrl" target="_blank">온라인 입장</a>
                </button>
                <!-- Modal -->
                <div class="modal fade" id="makeRoomModal" tabindex="-1" aria-labelledby="makeRoomModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="makeRoomModalLabel">온라인 만들기</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        <p>webex에 가입되어 있는 이메일을 입력해주세요!</p>
                        <input type="email" v-model="webexEmail">
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-green" @click="createLeader" data-dismiss="modal">온라인 만들기</button>
                      </div>
                    </div>
                  </div>
                </div>

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
                    @click="toClubEventUpdate(selectedClubEvent.id)">
                    이벤트 정보 수정
                  </button>
                  <button 
                    class="dropdown-item setting-btn text-center"
                    type="button"
                    @click="clickClubEventDelete()">
                    이벤트 삭제
                  </button>
                </div>
                
                <button
                  class="btn btn-warning"
                  v-if="selectedClubEvent.isMember & !isParticipant & !isLeader"
                  @click="clickParticipateClubEvent('apply')">
                  참가 신청
                </button>
                <button
                  class="btn btn-gray"
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
        <h4 class="text-left font-weight-bold mb-3">북클럽 이벤트 멤버({{ selectedClubEvent.participantCnt }})</h4>
        <carousel
          :loop="true"
          :navigationEnabled="true"
          navigationNextLabel="<h3><i class='fas fa-angle-right'></i></h3>"
          navigationPrevLabel="<h3><i class='fas fa-angle-left'></i></h3>"
          :perPageCustom="[[1, 1], [600, 2], [900, 3], [1200, 4], [1400, 5]]"
          paginationActiveColor="#3c756a"
          paginationColor="#88A498"
          paginationPadding="4"
          paginationSize="10"
          easing="linear"
          speed="300">
          <slide>
            <div class="profile-container pointer" @click="selectUser(selectedClubEvent.leader.id)">
              <img
                class="rounded-circle profile-image mx-auto"
                :src="selectedClubEvent.leader.profileImg"
                :alt="selectedClubEvent.leader.nickName"
                v-if="selectedClubEvent.leader.profileImg">
              <img
                class="rounded-circle profile-image mx-auto"
                src="http://bit.do/anonymouseuser"
                :alt="selectedClubEvent.leader.nickName"
                v-else>
              <div class="overlay rounded-circle mx-auto">
                <div class="text">{{ selectedClubEvent.leader.nickName }}</div>
              </div>
            </div>
          </slide>
          
          <slide v-for="participant in selectedClubEvent.participants" :key="participant.id">
            <div
              class="profile-container pointer"
              @click="selectUser(participant.id)">
              <img
                class="rounded-circle profile-image mx-auto"
                :src="participant.profileImg"
                :alt="participant.nickName"
                v-if="participant.profileImg">
              <img
                class="rounded-circle profile-image mx-auto"
                src="http://bit.do/anonymouseuser"
                :alt="participant.nickName"
                v-else>
              <div class="overlay rounded-circle mx-auto">
                <div class="text">{{ participant.nickName }}</div>
              </div>
            </div>
          </slide>
        </carousel>
      </div>

      <!-- <div>
        <h4 class="text-left font-weight-bold mb-3">북클럽 이벤트 멤버({{ selectedClubEvent.participantCnt }})</h4>
        <div class="d-flex justify-content-start">
          <div class="profile-container pointer mr-3" @click="selectUser(selectedClubEvent.leader.id)">
            <img
              class="rounded-circle profile-image"
              :src="selectedClubEvent.leader.profileImg"
              :alt="selectedClubEvent.leader.nickName"
              v-if="selectedClubEvent.leader.profileImg">
            <img
              class="rounded-circle profile-image"
              src="http://bit.do/anonymouseuser"
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
              src="http://bit.do/anonymouseuser"
              :alt="participant.nickName"
              v-else>
            <div class="overlay rounded-circle">
              <div class="text">{{ participant.nickName }}</div>
            </div>
          </div>
        </div>
      </div> -->

      <hr>

      <!-- clubEvent-detail-description -->
      <div>
        <h4 class="text-left font-weight-bold mb-3">북클럽 이벤트 설명</h4>
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
      <div v-if="isParticipant || isLeader">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h4 class="text-left font-weight-bold mb-0">멤버의 책 리뷰</h4>
          <button class="btn btn-green" @click="toPostCreate(selectedClubEvent.book.id)">책 리뷰 작성하기</button>
        </div>

        <carousel
          :loop="true"
          :navigationEnabled="true"
          navigationNextLabel="<h3><i class='fas fa-angle-right'></i></h3>"
          navigationPrevLabel="<h3><i class='fas fa-angle-left'></i></h3>"
          :perPageCustom="[[1, 1], [1000, 2], [1500, 3]]"
          paginationActiveColor="#3c756a"
          paginationColor="#88A498"
          paginationPadding="4"
          paginationSize="10"
          easing="linear"
          speed="300"
          v-if="selectedClubEvent.memberPosts.length">
        
          <slide
            v-for="post in selectedClubEvent.memberPosts"
            :key="post.id">
            <div class="card pointer mx-auto my-auto" @click="toPostDetail(post.id)" style="width: 315px">
              <div class="additional d-flex justify-content-center">
                <div class="user-card">
                  <div class="level center">
                    {{ post.nickName }}
                  </div>
                  <div class="points center">
                    <i class="fas fa-heart mr-1"></i> {{ post.likeUsers.length }}
                  </div>
                  <img :src="post.profileImg" v-if="post.profileImg">
                  <img src="http://bit.do/anonymouseuser" v-else>
                </div>
              </div>
              <div class="general d-flex flex-column justify-content-between">
                <div class="w-100 h-100 d-flex flex-column justify-content-around">
                  <div class="mb-2">
                    <span class="mb-3 star-container" v-for="index in post.rank" :key="index"><i class="fas fa-star" style="color:yellow"></i></span>
                  </div>
                  <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
                  <p class="card-text px-3" style="word-break:keep-all;">{{ post.onelineReview }}</p>
                  <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
                </div>
                <div class="more">
                  <span class="text-black-50"><small>{{ post.createdAt | moment('YYYY-MM-DD')}}</small></span>
                </div>
              </div>
            </div>
          </slide>
        </carousel>

        <div class="no-content d-flex justify-content-center align-items-center" v-else>
          <p class="mb-0">아직 멤버의 책 리뷰가 없습니다 ㄴ(°0°)ㄱ</p>
        </div>
      </div>
      
      <!-- <div>
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h4 class="text-left font-weight-bold mb-0">멤버의 책 리뷰</h4>
          <button class="btn btn-green" @click="toPostCreate(selectedClubEvent.book.id)">책 리뷰 작성하기</button>
        </div>
        <div class="d-flex scroll-sect" id="scroll-area-post" v-if="selectedClubEvent.memberPosts.length">
          <div 
            class="px-3 pb-3 pointer"
            v-for="post in selectedClubEvent.memberPosts"
            :key="post.id"
            @click="toPostDetail(post.id)"
            style="min-width: 345.59px; max-width: 345.59px;">
            <div class="card m-0">
              <div class="additional d-flex justify-content-center">
                <div class="user-card">
                  <div class="level center">
                    {{ post.nickName }}
                  </div>
                  <div class="points center">
                    <i class="fas fa-heart mr-1"></i> {{ post.likeUsers.length }}
                  </div>
                  <img :src="post.profileImg" v-if="post.profileImg">
                  <img src="http://bit.do/anonymouseuser" v-else>
                </div>
              </div>
              <div class="general d-flex flex-column justify-content-between">
                <div class="w-100 h-100 d-flex flex-column justify-content-around">
                  <div class="mb-2">
                    <span class="mb-3 star-container" v-for="index in post.rank" :key="index"><i class="fas fa-star" style="color:yellow"></i></span>
                  </div>
                  <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
                  <p class="card-text px-3" style="word-break:keep-all;">{{ post.onelineReview }}</p>
                  <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
                </div>
                <div class="more">
                  <span class="text-black-50"><small>{{ post.createdAt | moment('YYYY-MM-DD')}}</small></span>
                </div>
              </div>
            </div>
          </div>           
        </div>
        <div class="no-content d-flex justify-content-center align-items-center" v-else>
          <p class="mb-0">아직 멤버의 책 리뷰가 없습니다 ㄴ(°0°)ㄱ</p>
        </div>
      </div> -->

      <!-- <hr> -->

      <!-- clubEvent-detail-reviews -->
      <!-- <h4 class="text-left font-weight-bold mb-3">클럽 이벤트 기록</h4>
      <div class="no-content d-flex justify-content-center align-items-center" v-if="!selectedClubEvent.reviews">
        <p class="mb-0">아직 클럽 이벤트 기록이 없습니다 ㄴ(°0°)ㄱ</p>
      </div> -->
    </div>

    <div v-else class="mt-5">
      <img
        src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png"
        width="150px"
        class="mt-3">
      <h3 class="my-3">아쉽지만 북클럽 멤버만 접근 가능합니다.</h3>
      <button class="btn btn-green" @click="toSelectedClub(selectedClub.id)">북클럽으로 돌아가기</button>
    </div>
  </div>
</template>

<script>
import { Carousel, Slide } from 'vue-carousel'
import Swal from 'sweetalert2'
const swal = Swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success mr-2',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: false
})

import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ClubEventDetail',
  components: {
    Carousel,
    Slide
  },
  data() {
    return {
      params: {
        clubId: this.$route.params.clubId,
        clubEventId: this.$route.params.clubEventId
      },
      webexEmail: null,
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('clubStore', ['selectedClub', 'selectedClubEvent', 'webexUrl']),
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
    },
    // isCurrent() {
    //   if (Date() < new Date(this.selectedClubEvent.datetime)) {
    //     console.log(Date())
    //     console.log(new Date(this.selectedClubEvent.datetime))
    //     console.log(Date() < new Date(this.selectedClubEvent.datetime))
    //     // console.log()
    //     return true
    //   } else {
    //     return false
    //   }
    // }
  },
  methods: {
    ...mapActions('clubStore', ['findClub', 'findClubEvent', 'participateClubEvent', 'deleteClubEvent', 'checkPeople', 'createRoomUrl']),
    selectUser(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    },
    clickParticipateClubEvent(type) {
      if (type === 'apply') {
        swal.fire({
          text: "북클럽 이벤트에 참가하시겠습니까?",
          showCancelButton: true,
          confirmButtonText: '네',
          cancelButtonText: '아니요',
          icon: "warning",
        })
        .then((result) => {
          if (result.value) {
            this.participateClubEvent(this.params)
          } 
        });
      } else if (type === 'cancel') {
        swal.fire({
          text: "북클럽 이벤트 참가를 취소하시겠습니까?",
          showCancelButton: true,
          confirmButtonText: '네',
          cancelButtonText: '아니요',
          icon: "warning",
        })
        .then((result) => {
          if (result.value) {
            this.participateClubEvent(this.params)
          } 
        });
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
    },
    toClubEventUpdate(clubEventId) {
      router.push({ name: 'ClubEventUpdate', params: { clubEventId: clubEventId }})
    },
    clickClubEventDelete() {
      swal.fire({
          text: "북클럽 이벤트를 삭제하시겠습니까?",
          showCancelButton: true,
          confirmButtonText: '네',
          cancelButtonText: '아니요',
          icon: "warning",
        })
        .then((result) => {
          if (result.value) {
            this.deleteClubEvent(this.params)
          } 
        });
    },
    enterRoom() {
      console.log((this.isLeader || this.isParticipant))
      console.log(!this.selectedClubEvent.closed)
      console.log(this.selectedClubEvent.place === '온라인')
      console.log(this.selectedClubEvent.roomUrl)
      console.log((this.isLeader || this.isParticipant) && !this.selectedClubEvent.closed && (this.selectedClubEvent.place === '온라인') && this.selectedClubEvent.roomUrl)
    },
    toBookDetail() {
      // router.push({ name: 'BookDetail', params: { bookId: bookId}})
    },
    createLeader() {
      let webexData=  {
        emails: [
          this.webexEmail
        ],
        displayName: this.selectedClubEvent.leader.nickName,
        firstName: this.selectedClubEvent.leader.nickName,
        lastName: this.selectedClubEvent.leader.nickName,
        roles: [
          "Y2lzY29zcGFyazovL3VzL1JPTEUvaWRfcmVhZG9ubHlfYWRtaW4"
        ],
        selectedClubEvent: this.selectedClubEvent,
        clubId: this.selectedClub.id,
        clubEventId: this.selectedClubEvent.id,
        url: null,
      }
      this.checkPeople(webexData)
    },
  },
  created() {
    this.findClub(this.params.clubId)
    this.findClubEvent(this.params)
  },
  updated() {
    function stopWheel(e){
      if(!e){ e = window.event; } /* IE7, IE8, Chrome, Safari */
      if(e.preventDefault) { e.preventDefault(); } /* Chrome, Safari, Firefox */
      e.returnValue = false; /* IE7, IE8 */
    }

    if (this.selectedClubEvent.memberPosts.length >= 3) {
      const scrollAreaClub = document.querySelector('#scroll-area-post')
      scrollAreaClub.addEventListener('wheel', (e) => {
        scrollAreaClub.scrollLeft += e.deltaY;
        stopWheel()
      })
    }
  },
}
</script>

<style scoped>
  .book-image {
    padding: 0;
    display: block;
    width: 100%;
    height: 100%;
    box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
    border-radius: 6px;
    overflow: hidden;
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

  .profile-container .overlay {
    height: 150px;
    width: 150px;
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

  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .center {
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
}

.card {
  /* width: 300px; */
  height: 250px;
  background-color: #fff;
  background: linear-gradient(#f8f8f8, #fff);
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
  border-radius: 6px;
  overflow: hidden;
  position: relative;
  margin: 1.5rem;
}

.card:hover {
  /* width: 300px; */
  height: 250px;
  background-color: #fff;
  background: linear-gradient(#f8f8f8, #fff);
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.8);
  border-radius: 6px;
  overflow: hidden;
  position: relative;
  margin: 1.5rem;
}

.card h1 {
  text-align: center;
}

.card .additional {
  position: absolute;
  width: 33%;
  height: 100%;
  background: linear-gradient(#88A498, #88A487);
  transition: width 0.4s;
  overflow: hidden;
  z-index: 2;
}

.card.green .additional {
  background: linear-gradient(#92bCa6, #A2CCB6);
}


/* .card:hover .additional {
  width: 100%;
  border-radius: 0 5px 5px 0;
} */

.card .additional .user-card {
  width: 100px;
  height: 100%;
  position: relative;
  float: left;
}

.card .additional .user-card::after {
  content: "";
  display: block;
  position: absolute;
  top: 10%;
  right: -2px;
  height: 80%;
}

.card .additional .user-card .level,
.card .additional .user-card .points {
  top: 15%;
  color: #fff;
  text-transform: uppercase;
  font-size: 0.75em;
  font-weight: bold;
  background: rgba(0,0,0,0.15);
  padding: 0.125rem 0.75rem;
  border-radius: 100px;
  white-space: nowrap;
}

.card .additional .user-card .level {
  white-space: pre-wrap;
  width: 90%;
  word-break: break-all;
}

.card .additional .user-card .points {
  top: 85%;
}

.card .additional .user-card img {
  top: 35%;
  max-width: 50%;
  left: 23%;
  border-radius: 50%;
  position: absolute;
}
/* 
.card .additional .more-info {
  width: 300px;
  float: left;
  position: absolute;
  left: 150px;
  height: 100%;
}

.card .additional .more-info h1 {
  color: #fff;
  margin-bottom: 0;
}

.card.green .additional .more-info h1 {
  color: #224C36;
} */

.card .additional .coords {
  margin: 0 1rem;
  color: #fff;
  font-size: 1rem;
}

.card.green .additional .coords {
  color: #325C46;
}

.card .additional .coords span + span {
  float: right;
}

.card .additional .stats {
  font-size: 2rem;
  display: flex;
  position: absolute;
  bottom: 1rem;
  left: 1rem;
  right: 1rem;
  top: auto;
  color: #fff;
}

.card.green .additional .stats {
  color: #325C46;
}

.card .additional .stats > div {
  flex: 1;
  text-align: center;
}

.card .additional .stats i {
  display: block;
}

.card .additional .stats div.title {
  font-size: 0.75rem;
  font-weight: bold;
  text-transform: uppercase;
}

.card .additional .stats div.value {
  font-size: 1.5rem;
  font-weight: bold;
  line-height: 1.5rem;
}

.card .additional .stats div.value.infinity {
  font-size: 2.5rem;
}

.card .general {
  width: 66%;
  height: 100%;
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1;
  box-sizing: border-box;
  padding: 1rem;
}

.star-container {
  text-shadow: 1px 1px 2px rgb(0, 0, 0, 0.7);
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

  input {
    border-bottom: 1px solid rgba(0, 0, 0, 0.5);
    width: 100%;
    text-align: center;
  }

  input:focus {
    outline: none;
  }
  
  a {
    color: white;
    text-decoration: none;
  }
</style>