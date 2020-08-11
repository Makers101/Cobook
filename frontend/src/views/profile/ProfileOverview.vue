<template>
  <div>
    <!-- profile-overview-calendar -->
    <v-app>
      <v-row class="fill-height">
        <v-col>
          <v-sheet height="64">
            <v-toolbar flat color="white">
              <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
                Today
              </v-btn>
              <v-btn fab text small color="grey darken-2" @click="prev">
                <v-icon small>mdi-chevron-left</v-icon>
              </v-btn>
              <v-btn fab text small color="grey darken-2" @click="next">
                <v-icon small>mdi-chevron-right</v-icon>
              </v-btn>
              <v-toolbar-title v-if="$refs.calendar">
                {{ $refs.calendar.title }}
              </v-toolbar-title>
              <v-spacer></v-spacer>
              <div>
                <small class="mr-2"><i class="fas fa-dot-circle" style="color: #ff9800"></i>독서</small>
                <small class="mr-2"><i class="fas fa-dot-circle" style="color: #3f51b5"></i>클럽 이벤트</small>
                <small class="mr-3"><i class="fas fa-dot-circle" style="color: #4CAF50"></i>원데이 이벤트</small>
              </div>
              <v-menu bottom right>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    outlined
                    color="grey darken-2"
                    v-bind="attrs"
                    v-on="on"
                  >
                    <span>{{ typeToLabel[type] }}</span>
                    <v-icon right>mdi-menu-down</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item @click="type = 'day'">
                    <v-list-item-title>Day</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'week'">
                    <v-list-item-title>Week</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'month'">
                    <v-list-item-title>Month</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = '4day'">
                    <v-list-item-title>4 days</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-toolbar>
          </v-sheet>
          <v-sheet height="600">
            <v-calendar
              ref="calendar"
              v-model="focus"
              color="primary"
              :events="events"
              :event-color="getEventColor"
              :type="type"
              @click:event="showEvent"
              @click:more="viewDay"
              @click:date="viewDay"
              @change="updateRange"
            ></v-calendar>
            <v-menu
              v-model="selectedOpen"
              :close-on-content-click="false"
              :activator="selectedElement"
              offset-x
              min-width="350px"
              max-width="350px"
            >
              <v-card
                color="grey lighten-4"
                min-width="350px"
                max-width="350px"
                flat
              >
                <v-toolbar
                  :color="selectedEvent.color"
                  dark
                >
                  <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                  <v-spacer></v-spacer>
                  <v-btn icon color="white" @click="selectedOpen = false">
                    <i class="fas fa-times"></i>
                  </v-btn>
                </v-toolbar>
                <div
                  class="pointer"
                  @click="selectEvent(selectedEvent.clubEvent.id, selectedEvent.clubEvent.clubId)"
                  v-if="selectedEvent.clubEvent">
                  <div class="card h-100">
                    <div class="row no-gutters">
                      <div class="col-6 event-left">
                        <img class="bg-image" :src="selectedEvent.clubEvent.book.bookImg" width="100%">
                        <span class="badge mb-0 event-closed-true" v-if="selectedEvent.clubEvent.closed">종료</span>
                        <span class="badge mb-0 event-closed-false" v-else>예정</span>
                      </div>
                      <div class="col-6 text-left d-flex flex-column align-items-start p-2">
                        <p class="event-name font-weight-bold" lt="book">{{ selectedEvent.clubEvent.name }}</p>
                        <span class="badge badge-genre">{{ selectedEvent.clubEvent.book.genre }}</span>
                        <div class="mt-auto">
                          <p class="mb-0" v-if="selectedEvent.clubEvent.capacity"><small><i class="fas fa-users"></i> {{ selectedEvent.clubEvent.participantCnt}} / {{ selectedEvent.clubEvent.capacity + 1 }}</small></p>
                          <p class="mb-0" v-else><small><i class="fas fa-users"></i> {{ selectedEvent.clubEvent.participantCnt}}</small></p>
                          <p class="mb-0"><small><i class="fas fa-map-marker-alt"></i> {{ selectedEvent.clubEvent.place }}</small></p>
                          <p class="event-date mb-0"><small>{{ selectedEvent.clubEvent.datetime | moment('YYYY-MM-DD HH:mm') }}</small></p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div
                  class="pointer"
                  @click="selectEvent(selectedEvent.onedayEvent.id, null)"
                  v-if="selectedEvent.onedayEvent">
                  <div class="card h-100">
                    <div class="row no-gutters">
                      <div class="col-6 event-left">
                        <img class="bg-image" :src="selectedEvent.onedayEvent.book.bookImg" width="100%">
                        <span class="badge mb-0 event-closed-true" v-if="selectedEvent.onedayEvent.closed">종료</span>
                        <span class="badge mb-0 event-closed-false" v-else>예정</span>
                      </div>
                      <div class="col-6 text-left d-flex flex-column align-items-start p-2">
                        <p class="event-name font-weight-bold" lt="book">{{ selectedEvent.onedayEvent.name }}</p>
                        <span class="badge badge-genre">{{ selectedEvent.onedayEvent.book.genre }}</span>
                        <div class="mt-auto">
                          <p class="mb-0" v-if="selectedEvent.onedayEvent.capacity"><small><i class="fas fa-users"></i> {{ selectedEvent.onedayEvent.participantCnt}} / {{ selectedEvent.onedayEvent.capacity + 1 }}</small></p>
                          <p class="mb-0" v-else><small><i class="fas fa-users"></i> {{ selectedEvent.onedayEvent.participantCnt}}</small></p>
                          <p class="mb-0"><small><i class="fas fa-map-marker-alt"></i> {{ selectedEvent.onedayEvent.place }}</small></p>
                          <p class="event-date mb-0"><small>{{ selectedEvent.onedayEvent.datetime | moment('YYYY-MM-DD HH:mm') }}</small></p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div v-if="selectedEvent.post">
                  책 리뷰 게시물 카드가 들어갈 것!
                </div>
              </v-card>
            </v-menu>
          </v-sheet>
        </v-col>
      </v-row>
    </v-app>
    
    <!-- profile-overview-statistics -->
    <v-row>
      <v-col cols="6">
        <h3>월별 독서 통계</h3>
        <div class="d-flex justify-content-center align-items-center">
          <bar-chart></bar-chart>
        </div>
      </v-col>
      <v-col cols="6">
        <h3>장르별 독서 통계</h3>
        <div class="d-flex justify-content-center align-items-center" style="min-height:400px">
          <pie-chart v-if="pieData.labels.length > 0"></pie-chart>
          <div v-else>
            <img
              src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png"
              width="150px">
            <p class="my-3"><span class="font-weight-bold">{{ profile.nickName }}</span>님의 독서 기록이 없습니다.</p>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import router from '@/router'
import barChart from "@/views/profile/MonthlyChart.js";
import PieChart from "@/views/profile/GenreChart.js";
export default {
  name: 'ProfileOverview',
  components: {
    barChart,
    PieChart
  },
  data() {
    return {
      focus: '',
      type: 'month',
      typeToLabel: {
        month: 'Month',
        week: 'Week',
        day: 'Day',
        '4day': '4 Days',
      },
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
    }
  },
  computed: {
    ...mapState('profileStore', ['pieData', 'barData', 'profile', 'events'])
  },
  methods: {
    ...mapActions('profileStore', ['findOverview', 'collectEvents']),
    viewDay ({ date }) {
      this.focus = date
      this.type = 'day'
    },
    getEventColor (event) {
      return event.color
    },
    setToday () {
      this.focus = ''
    },
    prev () {
      this.$refs.calendar.prev()
    },
    next () {
      this.$refs.calendar.next()
    },
    showEvent ({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event
        this.selectedElement = nativeEvent.target
        setTimeout(() => this.selectedOpen = true, 10)
      }

      if (this.selectedOpen) {
        this.selectedOpen = false
        setTimeout(open, 10)
      } else {
        open()
      }

      nativeEvent.stopPropagation()
    },
    rnd (a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a
    },
    calendarType(type) {
      this.type = type
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
    this.findOverview(this.$route.params.userId)
    this.collectEvents()
  },
  mounted () {
    this.$refs.calendar.checkChange()
  },
}
</script>

<style scoped>
  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
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
</style>