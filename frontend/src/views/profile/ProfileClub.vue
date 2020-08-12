<template>
  <div class="mt-3 mb-5">
    
    <!-- profile-clubs -->
    <h5 class="text-left font-weight-bold mb-0 ml-3" v-if="clubs.length">{{ profile.nickName }}님의 Club</h5>
    <div class="d-flex my-2 club-list scroll-sect" id="scroll-area-club" v-if="clubs.length">
      <div 
        class="px-3"
        v-for="club in clubs"
        :key="`club_${club.id}`"
        style="min-width: 345.59px; max-width: 345.59px">
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
    <div v-else>
      <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
      <h3 class="mt-3">현재 <strong>{{ this.profile.nickName }}</strong>님이 가입한 클럽이 없습니다. </h3>
    </div>

    <!-- profile-events -->

    <h5 class="text-left font-weight-bold mb-0 ml-3 mt-5" v-if="integratedEvents.length">{{ profile.nickName }}님의 이벤트</h5>
    <div class="d-flex events-list my-2 scroll-sect" id="scroll-area-event" v-if="integratedEvents.length">
      <div 
        class="px-3 pointer"
        v-for="event in integratedEvents"
        :key="`event_${event.id}`"
        @click="selectEvent(event.id, event.clubId)"
        style="min-width: 345.59px; max-width: 345.59px;">
        <div class="card h-100">
          <div class="row no-gutters">
            <div class="col-6 event-left">
              <img class="bg-image" :src="event.book.bookImg" width="100%">
              <span class="badge mb-0 event-closed-true" v-if="event.closed">종료</span>
              <span class="badge mb-0 event-closed-false" v-else>예정</span>
            </div>
            <div class="col-6 text-left d-flex flex-column align-items-start p-2">
              <p class="event-name font-weight-bold" lt="book">{{ event.name }}</p>
              <span class="badge badge-genre">{{ event.book.genre }}</span>
              <div class="mt-auto">
                <p class="mb-0" v-if="event.capacity"><small><i class="fas fa-users"></i> {{ event.participantCnt}} / {{ event.capacity + 1 }}</small></p>
                <p class="mb-0" v-else><small><i class="fas fa-users"></i> {{ event.participantCnt}}</small></p>
                <p class="mb-0"><small><i class="fas fa-map-marker-alt"></i> {{ event.place }}</small></p>
                <p class="event-date mb-0"><small>{{ event.datetime | moment('YYYY-MM-DD HH:mm') }}</small></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
      <h3 class="mt-3">현재 <strong>{{ this.profile.nickName }}</strong>님이 참여한 이벤트가 없습니다. </h3>
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
    ...mapState('profileStore',['clubs', 'clubEvents', 'onedayEvents', 'profile']),
    ...mapState(['myaccount']),
    integratedEvents() {
      const events = this.clubEvents.concat(this.onedayEvents)
      events.sort(function(a, b) {
        let datetimeA = a.datetime
        let datetimeB = b.datetime
        if (datetimeA < datetimeB) {
          return 1
        }
        if (datetimeA > datetimeB) {
          return -1
        }
        return 0
      })
      return events
      }
  },
  methods: {
     ...mapActions('profileStore', ['fetchClubs']),
    selectClub(club_id) {
      router.push({ name: 'ClubDetail', params: { clubId: club_id }})
    },
    selectEvent(eventId, clubId) {
      if (clubId) {
        router.push({ name: 'ClubEventDetail', params: { clubId: clubId, clubEventId: eventId }})
      } else {
        router.push({ name: 'OnedayEventDetail', params: { onedayEventId: eventId }})
      }
    },
  },
  created() {
    this.fetchClubs(this.$route.params.userId)
  },
  updated() {
    function stopWheel(e){
      if(!e){ e = window.event; } /* IE7, IE8, Chrome, Safari */
      if(e.preventDefault) { e.preventDefault(); } /* Chrome, Safari, Firefox */
      e.returnValue = false; /* IE7, IE8 */
    }

    if (this.clubs.length >= 3) {
      const scrollAreaClub = document.querySelector('#scroll-area-club')
      scrollAreaClub.addEventListener('wheel', (e) => {
        scrollAreaClub.scrollLeft += e.deltaY;
        stopWheel()
      })
    }

    if (this.integratedEvents.length >= 3) {
      const scrollAreaEvent = document.querySelector('#scroll-area-event')
      scrollAreaEvent.addEventListener('wheel', (e) => {
        scrollAreaEvent.scrollLeft += e.deltaY;
        stopWheel()
      })
    }
  },
  beforeRouteUpdate (to, from, next) {
    this.fetchClubs(to.params.userId)
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

  .event-closed-true {
    background-color: #707070; 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 9%;
    left: 18%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .event-closed-false {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 9%;
    left: 18%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .event-name {
    word-break: keep-all;
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