<template>
  <div class="row">
    <div class="d-none d-lg-block col-2">
      <div class="myclub border rounded">
        <div class="myclub-header font-weight-extrabold mt-3">내 클럽</div>
        <hr>
        <div class="myclub-content">
          <div class="mb-3">
            <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            clubname1
            <span class="badge bg-green">Club</span>
          </div>
          <div class="mb-3">
            <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            clubname1
            <span class="badge bg-green">Club</span>
          </div>
        </div>
      </div>
    </div>
    <div class="col-lg-8 col-12">
      <!-- Button trigger modal -->
      <div type="button" class="border rounded my-3" data-toggle="modal" data-target="#createPostModal">
        <i class="fas fa-plus-circle color-green my-3"></i>
        <div class="text-muted">책 리뷰 게시물을 작성해주세요.</div>
      </div>

      <!-- Modal -->
      <div class="modal fade" id="createPostModal" tabindex="-1" role="dialog" aria-labelledby="createPostModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-body">
              ...
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
          </div>
        </div>
      </div>


      <hr class="mb-4">
      <div class="post mb-5">
        <div class="post-header d-flex justify-content-between mb-2">
          <div>
            <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            clubname1
            <span class="badge bg-green">Club</span>
          </div>
          <div class="color-beige small-text">
            <i class="fas fa-heart mr-2"></i><small class="mr-3">609</small>
            <i class="fas fa-comments mr-2"></i><small class="mr-3">120 </small>
            <i class="fas fa-bookmark"></i>
          </div>
        </div>
        <div class="post-content bg-light-beige row">
          <div class="col-4 my-5">
            <img style="height: 30vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
          </div>
          <div class="col-8 d-flex align-items-center">
            <div class="w-100 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="mr-5 mb-2">한줄 평은 이렇습니다.</div>
              <div class="ml-5">두줄 평은 이렇습니다.</div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
        </div>
        <div class="post-footer text-left mt-2">
          <span class="badge bg-green rounded-pill px-3 py-2 mr-2">태그1</span>
          <span class="badge bg-green rounded-pill px-3 py-2">태그2</span>
        </div>
      </div>
      <div class="post mb-5">
        <div class="post-header d-flex justify-content-between mb-2">
          <div>
            <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
            username1
          </div>
          <div class="color-beige small-text">
            <i class="fas fa-heart mr-2"></i><small class="mr-3">609</small>
            <i class="fas fa-comments mr-2"></i><small class="mr-3">120 </small>
            <i class="fas fa-bookmark"></i>
          </div>
        </div>
        <div class="post-content bg-light-beige row">
          <div class="col-4 my-5">
            <img style="height: 30vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
          </div>
          <div class="col-8 d-flex align-items-center">
            <div class="w-100 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="mr-5 mb-2">한줄 평은 이렇습니다.</div>
              <div class="ml-5">두줄 평은 이렇습니다.</div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
        </div>
        <div class="post-footer text-left mt-2">
          <span class="badge bg-green rounded-pill px-3 py-2 mr-2">태그1</span>
          <span class="badge bg-green rounded-pill px-3 py-2">태그2</span>
        </div>
      </div>
    </div>
    <div class="d-none d-lg-block col-2">
      <div class="recommend border rounded">
        <div class="recommend-header font-weight-extrabold mt-3">추천하는 책</div>
        <hr>
        <div class="recommend-content">
          <div class="row mb-3">
            <div class="offset-1 col-3">
              <img style="height: 10vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
            </div>
            <div class="col-8">
              <p>작가 : 000</p>
              <p>출판사 : 000</p>
            </div>
          </div>
          <div class="row">
            <div class="offset-1 col-3">
              <img style="height: 10vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
            </div>
            <div class="col-8">
              <p>작가 : 000</p>
              <p>출판사 : 000</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PostList',
  

}
</script>

<style scoped>
.feed-profile-img {
  height: 25px;
}

.small-text {
  font-size: 0.8rem;
}

.large-text {
  font-size: 1.3rem;
}

.myclub {
  position: fixed;
  top: 200px;
  left: 1%;
  width: 14%;
}

.recommend {
  position: fixed;
  top: 200px;
  right: 1%;
  width: 14%;
}

</style>