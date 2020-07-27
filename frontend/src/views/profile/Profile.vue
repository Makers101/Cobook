<template>
<div>
  <div class="row mt-3">
    <div class="offset-md-2 col-md-8 col-12">
      <div class="d-flex justify-content-start">
        <div class="d-flex justify-content-center align-items-center ml-5">
          <img class="profile-image bg-light-ivory" :src="`${user.profileImg}`">
        </div>
        <div class="part align-self-end">
          <div class="text-left ml-3">
            <div class="d-flex justify-content-between mt-auto">
              <h3 class="color-beige font-weight-bold">{{ user.userName }}</h3>
              <button class="btn bg-green px-4">팔로우</button>
            </div>
            <p class="">{{ user.description }}</p>
            <p class="color-light-black font-weight-bold"><span>{{ user.follower.length }} FOLLOWER</span> <span>{{ user.following.length }} FOLLOWING</span></p>        
          </div>
        </div>
      </div>

      <!-- routers -->
      <div class="d-flex justify-content-between mt-4">
        <router-link class="nav-link bg-beige text-left" to="/profile/feed"> Feed</router-link>
        <router-link class="nav-link bg-beige text-left" to="/profile/club"> Club</router-link>
        <router-link class="nav-link bg-beige text-left" to="/profile/bookmark"> Book Mark</router-link>
        <router-link class="nav-link bg-beige text-left" to="/profile/overview"> Overview</router-link>
      </div>
      <router-view/>
    </div>
  </div>
</div>
  
</template>

<script>
let user = {
    userName: 'userName1',
    description: '책을 좋아하는 사람입니다.특히 SF에 관심이 많습니다. 좋은 사람들과 club에서 함께하기 위해 참여했습니다.',
    profileImg: 'http://placehold.jp/200x200.png',
    follower: [
      {
        userId: 1,
        userName: '만든이'
      },
      {
        userId: 2,
        userName:'#101'
      }
    ],
    following: [
      {
        userId: 1,
        userName: '만든이'
      },
      {
        userId: 2,
        userName:'#101'
      }
    ]
}
export default {
  name: 'Profile',
  data() {
    return {
      user: user,
    }
  }
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
  background-color: #88A498;
}

</style>