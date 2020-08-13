<template>
  <div class="row no-gutters">
    <div class="col-3 text-left p-3 search-bar">
      <h4 class="mt-3"><strong>검색 결과:</strong></h4>
      <p>000</p>
      <hr>
      <h5 class="font-weight-bold">필터</h5>
      <router-link class="m-0" :to="{name: 'SearchUser'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
            <i class="fas fa-user p-2"></i>
          </div>
          <span>유저</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchPost'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
            <i class="fas fa-comment-dots p-2"></i>
          </div>
          <span>리뷰</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchBook'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
              <i class="fas fa-book-open p-2"></i>
          </div>
          <span class="m-0">도서</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchClub'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%">
              <i class="fas fa-users p-2"></i>
          </div>
          <span class="m-0">클럽</span>
        </div>
      </router-link>
      <router-link class="m-0" :to="{name: 'SearchOneDayEvent'}">
        <div class="filter d-flex p-2">
          <div class="bg-beige mr-2" style="border-radius:50%;">
            <span class="mdi mdi-calendar-star p-2" width="50px"></span>
          </div>
          <span class="m-0">원데이 이벤트</span>
        </div>
      </router-link>
    </div>
    <router-view class="col-9"></router-view>
  </div>
</template>

<script>
export default {
  name: 'Search'
}
</script>

<style scoped>
a:link, a:active, a:visited {
  color: black;
}
.search-bar {
  height: 100vh;
  position: sticky;
  top: 0px;
  border-right: 1px solid rgb(0, 0, 0, 0.2);
  box-shadow: 5px 5px 10px rgb(0, 0, 0, 0.2)
}

.filter:hover, a:hover {
  background-color: #88A498;
  color: #F8F8F8;
  text-decoration: none !important;
}

hr {
  border-top: 2px solid rgba(0,0, 0, 0.1);
}
</style>