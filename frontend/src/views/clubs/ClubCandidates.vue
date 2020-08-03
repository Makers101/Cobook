<template>
  <div class="custom-container my-3">
    <!-- club-header -->
    <div class="row">
      <img class="club-image col-4" :src="selectedClub.clubImg" alt="" v-if="selectedClub.clubImg">
      <img class="club-image col-4" src="http://placehold.jp/300x200.png?text=sample" alt="" v-else>
      <div class="col-8 py-2 d-flex flex-column justify-content-between">
        <div>
          <div class="d-flex justify-content-between">
            <div class="d-flex justify-content-start align-items-center">
              <h3 class="mb-0 font-weight-bold pointer" @click="toClub(selectedClub.clubId)">{{ selectedClub.name }}</h3>
              <!-- <span class="badge mb-0 ml-2 club-recruit" v-if="selectedClub.recruit">모집중</span> -->
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
    ...mapState('clubStore', ['selectedClub', 'candidates']),
  },
  methods: {
    ...mapActions('clubStore', ['findClub', 'fetchCandidates', 'decideClubApply']),
    clickDecideClubApply(clubMemberId, decision) {
      this.applyDecisionData.clubMemberId = clubMemberId
      this.applyDecisionData.decision = decision
      this.decideClubApply(this.applyDecisionData)
    },
    toProfile(userId) {
      router.push({ name: 'Profile', params: { userId: userId }})
    },
    toClub(clubId) {
      router.push({ name: 'ClubDetail', params: {clubId: clubId}})
    }
  },
  created() {
    this.findClub(this.$route.params.clubId)
    this.fetchCandidates(this.$route.params.clubId)
  }
}
</script>

<style scoped>
  .club-image {
    border-radius: 25px;
    padding: 8px;
    max-height: 300px;
  }
  
  .profile-image {
    max-height: 50px;
  }

</style>