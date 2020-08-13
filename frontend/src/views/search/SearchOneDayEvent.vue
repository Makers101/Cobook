<template>
  <div class="mt-5">
    <div>
      <div style="max-width:70%; margin:auto;">
        <div class="row rows-cols-1 row-cols-md-3" v-if="onedayevents.length">
          <div 
            class="col-12 col-sm-6 col-lg-4 mb-4 pointer"
            v-for="event in onedayevents"
            :key="`event_${event.id}`"
            @click="selectEvent(event.id)"
            style>
            <div class="card h-100">
              <div class="row no-gutters">
                <div class="col-6 event-left d-flex justify-content-center">
                  <img class="bg-image" :src="event.book.bookImg" width="100%">
                  <div class="badges">
                    <span class="badge mb-0 clubEvent-badge" v-if="event.capacity">원데이</span>
                    <span class="badge mb-0 onedayEvent-badge" v-else>클럽</span>
                    <span class="badge ml-1 mb-0 event-closed-true" v-if="event.closed">종료</span>
                    <span class="badge ml-1 mb-0 event-closed-false" v-else>예정</span>
                  </div>
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
        <div class="no-result" v-else>
          <div>
            <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
            <h3 class="mt-3">검색된 키워드의 이벤트가 없습니다. </h3>
          </div>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'SearchOneDayEvent',
  data() {
    return {
      popular_filter: false,
      open_filter: false,
      genre_filter: new Set(),
    }
  },
  computed: {
    ...mapState('searchStore',['onedayevents']),
  },
  methods: {
    ...mapActions('searchStore', ['fetchOnedayevents']),
    selectClub(club_id) {
      this.router.push({ name: 'ClubDetail', params: { clubId: club_id }})
    },
    selectEvent(eventId, clubId) {
      if (clubId) {
        this.router.push({ name: 'ClubEventDetail', params: { clubId: clubId, clubEventId: eventId }})
      } else {
        this.router.push({ name: 'OnedayEventDetail', params: { onedayEventId: eventId }})
      }
    },
  },
  created() {
    this.fetchOnedayevents(this.$route.params.content)
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

.clubEvent-badge {
  background-color: #AA706A; 
  color: #F8F8F8;
  text-align: center;
  padding: 6px;
}

.onedayEvent-badge {
  background-color: #7393C2; 
  color: #F8F8F8;
  text-align: center;
  padding: 6px;
}

.event-closed-true {
  background-color: #707070; 
  color: #F8F8F8;
  text-align: center;
  padding: 6px;
}

.event-closed-false {
  background-color: rgba(221, 118, 0, 0.8); 
  color: #F8F8F8;
  text-align: center;
  padding: 6px;
}

.event-name {
  word-break: keep-all;
}

.no-result {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -150px 0px 0px -200px;
}

</style>