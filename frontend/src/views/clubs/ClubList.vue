<template>
  <div class="custom-container">

    <!-- clubs-banner -->
    <div class="club-banner">
      <img
        class="club-banner-img"
        src="https://user-images.githubusercontent.com/57381062/88907929-3090a500-d294-11ea-94c0-601ed914d2e4.jpg" 
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
        <button
          class="btn btn-toggle-false mx-1"
          @click="selectFilter('recruit')"
          v-show="filters.recruit_filter"
        >
          #모집중
        </button>
        <button
          class="btn btn-toggle-true mx-1"
          @click="selectFilter('recruit')"
          v-show="!filters.recruit_filter"
        >
          #모집중
        </button>

        <span             
          v-for="genre in myaccount.likeGenres"
          :key="`userGenres_${genre.id}`"
        >
          <button 
            class="btn btn btn-toggle-false mx-1"
            @click="selectFilter(genre.id)"
            v-show="filters.genre_filter.has(genre.id)"
          >
            #{{ genre.name }}
          </button>
          <button 
            class="btn btn btn-toggle-true mx-1"
            @click="selectFilter(genre.id)"
            v-show="!filters.genre_filter.has(genre.id)"
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
        class="col-lg-4 col-md-6 col-sm-12 p-3"
        v-for="club in filteredClubs"
        :key="`club_${club.id}`">
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
              <!-- <small class="color-green font-weight-bold club-followers">{{ club.followerCnt }} FOLLOW</small> -->
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
  </div>
</template>

<script>
import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'ClubList',
  data() {
    return {
      filters: {
        recruit_filter: false,
        genre_filter: new Set()
      }
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('clubStore', ['clubs', 'filteredClubs']),
  },
  methods: {
    ...mapActions('clubStore', ['fetchClubs', 'filterClubs']),
    selectClub(clubId) {
      router.push({ name: 'ClubDetail', params: { clubId: clubId }})
    },
    toClubCreate() {
      router.push({ name: 'ClubCreate' })
    },
    selectFilter(filter) {
      if (filter === 'recruit') {
        this.filters.recruit_filter = !this.filters.recruit_filter
      } else {
        if (!this.filters.genre_filter.has(filter)) {
          this.filters.genre_filter.add(filter)
        } else {
          this.filters.genre_filter.delete(filter)
        }
      }
      this.$forceUpdate();
      this.filterClubs(this.filters);
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

  .btn-toggle-false {
    background-color: #88A498;
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

  .card-head {
    position: relative;
  }

  .card {
    background-color: #fff;
    background: linear-gradient(#f8f8f8, #fff);
    box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
    border-radius: 6px;
    overflow: hidden;
  }

  .card:hover {
    background-color: #fff;
    background: linear-gradient(#f8f8f8, #fff);
    box-shadow: 0 8px 16px -8px rgba(0,0,0,0.8);
    border-radius: 6px;
    overflow: hidden;
  }

  .club-recruit {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 5%;
    right: 3%;
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
  
</style>