<template>
  <div class="custom-container mb-5">
    
    <div class="profile-banner">
      <img class="profile-banner-img"
      src = "https://user-images.githubusercontent.com/57381062/88908230-90874b80-d294-11ea-96f1-10073a23235f.jpg"
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
                    v-model="profileUpdateData.basicData.nickName"
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
                    v-model="profileUpdateData.basicData.description"
                    color="blue-grey lighten-2"
                    label="소개"
                  ></v-textarea>
                </v-col>

                <!-- 프로필 사진 첨부 -->
                <v-col cols="12">
                  <div v-if="myaccount.profileImg && !profileImg">
                    <img class="img-fluid profile-image rounded" :src="myaccount.profileImg" :alt="myaccount.nickName">
                  </div>
                  <div v-if="previewImg && profileImg">
                    <img class="img-fluid profile-image rounded" :src="previewImg" :alt="myaccount.nickName">
                  </div>
                  <v-file-input
                    v-model="profileImg"
                    enctype="multipart/form-data"
                    accept="image/png, image/jpeg, image/bmp"
                    label="프로필 이미지 변경"
                    prepend-icon=""
                    chips
                    :rules="[v => !v || v.size < 2000000 || '이미지 크기는 최대 2MB 입니다.' ]"
                    @change="previewImage"
                  >
                  </v-file-input>
                </v-col>

                <!-- 장르란 -->
                <v-col cols="12">
                  <v-autocomplete
                    v-model="profileUpdateData.basicData.genres"
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
                        @click:close="remove(profileUpdateData.basicData.genres, data.item)"
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
              </v-row>

              <!-- Submit 버튼 -->
              <v-card-actions class="d-flex justify-content-end">
                <v-btn
                  :disabled="!valid"
                  class="btn btn-green"
                  @click="clickUpdate"
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
import Swal from 'sweetalert2'
const swal = Swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success mr-2',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: false
})

export default {
  name: 'ProfileUpdate',
  data () {
    return {
      profileImg: null,
      previewImg: null,
      profileUpdateData: {
        basicData: {
          nickName: null,
          description: null,
          genres: null,
        },
        profileImgFormData: null
      },
      valid: true,
      lazy: false,
      searchGenre: null,
      clicked: false
    }
  },
  computed: {
    ...mapState(['myaccount', 'genres']),
  },
  methods: {
    ...mapActions('profileStore', ['updateProfile']),
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
    clickUpdate() {
      this.clicked = true
      if (this.profileImg) {
        this.profileUpdateData.profileImgFormData = new FormData()
        this.profileUpdateData.profileImgFormData.append('profileImg', this.profileImg)
      }
      this.updateProfile(this.profileUpdateData)
    },
    previewImage() {
      if (this.profileImg) {
        let reader = new FileReader();
        reader.onload = e => {
          this.previewImg = e.target.result
        };
        reader.readAsDataURL(this.profileImg);
      }
    },
  },
  created() {
  },
  mounted() {
    this.profileUpdateData.basicData.nickName = this.myaccount.nickName;
    this.profileUpdateData.basicData.description = this.myaccount.description;
    const tempGenres = []
    this.myaccount.likeGenres.forEach(genre => {
      tempGenres.push(genre.id)
    });
    this.profileUpdateData.basicData.genres = tempGenres;
  },
  beforeRouteLeave(to, from, next) {
    if (!this.clicked) {
      if (this.profileUpdateData.basicData.nickName !== this.myaccount.nickName
          || this.profileUpdateData.basicData.description !== this.myaccount.description
          || this.profileUpdateData.basicData.genres.length !== this.myaccount.likeGenres.length
          || this.profileImg
         ) {
            swal.fire({
                html: "<p>수정 중인 프로필이 있습니다.</p><p>정말 넘어가시겠습니까?</p>",
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
.profile-img{
  max-height: 300px;
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

  .profile-image {
    max-height: 200px; 
  }
</style>