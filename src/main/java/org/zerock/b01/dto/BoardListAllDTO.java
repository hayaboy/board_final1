package org.zerock.b01.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListAllDTO {  //Board 객체 안에 Set과 같이 중첩된 구조를 처리할 경우 직접 Tuple을 이용해서 DTO로 변환하는 방식이 편리

    private Long bno;

    private String title;

    private String writer;

    private LocalDateTime regDate;

    private Long replyCount;


    private List<BoardImageDTO> boardImages;


}
