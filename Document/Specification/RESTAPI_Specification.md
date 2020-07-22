### 인증

##### 1. 회원가입

- input: 유저네임, 이메일, 패스워드, 패스워드 확인(OAuth로 가입할 경우 별도로 처리)
- 각 input 별 warning message 출력(ex_"패스워드는 8자리 이상이어야 합니다.")
- 모든 input이 문제없이 입력된 경우에만 '가입하기' 버튼 활성화
- 회원가입을 마치지 않고 화면을 벗어날 경우 경고 메시지
- 회원가입 완료 시 사용자에게 피드백 노출 + 자동 로그인

> - 회원가입 POST 요청(OAuth)
>   - request data(해당 OAuth에서 우리 backend로 날릴 데이터)
>     - username, platform_type
>   - response data
>     - auth_token
> - 회원가입 POST 요청(자체)
>   - request data
>     - username, email, password
>   - response data
>     - auth_token

##### 2. 로그인

- input: 이메일, 패스워드(OAuth로 가입할 경우 별도로 처리)
- 모든 input이 문제없이 입력된 경우에만 '가입하기' 버튼 활성화
- 로그인 실패 시 관련 에러 메시지 팝업으로 띄워주기
- 패스워드 찾기 버튼

> - 로그인 POST 요청(OAuth)
>   - request data(해당 OAuth에서 우리 backend로 날릴 데이터)
>     - username, platform_type
>   - response data
>     - auth_token
> - 로그인 POST 요청(자체)
>   - request data
>     - email, password
>   - response data
>     - auth_token

##### 3. 로그아웃

- 유저 프로필 - 설정 버튼 - 로그아웃 버튼

> - 로그아웃 POST 요청
>   - request data
>     - auth_token

##### 4. 패스워드 찾기

- input: 이메일

- 패스워드 재설정 안내 메일링
  - 패스워드 재설정 페이지
    - input: 패스워드, 패스워드 확인

> - 패스워드 찾기 POST 요청
>   - request data
>     - email
>   - response data
>     - 패스워드 변경 url
> - 패스워드 바꾸기 POST 요청(별도 페이지)
>   - request data
>     - user_id(페이지 내에 정보), password

### 프로필(서재)

##### 1. 기본정보(상단)

- 프로필 이미지, 유저네임, 한 줄 설명, 팔로우/팔로워, 관심장르, 통합 좋아요 수, 팔로우 버튼(타인만)
- 설정 버튼(본인만) - 클릭 시 드롭다운
  - 로그아웃
  - 정보 수정(누르면 Modal 창)
    - 프로필 이미지, 유저네임, 관심장르 수정 가능
    - 계정 삭제 가능

> - 프로필 기본정보 GET 요청
>   - request data
>     - user_id(url params)
>   - response data
>     - username, profile_img, description
>     - followers, followings, club_followings
>     - user_genre
>     - total_post_like(자기가 받은 좋아요 수...쿼리 미쳣...)
> - 프로필 기본정보 PUT 요청
>   - request data
>     - user_id(url params)
>     - auth_token
>     - username, profile_img, description
>     - user_genre
> - 패스워드 수정 PUT 요청
>   - request data
>     - user_id(url params)
>     - auth_token
>     - old_password, new_password
> - 프로필 DELETE 요청
>   - request data
>     - user_id(url params)
>     - auth_token

##### 2. 책(세부 탭 - 1)

- 읽은 책
  - 책 표지 이미지 리스팅
    - 호버 시 효과(ex_어두워 지면서 책제목, 저자, 출판사 나오게?)
    - 클릭하면 유저가 해당 책에 쓴 리뷰 디테일 페이지로
- 찜한 책
  - 책 표지 이미지 리스팅
    - 호버 시 효과(ex_어두워 지면서 책제목, 저자, 출판사 나오게?)
    - 클릭하면 해당 책 디테일 페이지로

> - 책 정보 GET 요청
>   - request data
>     - user_id(url params)
>   - response data
>     - user_books(하나의 객체)
>       - book_read
>         - 해당 유저가 쓴 책 리뷰 게시물의 책 리스트
>           - book_id, book_img, title
>       - book_like
>         - 해당 유저가 찜한 책 리뷰 게시물의 책 리스트
>           - book_id, book_img, title

##### 3. 모임(세부 탭 - 2)

- 참여 클럽
  - 클럽 카드 리스팅
    - 프로필 이미지, 클럽네임, 간단소개, 관심장르
    - 클릭하면 해당 클럽 디테일 페이지로
- 참여 밋업
  - 밋업 카드 리스팅
    - 책 표지 이미지, 도서명, 간단소개, 참여 인원, 장소, 일시
    - 클릭하면 해당 밋업 디테일 페이지로

> - 참여 모임 GET 요청
>   - request data
>     - user_id(url params)
>   - response data
>     - user_participations(하나의 객체)
>       - club
>         - 해당 유저가 참여한 club 리스트
>           - club_id, club_img, name, oneline_description, residence, open, participant_num, capacity
>           - club_genre
>       - meetup
>         - 해당 유저가 참여한 meetup 리스트
>           - meetup_id, oneline_description, place, datetime, participant_num, capacity
>           - book(book_id, book_img, title)

##### 4. 북마크(세부 탭 - 3)

- 유저가 북마크한 게시물
  - 게시물 피드와 유사한 형태로

> - 북마크한 게시물 GET 요청
>   - request data
>     - user_id(url params)
>   - response data
>     - bookmarked_posts
>       - 해당 유저가 북마크한 게시물 리스트
>         - post(post_id, oneline_review, rank)
>           - post_like_count
>           - comment_count
>           - post_tag
>         - book(book_id, book_img, title)
>         - user(user_id, username)

##### 5. Overview? 내 활동?(세부 탭 - 4) => 이거 네이밍 어떻게 할 지 생각해봅시당

- 독서 캘린더(유형별로 색 다르게 표현)
  - 내가 읽은 책(리뷰 남긴 날)
  - 내가 참여한 리딩(리딩 참여 날)
  - 내가 참여한 밋업(밋업 참여 날)

- 독서활동 통계
  - 월별 독서 활동 그래프
  - 월별 리딩/밋업 활동 그래프
  - 장르별 독서 활동 파이그래프

> - 독서활동 GET 요청
>   - request data
>     - user_id(url params)
>   - response data
>     - user_activities
>       - schedule(하나의 객체: 안에 데이터는 다음과 같은 구조: 날짜 - 통계타입 - 디테일정보)
>         - book_read
>           - date(해당 책 리뷰 생성 날)
>           - type(book_read)
>           - book_id, book_img, title
>         - reading
>           - date(해당 리딩 참여 날)
>           - type(reading)
>           - reading_id, title
>         - meetup
>           - date(해당 밋업 참여 날)
>           - type(meetup)
>           - meetup_id, title
>       - statistic
>         - monthly(월별 독서량)
>           - year_month
>           - book_read_count
>         - genre(장르별 독서 비율)
>           - genre
>           - ratio

---

### 검색

##### 1. 게시물 검색

> - 게시물 검색 결과 GET 요청
>   - request data
>     - keyword(url params)
>     - search_type - post(url params)
>   - response data
>     - searched_posts(검색에 걸린 게시물 리스트 - 태그로 필터링)
>       - post(post_id, oneline_review, rank)
>         - post_like(해당 게시물 좋아요한 유저 리스트(id만))
>         - post_bookmark(해당 게시물 북마크한 유저 리스트(id만)
>         - comment_count
>         - post_tag
>           - tag_id, tag_name
>       - book(book_id, book_img, title)
>       - user(user_id, username)

##### 2. 책 검색

> 책 검색 결과 GET 요청
>
> - request data
>   - keyword(url params)
>   - search_type - book(url params)
> - response data
>   - searched_books(검색에 걸린 책 리스트 - 제목으로 필터링)
>     - book(book_id, book_img, title)

##### 3. 유저 검색

> 유저 검색 결과 GET 요청
>
> - request data
>   - keyword(url params)
>   - search_type - user(url params)
> - response data
>   - searched_users(검색에 걸린 유저 리스트 - 제목으로 필터링)
>     - user(user_id, username)

##### 4. 클럽 검색

> - request data
>   - keyword(url params)
>   - search_type - club(url params)
> - response data
>   - searched_clubs(검색에 걸린 클럽 리스트 - 클럽명으로 필터링)
>     - club
>       - club_id, club_img, name, oneline_description, residence, open, participant_num, capacity
>       - club_genre

##### 5. 밋업 검색

> - request data
>   - keyword(url params)
>   - search_type - meetup(url params)
> - response data
>   - searched_meetups(검색에 걸린 밋업 리스트 - 밋업명으로 필터링)
>     - meetup
>       - meetup_id, oneline_description, place, datetime, participant_num, capacity
>       - book(book_id, book_img, title)

---



### 게시물 - Feed

> 최신 게시물 순으로 노출, 스크롤로 이전 게시물 탐색

##### 1. 책 리뷰 게시물(카드)

- 책 표지 이미지, 따옴표 안의 한줄평, 유저네임, 도서명, 좋아요수, 댓글수, 북마크, 태그
- 게시물 클릭 시 해당 책 리뷰의 디테일 페이지로
- 책 제목 or 표지 이미지 누르면 책 디테일 페이지로
- 유저네임 클릭 시 해당 유저의 프로필 페이지로
- 태그 클릭 시 해당 태그로 검색된 게시물 검색 창으로

##### 2. 클럽 게시물(아직 확정 X)

- 모임 리뷰 관련 게시물
- 클릭 시 해당 클럽 게시물 디테일 페이지로

##### 3. 책 큐레이션 게시물(아직 확정 X)

- 책 표지 이미지, 책 기본정보, 현재 이 책을 읽고 있는 사람들
- 클릭 시 해당 책 디테일 페이지로

##### 4. 책 문장 사진 게시물(교수님 추천 - 추후 논의)

> - 게시물 리스트 GET 요청
>   - request data
>     - 없음
>   - response data
>     - posts(팔로우한 유저의 게시물 리스트)
>       - post(post_id, oneline_review, rank)
>         - post_like(해당 게시물 좋아요한 유저 리스트(id만))
>         - post_bookmark(해당 게시물 북마크한 유저 리스트(id만)
>         - comment_count
>         - post_tag
>           - tag_id, tag_name
>       - book(book_id, book_img, title)
>       - user(user_id, username)

### 게시물 - Detail

##### 1. 게시물 기본정보(상단)

- 책 기본정보
  - 책 표지 이미지, 제목, 저자, 출판사
  - 클릭 시 책 디테일 페이지로
- 작성자 - 클릭 시 유저 프로필 페이지로
- 평점
- 좋아요 버튼, 북마크 버튼, 수정/삭제(드롭다운 - 작성자만)

##### 2. 게시물 컨텐츠(중단)

- 한줄평
- 책 리뷰 디테일
- 태그

##### 3. 댓글(하단)

- 댓글 작성 Form
- 댓글
  - 작성자 - 클릭 시 유저 프로필 페이지로
  - 댓글 내용
  - 수정/삭제(드롭다운 - 작성자만)

>- 게시물 Detail GET 요청
>  - request data
>    - post_id (url params)
>  - response data
>    - oneline_review, review, rank, created_at, updated_at
>    - post_like(해당 게시물 좋아요한 유저 리스트(id만))
>    - post_bookmark(해당 게시물 북마크한 유저 리스트(id만)
>    - post_tag
>      - tag_id, tag_name
>    - book (book_id, book_img, title, author, translators, publisher, datetime)
>    - user(username, user_id)
>- 게시물 Detail PUT 요청
>  - request data
>    - post_id(url params)
>    - auth token
>    - oneline_review, review, rank, open
>    - post_tag
>      - tag_name
>- 게시물 DELETE 요청
>  - request data
>    - post_id(url params)
>    - auth token
>
>- 게시물 Like 클릭 시 POST 요청
>  - request data
>    - post_id (url params)
>    - auth_token
>
>- 게시물 Bookmark 클릭 시 POST 요청
>  - request data
>    - post_id (url params)
>    - auth_token
>
>- 댓글 GET 요청
>  - request data
>    - post_id (url params)
>  - response data
>    - post_comment
>      - post_comment_id
>      - content
>      - user (username, user_id)
>      - created_at
>      - updated_at
>
>- 댓글 Create POST 요청 
>  - request data
>    - post_id (url params)
>    - auth_token
>    - content
>
>- 댓글 Update PUT 요청
>  - request data
>    - post_id, post_comment_id (url params) 
>    - auth_token
>    - content
>
>- 댓글 Delete 요청
>  - request data
>    - post_id, post_comment_id (url params)
>    - auth_token

### 게시물 - Create(Update)

##### 1. 게시물 기본정보(상단)

- 책 기본정보
  - 책 표지 이미지, 제목, 저자, 출판사
- 평점
- 공개 범위 설정

##### 2. 게시물 컨텐츠(하단)

- 한줄평
- 책 리뷰 디테일
- 태그

> - 게시물 create POST 요청
>
>   - request data
>
>     - auth_token
>     - book_id
>     - oneline_review, review, rank, open
>     - post_tag
>       - tag_name
>
>   - response data
>
>     - 성공 시: post_id
>     - 실패 시: error message
>

### 책 - Detail

##### 1. 책 기본정보(상단)

- 책 기본정보(책 표지 이미지, 제목, 저자, 옮긴이, 출판사, 평균평점)
- 찜하기 버튼
- 책 리뷰 작성하기 버튼

##### 2. 책 상세정보(중단)

- 페이지 수, 출판일, 책 소개, 목차, 저자 정보 등

##### 3. 관련 컨텐츠

- 책 관련 리뷰
  - 게시물 피드와 동일한 구조

> - 책 Detail GET 요청
>   - request data
>     - book_id(url params)
>   - response data
>     - book_id, book_img, title, author, translators, publisher, contents, url, datetime
>     - book_like(해당 책 좋아요한 유저 리스트(id만))
>     - book_genre
>       - genre_name
>     - book_posts(해당 책에 대한 리뷰 게시물)
>       - post(post_id, oneline_review, rank)
>         - post_like(해당 게시물 좋아요한 유저 리스트(id만))
>         - post_bookmark(해당 게시물 북마크한 유저 리스트(id만)
>         - comment_count
>         - post_tag
>           - tag_id, tag_name
>       - book(book_id, book_img, title)
>       - user(user_id, username)

---



### 클럽 - Feed

##### 1. 탭 설명(상단)

- 적당한 배너 이미지 + 문구(ex_"원하는 클럽에 참여해 함께 읽는 친구를 만들어보세요 :)")
- 클럽 생성 버튼

##### 2. 인기 클럽

- 클럽 카드 리스팅(좋아요 많이 받은 순)
  - 프로필 이미지, 클럽네임, 간단소개, 관심장르, 좋아요, 모집상태
  - 클릭하면 해당 클럽 디테일 페이지로

##### 3. 모집 중인 클럽

- 클럽 카드 리스팅(현재 모집상태가 "모집중"인 클럽 - 좋아요 순 or 최신 순?)
  - 프로필 이미지, 클럽네임, 간단소개, 관심장르, 좋아요, 모집상태
  - 클릭하면 해당 클럽 디테일 페이지로

##### 4. 추천 클럽(아직 확정 X)

- 클럽 카드 리스팅(장르 기반으로 클럽 추천?)
  - 프로필 이미지, 클럽네임, 간단소개, 관심장르, 좋아요, 모집상태
  - 클릭하면 해당 클럽 디테일 페이지로

> - 클럽 리스트 GET 요청
>   - request data
>     - none
>   - response data
>     - clubs
>       - open_clubs(모집 중인 클럽)
>         - club
>           - club_id, club_img, name, oneline_description, residence, open, participant_num, capacity
>           - club_genre
>       - popular_clubs(인기 있는 클럽 - 음... 최근 팔로우 많은 순서? 최근 참가 신청 많은 순서?)
>         - club
>           - club_id, club_img, name, oneline_description, residence, open, participant_num, capacity
>           - club_genre

### 클럽 - Detail

##### 1. 클럽 기본 정보

- 프로필 이미지, 클럽네임, 간단 소개, 관심 장르, 주 모임지역, 팔로워수
- 팔로우 버튼
- 가입 신청 버튼(비가입 유저만) - 클릭 시 Modal 창
  - 간단한 소개
  - 제출 버튼
- 설정 드롭다운(클럽장만)
  - 리딩 생성 버튼
  - 클럽 정보 수정
  - 클럽 멤버 모집
    - 클릭 시 Modal 창
      - 모집 인원 설정
      - 제출 버튼

##### 2. 클럽 멤버

- 유저 프로필 이미지 + 유저 네임
- 클럽장은 별도의 표시
- 기본 5명까지 노출(넷플릭스 처럼 오른쪽으로 넘길 수 있게)
- 각 유저 클릭하면 유저 프로필 페이지로

##### 3. 클럽 설명

- 접기 가능

##### 4. 진행 리딩

- 리딩 카드 리스팅
  - 책 표지 이미지, 도서명, 간단소개, 참여 인원, 장소, 일시
  - 예정 / 진행 / 종료 표시
  - 클릭하면 해당 리딩 디테일 페이지로

##### 5. 클럽 게시물

- 책 리뷰 게시물 피드와 같은 구조
  - (디테일 역시 책 리뷰 게시물 디테일과 같은 구조)

> - 클럽 Detail GET 요청
>   - request data
>     - club_id(url params)
>   - response data
>     - club_id, name, oneline_description, description, club_img, residence, created_at, updated_at, participant_num, capacity
>     - club_members
>       - user_id, username, role
>     - club_genres
>       - genre_name
>     - readings
>       - reading_id, oneline_description, place, datetime, participant_num
>       - book(book_id, book_img, title)
> - 클럽 Detail PUT 요청
>   - request data
>     - club_id(url params)
>     - auth_token
>     - name, oneline_description, description, club_img, residence
>     - club_genres
>       - genre_name
> - 클럽 DELETE 요청
>   - request data
>     - club_id(url params)
>     - auth_token
> - 클럽 멤버 모집 활성화 POST 요청
>   - request data
>     - club_id(url params)
>     - auth_token
>     - capacity(capacity가 participant_num 보다 클 경우 모집 활성화)
> - 클럽 멤버 신청 POST 요청
>   - request data
>     - club_id(url params)
>     - auth_token
>     - introduce
> - 클럽 멤버 신청 승인 POST 요청
>   - request data
>     - club_id(url params)
>     - auth_token
>     - user_id(신청자)
> - 클럽 멤버 퇴출 POST 요청
>   - request data
>     - club_id(url params)
>     - auth_token
>     - user_id(퇴출자)

### 클럽 - Create(Update)

##### 1. 클럽 기본 정보

- 프로필 이미지, 클럽네임, 간단 소개, 관심 장르, 주 모임지역

##### 2. 클럽 멤버

- 클럽장
- 클럽 멤버 추가 가(유저 서치)

##### 3. 클럽 설명

> - 클럽 Create POST 요청
>   - request data
>     - auth_token
>     - name, oneline_description, description, club_img, residence, capacity
>     - club_genres
>       - genre_name
>   - response data
>     - club_id

### 리딩 - Detail(a => 책 미선정, b=> 책 선정)

##### 1. 리딩 기본 정보

- 책 표지 이미지(책 선정된 경우에만), 리딩네임, 간단 소개, 참가 인원, 일시, 장소
- 참가 신청 / 취소 버튼
- 입장 버튼(온라인 이벤트인 경우, 시작 30분 전부터 활성화)
  - 클릭시 온라인 음성 모임 페이지로

##### 2. 참가 멤버

- 유저 프로필 이미지 + 유저 네임
- 클럽장은 별도의 표시
- 기본 5명까지 노출(넷플릭스 처럼 오른쪽으로 넘길 수 있게)
- 각 유저 클릭하면 유저 프로필 페이지로

##### 3. 리딩 설명

- 접기 가능

##### 4-a 책 선정

- 책 투표(좌측)
  - 투표 정보 - 복수 선택 가능 여부, 투표 기한 등
  - 책 투표 리스트
    - 책 표지 이미지, 제목, 선택 버튼(우측) 
- 책 검색 창(우측)
  - default는 내가 찜한 책 노출
  - 책 찾아서 클릭하면 투표 리스트로 이동

##### 4-b 질문지

- "+" 버튼
  - 리딩 리더인 경우 - 바로 질문지 추가/수정 가능
  - 일반 멤버인 경우 - 질문지 추가 요청만 가능
- 리딩 리더인 경우 활성화 되지 않은 질문 목록을 편집 가능(활성화, 삭제, 수정 등)

##### 5-b 리뷰 게시물

- 선정된 책에 대한 멤버들의 리뷰 게시물 리스팅(카드)
  - 따옴표 안의 한줄평, 유저네임, 태그

##### 6-b 기록(종료된 리딩의 경우에만)

- 음성 파일(클릭 시 다운로드)
- 기록지(클릭 시 모달창에 내용 - 다운로드 버튼)

> - 리딩 Detail GET 요청
>   - request data
>     - club_id, reading_id(url params)
>   - response data
>     - title, datetime, place, description, oneline_description, participant_num
>     - reading_members
>       - user_id, username, role
>     - candidate_books(책 안정해진 경우)
>       - book(book_id, book_img, title)
>     - reading_book(책 정해진 경우)
>       - book(book_id, book_img, title)
>     - reading_questions(책 정해진 경우)
>       - reading_questions_id, question
>     - member_posts(책 정해진 경우 / 참여 멤버가 작성한 책 리뷰 게시물)
>       - post(post_id, oneline_review, rank)
>         - post_like_count
>         - comment_count
>         - post_tag
>       - book(book_id, book_img, title)
>       - user(user_id, username)
> - 리딩 참여 신청/취소 POST 요청
>   - request data
>     - club_id, reading_id(url params)
>     - auth_token
> - 리딩 후보책 등록 POST 요청
>   - request data
>     - club_id, reading_id(url params)
>     - auth_token
>     - book_id
> - 리딩 후보책 선택 POST 요청
>   - request data
>     - club_id, reading_id(url params)
>     - auth_token
>     - reading_book_id
> - 리딩 질문지 등록 POST 요청
>   - request data
>     - club_id, reading_id(url params)
>     - auth_token
>     - question
> - 리딩 질문지 DELETE 요청
>   - request data
>     - club_id, reading_id(url params)
>     - auth_token
>     - reading_question_id
> - 리딩 Detail PUT 요청
>   - request data
>     - club_id, reading_id(url params)
>     - auth_token
>     - title, datetime, place, description, oneline_description, reading_book
> - 리딩 DELETE 요청
>   - request data
>     - club_id, reading_id(url params)
>     - auth_token

### 리딩 - Create(Update)

##### 1. 리딩 기본 정보

- 리딩네임, 간단 소개, 일시, 장소
- 책 선정
  - "투표" 상태로 선택 가능
  - 미니 책 검색 창
    - 특정 책 선택 가능

##### 2. 리딩 설명

> - 리딩 Create POST 요청
>   - request data
>     - club_id(url params)
>     - auth_token
>     - title, datetime, place, description, oneline_description, (reading_book / 생성하면서 책고르는 경우에만)
>   - response data
>     - reading_id

---



### 밋업 - Feed

##### 1. 탭 설명(상단)

- 적당한 배너 이미지 + 문구(ex_"초간단 책모임 참여! 밋업을 통해 쉽게 책에 대한 이야기를 나눠보세요 :)")
- 밋업 생성 버튼

##### 2. 오늘의 밋업

- 밋업 카드 리스팅(최신순?)
  - 책 표지 이미지, 책제목, 간단소개, 일시, 장소, 현재인원/정원
  - 클릭하면 해당 밋업 디테일 페이지로

##### 3. 추천 밋업

- 밋업 카드 리스팅(장르 기반 추천?)
  - 책 표지 이미지, 책제목, 간단소개, 일시, 장소, 현재인원/정원
  - 클릭하면 해당 밋업 디테일 페이지로

> 밋업 리스트 GET 요청
>
> - request data
>   - none
> - response data
>   - meetups
>     - recent_meetups(최신 밋업)
>       - meetup
>         - meetup_id, oneline_description, place, datetime, participant_num, capacity
>         - book(book_id, book_img, title)
>       - 
>         - club_id, club_img, name, oneline_description, residence, open, participant_num, capacity
>         - club_genre
>     - recommended_meetups(최신 밋업)
>       - meetup
>         - meetup_id, oneline_description, place, datetime, participant_num, capacity
>         - book(book_id, book_img, title)

### 밋업 - Detail

##### 1. 밋업 기본 정보

- 책 표지 이미지, 간단 소개, 참가 인원, 일시, 장소
- 참가 신청 / 취소 버튼
- 입장 버튼(온라인 이벤트인 경우, 시작 30분 전부터 활성화)
  - 클릭시 온라인 음성 모임 페이지로

##### 2. 참가 멤버

- 유저 프로필 이미지 + 유저 네임
- 클럽장은 별도의 표시
- 기본 5명까지 노출(넷플릭스 처럼 오른쪽으로 넘길 수 있게)
- 각 유저 클릭하면 유저 프로필 페이지로

##### 4-b 질문지

- "+" 버튼
  - 밋업 리더인 경우 - 바로 질문지 추가/수정 가능
  - 일반 멤버인 경우 - 질문지 추가 요청만 가능
- 밋업 리더인 경우 활성화 되지 않은 질문 목록을 편집 가능(활성화, 삭제, 수정 등)

##### 5-b 리뷰 게시물

- 선정된 책에 대한 멤버들의 리뷰 게시물 리스팅(카드)
  - 따옴표 안의 한줄평, 유저네임, 태그

##### 6-b 기록(종료된 밋업의 경우에만)

- 음성 파일(클릭 시 다운로드)
- 기록지(클릭 시 모달창에 내용 - 다운로드 버튼)

> 밋업 Detail GET 요청
>
> - request data
>   - meetup_id(url params)
> - response data
>   - title, datetime, place, description, oneline_description, participant_num, capacity
>   - meetup_members
>     - user_id, username, role
>   - reading_book
>     - book(book_id, book_img, title)
>   - reading_questions
>     - meetup_questions_id, question
>   - member_posts(참여 멤버가 작성한 책 리뷰 게시물)
>     - post(post_id, oneline_review, rank)
>       - post_like_count
>       - comment_count
>       - post_tag
>     - book(book_id, book_img, title)
>     - user(user_id, username)
> - 밋업 참여 신청/취소 POST 요청
>   - request data
>     - meetup_id(url params)
>     - auth_token
> - 밋업 질문지 등록 POST 요청
>   - request data
>     - meetup_id(url params)
>     - auth_token
>     - question
> - 밋업 질문지 DELETE 요청
>   - request data
>     - meetup_id(url params)
>     - auth_token
>     - meetup_question_id
> - 밋업 Detail PUT 요청
>   - request data
>     - meetup_id(url params)
>     - auth_token
>     - title, datetime, place, description, oneline_description, reading_book, capacity
> - 밋업 DELETE 요청
>   - request data
>     - meetup_id(url params)
>     - auth_token

### 밋업 - Create(Update)

##### 1. 밋업 기본 정보

- 간단 소개, 일시, 장소
- 인원 수
- 책 선정
  - 미니 책 검색 창
    - 특정 책 선택 가능

> 밋업 Create POST 요청
>
> - request data
>   - auth_token
>   - title, datetime, place, description, oneline_description, reading_book, capacity
> - response data
>   - meetup_id

---



### 추가적으로 구현해야할 기능

##### 1. 온라인 음성 모임(자동 녹음)

- 독서 모임 기록지(참가 멤버 공유 - 구글 docs 느낌)
- 참가 유저
  - 보이스 활성화 될 때마다 표시
- 멤버 리뷰 btn
  - 누르면 참가 멤버들의 책 리뷰 나옴
- 질문지 btn
  - 리더가 특정 질문 클릭하면 모든 멤버 화면 상단에 해당 질문 고정됨
- 투표 btn
  - 누르면 투표 생성 가능
  - 생성된 투표 참가 멤버들에게 모달창으로 노출
  - 결과 확인 가능
- 녹음 음량 조절 btn
- 모임 나가기 / 종료(리더만) btn
  - 클릭하면 리딩/밋업 디테일 페이지로

##### 2. 알람(Navbar)

- 게시물 관련 알람
- 클럽, 리딩, 밋업 관련 알람