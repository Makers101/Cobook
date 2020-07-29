<template>
  <v-app>
    <v-dialog v-model="show" max-width="500px">
    <v-card>
      <v-card-text class="">
        <div class="display-1 pt-3">팔로우</div>
        <p class="mb-0 text--primary">{{ profile.nickName }}님이 팔로우 하는 유저입니다.</p>
      </v-card-text>
      <v-divider class="m-0"></v-divider>
      <v-virtual-scroll :itemHeight="50" height="300" :items="followers">
        <template v-slot="{item}">
          <v-list-item>
            <v-list-item-avatar>
              <v-avatar>
                <img v-if="item.profileImg" :src="`${item.profileImg}`">
                <img v-else src="http://placehold.jp/200x200.png">
              </v-avatar>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>{{ item.nickName }}</v-list-item-title>
            </v-list-item-content>
            <v-list-item-action>
              <v-btn small dark color="grey lighten-1" v-if="item.isFollow">팔로잉</v-btn>
              <v-btn small outlined color="grey lighten-1" v-else>팔로우</v-btn>
            </v-list-item-action>
          </v-list-item>
        </template>
      </v-virtual-scroll>
      <v-divider class="m-0"></v-divider>
      <v-card-actions>
        <v-col class="text-right">
          <v-btn color="brown lighten-3" dark @click.stop="show=false" class="text-right"> Close</v-btn>
        </v-col>
      </v-card-actions>
    </v-card>
  </v-dialog>
  </v-app>
</template>


<script>
let followers = [
  {
    id: 1,
    nickName: 'Hoon',
    profileImg: 'http://i3a111.p.ssafy.io:8080/api/clubs/images/17',
    isFollow: true
  },
  {
    id: 2,
    nickName: 'Jin',
    profileImg: '',
    isFollow: false
  },
  {
    id: 3,
    nickName: 'Soom',
    profileImg: 'http://i3a111.p.ssafy.io:8080/api/clubs/images/16',
    isFollow: false
  },
  {
    id: 4,
    nickName: 'Sun',
    profileImg: 'http://i3a111.p.ssafy.io:8080/api/clubs/images/8',
    isFollow: false
  },
  {
    id: 5,
    nickName: 'Lin',
    profileImg: '',
    isFollow: false
  },
  {
    id: 6,
    nickName: 'Lin',
    profileImg: '',
    isFollow: false
  },
  {
    id: 7,
    nickName: 'Lin',
    profileImg: '',
    isFollow: false
  },
  {
    id: 8,
    nickName: 'Lin',
    profileImg: '',
    isFollow: false
  },
  {
    id: 9,
    nickName: 'Lin',
    profileImg: '',
    isFollow: false
  },
]
export default {
  data() {
    return {
      'followers': followers
    }
  },
  props: {
     value: Boolean,
     profile: Object,
  },
  computed: {
    show: {
      get () {
        return this.value
      },
      set (value) {
         this.$emit('input', value)
      }
    },
    
  }
}
</script>
