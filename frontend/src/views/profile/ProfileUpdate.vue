<template>
  <div>
    <div class="container p-3 mt-5 bg-light-ivory col-sm-8">
      <form>
        <div class="form-group row">
          <div class="col-sm-2">
            <img class="profile-image bg-light-ivory" src="http://placehold.jp/200x200.png">
          </div>
          <div class="col-sm-10 text-left align-self-center">
            <p class="m-0 font-weight-bold">ssafy@ssafy.com</p>
            <!-- <input type="file" class="form-control-file" id="exampleFormControlFile1"> -->
            <label class="color-light-black">
                프로필 사진 바꾸기<input type="file" style="display: none;">
            </label>
            <!-- <p class="m-0">프로필 사진 바꾸기</p> -->
          </div>
        </div>
        <div class="form-group row">
          <label for="inputNickName" class="col-sm-2 col-form-label" aria-describedby="nicknameHelpBlock">닉네임</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="inputNickName">
            <span id="nicknameHelpBlock" class="form-text text-muted text-left"><small>사람들이 이름, 별명 등 회원님의 알려진 이름을 사용하여 회원님의 계정을 찾을 수 있도록 도와주세요.</small></span>
          </div>
        </div>
        <div class="form-group row">
          <label for="inputDescription" class="col-sm-2 col-form-label">소개</label>
          <div class="col-sm-10">
            <textarea type="text" class="form-control" id="inputDescription" col="3"></textarea>
          </div>
        </div>
        <div class="form-group row">
          <label for="inputGenre" class="col-sm-2 col-form-label">선호 장르</label>
          <div class="col-sm-10">
            <select id="inputGenre" class="form-control">
              <option selected>장르1</option>
              <option>장르2</option>
            </select>
          </div>
        </div>
        <div>
          <button class="btn-beige btn">제출</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileUpdate'
}
</script>

<style scoped>
.profile-image{
  border-radius: 50%;
  width: 5vw;
  height: 5vw;
}

</style>                                                                                                                                                                                                                                                