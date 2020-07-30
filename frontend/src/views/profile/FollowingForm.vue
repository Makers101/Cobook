<template>
  <v-app>
    <v-dialog v-model="show" max-width="500px">
    <v-card>
      <v-card-text class="">
        <div class="display-1 pt-3">팔로잉</div>
        <p class="mb-0 text--primary">{{ profile.nickName }}님이 팔로잉 하는 유저입니다.</p>
      </v-card-text>
      <v-divider class="m-0"></v-divider>
      
      <v-virtual-scroll :itemHeight="50" height="300" :items="followingList">
        <template v-slot="{item}">
          <v-list-item>
            <v-list-item-avatar>
              <v-avatar>
                <img v-if="item.profileImg" :src="`${item.profileImg}`">
                <img v-else src="http://placehold.jp/200x200.png">
              </v-avatar>
            </v-list-item-avatar>
            <v-list-item-content class="text-left">
              <v-list-item-title><span @click="selectUser(item.toUserId)" class="nicknameSection">{{ item.nickname }}</span></v-list-item-title>
            </v-list-item-content>
            <v-list-item-action>
              <div v-if="item.toUserId !== myaccount.id">
                <v-btn 
                  small dark color="grey lighten-1" 
                  v-show="item.isFollow"
                  @click="clickedFollow(item)">팔로잉</v-btn>
                <v-btn 
                  small outlined color="grey lighten-1" 
                  v-show="!item.isFollow"
                  @click="clickedFollow(item)">팔로우</v-btn>
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
import { mapState, mapActions } from 'vuex'
import router from '@/router'
export default {
  data() {
    return {
    }
  },
  props: {
     value: Boolean,
     profile: Object,
     followingList: Array,
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
    ...mapState('profileStore', ['followingList']), 
    ...mapState(['myaccount'])
  },
  methods: {
    ...mapActions(['createNoti']),
    ...mapActions('profileStore', ['clickFollowModal']),
    selectUser(userId) {
      router.push({ name: 'ProfileFeed', params: { userId: userId }})
    }, 
    clickedFollow(item) {
      let notiData = new Object()
      notiData = {
        from: this.myaccount.id,
        to: this.profile.id,
        dataId: this.profile.id,
        type: "follow"
      }
      this.createNoti(notiData)
      this.clickFollowModal(item.toUserId)
      item.isFollow = !item.isFollow
    }
  },
  created() {
  }
}
</script>
<style scoped>
.nicknameSection {
  cursor: pointer;
}
</style>
