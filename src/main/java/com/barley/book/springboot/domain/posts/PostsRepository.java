package com.barley.book.springboot.domain.posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

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
    /*
    @Query
    - SpringDataJpa에서 제공하지 않는 메소드는 쿼리로 작성 가능
    - 아래의 코드는 SpringDataJpa에서 제공하는 기본 메소드만으로 해결 가능하나,
      @Query가 가독성이 훨씬 좋다.

    */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
