<template>
  <div>
    <div class="container py-5 mt-5 bg-light-ivory">
      <h5 class="mt-3">회원가입 인증메일이 발송되었습니다.</h5>
      <h5>이메일을 확인해주세요.</h5>
      <h5 class="bold">co-book@cobook.com</h5>
      <div class="button-container d-flex justify-content-center mt-5 mx-auto">
        <button class="bg-green green-btn btn " type="button">인증 메일 재발송하기</button>
      </div>
      <div class="button-container d-flex justify-content-center mt-3 mx-auto">
        <button class="bg-beige beige-btn btn" type="button">메일함가기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {

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
</style>