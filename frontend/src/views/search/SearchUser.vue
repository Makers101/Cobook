<template>
  <div class="my-3">
    <div v-if="users.length">
      <div v-for="user in users" :key="user.id" class="d-flex justify-content-center w-100 mt-3">
        <div class="d-flex justify-content-between user">
          <div class="d-flex profile-image">
            <!-- 프로필 이미지 -->
            <img
              v-if="!user.profileImg"
              class="img-fluid feed-profile-img ml-3 mr-3"
              style="max-width: 50%; border-radius: 50%;" 
              src="http://bit.do/anonymouseuser" 
              alt="유저 프로필 사진">
            <img 
              v-else
              class="img-fluid feed-profile-img ml-3 mr-3"
              style="max-width: 50%; border-radius: 50%;" 
              :src="user.profileImg" alt="작성자 프로필 사진">
            <!-- 프로필 닉네임 -->
            <p class="user-nickName pointer" @click="selectUser(user.id)"><strong>{{ user.nickName }}</strong></p>
          </div>
          <!-- 팔로잉 여부 -->
          <div class="d-flex align-items-center mr-3">
            <button v-if="user.isFollow" class="btn btn-following" @click="clickedFollow(user, 'unfollow')">언팔로잉</button>
            <button v-else class="btn btn-follow" @click="clickedFollow(user, 'follow')">팔로우</button>
            <!-- <button v-if="checkFollow(profile)" class="btn px-4 btn-following" @click="clickedFollow(profile, 'unfollow')">언팔로우</button>
            <button v-else class="btn btn-follow px-4" @click="clickedFollow(profile, 'follow')">팔로우</button> -->
          </div>
        </div>
      </div>
    </div>
    <div class="no-result" v-else>
      <img src="https://user-images.githubusercontent.com/57381062/88909174-c11bb500-d295-11ea-81b6-90c7bc3642ab.png" width="150px" class="mt-3">
      <h3 class="mt-3">검색된 키워드의 유저가 없습니다. </h3>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'SearchUser',
  computed: {
    ...mapState('searchStore', ['users']),
  },
  methods: {
    ...mapActions('searchStore', ['fetchUsers']),
    ...mapActions('profileStore', ['clickFollow']),
    clickedFollow(user, type) {
      if (type === 'unfollow') {
        if (confirm('팔로우를 취소하시겠습니까?') === false) {
          return false
        } 
      }
      this.clickFollow(user.id)

      if (type === 'unfollow') {
        user.isFollow = false
      } else {
        user.isFollow = true
      }
    },
    selectUser(userId) {
      this.$router.push({ name: 'Profile', params: { userId: userId }})
    },
  },
  created() {
    this.fetchUsers(this.$route.params.content)
  },
  
}
</script>

<style scoped>
p {
  margin: 0;
}

.user {
  border: 1px solid rgb(0, 0, 0, 0.2);
  border-radius: 10px;
  padding-top: 16px;
  padding-bottom: 16px;
  width: 65%;
  box-shadow: 5px 5px 10px 1px rgb(0, 0, 0, 0.2);
}

.profile-image img {
  border: 1px solid rgb(0, 0, 0, 0.2);
  border-radius: 50%;
  width: 60px;
}

.user-nickName {
  line-height: 60px;
}

.btn-following {
  background-color: #88A498;
  color: #F8F8F8;
}

.btn-follow {
  border-color: #88A498 !important;
  color: #88A498 !important;
}

.no-result {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: -150px 0px 0px -200px;
}
</style>