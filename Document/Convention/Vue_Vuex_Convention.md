# Vue Convention

## 필수 Convention

1. Props 정의

   - Prop은 가능한 상세하게 정의되어야 합니다.

   - ```javascript
     props: {
       status: String
     }
     
     // Even better!
     props: {
       status: {
         type: String,
         required: true,
         validator: function (value) {
           return [
             'syncing',
             'synced',
             'version-conflict',
             'error'
           ].indexOf(value) !== -1
         }
       }
     }
     ```

   <br>

2. `v-for` `Key` 지정

   - `v-for`는 항상 `key`와 함께 사용합니다.

   - ```html
     <ul>  
         <li    
            v-for="todo in todos"    
            :key="todo.id"  >    
         {{ todo.text }}  </li> 
     </ul>
     ```

   <br>

3. `v-if`와 `v-for`은 동시에 사용하지 않습니다.

   - `v-for`보다 v-if가 우선순위가 높으며, 둘다 이용하고 싶을 땐 `v-if`를 컨테이너로 옮기기

   - 리스트 목록을 필터링하기 위해서 입니다 (e.g. `v-for="user in users" v-if="user.isActive"`). 이 경우 users을 **새로운 computed 속성으로 필더링된 목록으로 대체**하십시오(e.g. `activeUsers`).

   - **숨기기 위해 리스트의 랜더링을 피할 때** 입니다 (e.g. `v-for="user in users" v-if="shouldShowUsers"`). 이 경우 `v-if`를 **컨테이너 엘리먼트**로 옮기세요 (e.g. `ul`, `ol`).

   - ```html
     <ul>
       <li
         v-for="user in activeUsers"
         :key="user.id"
       >
         {{ user.name }}
       </li>
     </ul>
     ```

   - ```html
     <ul v-if="shouldShowUsers">
       <li
         v-for="user in users"
         :key="user.id"
       >
         {{ user.name }}
       </li>
     </ul>
     ```

   <br>

4. 컴포넌트 style scoped
   
   - App.vue를 제외하고 다른 컴포넌트들은 style scoped으로 설정한다.

<br>

5. `src` or `@`
   - `@`를 이용한다.

<br>

## 추천 Convention

1. 컴포넌트 이름

   - 컴포넌트 파일 이름은 PascalCase를 이용 한다.
   - Abbreviation (축약어)를 사용하지 않는다.
   - 예시: MyComponent.vue

   <br>

2. 베이스 컴포넌트 이름

   - 한 페이지의 특정 기능/부분을 이용할 경우 Prefix (접두사)를 통일한다.
   - 예시: VButton, VTable, VIcon

   <br>

3. 강한 연관성을 가진 컴포넌트 이름

   - 부모 컴포넌트와 연결되어 있는 자식 컴포넌트들의 이름의 경우 접두사를 통일한다.
   - 예시: TodoList, TodoListItem, TodoListItemButton, SearchSideBar, SearchSidebarNavigation

   <br>

4. 다중 속성 엘리먼트

   - 속성이 여러개일 경우, 여러줄에 걸쳐 코드를 작성한다.

   - ```html
     <img
       src="https://vuejs.org/images/logo.png"
       alt="Vue Logo"
     >
     ```

5. HTML attribute에 속성 값 추가 시 

   - 속성값에 따옴표를 넣을 때는 띄어쓰기(공백-space)와 아래 형식을 사용한다.

   - ```html
     <AppSidebar :style="{ width: sidebarWidth + 'px' }">
     <input type="text">
     ```

6. 축약형 디렉티브 (`v-on` & `v-bind`)

   - `v-bind` 대신 `:` 을 사용한다.

   - `v-on` 대신 `@`을 사용한다.

   - ```html
     <input
       @input="onInput"
       @focus="onFocus"
     >
     <input
       :value="newTodoText"
       :placeholder="newTodoInstructions"
     >
     ```

     

# Vuex Convention

1. Module은 기능 단위로 자른다.
2. state은 중첩해서 사용하지 않는다 (should not be nested)
   - state은 언더바를 사용해 다음과 같이 지정한다: products_name, this_is_convention  
3. Getters
   - 참인지 거짓인지 판단할 경우 `is`를 사용한다. (예시: isUserLoggedIn, isUser)
   - Boolean이 아니면 `get`으로 시작한다.
4. Actions
   - 모듈 밖에서 상태 변화가 일어날 경우, action에서 일어나야 한다.
   - 여러개를 불러올 때는: `fetchProducts`
   - 한 개를 불러올 때는: `findProduct`
   - 생성 시에는: `createProduct`
   - 수정 시에는: `updateProduct`
   - 삭제 시에는: `deleteProduct`
5. Mutations
   - Mutation만 module의 state를 변경할 수 있다.
   - 대문자를 사용한다. (예시. ADD_PRODUCT, SET_CURRENT_PRODUCT_ID)
   - `SET`: 비어있는 state에 데이터를 배정한다.
   - `ADD`: 이미 배정되어 있는 state에 새로운 데이터를 추가한다.
   - `REMOVE`: `ADD`의 반대이다. 