<template>
  <v-app>
    <v-card>
      <template v-slot:progress>
        <v-progress-linear
          absolute
          color="green lighten-3"
          height="4"
          indeterminate
        ></v-progress-linear>
        <v-img
          height="125"
          src="https://cdn.vuetifyjs.com/images/cards/dark-beach.jpg"
        >
          <v-row>
            <v-row
              class="pa-4"
              align="center"
              justify="center"
            >
              <v-col class="text-center">
                <h3 class="headline text-light">프로필 수정하기!</h3>
                <span class="grey--text text--lighten-1">본인을 소개해주세요!</span>
              </v-col>
            </v-row>
          </v-row>
        </v-img>
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
                  :rules="[v => !!v || '필수항목입니다.']"
                  label="선호 장르"
                  item-text="genre"
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
                      @click:close="remove(teamUpdateData.genres, data.item)"
                    >
                      {{ data.item.genre }}
                    </v-chip>
                  </template>
                  <template v-slot:item="data">
                    <template v-if="typeof data.item !== 'object'">
                      <v-list-item-content v-text="data.item"></v-list-item-content>
                    </template>
                    <template v-else>
                      <v-list-item-content>
                        <v-list-item-title v-text="data.item.genre"></v-list-item-title>
                      </v-list-item-content>
                    </template>
                  </template>
                </v-autocomplete>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </template>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: 'ProfileUpdate',
  data () {
    return {
      accountUpdateData: {
        nickName: null,
        description: null,
        genres: null,
      },
      lazy: false,
      searchGenre: null,
    }
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
  },
  created() {
    
  }
}
</script>

<style scoped>
.profile-image{
  border-radius: 50%;
  width: 5vw;
  height: 5vw;
}

</style>                                                                                                                                                                                                                                                