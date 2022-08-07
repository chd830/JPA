package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
// Setter대신 생성자를 통해서 값을 할당
// JPA구현 라이브러리가 리플렉션이나 프록시 같은 기술을 사용해서 객체를 생성할 수 있도록 함
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 함부로 new로 생성하지 못하게 선언
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
