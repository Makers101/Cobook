<template>
  <div class="row">
    <div class="offset-lg-2 col-lg-8 col-12">

      <!-- club-banner -->
      <div class="club-banner">
        <img
          class="club-banner-img"
          src="@/assets/club_banner.jpg" 
          alt="">
        <div class="club-banner-text">
          <h3 class="font-weight-bold">Club</h3>
          <p class="mb-0">
            클럽은 정기적으로 함께 책을 읽는 사람들의 모임입니다.
            <br>
            취향이 맞는 클럽에 참여하여 함께 읽는 즐거움을 경험해보세요.
          </p>
        </div>
      </div>

      <!-- club-menubar -->
      <div class="club-menubar my-3 d-flex justify-content-between">
        <div class="club-toggle">
          <button class="btn btn-toggle-false mx-1" @click="selectFilter('popular')" v-show="popular_filter">#인기</button>
          <button class="btn btn-toggle-true mx-1" @click="selectFilter('popular')" v-show="!popular_filter">#인기</button>

          <button class="btn btn-toggle-false mx-1" @click="selectFilter('open')" v-show="open_filter">#모집중</button>
          <button class="btn btn-toggle-true mx-1" @click="selectFilter('open')" v-show="!open_filter">#모집중</button>

          <span             
            v-for="genre in user_genres"
            :key="`user_genre_${genre.id}`"
          >
            <button 
              class="btn btn btn-toggle-false mx-1"
              @click="selectFilter(genre.name)"
              v-show="genre_filter.has(genre.name)"
            >
              #{{ genre.name }}
            </button>
            <button 
              class="btn btn btn-toggle-true mx-1"
              @click="selectFilter(genre.name)"
              v-show="!genre_filter.has(genre.name)"
            >
              #{{ genre.name }}
            </button>
          </span>
        </div>
        
        <!-- club-create -->
        <button class="btn btn-club-create mx-1" data-toggle="modal" data-target="#exampleModal">클럽 생성</button>
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
                  <!-- <ol class="carousel-indicators">
                    <li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
                    <li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
                  </ol> -->
                  <div class="carousel-inner">
                    <div class="carousel-item active">
                      <!-- <img src="http://placehold.jp/300x200.png?text=sample" class="d-block w-100" alt="..."> -->
                      <!-- <div class="carousel-caption d-none d-md-block"> -->
                        <h5>First slide label</h5>
                        <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                      <!-- </div> -->
                    </div>
                    <div class="carousel-item">
                      <!-- <img src="http://placehold.jp/300x200.png?text=sample" class="d-block w-100" alt="..."> -->
                      <!-- <div class="carousel-caption d-none d-md-block"> -->
                        <h5>Second slide label</h5>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                      <!-- </div> -->
                    </div>
                    <div class="carousel-item">
                      <!-- <img src="http://placehold.jp/300x200.png?text=sample" class="d-block w-100" alt="..."> -->
                      <!-- <div class="carousel-caption d-none d-md-block"> -->
                        <h5>Third slide label</h5>
                        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
                      <!-- </div> -->
                    </div>
                  </div>
                  <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                  </a>
                  <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                  </a>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <hr class="my-0">

      <!-- club-list -->
      <div class="club-list my-2 row">
        <div 
          class="col-sm-4 col-12 p-3"
          v-for="club in clubs"
          :key="`club_${club.id}`">
          <div class="card">
            <div class="card-head">
              <img
                class="card-img-top to-detail"
                :src="club.image"
                :alt="club.name"
                @click="selectClub(club.id)"
              >
              <span class="badge mb-0 club-open" v-if="club.open">모집중</span>
            </div>        
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="card-title font-weight-bold mb-0 club-name to-detail" @click="selectClub(club.id)">{{ club.name }}</h4>
                <small class="color-green font-weight-bold club-followers">{{ club.followers }} FOLLOW</small>
              </div>
              <p class="card-text text-left club-oneline">{{ club.oneline_description }}</p>
              <div class="d-flex justify-content-start my-3">
                <span
                  class="badge badge-genre mr-2"
                  v-for="genre in club.genres"
                  :key="`club_genre_${genre.id}`">
                  #{{ genre.name }}
                </span>
              </div>
              <div class="d-flex justify-content-between">
                <p class="card-text mb-0">
                  <small class="color-black">주로 <span class="color-black font-weight-bold">{{ club.residence }}</span>에서 만남 :)</small>
                </p>
                <div class="d-flex">
                  <p class="card-text mb-0 mr-2">
                    <small class="color-black"><i class="fas fa-users"></i> {{ club.participant_num }}</small>
                  </p>
                </div>
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
import { mapState } from 'vuex'
export default {
  name: 'ClubList',
  data() {
    return {
      popular_filter: false,
      open_filter: false,
      genre_filter: new Set(),
      user_genres: ['문학', '철학', '예술']
    }
  },
  computed: {
    ...mapState('clubStore', ['clubs']),
  },
  methods: {
    selectClub(club_id) {
      router.push({ name: 'ClubDetail', params: { club_id: club_id }})
    },
    filterClubs() {
      let new_clubs = []
            
      if (this.open_filter) {
        this.clubs.forEach(club => {
          if (club.open) {
            new_clubs.push(club)
          }
        });
      } else {
        new_clubs = this.clubs
      }

      console.log(new_clubs)

      if (this.genre_filter.size !== 0) {
        console.log('여기1')
        let new_clubs2 = new Set()
        new_clubs.forEach(club => {
          let temp = 0
          club.genres.forEach(genre => {
            if (this.genre_filter.has(genre.name)) {
              temp = temp + 1
            }
          })
          console.log(club.genres.length)
          console.log(temp)
          if (club.genres.length === temp) {
            new_clubs2.add(club)
          }
        })
        this.clubs = new_clubs2
      } else {
        console.log('여기2')
        this.clubs = new_clubs
      }
    },
    selectFilter(filter) {
      if (filter === 'popular') {
        this.popular_filter = !this.popular_filter
      } else if (filter === 'open') {
        this.open_filter = !this.open_filter
      } else {
        if (!this.genre_filter.has(filter)) {
          this.genre_filter.add(filter)
        } else {
          this.genre_filter.delete(filter)
        }
      }
      this.$forceUpdate();
      this.filterClubs()
    }
  }
}
</script>

<style scoped>
  .club-banner {
    position: relative;
  }

  .club-banner-img {
    width: 100%;
    vertical-align: middle;
    filter: brightness(0.7)
  }
  .club-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }

  .btn-toggle-true {
    border-color: #88A498;
    color: #88A498;
  }

  .btn-toggle-true:hover {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-toggle-false {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-toggle-false:hover {
    background-color: white;
    border-color: #88A498;
    color: #88A498;
  }

  .btn-club-create {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-club-create:hover {
    background-color: #3c755a;
    color: #F8F8F8;
  }

  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .club-name {
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1; 
    -webkit-box-orient: vertical;
    text-align: start;
  }

  .club-followers {
    white-space: nowrap;
  }

  .club-oneline {
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2; 
    -webkit-box-orient: vertical;
    text-align: start;
    height: 3rem;
  }

  .card-head {
    position: relative;
  }

  .club-open {
    /* background-color: #b68145; */
    /* background-color: #907a62; */
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 11%;
    left: 89%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .to-detail:hover {
    cursor: pointer;
    color: #88A498
  }
</style>