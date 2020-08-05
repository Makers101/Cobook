<template>
  <div class="custom-container mb-5">

    <!-- clubEvent-create_banner -->
    <div class="clubEvent-banner">
      <img
        class="clubEvent-banner-img"
        src="https://user-images.githubusercontent.com/57381062/88908347-b57bbe80-d294-11ea-9d31-a88d3d0b3b23.jpg"
        alt="">
      <div class="clubEvent-banner-text">
        <h3 class="font-weight-bold">클럽 이벤트 만들기</h3>
        <p class="mb-0">
          클럽 이벤트를 만들어 멤버들과 함께 책을 읽고 좋은 대화를 나눠보세요 :)
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
              <v-col
                cols="12"
              >
                <v-text-field
                  v-model="clubEventCreateData.name"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽 이벤트 명"
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-autocomplete
                  v-model="clubEventCreateData.bookId"
                  v-if="books"
                  :items="books"
                  hide-selected
                  color="blue-grey lighten-2"
                  :rules="[
                    v => (v.length !== 0) || '필수항목입니다.'
                  ]"
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
                        <img class="w-100" :src="data.item.bookImg">
                      </v-list-item-content>
                      <v-list-item-content class="col-10">
                        <v-list-item-title v-text="data.item.title"></v-list-item-title>
                      </v-list-item-content>
                    </template>
                  </template>
                </v-autocomplete>
              </v-col>

              <v-col
                cols="12"
                md="12"
              >
                <v-textarea
                  v-model="clubEventCreateData.description"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="100"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽 이벤트 설명"
                ></v-textarea>
              </v-col>

              <v-col
                cols="12"
                md="12"
              >
                <v-text-field
                  v-model="clubEventCreateData.place"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="10"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽 이벤트 장소"
                ></v-text-field>
              </v-col>

              <v-col cols="6">
                <v-menu
                  v-model="menu1"
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
                    @input="menu1 = false"
                    color="blue-grey lighten-2"
                  >
                  </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="6">
                <v-menu
                  ref="menu"
                  v-model="menu2"
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
                    v-if="menu2"
                    color="blue-grey lighten-2"
                    v-model="time"
                    full-width
                    @click:minute="$refs.menu.save(time)"
                  ></v-time-picker>
                </v-menu>
              </v-col>
              <v-col>
                <v-combobox
                  v-model="clubEventCreateData.questions"
                  color="blue-grey lighten-2"
                  label="질문지"
                  multiple
                  chips
                  hint="질문지는 여러 개 작성이 가능합니다 :)"
                >
                  <!-- <template v-slot:no-data>
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title>
                          질문을 작성해주세요!
                        </v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template> -->
                </v-combobox>
              </v-col>
              
            </v-row>
          </v-container>
          
          <v-card-actions class="d-flex justify-content-end">
            <v-btn
              :disabled="!valid"
              class="button btn-green"
              @click="clickCreate"
            >
              클럽 이벤트 생성
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
      date: new Date().toISOString().substr(0, 10),
      time: '00:00',
      valid: true,
      lazy:false,
      searchBook: null,
      
      menu1: false,
      // modal: false,

      menu2: false,
      // modal2: false,
      // items: ['Gaming', 'Programming', 'Vue', 'Vuetify'],
      search: null,
    }
  },
  // watch: {
  //   model (val) {
  //     if (val.length > 5) {
  //       this.$nextTick(() => this.model.pop())
  //     }
  //   },
  // },
  computed: {
    ...mapState(['books']),
  },
  methods: {
    ...mapActions('clubStore', ['createClubEvent']),
    remove (data, item) {
      const index = data.indexOf(item.id)
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
      this.clubEventCreateData.datetime = this.date + 'T' + this.time
      const dataContainer = {
        clubEventCreateData: this.clubEventCreateData,
        clubId: this.$route.params.clubId
      }
      this.createClubEvent(dataContainer)
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