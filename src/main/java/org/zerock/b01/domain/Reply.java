package org.zerock.b01.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply", indexes = {@Index(name = "idx_reply_board_bno", columnList = "board_bno")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board") // 동시 조회시 상호 참조에 대한 문제가 발생할 수 있으므로 (exclude = "board") 지정해야함
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    //FetchType.EAGER는 즉시 로딩, 해당 엔티티 로딩시 같이 로딩, 성능에 영향을 줄 수 있으므로 우선은 LAZY
    //글로벌 페치 전략, EAGER(기본값)는 연관엔티티를 동시에 조회, LAZY는 연관 엔티티를 실제 사용할 때 조회, ManyToOne시에는 LAZY로 지정
    @ManyToOne(fetch = FetchType.LAZY)  // @JoinColumn(name = "board") 칼럼명이 다를 경우
    private Board board;

    private String replyText;

    private String replyer;


    public void changeText(String text){
        this.replyText = text;
    }
}
