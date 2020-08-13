<template>
  <div class="custom-container mb-5">

    <!-- post-create_banner -->
    <div class="post-banner">
      <img
        class="post-banner-img"
        src="https://user-images.githubusercontent.com/57381062/88908323-abf25680-d294-11ea-8edf-3b22787e7f04.jpg"
        alt="">
      <div class="post-banner-text">
        <h3 class="font-weight-bold">리뷰 작성하기</h3>
        <p class="mb-0">
          멋진 책 리뷰를 작성해주세요. :)
        </p>
      </div>
    </div>

    <!-- post-create-form -->
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
              <v-col cols="12">
                <v-autocomplete
                  v-model="postCreateData.bookId"
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
              >
                <v-text-field
                  v-model="postCreateData.onelineReview"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="한 줄 평"
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-subheader class="pl-0">평점</v-subheader>
                <v-slider
                  v-model="postCreateData.rank"
                  :tick-labels="rankArray"
                  ticks="always"
                  :thumb-size="30"
                  thumb-label="always"
                  min="1"
                  max="5"
                  track-color="#88A498"
                  color="#3c755a"
                >
                  <template v-slot:thumb-label="{ value }">
                    {{ satisfactionEmojis[value-1] }}
                  </template>
                </v-slider>
              </v-col>

              <v-col cols="12">
                <v-combobox
                  v-if="tags"
                  v-model="postCreateData.tags"
                  :items="tags"
                  :search-input.sync="searchTag"
                  hide-selected
                  counter="5"
                  :rules="[
                    v => (v.length < 6) || '최대 5개의 태그를 고를 수 있습니다.'
                  ]"
                  color="blue-grey lighten-2"
                  label="태그"
                  multiple
                  item-text="tagName"
                  item-value="tagName"
                  :return-object="false"
                  persistent-hint
                  small-chips
                >
                  <template v-slot:selection="data">
                    <v-chip
                      v-bind="data.attrs"
                      close
                      @click:close="remove(postCreateData.tags, data.item)"
                    >
                      {{ data.item }}
                    </v-chip>
                  </template>
                  <template v-slot:no-data>
                    <v-list-item>
                      <v-list-item-content>
                        <v-list-item-title>
                          "<strong>{{ searchTag }}</strong>"를 찾을 수 없습니다. <kbd>enter</kbd>를 눌러 새로운 태그를 만들어보세요. 
                        </v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                  </template>
                </v-combobox>
              </v-col>
              
              <v-col cols="12">
                <!-- <v-expansion-panels
                  v-model="panel"
                  flat
                >
                  <v-expansion-panel class="px-0" @click="activateDetailReview">
                    <v-expansion-panel-header>상세 리뷰</v-expansion-panel-header>
                    <v-expansion-panel-content class="px-0">
                      <div class="px-0" id="summernote"></div>
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                </v-expansion-panels> -->
                <v-card-actions class="d-flex justify-content-start px-0">
                  <v-btn
                    class="button btn-outline-green"
                    @click="activateDetailReview"
                  >
                    상세 리뷰(선택)
                  </v-btn>
                </v-card-actions>
                <div id="summernote" v-show="activatedDetail"></div>
              </v-col>


            </v-row>
          </v-container>

          <v-card-actions class="d-flex justify-content-end">
            <v-btn
              :disabled="!valid"
              class="button btn-green"
              @click="clickCreate"
            >
              리뷰 생성
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
  name: 'PostCreate',
  data() {
    return {
      postCreateData: {
        bookId: null,
        onelineReview: null,
        open: true,
        rank: 3,
        review: null,
        tags: []
      },
      oneline: true,
      detailReview: false,
      valid: true,
      lazy:false,
      searchBook: null,
      searchTag: null,
      rankArray: [1, 2, 3, 4, 5],
      satisfactionEmojis: ['😭', '😢', '😊', '😄', '😍'],
      activatedDetail: false,
      clicked: false
    }
  },
  computed: {
    ...mapState(['books']),
    ...mapState('postStore', ['tags']),
  },
  methods: {
    ...mapActions('postStore', ['fetchTags', 'createPost']),
    remove (data, item) {
      const index = data.indexOf(item)
      if (index >= 0) data.splice(index, 1)
    },
    makeTwoline() {
      this.oneline = false
    },
    createDetailReview() {
      this.detailReview = !this.detailReview
    },
    isBookNull() {
      this.$nextTick(() => {
        this.searchBook = null
      })
    },
    validate() {
      this.$refs.form.validate()
    },
    activateDetailReview() {
      if (!this.activatedDetail) {
        this.activatedDetail = true
        window.$('#summernote').summernote({
          placeholder: '상세 리뷰를 작성해주세요 :)',
          tabsize: 2,
          height: 200,
          toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['hr', 'link', 'picture', 'video']],
            ['view', ['undo', 'redo', 'help']]
          ]
        })
        window.$('#summernote').summernote('justifyLeft');
      }
    },
    clickCreate() {
      this.clicked = true
      if (window.$('#summernote').summernote('code') === '<p style="text-align: left;"><br></p>') {
        this.postCreateData.review = null
      } else if (window.$('#summernote').summernote('code') === '<p><br></p>') {
        this.postCreateData.review = null
      } else {
        this.postCreateData.review = window.$('#summernote').summernote('code')
      }
      this.createPost(this.postCreateData)
    }
  },
  created() {
    this.fetchTags()
  },
  mounted() {
    if (this.$route.params.selectedBookId) {
      this.postCreateData.bookId = this.$route.params.selectedBookId
    }
  },
  beforeRouteLeave(to, from, next) {
    if (!this.clicked) {
      if (this.postCreateData.bookId
          || this.postCreateData.onelineReview
          || (this.postCreateData.rank !== 3)
          || (window.$('#summernote').summernote('code') !== '<p><br></p>')
          || (this.postCreateData.tags.length !== 0)
         ) {
        if (confirm('작성 중인 리뷰가 있습니다. 정말 넘어가시겠습니까?') === true) {
          next()
        } else {
          return false
        }
      }
      next()
    } else {
      next()
    }
  }
}
</script>

<style scoped>
.post-banner {
  position: relative;
}

.post-banner-img {
  width: 100%;
  height: 200px;
  vertical-align: middle;
  filter: brightness(0.7)
}

.post-banner-text {
  color: #F8F8F8;
  text-align: center;
  text-shadow: 2px 2px 2px rgb(100, 100, 100);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate( -50%, -50% );
}

.card-header {
  background-color: white;
}

</style>