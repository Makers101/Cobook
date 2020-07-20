# #만든이101 Convention

## Git

### commit

- ```bash
  [이슈번호] type/기능단위 : 행위 행위요소(대문자) 행위디테일(소문자)
  
  body 
  ```

- ```bash
  [S0234165-1] feat/Boards : Update BoardCreateView font color
  
  body
  ```

  - 기능(feat): 새로운 기능을 추가
    - Create, Update, Delete
  - 버그(fix): 버그 수정
  - 리팩토링(refactor): 코드 리팩토링
  - 형식(style): 코드 형식, 정렬, 주석 등의 변경(동작에 영향을 주는 코드 변경 없음)
  - 테스트(test): 테스트 추가, 테스트 리팩토링(제품 코드 수정 없음, 테스트 코드에 관련된 모든 변경에 해당)
  - 문서(docs): 문서 수정(제품 코드 수정 없음)
  - 기타(chore): 빌드 업무 수정, 패키지 매니저 설정 등 위에 해당되지 않는 모든 변경(제품 코드 수정 없음)

- 제목은 50자를 넘지 않고, 대문자로 작성하고 마침표를 붙이지 않는다.
- 과거시제를 사용하지 않고 명령어를 작성한다.
  - Fixed -> Fix
  - Added -> Add



### branch

​		master

​			|

​	  develope

​			|

​	각각 기능단위



## Jira

- Epic : 기능 단위
- Story
  - Frontend
  - 기능을 CRUD로 작성, 페이지 단위
  - Backend
  - 기능을 CRUD로 작성
- Task
  - Frontend
    - 페이지 안에 세부적으로 들어가야 할 내용들
  - Backend
    - 미정 





## Code

### 전체

#### 변수명

snake_case

#### 파일명

camelCase



### JavaScript

Airbnb 가이드(한국어 번역본) : https://moonspam.github.io/ES5-Airbnb-JavaScript-Style-Guide-Korean/



### Vue

Vue 스타일 가이드 : https://kr.vuejs.org/v2/style-guide/index.html

vuex 스타일 가이드 : https://docs.vuestorefront.io/guide/vuex/vuex-conventions.html



### Java

네이버 스타일 가이드 : https://naver.github.io/hackday-conventions-java/