# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스
- 참고 : https://github.com/jojoldu/freelec-springboot2-webservice/
- 코드 수정 후 확인 시 반드시 서버 재시작(IntelliJ에서 Rerun 클릭)

## Ch.2 스프링 부트에서 테스트 코드를 작성하자
### Ch.2 마무리
- TDD와 단위 테스트란?
- 스프링 부트 환경에서 테스트 코드를 작성하는 방법
- 자바의 필수 유틸 롬복(lombok)의 사용법

## Ch.3 스프링 부트에서 JPA로 데이터베이스 다뤄보자
### 3.4 등록/수정/조회 API 만들기
- API를 만들기 위해 총 3가지 클래스 필요
    - Dto : request 데이터 수신
    - Controller : API 요청 수신
    - Service : 트랜잭션, 도메인 기능 간의 순서 보장
        - 여기서 비즈니스 로직을 처리하지 않음. (Domain에서 처리)
        - 트랜잭션 스크립트 : 기존에 Service로 처리하던 방식
- Controller, Service에서는 `@Autowired` annotation이 없다.
    - 스프링에서 Bean을 주입받는 방식 : @Autowired, setter, 생성자
    - setter를 갖아 권장 : 생성자로 Bean 객체를 받으면 @Autowired와 동일한 효과를 받는다.
    - `@RequiredArgsConstructor`에서 final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성한다.
- `PostsSaveRequestDto.toEntity()`
    - 주의
        - Entity 클래스는 Request/Response 클래스로 사용 금지!
        - Entity 클래스와 Controller에서 쓸 Dto도 분리
    - Entity 클래스는 테이블과 직접 연결되어 있다. 그래서 여러 클래스에 영향을 끼칠 수 있음.
    - Request, Response용 Dto는 View를 위한 클래스라 자주 변경 필요. 
    - Application 클래스의 main 메소드 실행 테스트
        - http://localhost:8080/h2-console로 접속
        - JDBC URL 주소는 `jdbc:h2:mem:testdb`로 설정, 입력 후 'Connect' 버튼 클릭
        - POSTS 테이블이 보이면 성공
        - 간단하게 데이터 삽입
          ```SQL
          insert into posts (author, content, title) values ('author', 'content', 'title');
          ```
        - 확인 : http://localhost:8080/api/v1/posts/1

### 3.5 JPA Auditing으로 생성시간/수정시간 자동화하기
- 매번 DB에 삽입(insert), 갱신(update) 전 날짜를 등록/수정하는 코드가 생기는 문제 해결

### Ch.3 마무리
- JPA / Hibernate / Spring Data Jpa의 관계
- Spring Data Jpa를 이용해 관계형 DB를 객체지향적으로 관리하는 법
- JPA의 더티 체킹을 이용하면 Update 쿼리 없이 테이블 수정 가능
- JPA Auditing을 이용해 등록/수정 시간 자동화

## Ch.4 머스테치(Mustache)로 화면 구성하기
- 공부하기
    - 서버 템플릿 엔진 vs 클라이언트 템플릿 엔진 차이
    - JSP가 아닌 머스테치를 선택한 이유?
    - 기본적인 CRUD 화면 개발 방법

### 4.1 서버 템플릿 엔진과 머스테치 소개
- 템플릿 엔진 : 지정된 템플릿 양식 + 데이터 = HTML 문서 출력
- 예 : JSP, Freemarker, React, Vue 등
- 템플릿 엔진 단점
    - JSP, Velocity : 스프링 부트에서는 권장하지 않음
    - Freemarker : 과도한 기능 지원. 높은 자유도로 인해 숙련도가 낮을수록 Freemarker 안에 비즈니스 로직이 추가될 수 있음.
    - Thymeleaf : 스프링 진영에서 밀고 있으나 문법이 어렵다. 태그 속성 방식, Javascript 프레임워크가 익숙하면 사용
- 머스테치 장점
    - 문법이 다른 템플릿 엔진보다 심플.
    - 로직 코드 사용 가능. View 역할/서버 역할 명확하게 분리
    - Mustache.js와 Mustache.java 2가지가 다 있음. 하나의 문법으로 클라이언트/서버 템플릿 모두 사용 가능
- 작가 생각 : 템플릿 엔진은 화면 역할에만 충실해야 한다.

### 4.2 머스테치 플러그인 설치
- IntelliJ : 플러그인 있음
  
### 4.3 게시글 등록 화면 만들기
- 3장에서 PostApiController로 API 구현 → 여기서 바로 화면 개발
- 부트스트랩 이용, 외부 CDN 사용
    - 사용 방법 : HTML/JSP/Mustache 코드에 1줄 추가
    - __주의__ 실제 서비스에서는 잘 사용하지 않는 방법. 외부 서비스에 의존하는 형태라, CDN을 서비스하는 곳에 문제가 생기면 우리 웹 사이트에도 문제가 생긴다.
- 레이아웃 방식으로 추가
    - 레이아웃 : 공통 영역을 별도의 파일로 분리, 필요한 곳에 가져다 쓰는 방식
  
### 4.4 전체 조회 화면 만들기
- Querydsl
    - Query Domain Specific Language : 쿼리 영역(?)에 특화된 언어
    - 규모가 있는 프로젝트에서의 데이터 조회 : FK의 조인, 복잡한 조건 등으로 조회용 프레임워크 추가
        - Entity 클래스만으로는 처리하기 어렵다.
        - 예 : Querydsl, jooq, MyBatis 등
- Querydsl 추천 이유
    - 타입 안정성 보장
        - 메소드 기반 쿼리 생성 : 오타, 존재하지 않는 칼럼명은 IDE에서 자동으로 검출
    - 국내의 많은 회사에서 사용
        - 쿠팡, 배달의민족 등
    - 레퍼런스가 많다

### 4.5 게시글 수정, 삭제 화면 만들기
- REST에서 CRUD는 아래와 같이 HTTP Method에 매핑됨
    ```
    생성(Create) - POST
    읽기(Read) - GET
    수정(Update) - PUT
    삭제(Delete) - DELETE
    ```
- 이쯤에서 기능 만드는 절차 정리
    - mustache 작성 or 수정 (예: posts-update.mustache)
    - 이벤트를 진행할 JS 코드 추가/수정 (예: index.js)
    - 서비스 메소드 생성/수정 (예: PostsService 자바 클래스 내 delete 메소드 등)
    - API 컨트롤러 생성/수정 (예: PostsService 클래스에 만들었으면 PostsApiController에 추가)

### Ch.4 마무리
- 서버 템플릿 엔진과 클라이언트 템플릿 엔진의 차이
- 머스테치의 기본 사용 방법
- 스프링 부트에서의 화면 처리 방식
- js/css의 선언 위치를 다르게 하여 웹 사이트의 로딩 속도를 향상하는 방법
- js 객체를 이용해 브라우저의 전역 변수 충돌 문제 회피
  
## Ch.5 스프링 시큐리티와 OAuth 2.0으로 로그인 기능 구현하기
- 스프링 시큐리티 : 막강한 인증(Authentication), 인가(Authorization) 기능을 가진 프레임워크
    - 인터셉터, 필터 기반 보안 구현보다 훨씬 낫다
- MVC, Data, Batch 등 다양한 요구사항을 손쉽게 추가/변경 가능

### 5.1 스프링 시큐리티와 스프링 시큐리티 OAuth2 클라이언트
- 소셜 로그인 기능
    - 로그인 기능을 직접 구현할 경우 배보다 배꼽이 더 커진다
    - 직접 구현하려면 필요한 것
        - 로그인 시 보안
        - 회원가입 시 이메일/전화번호 인증
        - 비밀번호 찾기
        - 비밀번호 변경
        - 회원정보 변경
    - OAuth 로그인 구현 시 위의 항목들은 모두 구글, 네이버, 카카오, 페이스북 등에 맡기면 된다.
- 스프링부트 1.5 vs 2.0
    - spring-security-oauth2-autoconfigure 라이브러리 덕분에 설정 방법은 비슷
    - 하지만 이 책에서는 Spring Security OAuth2 Client 라이브러리로 진행. 이유는...
        - 신규 기능은 새 OAuth2에서만 지원
        - 스프링 부트용 라이브러리(starter) 출시
        - 신규 라이브러리의 경우 확장 포인트를 고려해 설계. 기존 방식은 확장 포인트가 적절하게 오픈되어 있지 않아 직접 상속하거나 오버라이딩해야 한다.
- 이 책 이외에 2.0 방식의 자료를 찾고 싶다면 아래를 확인하기
    - spring-security-oauth2-autoconfigure 라이브러리를 사용했는지?
    - application.properties 혹은 application.yml 정보가 아래와 같이 차이가 있는지?
        - Spring Boot 1.5
            - URL을 모두 명시해야 한다.
            ```yml
            google :
                client :
                    clientId: 인증정보
                    clientSecret: 인증정보
                    accessTokenUri: https://accounts.google.com/o/oauth2/token
                    userAuthorizationUri: https://accounts.google.com/o/oauth2/auth
                    clientAuthenticationScheme: form
                    scope: email, profile
                resource :
                    userInfoUri: https://www.googleapis.com/oauth2/v2/userinfo
            ```
        - Spring Boot 2.0
            - client 인증 정보만 입력하면 된다.
            ```yml
            spring:
                security:
                    oauth2:
                        client:
                            clientId: 인증정보
                            clientSecret: 인증정보
            ```

### 5.2 구글 서비스 등록
- __주의__ OAuth 동의 화면을 먼저 만들어야 OAuth 클라이언트 ID를 생성할 수 있다.
- OAuth 동의 화면 : User Type은 외부만 선택 가능할 수도 있음
    - 범위
        - 기본값(범위 추가 안 했을 때) : email, profile, openid
        - 최신 등록 화면에서는 위 3개가 선택지에 안 보일 수도 있음
    - 테스트 사용자
        - 테스트 중이라면 여기에 사용자(구글 이메일)를 등록해야만 로그인이 가능하다.
        - https://soda-dev.tistory.com/m/60
- application-oauth.properties
    - spring.security.oauth2.client.registration.google.scope=profile,mail
        - 많은 예제에서는 이 scope를 별도로 등록하지 않는다. 기본값이 openid, profile, email이기 때문
        - 강제로 profile, email을 등록한 이유 : openid라는 scope가 있으면 openid provider로 인식하기 때문.
        - 이렇게 되면 OpenId Provider인 서비스(구글)와 그렇지 않은 서비스(네이버/카카오 등)로 나눠서 각각 OAuth2Service를 만들어야 한다.
        - 하나의 OAuth2Service로 사용하기 위해 일부러 openid scope를 빼고 등록
    - 작성이 끝났으면 .gitignore에 등록하기
    - .gitignore도 반드시 올라가야 한다! 안 그러면 파일 무시가 안 되고 그대로 올라간다.