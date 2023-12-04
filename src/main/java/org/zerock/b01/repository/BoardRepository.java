package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    @EntityGraph(attributePaths = {"imageSet"}) //attributePaths() 임시 가져오기 그래프 구성을 통해 가져오기 그래프를 사용자 정의할 수 있도록 하여
    // 동적 EntityGraph 정의를 지원합니다. attributePaths()가 지정되면 엔터티 그래프 이름 value()를 무시하고 이 EntityGraph를 동적으로 처리
    @Query("select b from Board b where b.bno =:bno") //bno는 Spring Data JPA의 쿼리 메소드나 네이티브 쿼리에서 사용되는 파라미터 바인딩 문법입니다. 여기서 :bno는 이름이 "bno"인 파라미터
    Optional<Board> findByIdWithImages(Long bno);


}
