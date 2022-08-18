package com.barley.book.springboot.service.posts;
import com.barley.book.springboot.domain.posts.Posts;
import com.barley.book.springboot.domain.posts.PostsRepository;
import com.barley.book.springboot.web.dto.PostsListResponseDto;
import com.barley.book.springboot.web.dto.PostsResponseDto;
import com.barley.book.springboot.web.dto.PostsSaveRequestDto;
import com.barley.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /*
    update()
    - 특징 : DB에 쿼리를 날리는 부분이 없다.
    - 가능한 이유 : JPA의 영속성 컨텍스트 때문
       - 영속성 컨텍스트 : 엔티티를 영구 저장하는 환경
    - JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 데이터베이스를 가져오면 → 영속성 컨텍스트가 유지된 상태.
    - 이 상태에서 해당 데이터의 값을 변경 = 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영.
    - 즉, Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없다. (= Dirty Checking)
    - 참고 : https://jojoldu.tistory.com/415

    */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException (
                "해당 게시글이 없습니다. id=" + id)
        );
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException (
                "해당 게시글이 없습니다. id=" + id)
        );
        return new PostsResponseDto(entity);
    }

    /*
    findAllDesc()
    - readOnly = true 옵션 : 트랜잭션 범위는 유지하되, 조회 기능만 남긴다 → 조회 속도 개선
      추천 : 등록, 수정, 삭제 기능이 없는 서비스 메소드
    - .map(PostsListResponseDto::new) = .map(posts -> new PostsListResposeDto(posts))

    */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

}
