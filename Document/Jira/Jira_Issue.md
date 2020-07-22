# Jira Issue 정리

> Issue별로 정리를 하고, Epic/Story/Task로 나누었습니다.
>
> Epic은 기능 단위, Story는 각 기능의 CRUD 및 페이지 단위로 작성, 마지막으로 Task는 페이지 안에 세부적인 내용들 (+오류)를 기준으로 작성하였습니다.

<br>

## Navbar - Story

- Navbar 각 메뉴 추가해서 넣기 - Task
  - 로고, 검색, 피드, 클럽, 번개, 알림, 프로필 (서재)
- 뒤로가기 구현 - Task (상의 필요)

<br>

## Search - Epic

- 게시물 검색 결과- Story
  - 게시물 검색 결과 디자인 - Task
  - 게시물 검색 Axios 요청 - Task
- 책 검색 결과- Story
  - 책 검색 결과 디자인 - Task
  - 책 검색 Axios 요청 - Task
- 유저 검색 결과 - Story
  - 유저 검색 결과 디자인 (최신순) - Task
  - 유저 검색 - 유사한 검색 값 자동 완성 노출 - Task
  - 유저 클릭 시 해당 유저 페이지로 이동 - Task
  - 유저 검색 Axios 요청 - Task
- 클럽 검색 결과 - Story
  - 클럽 검색 결과 디자인 - Task
  - 클럽 검색 Axios 요청 - Task
- 밋업 검색 결과 - Story
  - 밋업 검색 결과 디자인 - Task
  - 밋업 검색 Axios 요청 - Task

<br>

## Authentication - Epic

- 회원가입 페이지 생성 - Story
  - 회원가입 페이지 디자인 - Task
  - 회원가입 Input별 경고 메시지 출력 - Task
  - 회원가입 Axios 요청 - Task
- 로그인 페이지 생성 - Story
  - 로그인 페이지 디자인 - Task
  - 로그인 Input별 경고 메시지 출력 - Task
  - 로그인 Axios 요청 - Task
- 로그아웃 기능 구현- Story
- 패스워드 찾기 페이지 생성 - Story (상의 필요)

<br>

## Alert - Epic

- 알림 센터 디자인 - Story
  - 각 탭 전환 시 해당 탭이 선택되어 있음을 강조 - Task
  - 유저가 확인하지 않은 신규 알림 존재시 '새 알림' 표시 - Task
- Firestore로 알림 실시간 업데이트 - Story
- 알림 랩 구현 - Story
  - 신규 팔로워 승인 요청 구현 - Task
  - 신규 팔로워 승인 결과 구현 - Task
  - 신규 알림 및 기존 알림 구분하여 노출 - Task
- 요청 랩 구현 - Story
  - 팔로우 요청한 유저 목록 노출 - Task
  - 유저 목록과 함께 승인, 거절 버튼 노출 Task
  - 승인/거절 시 사용자에게 피드백 노출 - Task

<br>

## Profile - Epic

- 프로필 기본 정보 디자인- Story
- 프로필 기본 정보 수정 - Story
- 프로필 팔로우 기능 구현 - Story
  - 팔로워/팔로우 수 노출 - Task
  - 타 유저 프로필:  팔로우/팔로우 취소 버튼 노출 - Task
  - 팔로우 기능 구현 (상태변화 빠르게 구현) - Task
  - 팔로잉 리스트 구현 (상태변화 빠르게 구현)  - Task
  - 팔로워 리스트 구현 (상태변화 빠르게 구현) - Task
- 프로필 세부 탭 (책) - Story
  - 읽은 책 호버 시 효과 적용하기- Task
  - 찜한 책 호버 시 효과 적용하기- Task
- 프로필 세부 탭 (모임) - Story
  - 참여 클럽 디자인 - Task
  - 참여 밋업 디자인 - Task
- 프로필 세부 탭 (북마크) - Story
  - 북마크 게시물 디자인 - Task
- 프로필 세부 탭 (Overview) - Story
  - 캘린더 구현 - Task
  - 내가 읽은 책 (리뷰 남긴 날) 표시 - Task
  - 내가 참여한 리딩 (리딩 참여 날) 표시 - Task
  - 내가 참여한 밋업 (밋업 참여 날) 표시 - Task
  - 독서 활동 통계
    - 월별 독서 활동 그래프 생성 - Task
    - 월별 리딩/밋업 활동 그래프 생성 - Task
    - 장르별 동서 활동 파이 그래프 생성 - Task

<br>

## Feed - Epic

- 게시물 Feed 페이지- Story
  - 게시물 리스트 구현- Task
  - 스크롤 시, 더 많은 게시물 나올 수 있도록 표시 - Task
  - 유저네임 클릭 시 유저 프로필 페이지로 이동 - Task
  - 태그 클릭 시 태그로 검색된 게시물 검색창으로 이동 - Task
  - 피드 게시일 00시간 전, 00일 전으로 노출 - Task
  - 좋아요: 000님 외 00명이 좋아합니다 문구 노출 - Task
    - OOO: 사용자 본인의 닉네임
  - 북마크 구현 - Task
- 게시물 Detail 페이지 - Story
  - 책 정보 디자인 - Task
  - 게시물 컨텐츠 디자인 - Task
  - 피드 게시일 00시간 전, 00일 전으로 노출 - Task
  - 좋아요: 000님 외 00명이 좋아합니다 문구 노출 - Task
    - OOO: 사용자 본인의 닉네임
  - 좋아요 클릭 시 사용자에게 피드백 노출 - Task
  - 게시물 댓글 Create 구현 - Task
  - 게시물 댓글 데이터가 입력되었을 때만 작성 버튼 활성화 - Task
  - 게시물 댓글 작성 중 뒤로가기 시 작성 중단 여부 확인 - Task
  - 게시물 댓글 게시 후 사용자에게 피드백 노출 - Task
  - 게시물 댓글 숨김 처리 - Task
  - 게시물 댓글 Read 구현 - Task
  - 게시물 댓글 Delete 구현 - Task
    - 댓글 삭제 후 직전 행동 (삭제) 을 취소할 수 있도록 피드백 노출 - Task
  - 게시물 댓글 Update 구현 - Task
  - 북마크 구현 - Task
  - 북마크 버튼 클릭/재클릭 시 사용자에게 피드백 노출 - Task
- 게시물 Create 페이지 - Story
  - 게시물 Create 페이지 디자인 - Task
  - 공개범위 설정 기능 구현 - Task
- 게시물 Update 페이지 - Story
  - 게시물 Update 페이지 디자인 - Task
- 게시물 Delete 기능 구현 - Story

<br>

## Book - Epic

- 책 Detail 페이지 디자인 - Story
  - 책 기본정보 및 상세정보 디자인 - Task
  - 책 관련 리뷰 탭 디자인 - Task
  - 책 관련 밋업 탭 디자인 - Task

<br>

## Club - Epic

- 클럽 Feed 페이지- Story
  - 클럽 배너 디자인 - Task
  - 인기 클럽 카드 리스팅 - Task
  - 모집 중인 클럽 카드 리스팅 - Task
- 클럽 Detail 페이지 - Story
  - 클럽 기본 정보 디자인 - Task
  - 클럽 팔로우 기능 구현 - Task
  - 클럽장만 클럽 설정 가능하도록 구현 - Task
  - 클럽 설명 접을 수 있도록 구현 - Task
  - 리딩 카드 리스트 디자인 - Task
- 클럽 가입 신청 Modal 페이지 - Story
- 클럽 Create 페이지 - Story
  - 클럽 설명 및 기본 정보 디자인 - Task
  - 클럽 멤버 추가할 수 있도록 구현 - Task
- 클럽 Update 페이지 - Story
  - 클럽 설명 및 기본 정보 디자인 - Task
  - 클럽 멤버 추가할 수 있도록 구현 - Task
- 클럽 Delete 기능 구현 - Story

<br>

## Reading - Epic

- Reading Create 페이지 - Story
  - 리딩 기본 정보 및 설명 디자인 - Task
  - 책 검색 창 구현 - Task
- Reading Update 페이지 - Story
  - 리딩 기본 정보 및 설명 디자인 - Task
  - 책 검색 창 구현 - Task
- Reading Detail 페이지 - Story
  - 책 기본 정보 디자인 - Task
  - 참가 멤버 디자인 - Task
  - Reading 설명 접을 수 있도록 구현 - Task
  - 책 투표할 수 있도록 구현 - Task
  - 책 검색하면 투표 리스트로 이동하도록 구현 - Task
  - 질문지 기능 구현 - Task
  - 선정된 책에 대한 멤버들의 리뷰 게시물 리스트 디자인 - Task
  - 음성 파일 및 기록지 구현 - Task
- Reading Delete 기능 구현 - Story

<br>

## Meetup - Epic

- Meetup Create 페이지 - Story
  - 밋업 책 선정 검색 창 구현 - Task
  - 밋업 기본 정보 입력 디자인 - Task
- Meetup Update 페이지 - Story
  - 밋업 책 선정 검색 창 구현 - Task
  - 밋업 기본 정보 입력 디자인 - Task
- Meetup Feed 페이지 - Story
  - Meetup 배너 디자인 - Task
  - 오늘의 밋업 리스팅 디자인 - Task
  - 추천 밋업 리스팅 디자인 - Task
- Meetup Detail 페이지 - Story
  - 밋업 기본 정보 디자인 - Task
  - 참가 멤버 디자인 - Task
  - Reading 설명 접을 수 있도록 구현 - Task
  - 질문지 기능 구현 - Task
  - 선정된 책에 대한 멤버들의 리뷰 게시물 리스트 디자인 - Task
  - 음성 파일 및 기록지 구현 - Task