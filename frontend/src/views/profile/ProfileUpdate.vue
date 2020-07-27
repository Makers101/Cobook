<template>
  <div class="custom-container mb-5">
    <div class="profile-banner">
      <img class="profile-banner-img"
      src = "@/assets/night.jpg"
      alt="profile banner image">
      <div class="profile-banner-text">
        <h3 class="font-weight-bold">프로필 수정하기</h3>
        <p class="mb-0">본인을 소개해주세요 :)</p>
      </div>
    </div>
    <!-- Profile Update form -->
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
                <!-- 닉네임란 -->
                <v-col
                  cols="12"
                >
                  <v-text-field
                    v-model="accountUpdateData.nickName"
                    color="blue-grey lighten-2"
                    :rules="[v => !!v || '필수항목입니다.']"
                    label="닉네임"
                  ></v-text-field>
                </v-col>
                <!-- 자기소개란 -->
                <v-col
                  cols="12"
                  md="12"
                >
                  <v-textarea
                    v-model="accountUpdateData.description"
                    color="blue-grey lighten-2"
                    :rules="[v => !!v || '필수항목입니다.']"
                    label="소개"
                  ></v-textarea>
                </v-col>
                <!-- 장르란 -->
                <v-col cols="12">
                  <v-autocomplete
                    v-model="accountUpdateData.genres"
                    v-if="genres"
                    :items="genres"
                    chips
                    hide-selected
                    color="blue-grey lighten-2"
                    counter="5"
                    :rules="[
                      v => (v.length !== 0) || '필수항목입니다.',
                      v => (v.length < 6) || '최대 5개 관심 장르를 고를 수 있습니다.'
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
                        @click:close="remove(accountUpdateData.genres, data.item)"
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
                <!-- 프로필 사진 첨부 -->
                <v-col cols="12">
                  <v-file-input
                    v-model="accountUpdateData.profileImg"
                    accept="image/png, image/jpeg, image/bmp"
                    label="프로필 이미지"
                    prepend-icon=""
                    chips
                    :rules="[v => !!v || '필수항목입니다.', v => !v || v.size < 2000000 || '이미지 크기는 최대 2MB 입니다.' ]"
                  >
                  </v-file-input>
                </v-col>
                <!-- Submit 버튼 -->
                
              </v-row>
              <v-card-actions class="d-flex justify-content-end">
                <v-btn
                  :disabled="!valid"
                  class="btn btn-green"
                  @click="updateProfile"
                >
                  프로필 수정
                </v-btn>
              </v-card-actions>
            </v-container>
          </v-form>
      </v-card>
    </v-app>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
let account = {
  nickName : 'nickname1',
  description: '책을 좋아하는 사람입니다.특히 SF에 관심이 많습니다. 좋은 사람들과 club에서 함께하기 위해 참여했습니다.',
  genres: [110, 120, 310, 410],
}

export default {
  name: 'ProfileUpdate',
  data () {
    return {
      account: account,
      accountUpdateData: {
        nickName: null,
        description: null,
        genres: null,
      },
      valid: true,
      lazy: false,
      searchGenre: null,
    }
  },
  computed: {
    ...mapState('profileStore', ['genres'])
  },
  methods: {
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
    ...mapActions('profileStore', ['fetchGenres'])
  },
  created() {
    this.fetchGenres()
  },
  mounted() {
    this.accountUpdateData.nickName = this.account.nickName;
    this.accountUpdateData.description = this.account.description;
    if (this.account.genre.length) {
      this.accountUpdateData.genres = this.account.genres;
    }
    this.accountUpdateData.profileImg = this.account.profileImg
  }
}
</script>

<style scoped>
.profile-image{
  border-radius: 50%;
  width: 5vw;
  height: 5vw;
}

 .profile-banner {
    position: relative;
  }

  .profile-banner-img {
    width: 100%;
    height: 200px;
    vertical-align: middle;
    filter: brightness(0.7)
  }
  
  .profile-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }
</style>                                                                                                                                                                                                                                                