package me.pushstone.demospringdata;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable // 엔티티가 아닌, 엔티티 안의 멤버변수중 하나로 취급될때 사용
// Address의 생명주기가 Account에 속해져있음. = value타입
public class Address {

    @Column // 얘네도 컬럼이 생략되있음.
    private String street;

    private String city;

    private String state;

    private String zipCode;


}
