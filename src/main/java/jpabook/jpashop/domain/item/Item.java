package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
// 상속관계의 전략 지정(부모에서).
// JOINED: 가장 정규화됨
// SINGLE_TALBE: 한테이블에 다 때려넣음
// TABLE_PER_CLASS: 클래스별로 테이블로 나눔
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 추상화한 클래스가 어떤 값을 가지고 있는지 받는 역할
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
