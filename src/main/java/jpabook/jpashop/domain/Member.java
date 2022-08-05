package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    // 내장 타입으로 사용. Embedded나 Embedable 둘 중 하나만 있어도 됨
    @Embedded
    private Address address;

    // 1대다. mappedBy를 통해 order테이블의 member와 캐핑
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
