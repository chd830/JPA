package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delevery_id")
    private Long id;

    // 1대1의 관계이기 때문에 어디에 연관관계의 주인을 선언해도 상관없다
    @OneToOne(mappedBy = "delivery")
    private Order order;

    // 내장 타입
    @Embedded
    private Address address;

    // ORDINAL: 1, 2, 3, 4 숫자로 들어감. enum이 추가된다면 장애가 날 수 있음
    // STRING: 문자로 들어감
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
