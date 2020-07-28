<template>
  <div class="custom-container">
    <!-- clubs-banner -->
    <div class="club-banner">
      <img
        class="club-banner-img"
        src="@/assets/club_banner.jpg" 
        alt="">
      <div class="club-banner-text">
        <h3 class="font-weight-bold">클럽</h3>
        <p class="mb-0">
          클럽은 정기적으로 함께 책을 읽는 사람들의 모임입니다.
          <br>
          취향이 맞는 클럽에 참여하여 함께 읽는 즐거움을 경험해보세요.
        </p>
      </div>
    </div>

    <!-- clubs-menubar -->
    <div class="club-menubar my-3 d-flex justify-content-between">
      <div class="club-toggle">
        <!-- <button class="btn btn-toggle-false mx-1" @click="selectFilter('popular')" v-show="popular_filter">#인기</button>
        <button class="btn btn-toggle-true mx-1" @click="selectFilter('popular')" v-show="!popular_filter">#인기</button> -->

        <button class="btn btn-toggle-false mx-1" @click="selectFilter('open')" v-show="open_filter">#모집중</button>
        <button class="btn btn-toggle-true mx-1" @click="selectFilter('open')" v-show="!open_filter">#모집중</button>

        <span             
          v-for="genre in userGenres"
          :key="`userGenres_${genre.id}`"
        >
          <button 
            class="btn btn btn-toggle-false mx-1"
            @click="selectFilter(genre.name)"
            v-show="genre_filter.has(genre.name)"
          >
            #{{ genre.name }}
          </button>
          <button 
            class="btn btn btn-toggle-true mx-1"
            @click="selectFilter(genre.name)"
            v-show="!genre_filter.has(genre.name)"
          >
            #{{ genre.name }}
          </button>
        </span>
      </div>
      <button class="btn btn-green mx-1" @click="toClubCreate">클럽 생성</button>
    </div>
    <hr class="my-0">

    <!-- clubs-list -->
    <div class="club-list my-2 row">
      <div 
        class="col-sm-4 col-12 p-3"
        v-for="club in clubs"
        :key="`club_${club.id}`">
        <div class="card">
          <div class="card-head">
            <img
              class="card-img-top to-detail"
              :src="club.clubImg"
              :alt="club.name"
              @click="selectClub(club.id)"
              v-if="club.clubImg"
            >
            <img
              class="card-img-top to-detail"
              src="http://placehold.jp/300x200.png?text=sample"
              :alt="club.name"
              @click="selectClub(club.id)"
              v-else
            >
            <span class="badge mb-0 club-open" v-if="club.recruit">모집중</span>
          </div>        
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
              <h4 class="card-title font-weight-bold mb-0 club-name to-detail" @click="selectClub(club.id)">{{ club.name }}</h4>
              <small class="color-green font-weight-bold club-followers">{{ club.followerCnt }} FOLLOW</small>
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
              <p class="card-text mb-0">
                <small class="color-black">주로 <span class="color-black font-weight-bold">{{ club.residence }}</span>에서 만남 :)</small>
              </p>
              <div class="d-flex">
                <p class="card-text mb-0 mr-2">
                  <small class="color-black"><i class="fas fa-users"></i> {{ club.memberCnt }}</small>
                </p>
              </div>
            </div>
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
  name: 'ClubList',
  data() {
    return {
      popular_filter: false,
      open_filter: false,
      genre_filter: new Set(),
      
    }
  },
  computed: {
    ...mapState('clubStore', ['clubs', 'userGenres']),
  },
  methods: {
    ...mapActions('clubStore', ['fetchClubs', 'findClub']),
    selectClub(clubId) {
      router.push({ name: 'ClubDetail', params: { clubId: clubId }})
    },
    toClubCreate() {
      router.push({ name: 'ClubCreate' })
    },
    // filterClubs() {
    //   let new_clubs = []
            
    //   if (this.open_filter) {
    //     this.clubs.forEach(club => {
    //       if (club.open) {
    //         new_clubs.push(club)
    //       }
    //     });
    //   } else {
    //     new_clubs = this.clubs
    //   }

    //   if (this.genre_filter.size !== 0) {
    //     let new_clubs2 = new Set()
    //     new_clubs.forEach(club => {
    //       let temp = 0
    //       club.genres.forEach(genre => {
    //         if (this.genre_filter.has(genre.name)) {
    //           temp = temp + 1
    //         }
    //       })
    //       if (club.genres.length === temp) {
    //         new_clubs2.add(club)
    //       }
    //     })
    //     this.clubs = new_clubs2
    //   } else {
    //     this.clubs = new_clubs
    //   }
    // },
    selectFilter(filter) {
      if (filter === 'popular') {
        this.popular_filter = !this.popular_filter
      } else if (filter === 'open') {
        this.open_filter = !this.open_filter
      } else {
        if (!this.genre_filter.has(filter)) {
          this.genre_filter.add(filter)
        } else {
          this.genre_filter.delete(filter)
        }
      }
      this.$forceUpdate();
      // this.filterClubs()
    }
  },
  created() {
    this.fetchClubs()
  }
}
</script>

<style scoped>
  .club-banner {
    position: relative;
  }

  .club-banner-img {
    width: 100%;
    height: 200px;
    vertical-align: middle;
    filter: brightness(0.7)
  }

  .club-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }

  .btn-toggle-true {
    border-color: #88A498;
    color: #88A498;
  }

  .btn-toggle-true:hover {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-toggle-false {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-toggle-false:hover {
    background-color: white;
    border-color: #88A498;
    color: #88A498;
  }

  .btn-club-create {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-club-create:hover {
    background-color: #3c755a;
    color: #F8F8F8;
  }

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

  .club-followers {
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

  .card-head {
    position: relative;
  }

  .club-open {
    /* background-color: #b68145; */
    /* background-color: #907a62; */
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

  .card-img-top {
    height: 200px;
  }
</style>