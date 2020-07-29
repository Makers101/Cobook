<template>
  <div class="custom-container my-3">
    <!-- club-header -->
    <div class="row">
      <img class="club-image col-4" :src="selectedClub.clubImg" alt="" v-if="selectedClub.clubImg">
      <img class="club-image col-4" src="http://placehold.jp/300x200.png?text=sample" alt="" v-else>
      <div class="col-8 py-2 d-flex flex-column justify-content-between">
        <div>
          <div class="d-flex justify-content-between">
            <h3 class="mb-0 font-weight-bold">{{ selectedClub.name }}</h3>
            <div class="d-flex align-items-center">
              <p class="mb-0">{{ selectedClub.followerCnt }} FOLLOW</p>
              <button class="btn btn-green ml-2">팔로우</button>
            </div>
          </div>
          <p class="text-left">{{ selectedClub.onelineDescription }}</p>
        </div>
        
        <div>
          <p class="color-black text-left">주로 <span class="color-black font-weight-bold">{{ selectedClub.residence }}</span>에서 만남 :)</p>
          <div class="d-flex justify-content-between">
            <div>
              <button class="btn btn-green mr-2" v-for="genre in selectedClub.genres" :key="genre.id">#{{ genre.name }}</button>
            </div>
            <div>
              <button
                class="btn btn-secondary dropdown-toggle mr-2"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                클럽 설정
              </button>
              <div class="dropdown-menu">
                <router-link class="dropdown-item text-center" :to="{ name: 'ReadingCreate' }">리딩 생성</router-link>
                <a class="dropdown-item text-center">멤버 모집</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item text-center">정보 수정</a>
              </div>
              <button class="btn btn-warning mr">가입 신청</button>
            </div>
          </div>
        </div>

      </div>
    </div>

    <hr>

    <!-- club-members -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">클럽 멤버({{ selectedClub.memberCnt }})</h4>
      <div class="d-flex justify-content-start">
        <div class="profile-container pointer" v-for="member in selectedClub.members" :key="member.id" @click="selectUser(member.id)">
          <img class="rounded-circle image" :src="member.proflieImg" alt="" v-if="member.profileImg">
          <img class="rounded-circle image" src="http://placehold.jp/150x150.png?text=profile" alt="" v-else>
          <div class="overlay rounded-circle">
            <div class="text">{{ member.nickName }}</div>
          </div>
        </div>
      </div>
    </div>

    <hr>

    <!-- club-description -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">클럽 설명</h4>
      <p class="text-left px-2">{{ selectedClub.description }}</p>
    </div>

    <hr>

    <!-- club-readings -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">{{ selectedClub.name }}에서 진행한 리딩</h4>
      <div class="row rows-cols-1 row-cols-sm-3">
        <div class="mb-2 col-12 col-sm-4 pointer" v-for="reading in selectedClub.readings" :key="reading.id" @click="selectReading(reading.id)">
          <div class="card h-100">
            <div class="row no-gutters">
              <div class="col-6 reading-left">
                <img class="bg-image" :src="reading.book.bookImg" width="100%">
                <span class="badge mb-0 reading-closed-true" v-if="reading.recruit">종료</span>
                <span class="badge mb-0 reading-closed-false" v-else>예정</span>
              </div>
              <div class="col-6 text-left d-flex flex-column p-2">
                <p class="color-light-black book-title" lt="book">{{ reading.book.title }}</p>
                <small>{{ reading.name }}</small>
                <div class="mt-auto">
                  <div class="d-flex justify-content-between">
                    <span><small><i class="fas fa-users"></i> {{ reading.participantCnt}}</small></span>
                    <span><small><i class="fas fa-map-marker-alt"></i> {{ reading.place }}</small></span>
                  </div>
                  <span><small>{{ reading.datetime.slice(0, 10) }}</small></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="no-content d-flex justify-content-center align-items-center" v-if="!selectedClub.readings">
        <p class="mb-0">아직 {{ selectedClub.name }}의 리딩이 없습니다 ㄴ(°0°)ㄱ</p>
      </div>
    </div>  
    <hr>

    <!-- club-posts -->
    <div>
      <h4 class="text-left font-weight-bold mb-3">{{ selectedClub.name }}의 게시물</h4>
      <div class="no-content d-flex justify-content-center align-items-center" v-if="!selectedClub.posts">
        <p class="mb-0">아직 {{ selectedClub.name }}의 게시물이 없습니다 ㄴ(°0°)ㄱ</p>
      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ClubDetail',
  data() {
    return {
    }
  },
  computed: {
    ...mapState('clubStore', ['selectedClub'])
  },
  methods: {
    ...mapActions('clubStore', ['findClub']),
    selectReading(readingId) {
      router.push({ name: 'ReadingDetail', params: { clubId: this.$route.params.clubId, readingId: readingId }})
    },
    selectUser(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    }
  },
  created() {
    this.findClub(this.$route.params.clubId)
  }
}
</script>

<style scoped>
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

  .reading-left {
    position: relative;
  }

  .reading-closed-false {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 9%;
    left: 18%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .reading-closed-true {
    background-color: #707070; 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 9%;
    left: 18%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .no-content {
    height: 8rem;
  }

  .club-image {
    border-radius: 25px;
    padding: 8px;
  }

  .book-title {
    word-break: keep-all;
  }
</style>