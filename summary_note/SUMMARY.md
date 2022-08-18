# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스
- 참고 : https://github.com/jojoldu/freelec-springboot2-webservice/
- 코드 수정 후 확인 시 반드시 서버 재시작(IntelliJ에서 Rerun 클릭)

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