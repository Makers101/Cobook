<template>
  <div class="background">
    <h3>HELLO</h3>
    <div class="container p-3 mt-5 bg-light-ivory formatting">
      <h3>회원가입</h3>
      <h5 class="mt-3">회원가입을<br><br>
        완료하고 싶으시다면<br><br>
        이메일을 입력해주세요.</h5>

      <div class="input-with-label mt-3">
        <input 
          v-model="signupKakaoData.email" 
          v-bind:class="{error : error.email, complete:!error.email&&signupKakaoData.email.length!==0}"
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
      <div class="buttons mt-5 ">
        <button class="btn done-button" :class="{disabled: !isSubmit}" @click="signupKakao(signupKakaoData)" >입력완료</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import SERVER from '@/api/api'
import Swal from 'sweetalert2'
import axios from 'axios'
import router from '@/router'

export default {
  name: 'SignupKakao',
  data() {
    return {
      signupKakaoData: {
        nickName: this.$route.params.nickName,
        email: "",
        platformType: this.$route.params.platformType,
      },
      error: {
        email: false,
      },
      isSubmit: false,
    }
  },
  created() {
    this.component = this;
  },
  watch: {
    signupKakaoData: {
      deep: true,

      handler() {
        this.checkEmailForm()
      }
    } 
  },
  methods: {
    ...mapMutations(['SET_TOKEN']),
    checkEmailForm() {
      if ( this.signupKakaoData.email.length > 0 && !this.validEmail(this.signupKakaoData.email) ) {
        this.error.email = "올바른 이메일 형식이 아니에요"   
      }
      else this.error.email = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    validEmail(email) {
      // eslint-disable-next-line
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    signupKakao(info) {
      axios.post(SERVER.URL + SERVER.ROUTES.social, info)
        .then(res => {
          this.SET_TOKEN(res.data)
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
              }
          })
          Toast.fire({
            icon: 'success',
            title: "로그인에 성공하였습니다."
          })
          router.push('/')
        })
        .catch (err => {
          console.log(err.response)
        })
    }
  }
}
</script>

<style scoped>
.background {
  background-repeat: repeat;
}

.container {
  width: 30%;
  border-radius: 25px;
}

h3 {
  color: #88a498;
  font-weight: 800;
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

.done-button{
  background-color: #88A498;
  color: #F8F8F8;
  width: 70%;
}

.done-button:hover {
  background-color: #3c755a;
  color: #F8F8F8;
}

.disabled, .disabled:hover {
  background-color: rgb(136, 154, 152, 0.25);
  color: #F8F8F8;
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