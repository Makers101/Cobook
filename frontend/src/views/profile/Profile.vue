<template>
<div>
  <div class="row mt-3">
    <div class="offset-md-2 col-md-8 col-12">
      <div class="d-flex justify-content-start">
        <div class="d-flex justify-content-center align-items-center ml-5">
          <img v-if="profile.profileImg" class="profile-image bg-light-ivory" :src="`${profile.profileImg}`">
          <img v-else class="profile-image bg-light-ivory" src="http://placehold.jp/200x200.png">
        </div>
        <div class="part align-self-end">
          <div class="text-left ml-3">
            <div class="d-flex justify-content-between mt-auto">
              <h3 class="color-beige font-weight-bold">{{ profile.nickName }}</h3>
              <span v-if="myaccount.id !== profile.id">
                <button v-if="checkFollow(profile)" class="btn px-4 bg-light-black" @click="clickedFollow(profile)">팔로잉</button>
                <button v-else class="btn bg-green px-4" @click="clickedFollow(profile)">팔로우</button>
              </span>
              
            </div>
            <p class="">{{ profile.description }}</p>
            <p class="color-light-black font-weight-bold">
              <span>{{ profile.followerList.length }}</span> <span @click.stop="showFollowerForm=true" class="mr-3"> FOLLOWER</span> 
              <span>{{ profile.followingList.length }}</span><span @click.stop="showFollowingForm=true"> FOLLOWING</span>
            </p>        
          </div>
        </div>
      </div>
      <FollowerForm 
        v-if="showFollowerForm" 
        v-model="showFollowerForm" 
        :profile="this.profile" 
        id="followerModal"
      />
      <FollowingForm 
      v-if="showFollowingForm" 
      v-model="showFollowingForm" 
      :profile="this.profile"
      id="followingModal"/>

      <!-- routers -->
      <div class="d-flex justify-content-between mt-4">
        <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileFeed', params: { userId:this.$route.params.userId }}"> Feed</router-link>
        <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileClub', params: { userId:this.$route.params.userId }}"> Club</router-link>
        <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileBookmark', params: { userId:this.$route.params.userId }}"> Book Mark</router-link>
        <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileOverview', params: { userId:this.$route.params.userId }}"> Overview</router-link>
      </div>
      <router-view/>
    </div>
  </div>
</div>
  
</template>

<script>
import { mapState, mapActions } from 'vuex'
import FollowerForm from './FollowerForm'
import FollowingForm from './FollowingForm'
export default {
  name: 'Profile',
  computed: {
    ...mapState('profileStore',['profile', 'followingList']),
    ...mapState(['myaccount'])
  },
  data() {
    return {
      showFollowerForm: false,
      showFollowingForm: false,
      currentlyFollowing: null,
    }
  },
  components: {
    FollowerForm,
    FollowingForm
  },

  methods: {
    ...mapActions('profileStore', ['findProfile', 'clickFollow', 'fetchFollowerList', 'fetchFollowingList']),
    clickedFollow(profile) {
      this.clickFollow(profile.id)
      var temp = {
        isFollow: true,
        nickname: this.myaccount.nickName,
        profileImg: this.myaccount.profileImg,
        toUserId: this.myaccount.id
      }
      var flag = false
      for (let [index, key] of profile.followerList.entries()) {
        // 일치하는 id가 있다면 followerList에서 제거
        if (key.toUserId === this.myaccount.id){
          flag = true
          profile.followerList.splice(index, 1);
          break;
        }
      }
      if (flag === false){
        profile.followerList.push(temp)
      }
    },
    checkFollow(profile) {
      var flag = false
      for (let key of profile.followerList.entries()) {
        // 일치하는 id가 있다면 followerList에서 제거
        if (key[1].toUserId === this.myaccount.id){
          flag = true
          return true
        }
      }
      if (flag === false){
        return false
      }

    },
    closeDialog: function() {
      this.showFollowerDialog = false
      this.showFollowingDIalog = false
    }
  },
  created() {
    console.log(this.$route.params.userId)
    this.findProfile(this.$route.params.userId)
  },

  mounted() {
    this.$router.push({ name: 'ProfileFeed',  params: { userId:this.$route.params.userId }})
  },

  beforeRouteUpdate (to, from, next) {
    this.findProfile(to.params.userId)
    this.showFollowerForm = false
    this.showFollowingForm = false

    next();
  },
}
</script>

<style scoped>
.profile-image{
  border-radius: 50%;
  width: 10vw;
  height: 10vw;
}

.part {
  width: 52vw;
}

.nav-link {
  width: 23%;
  font-size: 0.8rem;
  color: #F8F8F8;
  margin-top: 5px;
  margin-bottom: 5px;
}

.router-link-active {
  background-color: #88A498 !important;
}

.following {
  background-color: #F9F9F9;
}


</style>