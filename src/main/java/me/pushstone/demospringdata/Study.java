package me.pushstone.demospringdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Study {

    @Id @GeneratedValue
    private Long id;

    private String name;

    //스터디 원과의 관계를 만듬
    @ManyToOne // 스터디 입장에서 Many To one
    private Account owner;
    // 스터디 테이블안에 account table의 pk를 참조하는 foreign키 컬럼을 생성해서 갖고있게됨.
    // 반대쪽 엔티티를 가지고있는 Study가 현재 주인임. 주인->관계를 설정했을때, 그 값이 반영되는것.
    //
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
