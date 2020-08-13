<template>
  <div class="mt-5">
    <div>
      <div style="max-width:70%; margin:auto;">
        <div class="row rows-cols-1 row-cols-md-3" id="scroll-area-club" v-if="clubs.length">
          <div 
            class="col-12 col-sm-6 col-lg-4 mb-4 pointer"
            v-for="club in clubs"
            :key="`club_${club.id}`"
            >
            <div class="card">
              <div class="card-head club-image-container">
                <img
                  class="card-img-top club-image to-detail"
                  :src="club.clubImg"
                  :alt="club.name"
                  @click="selectClub(club.id)"
                  v-if="club.clubImg"
                >
                <img
                  class="card-img-top to-detail"
                  :src="'http://placehold.jp/300x150.png?text=' + club.name"
                  :alt="club.name"
                  @click="selectClub(club.id)"
                  v-else
                >
                <span class="badge mb-0 club-recruit" v-if="club.recruit">모집중</span>
              </div>        
              <div class="card-body">
                <div class="d-flex justify-content-between align-items-center mb-3">
                  <h4 class="card-title font-weight-bold mb-0 club-name to-detail" @click="selectClub(club.id)">{{ club.name }}</h4>
                </div>
                <p class="card-text text-left club-oneline">{{ club.onelineDescription }}</p>
                <div class="d-flex justify-content-start my-3">
                  <span
                    class="badge badge-genre mr-2"
                    v-for="genre in club.genres"
                    :key="`club_genre_${genre.id}`">
                    #{{ genre.name }}
                  </span>
                </div>
                <div class="d-flex justify-content-between">
                  <small class="color-black club-residence">주로 <span class="color-black font-weight-bold">{{ club.residence }}</span>에서 만남 :)</small>
                  <small class="color-black club-memberCnt"><i class="fas fa-users"></i> {{ club.memberCnt }}</small>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="no-result" v-else>
          <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
          <h3 class="mt-3">검색된 키워드의 클럽이 없습니다. </h3>
        </div>
      </div>
    </div>
    
    
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'SearchClub',
  computed: {
     ...mapState('searchStore', ['clubs']),
  },
  methods: {
    ...mapActions('searchStore', ['fetchClubs']),
  },
  created() {
    this.fetchClubs(this.$route.params.content)
  }
}
</script>

<style scoped>
.badge-genre {
  background-color: #88A498;
  color: #F8F8F8;
  padding: 6px;
}

.club-name {
  overflow: hidden;
  white-space: normal;
  word-wrap: break-word;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1; 
  -webkit-box-orient: vertical;
  text-align: start;
}

.club-memberCnt {
  white-space: nowrap;
}

.club-oneline {
  overflow: hidden;
  white-space: normal;
  word-wrap: break-word;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2; 
  -webkit-box-orient: vertical;
  text-align: start;
  height: 3rem;
}

.club-residence {
  overflow: hidden;
  white-space: normal;
  word-wrap: break-word;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1; 
  -webkit-box-orient: vertical;
  text-align: start;
}

.card {
  background-color: #fff;
  background: linear-gradient(#f8f8f8, #fff);
  box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
  border-radius: 6px;
  overflow: hidden;
}

.card-head {
  position: relative;
}

.club-recruit {
  background-color: rgba(221, 118, 0, 0.8); 
  color: #F8F8F8;
  text-align: center;
  position: absolute;
  top: 11%;
  left: 89%;
  transform: translate( -50%, -50% );
  padding: 6px;
}

.to-detail:hover {
  cursor: pointer;
  color: #88A498
}

.club-image-container {
  max-width: 100%;
  height: 150px;
  overflow: hidden;
}

.club-image {
  width: 100%;
  height: auto;
}

.event-left {
  position: relative;
}

.badges {
  position: absolute;
  top: 3%;
  left: 6%;
}

.no-result {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -150px 0px 0px -200px;
}

</style>