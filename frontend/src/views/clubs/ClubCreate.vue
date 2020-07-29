<template>
  <div class="custom-container mb-5">

    <!-- clubs-create_banner -->
    <div class="club-banner">
      <img
        class="club-banner-img"
        src="@/assets/club_create_banner.jpg"
        alt="">
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
                  v-model="clubCreateData.basicData.name"
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

              <v-col cols="12">
                <v-autocomplete
                  v-model="clubCreateData.members"
                  :items="users"
                  chips
                  hide-selected
                  color="blue-grey lighten-2"
                  label="클럽 멤버"
                  item-text="nickName"
                  item-value="id"
                  multiple
                  :search-input.sync="searchMember"
                  @change="isMemberNull()"
                  @keypress.enter="isMemberNull()"
                >
                  <template v-slot:selection="data">
                    <v-chip
                      v-bind="data.attrs"
                      :input-value="data.selected"
                      close
                      @click="data.select"
                      @click:close="remove(clubCreateData.members, data.item)"
                    >
                      <!-- <v-avatar left>
                        <v-img :src="data.item.avatar"></v-img>
                      </v-avatar> -->
                      {{ data.item.nickName }}
                    </v-chip>
                  </template>
                  <template v-slot:item="data">
                    <template v-if="typeof data.item !== 'object'">
                      <v-list-item-content v-text="data.item"></v-list-item-content>
                    </template>
                    <template v-else>
                      <!-- <v-list-item-avatar>
                        <img :src="data.item.avatar">
                      </v-list-item-avatar> -->
                      <v-list-item-content>
                        <v-list-item-title v-html="data.item.nickName"></v-list-item-title>
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
                  v-model="clubCreateData.basicData.onelineDescription"
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
                  v-model="clubCreateData.basicData.description"
                  color="blue-grey lighten-2"
                  counter
                  maxlength="100"
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="클럽 설명"
                ></v-textarea>
              </v-col>

              <v-col cols="12">
                <v-autocomplete
                  v-model="clubCreateData.basicData.genres"
                  v-if="genres"
                  :items="genres"
                  chips
                  hide-selected
                  color="blue-grey lighten-2"
                  counter="5"
                  :rules="[
                    v => (v.length !== 0) || '필수항목입니다.',
                    v => (v.length < 6) || '최대 5개의 관심 장르를 고를 수 있습니다.'
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

              <v-col
                cols="12"
                md="12"
              >
                <v-text-field
                  v-model="clubCreateData.basicData.residence"
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
const clubImgFormData = new FormData()

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
          members: [],
          genres: [],
          clubImg: null        
        },
        clubImgFormData: clubImgFormData
      },
      clubImg: null,
      valid: true,
      lazy:false,
      searchMember: null,
      searchGenre: null,
    }
  },
  computed: {
    ...mapState('clubStore', ['users']),
    ...mapState(['genres', 'myaccount', 'users'])
  },
  methods: {
    ...mapActions('clubStore', ['createClub']),
    remove (data, item) {
      const index = data.indexOf(item.id)
      if (index >= 0) data.splice(index, 1)
    },
    isMemberNull() {
      this.$nextTick(() => {
        this.searchMember = null
      })
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
      // const formData = new FormData()
      // formData.append('name', this.clubCreateData.name)
      this.clubCreateData.clubImgFormData.append('clubImg', this.clubImg)
      // console.log(typeof(this.clubCreateData.clubImg))
      // formData.append('onelineDescription', this.clubCreateData.onelineDescription)
      // formData.append('description', this.clubCreateData.description)
      // formData.append('residence', this.clubCreateData.residence)
      // formData.append('genres', this.clubCreateData.genres)
      this.createClub(this.clubCreateData)
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
</style>