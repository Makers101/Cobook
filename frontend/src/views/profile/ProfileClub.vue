<template>
  <div class="mt-3">
    <h5 class="text-left font-weight-bold" v-if="clubs.length">Club</h5>
    <!-- Club cards -->
    <div class="club-list my-2 row" v-if="clubs.length">
      <div 
        class="col-sm-4 col-12 p-3"
        v-for="club in clubs"
        :key="`club_${club.id}`">
        <div class="card">
          <div class="card-head club-image-container">
            <img
              class="card-img-top to-detail club-image"
              :src="club.clubImg"
              :alt="club.name"
              @click="selectClub(club.id)"
            >
            <span class="badge mb-0 club-open" v-if="club.recruit">모집중</span>
          </div>        
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
              <h4 class="card-title font-weight-bold mb-0 club-name to-detail" @click="selectClub(club.id)">{{ club.name }}</h4>
              <small class="color-green font-weight-bold club-followers">{{ club.followerCnt }} FOLLOW</small>
            </div>
            <p class="card-text text-left club-oneline">{{ club.onelineDescription }}</p>
            <div class="d-flex justify-content-start my-3 club-genre">
              <span
                class="badge badge-genre mr-2"
                v-for="genre in club.genres.slice(0,3)"
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
                  <small class="color-black"><i class="fas fa-users"></i> {{ club.participant_num }}</small>
                </p>
                <!-- <p class="card-text mb-0" v-if="club.open">
                  <small class="text-danger font-weight-bold">모집중</small>
                </p> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <img src="@/assets/ready.png" width="150px" class="mt-3">
      <h3 class="mt-3">현재 <strong>{{ this.profile.nickName }}</strong>님이 가입한 클럽이 없습니다. </h3>
    </div>
    <!-- Reading & Meetups -->
    <h5 class="text-left font-weight-bold" v-if="clubs.length">Reading & Meetup</h5>
    <div class="row rows-cols-1 row-cols-sm-3">
      <div class="col mb-4 col-12 col-sm-4" v-for="reading in readings" :key="reading.id">
        <div class="card h-100">
          <div class="row no-gutters">
            <div class="col-6" @click="selectReading(reading.clubId, reading.id)">
              <img class="bg-image" :src="`${ reading.book.bookImg }`" width="100%">
            </div>
            <div class="col-6 text-left d-flex flex-column p-2">
              <p class="color-light-black book-title reading-booktitle" alt="book">{{ reading.book.title }}</p>
              <small class="reading-name" @click="selectReading(reading.clubId, reading.id)">{{ reading.name }}</small>
              <div class="mt-auto">
                <div class="d-flex justify-content-between">
                  <span><small><i class="fas fa-users"></i> {{ reading.participantCnt}}</small></span>
                  <span><small><i class="fas fa-map-marker-alt"></i> {{ reading.place }}</small></span>
                </div>
                <span class="reading-date"><small>{{ reading.datetime | moment('YYYY-MM-DD HH:mm') }}</small></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import router from '@/router'

export default {
  name: 'ProfileClub',
  data() {
    return {
      popular_filter: false,
      open_filter: false,
      genre_filter: new Set(),
    }
  },
  computed: {
    ...mapState('profileStore',['clubs', 'readings', 'profile']),
    ...mapState(['myaccount'])
  },
  methods: {
     ...mapActions('profileStore', ['fetchClubs', 'fetchReadings']),
    selectClub(club_id) {
      router.push({ name: 'ClubDetail', params: { clubId: club_id }})
    },
    selectReading(club_id, reading_id) {
      router.push({ name: 'ReadingDetail', params: {clubId: club_id, readingId: reading_id }})
    },
    // filterClubs() {
    //   let new_clubs = []
            
    //   if (this.open_filter) {
    //     sample_clubs.forEach(club => {
    //       if (club.open) {
    //         new_clubs.push(club)
    //       }
    //     });
    //   } else {
    //     new_clubs = sample_clubs
    //   }

    //   console.log(new_clubs)

    //   if (this.genre_filter.size !== 0) {
    //     console.log('여기1')
    //     let new_clubs2 = new Set()
    //     new_clubs.forEach(club => {
    //       let temp = 0
    //       club.genres.forEach(genre => {
    //         if (this.genre_filter.has(genre.name)) {
    //           temp = temp + 1
    //         }
    //       })
    //       console.log(club.genres.length)
    //       console.log(temp)
    //       if (club.genres.length === temp) {
    //         new_clubs2.add(club)
    //       }
    //     })
    //     this.clubs = new_clubs2
    //   } else {
    //     console.log('여기2')
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
      this.filterClubs()
    }
  },
  created() {
    this.fetchClubs(this.$route.params.userId)
    this.fetchReadings(this.$route.params.userId)
  },
  beforeRouteUpdate (to, from, next) {
    this.fetchClubs(to.params.userId)
    this.fetchReadings(to.params.userId)
    next();
  },
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

  .club-followers {
    white-space: nowrap;
  }

  .club-oneline, .reading-name {
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

  .reading-date {
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1; 
    -webkit-box-orient: vertical;
    text-align: start;
  }

  .reading-booktitle {
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3; 
    -webkit-box-orient: vertical;
    text-align: start;
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

  .club-genre {
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1; 
    -webkit-box-orient: vertical;
    text-align: start;
  }
</style>