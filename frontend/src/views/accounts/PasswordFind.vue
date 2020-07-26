<template>
  <div>
    <div class="container p-3 mt-5 bg-light-ivory">
      <h3>비밀번호 찾기</h3>
      <h5 class="mt-3">비밀번호를 찾고싶으시다면<br><br>
        가입하실 때 기입하신<br><br>
        이메일을 입력해주세요.</h5>

      <div class="input-with-label mt-3">
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

      <div class="buttons mt-5 ">
        <button class="btn done-button" :class="{disabled: !isSubmit}" @click="clickDone" >입력완료</button>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: 'PasswordFind',
  data() {
    return {
      email: "",
      error: {
        email: false,
      },
      isSubmit: false
    }
  },
  created() {
    this.component = this;
  },
  watch: {
    email() {
      this.checkEmailForm();
    }
  },
  methods: {
    checkEmailForm() {
      if ( this.email.length > 0 && !this.validEmail(this.email) ) {
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
    clickDone() {
      if ( this.isSubmit ){
        this.$router.push({name: 'PasswordFindEmail'})
      }
      
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

</style>