<template>
  <div class="mt-3">
    <div class="row rows-cols-1 row-cols-md-3">
      <div class="col-12 col-sm-4 mb-4" v-for="book in books" :key="book.id">
        <div class="card h-100">
           <div style="max-height:70px;overflow:hidden;">
            <!--  style="position:absolute; clip:rect(0px, 100px, 100px, 0px)" -->
            <img class="bg-image" :src="`${ book.book.bookImg }`" >
            <h5 
              class="card-img-top color-light-black px-5 book-title" 
              alt="book" >
              {{ book.book.title }}</h5>
          </div>
          <div class="card-body bg-light-ivory d-flex flex-column">
            <div class="mt-auto">
              <div class="w-100">
                <p class="text-left m-0"><i class="fas fa-quote-left"></i></p>
                <p class="card-text" style="word-break:keep-all;">{{ book.onelineReview }}</p>
                <p class="text-right m-0"><i class="fas fa-quote-right"></i></p>
              </div>
            </div>
            <div class="text-left bg-light-ivory pt-0 mt-auto">
              <span class="mr-3"><i class="fas fa-heart mr-2"></i>{{ book.likeUsers.length }}</span>
              <span><i class="fas fa-comment mr-2"></i>{{ book.comments.length }}</span>
            </div>
          </div>
        </div>
       
      </div>
      
    </div>
  </div>
</template>

<script>
const sample_books = [
  {
    id: 1,
    book: {
      id: 1,
      title: '돈 뜨겁게 사랑하고 차갑게 다루어라',
      bookImg: 'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1289685%3Ftimestamp%3D20200203125625'
    },
    name: 'sample_name_1',
    onelineReview: '그 무엇보다 비판적이고, 작가만의 통찰력이 재미있다.',
    likeUsers: [
      1,
      2,
    ],
    comments: [
      'comment1',
      'comment2'
    ]
  },
  {
    id: 2,
    book: {
      id: 2,
      title: '책 제목 모르겠다..ㅠㅠㅠ',
      bookImg: 'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1274465%3Ftimestamp%3D20200212131323'
    },
    name: 'sample_name_2',
    onelineReview: '깊은 생각을 할 수 있게 하는 책, 추천하고 싶다.',
    likeUsers: [
      1,
      2,
    ],
    comments: [
      'comment1',
      'comment2'
    ]
  },
  {
    id: 3,
    book: {
      id: 3,
      title: '책 제목 3',
      bookImg: 'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1011286%3Ftimestamp%3D20200411133721'
    },
    name: 'sample_name_3',
    onelineReview: '내 인생작, 너무 재밌다.',
    likeUsers: [
      1,
      2,
    ],
    comments: [
      'comment1',
      'comment2'
    ]
  },
  {
    id: 4,
    book: {
      id: 4,
      title: '카밍시그널',
      bookImg: 'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1669732%3Ftimestamp%3D20200723135715'
    },
    name: 'sample_name_4',
    onelineReview: '강아지를 키우는 사람이라면 한번쯤은 꼭 읽어보라고 추천해주고 싶은 책. 강아지의 행동에 대해 배울 수 있다.',
    likeUsers: [
      1,
      2,
    ],
    comments: [
      'comment1',
      'comment2'
    ]
  },
  {
    id: 5,
    book: {
      id: 5,
      title: '야큐정전',
      bookImg: 'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1410820%3Ftimestamp%3D20200723133257'
    },
    name: 'sample_name_5',
    onelineReview: '노예 근성에 날카로운 비판을 가함과 동시에 신해혁명이 일어나자 오히려 억울한 죄를 뒤집어쓰고 처형되는 아Q의 운명을 그려 신해혁명의 본질을 비판한 작품',
    likeUsers: [
      1,
      2,
    ],
    comments: [
      'comment1',
      'comment2'
    ]
  },
  {
    id: 6,
    book: {
      id: 6,
      title: '가면산장 살인사건',
      bookImg: 'https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1339602%3Ftimestamp%3D20200723140442'
    },
    name: 'sample_name_6',
    onelineReview: '처음 읽어본 추리 소설이자 일본 문학. 영상으로 발견만 해보던 복선을 텍스트로, 의도적으로 의미를 부여하며 발견하려니 나를 계속 의심하는 상황들의 향연이었다.',
    likeUsers: [
      1,
      2,
      3
    ],
    comments: [
      'comment1',
      'comment2'
    ]
  },
]
export default {
  name: 'ProfileFeed',
  data() {
    return {
      books: sample_books,
    }
  }
}
</script>

<style scoped>
.bg-image {
  position: static;
  width: 100%;
  max-height: initial; 
  margin-top: 0%;
  filter: blur(5px);
}

.book-title {
  position: absolute;
  top: 3%;
  left: 0;
  text-shadow: 1px 1px 2px white;
  font-weight: 900;
  word-break: keep-all;
}

</style>