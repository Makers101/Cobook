<template>
  <div class="custom-container">

    <!-- onedayEvents-banner -->
    <div class="onedayEvents-banner">
      <img
        class="onedayEvents-banner-img"
        src="https://user-images.githubusercontent.com/57381062/89487644-f9188000-d7e0-11ea-8d6d-4fa1283c7393.jpg" 
        alt="onedayEvents-banner-image">
      <div class="onedayEvents-banner-text">
        <h3 class="font-weight-bold">원데이이벤트</h3>
        <p class="mb-0">
          원데이이벤트는 간편하게 만나 함께 책을 읽는 일회성 책 모임입니다.
          <br>
          취향이 맞는 원데이이벤트에 참여하여 함께 읽는 즐거움을 경험해보세요.
        </p>
      </div>
    </div>

    <!-- onedayEvents-menubar -->
    <div class="onedayEvents-menubar my-3 d-flex justify-content-between">
      <div class="onedayEvents-toggle">
        <button
          class="btn btn-toggle-false mx-1"
          @click="selectFilter('recruit')"
          v-show="filters.recruit_filter"
        >
          #모집중
        </button>
        <button
          class="btn btn-toggle-true mx-1"
          @click="selectFilter('recruit')"
          v-show="!filters.recruit_filter"
        >
          #모집중
        </button>

        <span             
          v-for="genre in myaccount.likeGenres"
          :key="`userGenres_${genre.id}`"
        >
          <button 
            class="btn btn btn-toggle-false mx-1"
            @click="selectFilter(genre.name)"
            v-show="filters.genre_filter === genre.name"
          >
            #{{ genre.name }}
          </button>
          <button 
            class="btn btn btn-toggle-true mx-1"
            @click="selectFilter(genre.name)"
            v-show="!(filters.genre_filter === genre.name)"
          >
            #{{ genre.name }}
          </button>
        </span>
      </div>
      <button class="btn btn-green mx-1" @click="toOnedayEventCreate">원데이이벤트 생성</button>
    </div>
    <hr class="my-0">

    <!-- onedayEvents-list -->
    <div class="onedayEvents-list my-2 row">
      <div 
        class="col-lg-4 col-md-6 col-sm-12 p-3 pointer"
        v-for="onedayEvent in filteredOnedayEvents"
        :key="`onedayEvent_${onedayEvent.id}`"
        @click="selectOnedayEvent(onedayEvent.id)">
        <div class="card h-100">
          <div class="row no-gutters">
            <div class="col-6 onedayEvent-left">
              <img class="bg-image" :src="onedayEvent.book.bookImg" width="100%">
              <span class="badge mb-0 onedayEvent-recruit" v-if="onedayEvent.participantCnt < onedayEvent.capacity">모집중</span>
              <!-- <span class="badge mb-0 onedayEvent-recruit-false" v-else>만석</span> -->
            </div>
            <div class="col-6 text-left d-flex flex-column align-items-start p-2">
              <p class="onedayEvent-name font-weight-bold" lt="book">{{ onedayEvent.name }}</p>
              <span class="badge badge-genre">{{ onedayEvent.book.genre }}</span>
              <div class="mt-auto">
                <p class="mb-0"><small><i class="fas fa-users"></i> {{ onedayEvent.participantCnt}} / {{ onedayEvent.capacity }}</small></p>
                <p class="mb-0"><small><i class="fas fa-map-marker-alt"></i> {{ onedayEvent.place }}</small></p>
                <p class="onedayEvent-date mb-0"><small>{{ onedayEvent.datetime | moment('YYYY-MM-DD HH:mm') }}</small></p>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'OnedayEventList',
  data() {
    return {
      filters: {
        recruit_filter: false,
        genre_filter: null,
      }
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('onedayEventStore', ['onedayEvents', 'filteredOnedayEvents']),
  },
  methods: {
    ...mapActions('onedayEventStore', ['fetchOnedayEvents', 'filterOnedayEvents']),
    selectOnedayEvent(onedayEventId) {
      router.push({ name: 'OnedayEventDetail', params: { onedayEventId: onedayEventId }})
    },
    toOnedayEventCreate() {
      router.push({ name: 'OnedayEventCreate' })
    },
    selectFilter(filter) {
      if (filter === 'recruit') {
        this.filters.recruit_filter = !this.filters.recruit_filter
      } else {
        if (this.filters.genre_filter === filter) {
          this.filters.genre_filter = null
        } else {
          this.filters.genre_filter = filter
        }
      }
      this.$forceUpdate();
      this.filterOnedayEvents(this.filters);
    }
  },
  created() {
    this.fetchOnedayEvents()
  }
}
</script>

<style scoped>
  .onedayEvents-banner {
    position: relative;
  }

  .onedayEvents-banner-img {
    width: 100%;
    height: 200px;
    vertical-align: middle;
    filter: brightness(0.7)
  }

  .onedayEvents-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }

  .card {
    background-color: #fff;
    background: linear-gradient(#f8f8f8, #fff);
    box-shadow: 0 8px 16px -8px rgba(0,0,0,0.4);
    border-radius: 6px;
    overflow: hidden;
  }

  .btn-toggle-true {
    border-color: #88A498;
    color: #88A498;
  }

  .btn-toggle-false {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .onedayEvent-left {
    position: relative;
  }

  .onedayEvent-recruit {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 3%;
    left: 5%;
    padding: 6px;
  }

  .onedayEvent-recruit-false {
    background-color: #707070; 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 3%;
    left: 5%;
    padding: 6px;
  }

  .onedayEvent-name {
    word-break: keep-all;
  }

</style>