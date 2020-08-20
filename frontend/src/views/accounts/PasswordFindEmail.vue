<template>
  <div class="background">
    <div class="container py-5 mt-5 bg-light-ivory formatting">
      <h5 class="mt-3">비밀번호 변경 메일이 발송되었습니다.</h5>
      <h5>이메일을 확인해주세요.</h5>
      <h5 class="bold">{{ passwordFindData.data.email }}</h5>
      <div class="button-container d-flex justify-content-center mt-5 mx-auto">
        <button class="bg-green green-btn btn " type="button" @click="resendPasswordEmail(passwordFindData, type)">메일 재발송하기</button>
      </div>
      <div class="button-container d-flex justify-content-center mt-3 mx-auto">
        <button class="bg-beige beige-btn btn" type="button" @click="goToSite()">메일함가기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'PasswordFindEmail',
  data() {
    return {
      // email: this.$route.params.email,
      passwordFindData: {
        data: this.$route.params.passwordFindData,
        type: "resetPassword"
      }
    }
  },
  methods: {
    ...mapActions("accountStore", ["resendPasswordEmail"]),
    goToSite() {
      let idx = this.passwordFindData.data.email.indexOf('@')
      let site = this.passwordFindData.data.email.slice(idx,)
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