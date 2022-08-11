package com.barley.book.springboot.domain.posts;
import com.barley.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
annotation 꿀팁
- 주요 어노테이션은 클래스에 가깝게 둔다 (꼭 필요한 어노테이션일수록 클래스 선언문 가까이에 둔다)
- 예 : Lombok 어노테이션은 필수는 아니라서 위쪽에 둔다 → 나중에 필요 없을 떄 쉽게 삭제 가능

@Getter
- 클래스 내 모든 필드의 Getter 메소드를 재생성

@NoArgsConstructor
- 기본 생성자 자동 추가
- public Posts() {}와 효과가 같다.

@Entity
- 테이블과 링크될 클래스
- 기본값으로 클래스의 Camel Case 이름을 Underscore(_)로 매칭
- 예 : SalesManager.java → sales_manager table

*/
@Getter // Lombok annotation
@NoArgsConstructor // Lombok annotation
@Entity // JPA annotation
public class Posts extends BaseTimeEntity {
    /*
    Posts 클래스(= Entity 클래스)
    - 실제 DB의 테이블과 매칭될 클래스
    - JPA 사용 시 DB 데이터에 작업할 경우 실제 쿼리를 전송하기보다는 이 Entity 클래스의 수정을 통해 작업
    - BaseTimeEntity 상속 : 등록일/수정일 자동 입력됨

    참고
    - Entity의 PK는 Long 타입의 Auto_increment 추천(MySQL 기준 bigint)
    - 유니크 키(주민등록번호) 또는 여러 키를 조합한 복합키로 PK를 잡으면 난감해진다.
    - FK 맺을 때 다른 테이블에서 복합 키 전부를 갖고 있거나, 중간 테이블을 하나 더 둬야 한다.
    - 인덱스에 좋은 영향을 끼치지 못한다
    - 유니크한 조건이 변경될 경우 PK 전체를 수정해야 한다.

    @Id
    - 해당 테이블의 PK 필드
    
    @GeneratedValue
    - PK의 생성 규칙
    - 스프링부트 2.0 : GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 된다.
    - 스프링부트 1.5 vs 2.0 차이 : https://jojoldu.tistory.com/295
    
    @Column
    - 테이블의 칼럼을 나타냄. 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다.
    - 사용 이유 : 기본값 외에 추가로 변경이 필요한 옵션 있으면 사용
    - 문자열의 경우 기본값은 VARCHAR(255)이나, 사이즈를 500으로 또는 타입을 TEXT로 변경하는 등의 경우 사용

    @Builder
    - 해당 클래스의 빌더 패턴 클래스를 생성
    - 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
