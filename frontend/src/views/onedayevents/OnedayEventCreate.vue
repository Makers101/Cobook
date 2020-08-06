<template>
  <div class="custom-container mb-5">

    <!-- onedayEvent-create-banner -->
    <div class="onedayEvent-banner">
      <img
        class="onedayEvent-banner-img"
        src="https://user-images.githubusercontent.com/57381062/89494349-f6258b80-d7f0-11ea-9168-2feced39fab5.jpg"
        alt="onedayEvent-banner">
      <div class="onedayEvent-banner-text">
        <h3 class="font-weight-bold">원데이 이벤트 만들기</h3>
        <p class="mb-0">
          원데이 이벤트를 만들어 다른 유저와 함께 책을 읽고 좋은 대화를 나눠보세요 :)
        </p>
      </div>
    </div>
  
    <!-- onedayEvent-create-form -->
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

              <!-- onedayEvent-create-name -->
              <v-col cols="12">
                <v-text-field
                  v-model="onedayEventCreateData.name"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="원데이 이벤트 명"
                ></v-text-field>
              </v-col>

              <!-- onedayEvent-create-book -->
              <v-col cols="12">
                <v-autocomplete
                  v-model="onedayEventCreateData.bookId"
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

              <!-- onedayEvent-create-description -->
              <v-col cols="12">
                <v-textarea
                  v-model="onedayEventCreateData.description"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="100"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="원데이 이벤트 설명"
                ></v-textarea>
              </v-col>

              <!-- onedayEvent-create-place -->
              <v-col class="mt-5" cols="12">
                <div class="d-flex justify-content-start">
                  <label class="v-label theme--light">원데이 이벤트 장소</label>
                </div>
                <v-radio-group
                  v-model="onedayEventCreateData.place"
                  :rules="[v => !!v || '필수항목입니다.']"
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
                      label="원데이 이벤트 장소"
                      counter
                      maxlength="30"
                      :rules="placeRules"
                    ></v-text-field>
                  </div>
                </v-radio-group>
              </v-col>

              <!-- onedayEvent-create-date -->
              <v-col class="pb-0" cols="12">
                <div class="d-flex justify-content-start">
                  <label class="v-label theme--light">원데이 이벤트 일시</label>
                </div>
              </v-col>
              <v-col cols="6">
                <v-menu
                  v-model="onedayEventDate"
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
                    @input="onedayEventDate = false"
                    color="blue-grey lighten-2"
                  >
                  </v-date-picker>
                </v-menu>
              </v-col>

              <!-- onedayEvent-create-time -->
              <v-col cols="6">
                <v-menu
                  ref="menu"
                  v-model="onedayEventTime"
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
                    v-if="onedayEventTime"
                    color="blue-grey lighten-2"
                    v-model="time"
                    full-width
                    ampm-in-title
                    :allowed-minutes="m => m % 30 === 0"
                    format="24hr"
                    @click:minute="$refs.menu.save(time)"
                  ></v-time-picker>
                </v-menu>
              </v-col>

              <!-- onedayEvent-create-capacity -->
              <v-col class="mt-5" cols="12">
                <v-card-text class="p-0">
                  <v-slider
                    v-model="onedayEventCreateData.capacity"
                    label="모집 인원"
                    :max="10"
                    :min="1"
                    step="1"
                    :rules="[v => !!v || '필수항목입니다.']"
                    thumb-label="always"
                    ticks
                  ></v-slider>
                </v-card-text>
              </v-col>
              
              <!-- onedayEvent-create-questions -->
              <v-col class="mt-5" cols="12">
                <div class="d-flex justify-content-start">
                  <label class="v-label theme--light mb-0">질문지</label>
                </div>
                <v-combobox
                  class="pt-0"
                  v-model="onedayEventCreateData.questions"
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
                      @click:close="remove(onedayEventCreateData.questions, data.item)"
                    >
                      {{ data.item }}
                    </v-chip>
                  </template>
                </v-combobox>
              </v-col>
            </v-row>
          </v-container>
          
          <!-- onedayEvent-create-button -->
          <v-card-actions class="d-flex justify-content-end">
            <v-btn
              :disabled="!valid"
              class="button btn-green"
              @click="clickCreate"
            >
              원데이 이벤트 생성
            </v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-app>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'OnedayEventCreate',
  data() {
    return {
      onedayEventCreateData: {
        name: null,
        description: null,
        place: null,
        datetime: null,
        bookId: null,
        capacity: null,
        questions: []
      },
      date: new Date().toISOString().substr(0, 10),
      time: '00:00',
      valid: true,
      lazy:false,
      searchBook: null,
      onedayEventDate: false,
      onedayEventTime: false,
      search: null,
      offlinePlace: null
    }
  },
  computed: {
    ...mapState(['books']),
    offlineEnabled() {
      if (this.onedayEventCreateData.place === null && this.offlinePlace === null) {
        return false
      } else if (this.onedayEventCreateData.place === '온라인' && this.offlinePlace !== '온라인') {
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
    ...mapActions('onedayEventStore', ['createOnedayEvent']),
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
      this.onedayEventCreateData.datetime = this.date + 'T' + this.time
      if (this.offlineEnabled) {
        this.onedayEventCreateData.place = this.offlinePlace
      }
      this.createOnedayEvent(this.onedayEventCreateData)
    }
  }
}
</script>

<style scoped>
  .onedayEvent-banner {
    position: relative;
  }

  .onedayEvent-banner-img {
    width: 100%;
    height: 200px;
    vertical-align: middle;
    filter: brightness(0.7)
  }
  
  .onedayEvent-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }

</style>