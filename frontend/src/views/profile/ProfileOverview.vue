<template>
  <div style="padding-bottom:100px">
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
                <small class="mr-2"><i class="fas fa-dot-circle" style="color: #74a892"></i>독서</small>
                <small class="mr-2"><i class="fas fa-dot-circle" style="color: #b484bf"></i>북클럽 이벤트</small>
                <small class="mr-3"><i class="fas fa-dot-circle" style="color: #7393C2"></i>원데이 이벤트</small>
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
              color="#c4bbc4"
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
                  <div 
                    class="pointer"
                    @click="selectFeed(selectedEvent.post.id)">
                    <div class="card card-post m-0 ">
                      <div class="additional d-flex justify-content-start px-1">
                        <div class="user-card">
                          <div class="points center">
                            <i class="fas fa-heart mr-1"></i> {{ selectedEvent.post.likeUsers.length }}
                          </div>
                          <img class="mt-5" :src="selectedEvent.post.book.bookImg">  
                        </div>
                        <div class="more-info d-flex flex-column justify-content-around">
                          <p class="book-title my-2">{{ selectedEvent.post.book.title }}</p>
                          <div class="text-left">
                            <div class="coords">
                              <p>
                                <!-- <mark>작가</mark> -->
                                <img class="mr-2" src="https://user-images.githubusercontent.com/25967949/89915180-00042000-dc31-11ea-8777-4cc7df56b4b4.png" width="20px">
                                <span>{{ selectedEvent.post.book.author }}</span>
                              </p>
                            </div>
                            <div class="coords">
                              <p>
                                <!-- <mark>출판사</mark> -->
                                <img class="mr-2" src="https://user-images.githubusercontent.com/25967949/89915533-71dc6980-dc31-11ea-96c5-139fa8877eb2.png" width="20px">
                                <span>{{ selectedEvent.post.book.publisher }}</span>
                              </p>
                            </div>
                            <div class="coords">
                              <p>
                                <!-- <mark>출판일</mark> -->
                                <img class="mr-2" src="https://user-images.githubusercontent.com/25967949/89915335-304bbe80-dc31-11ea-9dbc-06270437603f.png" width="20px">
                                <span>{{ selectedEvent.post.book.pubDate | moment('YYYY-MM-DD') }}</span>
                              </p>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="general d-flex flex-column justify-content-between">
                        <div class="w-100 h-100 d-flex flex-column justify-content-around">
                          <div class="mb-2">
                            <span class="mb-3 star-container" v-for="index in selectedEvent.post.rank" :key="index"><i class="fas fa-star" style="color:yellow"></i></span>
                          </div>
                          <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
                          <p class="card-text px-3" style="word-break:keep-all;">{{ selectedEvent.post.onelineReview }}</p>
                          <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
                        </div>
                        <div class="more">
                          <span class="text-black-50"><small>{{ selectedEvent.post.createdAt | moment('YYYY-MM-DD')}}</small></span>
                        </div>
                      </div>
                    </div>
                  </div>   
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
          <bar-chart class="col-12"></bar-chart>
        </div>
      </v-col>
      <v-col cols="6">
        <h3>장르별 독서 통계</h3>
        <div class="d-flex justify-content-center align-items-center">
          <pie-chart class="col-12" v-if="pieData.labels.length"></pie-chart>
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
    selectFeed(id) {
      this.$router.push({name: 'PostDetail', params: {postId: id}})
    }
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

  /* card */

  .book-title {
    color: white;
    padding-left: 5px;
    padding-right: 8px;
    text-shadow: 1px 1px 2px rgb(0,0,0,0.7);
    font-weight: 700;
    word-break: keep-all;
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2; 
    -webkit-box-orient: vertical;
    height: 3rem;
  }

  .card-post {
    cursor: pointer;
  }

  .center {
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
  }

  .card-post {
    height: 250px;
    background-color: #fff;
    background: linear-gradient(#f8f8f8, #fff);
    box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
    border-radius: 6px;
    overflow: hidden;
    position: relative;
    margin: 1.5rem;
  }

  .card-post h1 {
    text-align: center;
  }

  .card-post .additional {
    position: absolute;
    width: 33%;
    height: 100%;
    background: linear-gradient(#88A498, #88A487);
    transition: width 0.4s;
    overflow: hidden;
    z-index: 2;
  }

  .card-post.green .additional {
    background: linear-gradient(#92bCa6, #A2CCB6);
  }


  .additional:hover {
    width: 100%;
    border-radius: 0 5px 5px 0;
  }


  .additional:hover.user-card {
    position: absolute;
    left: 0;
  }

  .card-post .additional .user-card {
    width: 120px;
    height: 100%;
    position: relative;
    float: left;
  }

  .card-post .additional .user-card::after {
    content: "";
    display: block;
    position: absolute;
    top: 10%;
    right: -2px;
    height: 80%;
  }

  .card-post .additional .user-card .level,
  .card-post .additional .user-card .points {
    top: 15%;
    color: #fff;
    text-transform: uppercase;
    font-size: 0.75em;
    font-weight: bold;
    background: rgba(0,0,0,0.15);
    padding: 0.125rem 0.75rem;
    border-radius: 100px;
    white-space: nowrap;
  }

  .card-post .additional .user-card .level {
    white-space: pre-wrap;
    width: 100%;
    word-break: keep-all;
  }

  .card-post .additional .user-card .points {
    top: 85%;
  }

  .card-post .additional .user-card img {
    /* top: 10%; */
    margin-top: 30%;
    max-width: 80%;
    /* left: 14%; */
    border-radius: 5%;
    /* position: absolute; */
  }

  .card-post .additional .more-info {
    width: 58%;
    float: left;
    position: absolute;
    left: 120px;
    height: 100%;
  }

  .card-post .additional .more-info h1 {
    color: #fff;
    margin-bottom: 0;
  }

  .card-post.green .additional .more-info h1 {
    color: #224C36;
  }

  .card-post .additional .coords {
    /* margin: 0 1rem; */
    padding-left: 8px;
    padding-right: 8px;
    margin-bottom: 8px;
    color: #fff;
    font-size: 1rem;
  }

  .card-post.green .additional .coords {
    color: #325C46;
  }

  .card-post .additional .coords span + span {
    float: right;
  }

  .card-post .additional .stats {
    font-size: 2rem;
    display: flex;
    position: absolute;
    bottom: 1rem;
    left: 1rem;
    right: 1rem;
    top: auto;
    color: #fff;
  }

  .card-post.green .additional .stats {
    color: #325C46;
  }

  .card-post .additional .stats > div {
    flex: 1;
    text-align: center;
  }

  .card-post .additional .stats i {
    display: block;
  }

  .card-post .additional .stats div.title {
    font-size: 0.75rem;
    font-weight: bold;
    text-transform: uppercase;
  }

  .card-post .additional .stats div.value {
    font-size: 1.5rem;
    font-weight: bold;
    line-height: 1.5rem;
  }

  .card-post .additional .stats div.value.infinity {
    font-size: 2.5rem;
  }

  .card-post .general {
    width: 66%;
    height: 100%;
    position: absolute;
    top: 0;
    right: 0;
    z-index: 1;
    box-sizing: border-box;
    padding: 1rem;
  }

  .card-post p {
    margin: 0;
    padding-left: 10px;
    padding-right: 10px;
  }

  mark {
    background-color: #fff7f7;
    color: black;
    border-radius: 10px;
  }

  .star-container {
    text-shadow: 1px 1px 2px rgb(0, 0, 0, 0.7);
  }

  .fa-heart {
    color: red;
    text-shadow: 1px 1px 2px rgb(0, 0, 0, 0.7);
  }
</style>