<template>
  <div class="background">
    <div class="container py-5 mt-5 bg-light-ivory formatting">
      <h5 class="mt-3">회원가입이 완료되었습니다.<br>
      로그인해주세요.</h5>
      <div class="buttons mt-5">
        <button class="btn login-button" @click="clickLogin" >로그인하기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignupSuccessful',
  methods: {
    clickLogin() {
      this.$router.push({ name: 'Login' })
    },
  }

}
</script>

<style scoped>
.container {
  width: 30%;
  border-radius: 25px;
}

h5 { 
  color: #88A498;
}

.login-button{
  background-color: #88A498;
  color: #F8F8F8;
  width: 70%;
  /* font-size: 10px; */
}

.background::after {
  content:"";
  background-image: url('https://user-images.githubusercontent.com/57381062/88908481-de03b880-d294-11ea-8567-9e74079c2a7b.jpg');
  opacity: 0.5;
  top: 0;
  left:0;
  bottom:0;
  right: 0;
  position: absolute;
  z-index: -1;
  width: 100vw;
  height: 100vh;
  filter: brightness(0.7);
}

.formatting {
  opacity: 0.9;
}

</style>