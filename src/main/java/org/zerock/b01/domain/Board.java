package org.zerock.b01.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false) //컬럼의 길이와 null허용여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    //@OneToMany의 fetch 속성(EAGER : 연관 엔티티를 동시에 조회, LAZY는 연관 엔티티를 실제 사용할 때 조회, 기본값 : LAZY
    //@ManyToOne의 fetch 속성(EAGER : 연관 엔티티를 동시에 조회, LAZY는 연관 엔티티를 실제 사용할 때 조회, 기본값 : EAGER
    //CascadeType.ALL 상위엔티티(Board)의 모든 상태 변경이 하위 엔티티(BoardImage)에 적용, FetchType.LAZY
    //orphanRemoval = true  //cascade 속성이 ALL이긴 하지만 삭제되지 않아서 orphanRemoval = true로 해야 실제 삭제가 이루어짐
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default //@Builder.Default 애노테이션을 사용하면 특정 프로퍼티에 대해 기본값을 지정
    @BatchSize(size = 20)
    private Set<BoardImage> imageSet = new HashSet<>();


    //게시글에 이미지(이미지 갯수) 추가

    public void addImage(String uuid, String fileName){


       BoardImage boardImage= BoardImage.builder().uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(boardImage);
    }


    // 이미지 삭제, 첨부 파일을 모두 삭제하므로 BoardImage 객체의 Board 참조를 null로 변경
    public void clearImages(){
        imageSet.forEach( boardImage -> {boardImage.changeBoard(null);});
    }








    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }







}
