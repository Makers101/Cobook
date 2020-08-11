<template>
  <div class="custom-container my-3">
    <!-- club-header -->
    <div class="row">
      <!-- club-candidate-clubImg -->
      <img
        class="club-image col-4"
        :src="selectedClub.clubImg"
        :alt="selectedClub.name"
        v-if="selectedClub.clubImg">
      <img
        class="club-image col-4"
        :src="'http://placehold.jp/300x200.png?text=' + selectedClub.name"
        :alt="selectedClub.name"
        v-else>
      <div class="col-8 py-2 d-flex flex-column justify-content-between">
        <div>
          <div class="d-flex justify-content-between mb-2">
            <div class="d-flex justify-content-start align-items-center">
              <!-- club-candidate-name -->
              <h3 class="mb-0 font-weight-bold">{{ selectedClub.name }}</h3>
              <!-- club-candidate-recruit -->
              <span class="badge mb-0 ml-2 club-recruit" v-if="selectedClub.recruit">모집중</span>
            </div>
            <!-- <div class="d-flex align-items-center">
              <p class="mb-0">{{ selectedClub.followerCnt }} FOLLOW</p>
              <button class="btn btn-green ml-2">팔로우</button>
            </div> -->
          </div>
          <!-- club-candidate-onelineDescription -->
          <p class="text-left">{{ selectedClub.onelineDescription }}</p>
        </div>
        
        <div>
          <!-- club-candidate-residence -->
          <p class="color-black text-left">주로 <span class="color-black font-weight-bold">{{ selectedClub.residence }}</span>에서 만남 :)</p>
          <div class="d-flex justify-content-between">
            <!-- club-candidate-genres -->
            <div>
              <button class="btn btn-genre mr-2" disabled v-for="genre in selectedClub.genres" :key="genre.id">#{{ genre.name }}</button>
            </div>
            <!-- club-candidate-buttons -->
            <div>
              <button
                class="btn btn-secondary dropdown-toggle"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
                v-if="isLeader"
              >
                클럽 설정
              </button>
              <div class="dropdown-menu py-0">
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="toClubEventCreate">
                  클럽 이벤트 생성
                </button>
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="clickUpdateRecruit(selectedClub.id)"
                  v-if="!selectedClub.recruit">
                  모집 활성화
                </button>
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="clickUpdateRecruit(selectedClub.id)"
                  v-if="selectedClub.recruit">
                  모집 비활성화
                </button>
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="toClubDetail(selectedClub.id)">
                  클럽 페이지
                </button>
                <div class="dropdown-divider my-1"></div>
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="toClubUpdate(selectedClub.id)">
                  클럽 정보 수정
                </button>
                <button 
                  class="dropdown-item setting-btn text-center"
                  type="button"
                  @click="clickClubDelete(selectedClub.id)">
                  클럽 삭제
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <hr>

    <div>
      <h4 class="text-left font-weight-bold mb-3">클럽 가입 신청</h4>
      <div class="list-group">
        <div class="list-group-item d-flex justify-content-between align-items-center" v-for="candidate in candidates" :key="candidate.userId">
          <div class="d-flex justify-content-start align-items-center pointer" @click="toProfile(candidate.userId)">
            <img class="rounded-circle profile-image" :src="candidate.profileImg" alt="" v-if="candidate.profileImg">
            <img class="rounded-circle profile-image" src="http://placehold.jp/150x150.png?text=profile" alt="" v-else>
            <h5 class="mb-0 ml-2 font-weight-bold">{{ candidate.nickName }}</h5>
          </div>
          <div class="d-flex justify-content-end align-items-center">
            <button class="btn btn-green mr-2" @click="clickDecideClubApply(candidate.clubMemberId, 'approve')">승인</button>
            <button class="btn btn-danger" @click="clickDecideClubApply(candidate.clubMemberId, 'reject')">거절</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ClubCandidates',
  data() {
    return {
      applyDecisionData: {
        clubId: this.$route.params.clubId,
        clubMemberId: null,
        decision: null
      }
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('clubStore', ['selectedClub', 'candidates']),
    isLeader() {
      if (this.myaccount.id === this.selectedClub.leader.id) {
        return true
      } else {
        return false
      }
    },
    isMember() {
      let result = false
      this.selectedClub.members.forEach(member => {
        if (this.myaccount.id === member.id) {
          result = true
        }
      });
      return result
    },
    isCandidate() {
      if (this.selectedClub.candidates.includes(this.myaccount.id)) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    ...mapActions('clubStore', ['findClub', 'fetchCandidates', 'decideClubApply', 'updateRecruit', 'deleteClub']),
    clickDecideClubApply(clubMemberId, decision) {
      this.applyDecisionData.clubMemberId = clubMemberId
      this.applyDecisionData.decision = decision
      this.decideClubApply(this.applyDecisionData)
    },
    clickUpdateRecruit(clubId) {
      this.updateRecruit(clubId)
      this.selectedClub.recruit = !this.selectedClub.recruit
    },
    clickClubDelete(clubId) {
      if (confirm('클럽을 삭제하시겠습니까?') === true) {
        this.deleteClub(clubId)
      } else {
        return false
      }
    },
    toProfile(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    },
    toClub(clubId) {
      router.push({ name: 'ClubDetail', params: {clubId: clubId}})
    },
    toClubEventCreate() {
      router.push({ name: 'ClubEventCreate' })
    },
    toClubUpdate(clubId) {
      router.push({ name: 'ClubUpdate', params: { clubId: clubId }})
    },
    toClubDetail(clubId) {
      router.push({ name: 'ClubDetail', params: { clubId: clubId }})
    }
  },
  created() {
    this.findClub(this.$route.params.clubId)
    this.fetchCandidates(this.$route.params.clubId)
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

  .clubEvent-left {
    position: relative;
  }

  .clubEvent-closed-false {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 9%;
    left: 18%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .clubEvent-closed-true {
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
    height: 200px;
  }

  .clubEvent-name {
    word-break: keep-all;
  }

  .club-recruit {
    /* background-color: #b68145; */
    /* background-color: #907a62; */
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    padding: 6px;
  }

  .description {
    white-space: pre-line;
  }

  .btn-genre {
    background-color: #88A498;
    color: #F8F8F8;
    opacity: 1;
  }

  .setting-btn:focus {
    background-color: #707070 !important;
    outline: none;
  }

  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .profile-image {
    width: 50px;
    height: 50px;
  }

</style>