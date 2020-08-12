<template>
  <div class="mt-2">
    <div class="d-flex justify-content-center w-100 mt-5">
      <div class="d-flex justify-content-between user">
        <div class="d-flex">
          <!-- 프로필 이미지 -->
          <img class="ml-3 mr-3" src="http://placehold.jp/200x200.png?text=">
          <!-- 프로필 닉네임 -->
          <p class="user-nickName">닉네임</p>
        </div>
        <!-- 팔로잉 여부 -->
        <div class="d-flex align-items-center mr-3">
          <button class="btn btn-green">팔로우</button>
          <!-- <button v-if="checkFollow(profile)" class="btn px-4 btn-following" @click="clickedFollow(profile, 'unfollow')">언팔로우</button>
          <button v-else class="btn btn-follow px-4" @click="clickedFollow(profile, 'follow')">팔로우</button> -->
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'SearchUser',
  computed: {
    ...mapState('searchStore',['users']),
  },
  methods: {
    ...mapActions('searchStore', ['fetchUsers']),
    clickedFollow(profile, type) {
      if (type === 'unfollow') {
        if (confirm('팔로우를 취소하시겠습니까?') === false) {
          return false
        }
      }
    }
  },
  created() {
    this.fetchUsers('ssaf')
  }
  
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
}

img {
  border: 1px solid rgb(0, 0, 0, 0.2);
  border-radius: 50%;
  width: 60px;
}

.user-nickName {
  line-height: 60px;
}

</style>