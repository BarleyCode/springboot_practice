package com.barley.book.springboot.domain.posts;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Interface 생성하기
- IntelliJ : 우클릭 > New > Java Class > Interface 선택

JpaRepository
- DB Layer 접근자. ibatis 또는 MyBatis에서 DAO라고 불린다.
- JPA에서는 Repository라고 부르며, 인터페이스로 생성한다.
- 그냥 JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드가 자동으로 생성된다.
- 장점 : @Repository를 추가할 필요 없음. (대신 Entity 클래스와 기본 Entity Repository는 함께 있어야 한다)
    - 여기서는 같이 있는 'Posts'가 Entity 클래스.
*/

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
