<template>
  <div class="custom-container mt-3">
    
    <div class="row">
      <div class="col-3 d-flex justify-content-center align-items-center">
        <img
          class="profile-image bg-light-ivory"
          :src="profile.profileImg"
          :alt="profile.nickName"
          v-if="profile.profileImg">
        <img
          class="profile-image bg-light-ivory"
          :src="'http://placehold.jp/200x200.png?text=' + profile.nickName"
          :alt="profile.nickName"
          v-else>
      </div>
      <div class="col-9 part text-left d-flex flex-column justify-content-between">
        <div class="d-flex flex-column justify-content-start">
          <div class="d-flex justify-content-between">
            <h3 class="color-beige font-weight-bold">{{ profile.nickName }}</h3>
            <span v-if="myaccount.id !== profile.id">
              <button v-if="checkFollow(profile)" class="btn px-4 btn-following" @click="clickedFollow(profile, 'unfollow')">언팔로우</button>
              <button v-else class="btn btn-follow px-4" @click="clickedFollow(profile, 'follow')">팔로우</button>
            </span>
          </div>
          <p class="profile-description">{{ profile.description }}</p>
        </div>
        
        <div class="d-flex flex-column justify-content-end">
          <p class="color-light-black font-weight-bold mb-2">
            <span>{{ profile.followerList.length }}</span> <span @click.stop="showFollowerForm=true" class="mr-3 pointer"> FOLLOWER</span> 
            <span>{{ profile.followingList.length }}</span><span @click.stop="showFollowingForm=true" class="pointer"> FOLLOWING</span>
          </p> 
          <div class="d-flex justify-content-between">
            <div>
              <button class="btn btn-genre mr-2" disabled v-for="genre in profile.likeGenres" :key="genre.id">#{{ genre.name }}</button>
            </div>
            <button class="btn btn-secondary" v-if="myaccount.id === profile.id" @click="clickUpdate(profile.id)">프로필 수정</button>   
          </div>
        </div>
      </div>
    </div>

    <FollowerForm 
      v-if="showFollowerForm" 
      v-model="showFollowerForm" 
      :followerList = "this.followerList"
      :profile="this.profile" 
      id="followerModal"
    />
    <FollowingForm 
    v-if="showFollowingForm" 
    v-model="showFollowingForm" 
    :followingList = "this.followingList"
    :profile="this.profile"
    id="followingModal"/>

    <!-- routers -->
    <div class="d-flex justify-content-between mt-4">
      <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileFeed', params: { userId:this.$route.params.userId }}">리뷰</router-link>
      <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileClub', params: { userId:this.$route.params.userId }}">클럽 · 이벤트</router-link>
      <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileBookmark', params: { userId:this.$route.params.userId }}">북마크</router-link>
      <router-link class="nav-link bg-beige text-left" :to="{name: 'ProfileOverview', params: { userId:this.$route.params.userId }}">일정 · 통계</router-link>
    </div>
    <transition name="slide" mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import FollowerForm from './FollowerForm'
import FollowingForm from './FollowingForm'
export default {
  name: 'Profile',
  computed: {
    ...mapState('profileStore',['profile', 'followingList', 'followerList']),
    ...mapState(['myaccount'])
  },
  data() {
    return {
      showFollowerForm: false,
      showFollowingForm: false,
      currentlyFollowing: null,
      transitionName: ''
    }
  },
  components: {
    FollowerForm,
    FollowingForm
  },
  // watch: {
  //   $route(to, from) {
  //     if (to.meta.index > from.meta.index){
  //       this.trnasitionName='slide-left';
  //     } else {
  //       this.transitionName='slide-right';
  //     }
  //   }
  // },
  methods: {
    ...mapActions(['createNoti']),
    ...mapActions('profileStore', ['findProfile', 'clickFollow', 'fetchFollowerList', 'fetchFollowingList', 'findOverview', 'fetchClubEvents', 'fetchOnedayEvents']),
    clickedFollow(profile, type) {
      if (type === 'unfollow') {
        if (confirm('팔로우를 취소하시겠습니까?') === false) {
          return false
        }
      }

      let notiData = new Object()
      notiData = {
        to: this.profile.id,
        clubId: 0,
        isRead: false,
        type: "follow"
      }
      this.createNoti(notiData)
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
    },
    clickUpdate(userId) {
      this.$router.push({ name: 'ProfileUpdate', params: { userId: userId }})
    }
  },
  created() {
    this.findProfile(this.$route.params.userId)
    this.fetchFollowerList(this.$route.params.userId) 
    this.fetchFollowingList(this.$route.params.userId)
    this.fetchClubEvents(this.$route.params.userId)
    this.fetchOnedayEvents(this.$route.params.userId)
    this.findOverview(this.$route.params.userId)
  },

  mounted() {
    this.$router.push({ name: 'ProfileFeed',  params: { userId:this.$route.params.userId }})
  },

  beforeRouteUpdate (to, from, next) {
    this.showFollowerForm = false
    this.showFollowingForm = false
    this.findProfile(to.params.userId)
    next();
    this.fetchFollowerList(to.params.userId)
    this.fetchFollowingList(to.params.userId)
  },
}
</script>

<style scoped>
.profile-image{
  border-radius: 50%;
  width: 12vw;
  height: 12vw;
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

.profile-description {
  white-space: pre-line;
}

.btn-genre {
  background-color: #88A498;
  color: #F8F8F8;
  opacity: 1;
}

.btn-following {
  background-color: #88A498;
  color: #F8F8F8;
}

.btn-follow {
  border-color: #88A498 !important;
  color: #88A498 !important;
}

.slide-leave-active {
        transition: opacity 0.3s ease;
        opacity: 0;
        animation: slide-out 0.3s ease-out forwards;
    }

    .slide-leave {
        opacity: 1;
        transform: translateX(0);
    }

    .slide-enter-active {
        animation: slide-in 0.3s ease-out forwards;
    }

    @keyframes slide-out {
        0% {
            transform: translateY(0);
        }
        100% {
            transform: translateY(-30px);
        }
    }

    @keyframes slide-in {
        0% {
            transform: translateY(-30px);
        }
        100% {
            transform: translateY(0);
        }
    }

</style>