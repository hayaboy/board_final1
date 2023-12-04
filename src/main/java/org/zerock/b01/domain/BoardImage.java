package org.zerock.b01.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{

    @Id
    private String uuid;

    private String fileName;

    // 게시글 당 이미지의 갯수, 각각의 순서
    private int ord;


    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other) {
        return this.ord- other.ord;
    }


    // Board 엔티티 삭제시 BoardImage 객체의 참조도 변경하기 위해
    public void changeBoard(Board board){
        this.board = board;
    }
}
