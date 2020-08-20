# Java Convention

## Naming

1. 한국어 발음대로의 Naming 금지한다.
2. 변수 및 이름에는 숫자, 영어만을 허용한다.
3. 패키지는 소문자로 구성한다.
   * 단어별 구분을 위한 언더스코어(_), 대문자를 섞지 않는다.
4. 클래스, 인터페이스에는 대문자 카멜케이스 적용
   * `CamelCase`
5. 클래스 이름에는 명사 사용
6. 인터페이스 이름에는 명사 형용사 사용
7. 메서드, 변수 이름에는 소문자 카멜케이스 적용
   * `camelCase`
8. 메서드 이름은 동사, 전치사
9. 상수는 대문자와 언더스코어로 구성
   * `MAX_VALUE`

## 선언(Declarations)

1. 제한자 선언의 순서

   * public protected private abstract static final transient volatile synchronized native strictfp 순으로 제한자를 선언한다.

2. 애너테이션 후 새 줄 사용

   좋은 예

   ```java
   @RequestMapping("/guests")
   public void findGuests() {}
   ```

   나쁜 예

   ```java
   @Override public void destroy() {}
   ```

3. 한 줄에 한 문장

   * 문장이 끝나는 `;` 뒤에는 새줄을 삽입한다. 한 줄에 여러 문장을 쓰지 않는다.

   나쁜 예

   ```java
   int base = 0; int weight = 2;
   ```

   좋은 예

   ```java
   int base = 0;
   int weight = 2;
   ```

4. 하나의 선언문에는 하나의 변수만

   * 변수 선언문은 한 문장에서 하나의 변수만을 다룬다.

   나쁜 예

   ```java
   int base, weight;
   ```

   좋은 예

   ```java
   int base;
   int weight;
   ```

5. 배열의 대괄호는 타입 뒤에 선언
   나쁜 예

   ```java
   String names[]
   ```

   좋은 예

   ```java
   String[] names;
   ```

6. Long 형 값 마지막엔` L `붙이기

## 중괄호

1. K&R 스타일로 중괄호를 삽입한다.
   중괄호 선언은 K&R 스타일(Kernighan and Ritchie style)을 따른다.
   줄의 마지막에서 시작 중괄호`{`를 쓰고 열고 새줄을 삽입한다. 블럭을 마친후에는 새줄 삽입 후 중괄호를 닫는다.
   나쁜 예

   ```java
   public class SearchConditionParser
   {
       public boolean isValidExpression(String exp)
       {
   
           if (exp == null)
           {
               return false;
           }
   ...
   ```

   좋은 예

   ```java
   public class SearchConditionParser {
       public boolean isValidExpression(String exp) {
   
           if (exp == null) {
               return false;
           }
   ...
   ```

2. 닫는 중괄호와 같은 줄에 `else`, `catch`, `finally`, `while` 선언

   * else
   * catch, finally
   * do-while의 while

   나쁜 예

   ```java
   if (line.startWith(WARNING_PREFIX)) {
       return LogPattern.WARN;
   }
   else if (line.startWith(DANGER_PREFIX)) {
       return LogPattern.DANGER;
   }
   
   try {
       writeLog();
   }
   catch (IOException ioe) {
       reportFailure(ioe);
   }
   
   do {
       write(line);
       line = readLine();
   }
   while 
   ```

   좋은 예

   ```java
   if (line.startWith(WARNING_PREFIX)) {
       return LogPattern.WARN;
   } else if (line.startWith(DANGER_PREFIX)) {
       return LogPattern.NORMAL;
   } 
   
   try {
       writeLog();
   } catch (IOException ioe) {
       reportFailure(ioe);
   } finally {
       writeFooter();
   }
   
   do {
       write(line);
       line = readLine();
   } while (line != null);
   ```

3. 빈 블럭에는 새 줄 없이 중괄호 닫기 허용

   ```java
   public void close() {}
   ```

4. 조건/반복문에는 중괄호 필수

   * 한 줄로 끝나더라도 중괄호를 활용한다.