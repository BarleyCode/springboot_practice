package com.barley.book.springboot.web.domain.posts;
import com.barley.book.springboot.domain.posts.Posts;
import com.barley.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    /*
    @After
    - Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    - 보통은 배포 전 전체 테스트를 수행할 때 테스트 간 데이터 침범을 막기 위해 사용
    - 여러 테스트가 동시에 수행되면 테스트용 DB인 H2에 데이터가 그대로 남아 있음 → 다음 테스트 실행 시 실패할 수 있음

    */
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void post_save_load() {
        /*
        post_save_load() 원래 이름 : 게시글저장_불러오기()

        postsRepository.save
        - 테이블 posts에 insert/update 쿼리를 실행
        - id 값이 있다면 update, 없다면 insert 쿼리 실행

        postsRepository.findAll
        - 테이블 posts에 있는 모든 데이터를 조회

        */
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("barley")
                .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
