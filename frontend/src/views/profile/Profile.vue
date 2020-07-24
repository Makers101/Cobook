<template>
<div>
  <div class="row mt-3">
    <div class="offset-md-2 col-md-8 col-12">
      <div class="d-flex justify-content-start">
        <div class="profile-image bg-light-ivory d-flex justify-content-center align-items-center ml-5">
          Profile
        </div>
        <div class="part align-self-end">
          <div class="text-left ml-3">
            <div class="d-flex justify-content-between mt-auto">
              <h3 class="color-beige font-weight-bold">User Name</h3>
              <button class="btn bg-green px-4">팔로우</button>
            </div>
            <p class="color-light-black font-weight-bold"><span>999 FOLLOWER</span> <span>999 FOLLOWING</span></p>        
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
export default {
  name: 'Profile',
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