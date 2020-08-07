<template>
  <div class="custom-container mb-5">

    <!-- clubs-create-banner -->
    <div class="club-banner">
      <img
        class="club-banner-img"
        src="https://user-images.githubusercontent.com/57381062/88908102-70f02300-d294-11ea-9c03-7aac0e803ea0.jpg"
        alt="club-banner-img">
      <div class="club-banner-text">
        <h3 class="font-weight-bold">클럽 만들기</h3>
        <p class="mb-0">
          멋진 독서 클럽을 만들어보세요 :)
        </p>
      </div>
    </div>

    <!-- club-create-form -->
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
        <v-form v-model="valid" :lazy-validation="lazy">
          <v-container>
            <v-row>

              <!-- club-create-name -->
              <v-col cols="12">
                <v-text-field
                  v-model="clubCreateData.basicData.name"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽명"
                ></v-text-field>
              </v-col>
              
              <!-- club-create-image -->
              <v-col cols="12">
                <div v-if="previewImg && clubImg">
                  <img class="img-fluid club-image rounded" :src="previewImg">
                </div>
                <v-file-input
                  v-model="clubImg"
                  @change="previewImage"
                  enctype="multipart/form-data"
                  accept="image/png, image/jpeg, image/bmp"
                  label="프로필 이미지"
                  show-size
                  prepend-icon=""
                  chips
                  :rules="[
                    v => !!v || '필수항목입니다.',
                    v => !v || v.size < 2000000 || '이미지 크기는 최대 2MB 입니다.'
                  ]"
                >
                </v-file-input>
              </v-col>

              <!-- club-create-leader -->
              <v-col cols="6">
                <v-text-field
                  v-if="myaccount"
                  v-model="myaccount.nickName"
                  disabled
                  color="blue-grey lighten-2"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽장"
                ></v-text-field>
              </v-col>

              <!-- club-create-onelineDescription -->
              <v-col cols="12">
                <v-text-field
                  v-model="clubCreateData.basicData.onelineDescription"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="한 줄 설명"
                ></v-text-field>
              </v-col>

              <!-- club-create-description -->
              <v-col cols="12">
                <v-textarea
                  v-model="clubCreateData.basicData.description"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="100"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽 설명"
                ></v-textarea>
              </v-col>

              <!-- club-create-genres -->
              <v-col cols="12">
                <v-autocomplete
                  v-model="clubCreateData.basicData.genres"
                  v-if="genres"
                  :items="genres"
                  chips
                  hide-selected
                  color="blue-grey lighten-2"
                  counter="3"
                  :rules="[
                    v => (v.length !== 0) || '필수항목입니다.',
                    v => (v.length < 4) || '최대 3개의 관심 장르를 고를 수 있습니다.'
                  ]"
                  label="관심 장르"
                  item-text="name"
                  item-value="id"
                  multiple
                  :search-input.sync="searchGenre"
                  @change="isGenreNull()"
                  @keypress.enter="isGenreNull()"
                >
                  <template v-slot:selection="data">
                    <v-chip
                      v-bind="data.attrs"
                      :input-value="data.selected"
                      close
                      @click="data.select"
                      @click:close="remove(clubCreateData.basicData.genres, data.item)"
                    >
                      {{ data.item.name }}
                    </v-chip>
                  </template>
                  <template v-slot:item="data">
                    <template v-if="typeof data.item !== 'object'">
                      <v-list-item-content v-text="data.item"></v-list-item-content>
                    </template>
                    <template v-else>
                      <v-list-item-content>
                        <v-list-item-title v-text="data.item.name"></v-list-item-title>
                      </v-list-item-content>
                    </template>
                  </template>
                </v-autocomplete>
              </v-col>

              <!-- club-create-residence -->
              <v-col class="mt-5" cols="12">
                <div class="d-flex justify-content-start">
                  <label class="v-label theme--light">주 모임 지역</label>
                </div>
                <v-radio-group
                  v-model="clubCreateData.basicData.residence"
                  :rules="[v => !!v || '필수항목입니다.']"
                  @mousedown="preventDoubling"
                >
                  <div class="d-flex justify-content-start align-items-center">
                    <v-radio label="온라인" value="온라인"></v-radio>
                  </div>
                  <div class="d-flex justify-content-start align-items-center">
                    <v-radio label="오프라인" :value="offlineResidence"></v-radio>
                    <v-text-field
                      class="mx-3 mb-2"
                      v-model="offlineResidence"
                      :disabled="!offlineEnabled"
                      label="오프라인 모임 장소"
                      :rules="residenceRules"
                    ></v-text-field>
                  </div>
                </v-radio-group>
              </v-col>

            </v-row>
          </v-container>

          <!-- club-create-button -->
          <v-card-actions class="d-flex justify-content-end">
            <v-btn
              :disabled="!valid"
              class="button btn-green"
              @click="clickCreate"
            >
              클럽 생성
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
  name: 'ClubCreate',
  data() {
    return {
      clubCreateData: {
        basicData: {
          name: null,
          onelineDescription: null,
          description: null,
          residence: null,
          genres: []       
        },
        clubImgFormData: new FormData()
      },
      clubImg: null,
      previewImg: null,
      valid: true,
      lazy:false,
      searchGenre: null,
      offlineResidence: null
    }
  },
  computed: {
    ...mapState(['genres', 'myaccount']),
    offlineEnabled() {
      if (this.clubCreateData.basicData.residence === null && this.offlineResidence === null) {
        return false
      } else if (this.clubCreateData.basicData.residence === '온라인' && this.offlineResidence !== '온라인') {
        return false
      } else {
        return true
      }
    },
    residenceRules() {
      const tempRules = []
      if (this.offlineEnabled) {
        const rule = v => !!v || '필수항목입니다.'
        tempRules.push(rule)
      }
      if (this.offlineResidence) {
        const rule = v => v !== '온라인' || '오프라인 장소를 작성해주세요 :)'
        tempRules.push(rule)
      }
      return tempRules
    }
  },
  methods: {
    ...mapActions('clubStore', ['createClub']),
    remove (data, item) {
      const index = data.indexOf(item.id)
      if (index >= 0) data.splice(index, 1)
    },
    isGenreNull() {
      this.$nextTick(() => {
        this.searchGenre = null
      })
    },
    validate() {
      this.$refs.form.validate()
    },
    clickCreate() {
      this.clubCreateData.clubImgFormData.append('clubImg', this.clubImg)
      if (this.offlineEnabled) {
        this.clubCreateData.basicData.residence = this.offlineResidence
      }
      this.createClub(this.clubCreateData)
    },
    previewImage() {
      if (this.clubImg) {
        let reader = new FileReader();
        reader.onload = e => {
          this.previewImg = e.target.result
        };
        reader.readAsDataURL(this.clubImg);
      }
    },
    preventDoubling() {
      if (this.offlineResidence === '온라인') {
        this.offlineResidence = null
      }
    }
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

  .club-image {
    height: 200px; 
  }
</style>