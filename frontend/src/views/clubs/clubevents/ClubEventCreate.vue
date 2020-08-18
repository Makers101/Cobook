<template>
  <div class="custom-container mb-5">

    <!-- clubEvent-create-banner -->
    <div class="clubEvent-banner">
      <img
        class="clubEvent-banner-img"
        src="https://user-images.githubusercontent.com/57381062/88908347-b57bbe80-d294-11ea-9d31-a88d3d0b3b23.jpg"
        alt="clubEvent-banner">
      <div class="clubEvent-banner-text">
        <h3 class="font-weight-bold">북클럽 이벤트 만들기</h3>
        <p class="mb-0">
          북클럽 이벤트를 만들어 멤버들과 함께 책을 읽고 좋은 대화를 나눠보세요 :)
        </p>
      </div>
    </div>
  
    <!-- clubEvent-create-form -->
    <v-app>
      <v-card>
        <template v-slot:progress>
          <v-progress-linear
            absolute
            color="green lighten-3"
            height="4"
            indeterminate
          ></v-progress-linear>
        </template>

        <v-form
          v-model="valid"
          :lazy-validation="lazy"
        >
          <v-container>
            <v-row>

              <!-- clubEvent-create-name -->
              <v-col cols="12">
                <v-text-field
                  v-model="clubEventCreateData.name"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="북클럽 이벤트 명"
                ></v-text-field>
              </v-col>

              <!-- clubEvent-create-book -->
              <v-col cols="12">
                <v-autocomplete
                  v-model="clubEventCreateData.bookId"
                  v-if="books"
                  :items="books"
                  hide-selected
                  color="blue-grey lighten-2"
                  :rules="[v => (v.length !== 0) || '필수항목입니다.']"
                  label="책 검색"
                  item-text="title"
                  item-value="id"
                  :search-input.sync="searchBook"
                  @change="isBookNull()"
                  @keypress.enter="isBookNull()"
                >
                  <template v-slot:selection="data">
                    {{ data.item.title }}
                  </template>
                  <template v-slot:item="data">
                    <template v-if="typeof data.item !== 'object'">
                      <v-list-item-content v-text="data.item"></v-list-item-content>
                    </template>
                    <template v-else class="row">
                      <v-list-item-content class="offset-1 col-1">
                        <img class="w-100" :src="data.item.bookImg" :alt="data.item.title">
                      </v-list-item-content>
                      <v-list-item-content class="col-10">
                        <v-list-item-title v-text="data.item.title"></v-list-item-title>
                      </v-list-item-content>
                    </template>
                  </template>
                </v-autocomplete>
              </v-col>

              <!-- clubEvent-create-description -->
              <v-col cols="12">
                <v-textarea
                  v-model="clubEventCreateData.description"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="100"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="북클럽 이벤트 설명"
                ></v-textarea>
              </v-col>

              <!-- clubEvent-create-place -->
              <v-col class="mt-5" cols="12">
                <div class="d-flex justify-content-start">
                  <label class="v-label theme--light">북클럽 이벤트 장소</label>
                </div>
                <v-radio-group
                  v-model="clubEventCreateData.place"
                  :rules="[v => !!v || '필수항목입니다.']"
                  @mousedown="preventDoubling"
                >
                  <div class="d-flex justify-content-start align-items-center">
                    <v-radio label="온라인" value="온라인"></v-radio>
                  </div>
                  <div class="d-flex justify-content-start align-items-center">
                    <v-radio label="오프라인" :value="offlinePlace"></v-radio>
                    <v-text-field
                      class="mx-3 mb-2"
                      v-model="offlinePlace"
                      :disabled="!offlineEnabled"
                      label="북클럽 이벤트 장소"
                      counter
                      maxlength="30"
                      :rules="placeRules"
                    ></v-text-field>
                  </div>
                </v-radio-group>
              </v-col>

              <!-- clubEvent-create-date -->
              <v-col class="pb-0" cols="12">
                <div class="d-flex justify-content-start">
                  <label class="v-label theme--light">클럽 이벤트 일시</label>
                </div>
              </v-col>
              <v-col cols="6">
                <v-menu
                  v-model="clubEventDate"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="date"
                      color="blue-grey lighten-2"
                      label="날짜"
                      :rules="[v => !!v || '필수항목입니다.']"
                      prepend-icon=""
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="date"
                    @input="clubEventDate = false"
                    color="blue-grey lighten-2"
                    :min="localDatetime('date')"
                  >
                  </v-date-picker>
                </v-menu>
              </v-col>

              <!-- clubEvent-create-time -->
              <v-col cols="6">
                <v-menu
                  ref="menu"
                  v-model="clubEventTime"
                  color="blue-grey lighten-2"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  :return-value.sync="time"
                  transition="scale-transition"
                  offset-y
                  max-width="290px"
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="time"
                      color="blue-grey lighten-2"
                      label="시간"
                      :rules="[v => !!v || '필수항목입니다.']"
                      prepend-icon=""
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-time-picker
                    v-if="clubEventTime"
                    color="blue-grey lighten-2"
                    v-model="time"
                    full-width
                    ampm-in-title
                    :allowed-minutes="m => m % 30 === 0"
                    format="24hr"
                    @click:minute="$refs.menu.save(time)"
                    :min="localDatetime('time')"
                  ></v-time-picker>
                </v-menu>
              </v-col>

              <!-- clubEvent-create-questions -->
              <v-col class="mt-5" cols="12">
                <div class="d-flex justify-content-start">
                  <label class="v-label theme--light mb-0">질문지</label>
                </div>
                <v-combobox
                  class="pt-0"
                  v-model="clubEventCreateData.questions"
                  color="blue-grey lighten-2"
                  multiple
                  chips
                  hint="질문지는 여러 개 작성이 가능합니다 :)"
                  placeholder="작성 후 Enter를 누르세요 :)"
                >
                  <template v-slot:selection="data">
                    <v-chip
                      v-bind="data.attrs"
                      close
                      @click:close="remove(clubEventCreateData.questions, data.item)"
                    >
                      {{ data.item }}
                    </v-chip>
                  </template>
                </v-combobox>
              </v-col>
              
            </v-row>
          </v-container>
          
          <!-- clubEvent-create-button -->
          <v-card-actions class="d-flex justify-content-end">
            <v-btn
              :disabled="!valid"
              class="button btn-green"
              @click="clickCreate"
            >
              북클럽 이벤트 생성
            </v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-app>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import Swal from 'sweetalert2'
const swal = Swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success mr-2',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: false
})

export default {
  name: 'ClubEventCreate',
  data() {
    return {
      clubEventCreateData: {
        name: null,
        description: null,
        place: null,
        datetime: null,
        bookId: null,
        questions: []
      },
      date: null,
      time: null,
      valid: true,
      lazy:false,
      searchBook: null,
      clubEventDate: false,
      clubEventTime: false,
      search: null,
      offlinePlace: null,
      clicked: false
    }
  },
  computed: {
    ...mapState(['books']),
    offlineEnabled() {
      if (this.clubEventCreateData.place === null && this.offlinePlace === null) {
        return false
      } else if (this.clubEventCreateData.place === '온라인' && this.offlinePlace !== '온라인') {
        return false
      } else {
        return true
      }
    },
    placeRules() {
      const tempRules = []
      if (this.offlineEnabled) {
        const rule = v => !!v || '필수항목입니다.'
        tempRules.push(rule)
      }
      if (this.offlinePlace) {
        const rule = v => v !== '온라인' || '오프라인 장소를 작성해주세요 :)'
        tempRules.push(rule)
      }
      return tempRules
    }
  },
  methods: {
    ...mapActions('clubStore', ['createClubEvent']),
    remove (data, item) {
      const index = data.indexOf(item)
      if (index >= 0) data.splice(index, 1)
    },
    isBookNull() {
      this.$nextTick(() => {
        this.searchBook = null
      })
    },
    validate() {
      this.$refs.form.validate()
    },
    clickCreate() {
      this.clicked = true
      this.clubEventCreateData.datetime = this.date + 'T' + this.time
      if (this.offlineEnabled) {
        this.clubEventCreateData.place = this.offlinePlace
      }
      const dataContainer = {
        clubEventCreateData: this.clubEventCreateData,
        clubId: this.$route.params.clubId
      }
      this.createClubEvent(dataContainer)
    },
    preventDoubling() {
      if (this.offlinePlace === '온라인') {
        this.offlinePlace = null
      }
    },
    localDatetime(type) {
      const tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
      const localISOTime = (new Date(Date.now() - tzoffset)).toISOString().slice(0, -1);
      if (type === 'time') {
        if (this.date === localISOTime.slice(0, 10)) {
          if (Number(localISOTime.slice(14, 16)) < 30) {
            return localISOTime.slice(11, 13) + ':30'
          } else {
            return (Number(localISOTime.slice(11, 13)) + 1)%24 + ':00'
          }
        } else {
          return ''
        }
      } else if (type === 'date') {
        return localISOTime.slice(0, 10)
      } else {
        return ''
      }
    },
  },
  mounted() {
    this.date = this.localDatetime('date')
    this.time = this.localDatetime('time')
  },
  beforeRouteLeave(to, from, next) {
    if (!this.clicked) {
      if (this.clubEventCreateData.name
          || this.clubEventCreateData.description
          || this.clubEventCreateData.bookId
          || this.clubEventCreateData.place
          || this.offlinePlace
          || this.date !== this.localDatetime('date')
          || this.time !== this.localDatetime('time')
          || this.clubEventCreateData.questions.length > 0
         ) {
            swal.fire({
              html: "<p>생성 중인 북클럽 이벤트가 있습니다.</p><p>정말 넘어가시겠습니까?</p>",
              showCancelButton: true,
              confirmButtonText: '네',
              cancelButtonText: '아니요',
              icon: "warning",
            })
            .then((result) => {
              if (result.value) {
                next()
              }
            });
          } else {
            next()
          }
    } else {
      next()
    }
  }
}
</script>

<style scoped>
  .clubEvent-banner {
    position: relative;
  }

  .clubEvent-banner-img {
    width: 100%;
    height: 200px;
    vertical-align: middle;
    filter: brightness(0.7)
  }
  
  .clubEvent-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }
</style>