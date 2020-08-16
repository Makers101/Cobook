<template>
  <div class="background">
    <div class="container py-5 mt-5 bg-light-ivory formatting">
      <h5 class="mt-3">회원가입 인증메일이 발송되었습니다.</h5>
      <h5>이메일을 확인해주세요.</h5>
      <h5 class="bold">{{ email }}</h5>
      <div class="button-container d-flex justify-content-center mt-5 mx-auto">
        <button class="bg-green green-btn btn " type="button">인증 메일 재발송하기</button>
      </div>
      <div class="button-container d-flex justify-content-center mt-3 mx-auto">
        <button class="bg-beige beige-btn btn" type="button"  @click="goToSite()">메일함가기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignupEmail',
  data() {
    return {
      email: this.$route.params.signupEmail.email
    }
  },
  methods: {
    goToSite() {
      let idx = this.email.indexOf('@')
      let site = this.email.slice(idx,)
      window.open('https://' + site, '_blank');
    }
  }
}
</script>

<style scoped>
.container {
  width: 30%;
  border-radius: 25px;
  margin-top: 20vh !important;
}

.button-container {
  width: 70%;
}

button {
  width: 100%;
  color: #F8F8F8;
}

.green-btn:hover {
  background-color: #3c755a;
  /* color: #D6CBBD; */
  color: #F8F8F8;
}

.beige-btn:hover {
  background-color: #907a62;
  color: #F8F8F8;
}

.bold {
  font-weight: 600;
}

.background {
  background-repeat: repeat;
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