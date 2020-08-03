<template>
  <div class="custom-container mb-5">

    <!-- clubs-update-banner -->
    <div class="club-banner">
      <img
        class="club-banner-img"
        src="https://user-images.githubusercontent.com/57381062/88908102-70f02300-d294-11ea-9c03-7aac0e803ea0.jpg"
        alt="">
      <div class="club-banner-text">
        <h3 class="font-weight-bold">클럽 정보 수정</h3>
        <p class="mb-0">
           클럽의 기본 정보를 수정해보세요 :)
        </p>
      </div>
    </div>

    <!-- club-update-form -->
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
                  v-model="clubUpdateData.basicData.name"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽명"
                ></v-text-field>
              </v-col>
              
              <v-col cols="12">
                <v-file-input
                  v-model="clubImg"
                  enctype="multipart/form-data"
                  accept="image/png, image/jpeg, image/bmp"
                  label="프로필 이미지"
                  show-size
                  prepend-icon=""
                  chips
                  :rules="[v => !!v || '필수항목입니다.', v => !v || v.size < 2000000 || '이미지 크기는 최대 2MB 입니다.' ]"
                >
                </v-file-input>
              </v-col>

              <v-col
                cols="6"
                md="6"
              >
                <v-text-field
                  v-if="myaccount"
                  v-model="myaccount.nickName"
                  disabled
                  color="blue-grey lighten-2"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽장"
                ></v-text-field>
                <v-text-field
                  v-else
                  v-model="myaccount.nickName"
                  disabled
                  color="blue-grey lighten-2"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽장"
                ></v-text-field>
              </v-col>

              <v-col
                cols="12"
                md="12"
              >
                <v-text-field
                  v-model="clubUpdateData.basicData.onelineDescription"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="30"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="한 줄 설명"
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                md="12"
              >
                <v-textarea
                  v-model="clubUpdateData.basicData.description"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="100"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽 설명"
                ></v-textarea>
              </v-col>

              <v-col cols="12">
                <v-autocomplete
                  v-model="clubUpdateData.basicData.genres"
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
                      @click:close="remove(clubUpdateData.basicData.genres, data.item)"
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

              <v-col
                cols="12"
                md="12"
              >
                <v-text-field
                  v-model="clubUpdateData.basicData.residence"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="10"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="주 모임지역"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>

          <v-card-actions class="d-flex justify-content-end">
            <v-btn
              :disabled="!valid"
              class="button btn-green"
              @click="clickUpdate"
            >
              클럽 수정
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
  name: 'ClubUpdate',
  data() {
    return {
      clubUpdateData: {
        basicData: {
          name: null,
          onelineDescription: null,
          description: null,
          residence: null,
          genres: [],
        },
        clubId: this.$route.params.clubId,
        clubImgFormData: new FormData()
      },
      clubImg: null,
      valid: true,
      lazy:false,
      searchGenre: null,
    }
  },
  computed: {
    ...mapState(['genres', 'myaccount', 'users']),
    ...mapState('clubStore', ['selectedClub'])
  },
  methods: {
    ...mapActions('clubStore', ['findClub', 'updateClub']),
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
    // clickUpdate() {
    //   this.clubCreateData.clubImgFormData.append('clubImg', this.clubImg)
    //   this.createClub(this.clubCreateData)
    // }
  },
  created() {
    this.findClub(this.$route.params.clubId)
  },
  mounted() {
    this.clubUpdateData.basicData.name = this.selectedClub.name
    this.clubUpdateData.basicData.onelineDescription = this.selectedClub.onelineDescription
    this.clubUpdateData.basicData.description = this.selectedClub.description
    this.clubUpdateData.basicData.residence = this.selectedClub.residence
    this.selectedClub.genres.forEach(genre => {
      this.clubUpdateData.basicData.genres.push(genre.id)
    })
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
</style>