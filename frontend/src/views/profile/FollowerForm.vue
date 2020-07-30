<template>
  <v-app id="followers">
    <v-dialog v-model="show" max-width="500px">
    <v-card>
      <v-card-text class="">
        <div class="display-1 pt-3">팔로우</div>
        <p class="mb-0 text--primary">{{ profile.nickName }}님을 팔로우 하는 유저입니다.</p>
      </v-card-text>
      <v-divider class="m-0"></v-divider>
      <v-virtual-scroll :itemHeight="50" height="300" :items="followerList">
        <template v-slot="{item}">
          <v-list-item>
            <v-list-item-avatar>
              <v-avatar>
                <img v-if="item.profileImg" :src="`${item.profileImg}`">
                <img v-else src="http://placehold.jp/200x200.png">
              </v-avatar>
            </v-list-item-avatar>
            <v-list-item-content class="text-left">
              <v-list-item-title class="nicknameSection" @click="selectUser(item.toUserId)">{{ item.nickname }}</v-list-item-title>
            </v-list-item-content>
            <v-list-item-action  >
              <div v-if="item.toUserId !== myaccount.id">
                <v-btn small dark color="grey lighten-1" v-if="item.isFollow">팔로잉</v-btn>
                <v-btn small outlined color="grey lighten-1" v-else>팔로우</v-btn>
              </div>
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
import { mapState } from 'vuex'
import router from '@/router'
export default {
  data() {
    return {
    }
  },
  props: {
     value: Boolean,
     profile: Object,
     followerList: Array,
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
    // ...mapState('profileStore', ['followerList']), 
    ...mapState(['myaccount'])
  },
  methods: {
    // ...mapActions('profileStore', ['fetchFollowerList']),
    selectUser(userId) {
      router.push({ name: 'ProfileFeed', params: { userId: userId }})
    }
  },
  created() {
    // this.fetchFollowerList(this.$route.params.userId)
  }
}
</script>
<style scoped>
.nicknameSection {
  cursor: pointer;
}
</style>