<template>
  <div>
    <div class="container p-3">
      <h3>회원가입</h3>

      <div class="input-with-label">
        <input 
          v-model="nickName"
          class="inputs"
          id="nickName"
          placeholder="닉네임" 
          type="text" 
          autocapitalize="none"
          autocorrect="none"
          style="text-transform:lowercase"
        />
        <label for="nickName"></label>
        <!-- <div class="error-text ml-5" v-if="error.nickName">{{error.nickName}}</div> -->
      </div>

      <div class="input-with-label">
        <input 
          v-model="email" 
          class="inputs"
          id="email" 
          placeholder="이메일" 
          type="text" 
          autocapitalize="none"
          autocorrect="none"
          style="text-transform:lowercase"
          />
        <label for="email"></label>
        <!-- <div class="error-text ml-5" v-if="error.email">{{error.email}}</div> -->
      </div>

      <div class="input-with-label">
        <input 
          v-model="password" 
          class="inputs"
          id="password" 
          :type="passwordType"
          placeholder="비밀번호를 입력하세요." 
        />
        <label for="password"></label>
        <!-- <div class="error-text ml-5" v-if="error.password">{{error.password}}</div> -->
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시 입력해주세요."
          class="inputs"
        />
        <label for="password-confirm"></label>
        <!-- <div class="error-text ml-5" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div> -->
      </div>

      <div class="buttons mt-3">
        <button class="btn signup-button">가입하기</button>
      </div>

      <hr class="divide">

      <div class="buttons d-flex justify-content-center">
        <button class="btn kakao d-flex align-items-center justify-content-center">
          <i class="xi-2x xi-kakaotalk text-dark"></i>
          <span class="justify-content-center">카카오 아이디 로그인</span>
        </button>
      </div>
      <div class="buttons mt-2 d-flex justify-content-center">
        <button class="btn google d-flex align-items-center justify-content-center row">
          <!-- <i class="fab fa-google-plus-g"></i> -->
          <img class="google-logo" src="@/assets/google.png" width="32px" height="32px" >
          <span class="justify-content-center col-">구글 아이디 로그인</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Signup',

  data: () => {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      nickName: "",
      error: {
        email: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
    };
  },
   created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  methods: {
  }
}
</script>

<style scoped>
.container {
  width: 30%;
  background-color: #F8F8F8;
  border-radius: 25px;
}

.inputs {
  border-style: none;
  border-bottom: 1px solid #88A498;
  background-color: transparent;
  width: 100%;
  padding: 10px;
  padding-left: 20px;
  margin-top: 20px;
}

.signup-button{
  background-color: #88A498;
  color: #F8F8F8;
  width: 70%;
  /* font-size: 10px; */
}

.divide {
  width: 10%;
  border-top: 1px solid #88A498;
}


.kakao {
  background-color: #ffe812;
  border-radius: 5px;
  width: 70%;
}

.google {
  background-color:  #FFFFFF;
  border-radius: 5px;
  width: 70%;
}


</style>