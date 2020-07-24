<template>
  <div>
    <div class="container p-3 mt-5 bg-light-ivory">
      <h3>로그인</h3>
    
      <div class="input-with-label">
        <input 
          v-model="email" 
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          class="inputs"
          id="email" 
          placeholder="이메일" 
          type="text" 
          autocapitalize="none"
          autocorrect="none"
          style="text-transform:lowercase"
          required
          />
        <label for="email"></label>
        <div class="error-text ml-3" v-if="error.email">{{error.email}}</div>
      </div>
      <div class="input-with-label">
        <input 
          v-model="password" 
          
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          class="inputs"
          id="password" 
          type="password"
          placeholder="비밀번호를 입력하세요." 
          required
        />
      <label for="password"></label>
      <div class="error-text ml-3" v-if="error.password">{{error.password}}</div>
    </div>
    <p class="my-3"><span class="items" @click="clickSignup">회원가입 </span>ㆍ<span class="items" @click="clickPasswordFind">비밀번호 찾기</span></p>
    <div class="buttons mt-3">
      <button class="btn login-button" :class="{disabled: !isSubmit}" @click="clickLogin" >로그인하기</button>
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
  name: 'Login',
  data() {
    return {
      email: "",
      password: "",
      error: {
        email: false,
        password: false,
      },
      isSubmit: false,
    };
  },
  created() {
    this.component = this;
  },
  watch: {
    email() {
      this.checkEmailForm();
    },
    password() {
      this.checkPasswordForm();
    },
  },
  methods: {
    checkEmailForm() {
      if ( this.email.length > 0 && !this.validEmail(this.email) ) {
        this.error.email = "올바른 이메일 형식이 아니에요"   
      }
      else this.error.email = false;
    },
    validEmail(email) {
      // eslint-disable-next-line
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    checkPasswordForm() {
      if (this.password.length > 0 && this.password.length < 8) {
          this.error.password = "비밀번호가 너무 짧아요"
        } else if ( this.password.length >= 8 && !this.validPassword(this.password) ) {
          this.error.password = "영문, 숫자 포함 8 자리 이상이어야 해요.";
        } else this.error.password = false;
      
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    validPassword(password) {
      var va = /^(?=.*\d)(?=.*[a-z])[0-9a-zA-Z]{8,}$/;
      return va.test(password);
    },
    clickSignup() {
      this.$router.push({ name: 'Signup' })
    },
    clickLogin() {
      if ( this.isSubmit ) {
        this.$router.push('/')
      }
    },
    clickPasswordFind() {
      this.$router.push({ name: 'PasswordFind' })
    }
  }
}
</script>

<style scoped>
.container {
  width: 30%;
  border-radius: 25px;
}

h3 { 
  color: #88A498;
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

.login-button{
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

.inputs:focus {
  border-style: none;
  border-bottom: 2px solid #D6CBBD;
  outline-style: none;
}

input[type="password"] {
  font-family:sans-serif;
}
.error, .error:focus {
  border-bottom: 2px solid rgb(250, 25, 59, 0.7); 
}

.error-text {
  color: rgb(250, 25, 59, 0.7);
  text-align: left;
  padding-left: 5px;
}

.login-button:hover {
  background-color: #3c755a;
  color: #F8F8F8;
}

.disabled, .disabled:hover {
  background-color: rgb(136, 154, 152, 0.25);
  color: #F8F8F8;
}

.items:hover {
  cursor:pointer;
  color: #D6CBBD;
}
</style>