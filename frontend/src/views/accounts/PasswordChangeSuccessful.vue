<template>
  <div class="background">
    <div class="container py-5 mt-5 bg-light-ivory formatting">
      <h3>비밀번호 변경 완료</h3>
      <h5 class="mt-3">비밀번호 변경이 완료되었습니다.<br>
      로그인해주세요.</h5>
      <div class="buttons mt-5">
        <button class="btn login-button" @click="clickLogin" >로그인하기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PasswordChangeSuccessful',
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

h3 {
  color: #88a498;
  font-weight: 800;
}

h5 { 
  color: black;
}

.login-button{
  background-color: #88A498;
  color: #F8F8F8;
  width: 70%;
  /* font-size: 10px; */
}

.background {
  background-image: url("https://user-images.githubusercontent.com/25967949/90751489-27ce4480-e311-11ea-93aa-2ab9d1f41b4e.png");
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background-repeat: repeat;
}


.formatting {
  margin-top: 20vh !important;
  opacity: 0.9;
}
</style>