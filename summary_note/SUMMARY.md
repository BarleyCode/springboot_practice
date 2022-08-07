# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스

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
      