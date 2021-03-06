package me.pushstone.demospringdata;

import javax.persistence.*; // 어노테이션들은 javax.persistence 패키지에서 오는걸로 써야함.
import java.util.Date;
import java.util.Set;

@Entity(name ="myAccount") // 이 어노테이션 덕분에 db의 테이블과 매핑이 됨 , 사실상 @Table 어노테이션도 생략 되어있음
//name사용하지 않을시 기본적으로 클래스 이름 사용.
//위처럼 이름을 설정하면 하이버네이트,JPA 안에선 저 이름이 됨  -> 객체 인스턴스를 가르킬때 쓰는 이름임
@Table
//실제 테이블에 매핑되는 이름은 테이블 어노테이션에 설정가능 , 이때 테이블에 아무것도 설정하지 않을시 기본값이 Entity의 이름
//User는 Db에따라 사용 불가능한 db가 있으므로, Account를씀. or users
public class Account {
    //value 타입 vs 엔티티 타입
    //엔티티 타입 : 고유 식별자를 가지고있음 , 다른 엔티티에서 이 엔티티를 참조하는 경우가 생김.
    //value 타입 : 엔티티를 거쳐서 참조를함.
    //복합적인 value 타입 : (ex_ address) 주소가 엔티티에 속하는 멤버변수로 들어갈경우
    @Id// Id.java에서 볼수있음 -> 모든 원시형 가능, 원시 래퍼형가능, String, Date등 가능
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동생성 값 사용 : db에따라 생성전략이 달라짐, postgres는 시퀀스사용,
    // 명시적으로 설정가능. 기본적으로 AUTO , 복합키도 사용가능
    private Long id; // primitive vs reference타입 뭘쓸지 논쟁
    //long을 쓸경우 default값이 0으로 입력되므로, 실제 0번이랑 구분하기위해 wrapper타입 사용.

    //모든 엔티티에 들어있는 멤버변수에는 컬럼이 붙어있는거나 마찬가지.
    //컬럼에서 설정할수있는값은 컬럼이름 , unique, nullable, 업데이터블, 렝스 등등 설정가능.
    //SQL문법을써서 등록하고 싶은경우는 columnDefinition이용
    @Column(nullable = false, unique = true)
    private String username;

    private String password;


    // fk를 가진 study가 기본적으로 주인.
    @OneToMany(mappedBy = "onwer")// oneToMany쪽에 이 관계가 반대쪽에 뭐라 매핑되있는지, 관계를 정의한 필드를 여기 적어야함. study에서 account를 owner로 정의해둿다.
    //그래야 얘가 이에 따른 필요 관계를 또 안만들고, Study에 정의된 관계에 종속된 쪽이 됨.
    private Set<Study> studies;// 끝이 Many로 끝나니까 studies

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    @Temporal(TemporalType.TIMESTAMP) // 설정가능한거 3개지 , 날짜 시간 날짜+시간 = 타임스탬프
    private Date created = new Date();

    @Transient
    private String yes;
    //컬럼으로 안쓰고, 객체에서만 쓰는 데이터에필요한 칼럼일때
    //Transient 어노테이션 사용 -> 컬럼으로 매핑을 안해

    //composite한 value타입을 매핑하는 방법
    @Embedded // 컴포짓한 타입의 데이터를쓰고싶을경우.
    @AttributeOverrides({
            @AttributeOverride(name ="street", column = @Column(name = "home_street")) // name, column 필수값 , 이렇게 하나씩 매핑하면됨.

            //street value를 home_street으로 바꿔서 쓰겠다.
    })//컴포짓한 타입을 두개이상 쓰고싶을경우.
    private Address homeAddress;

/*
    @Embedded
    private Address officeAddress;
*/

    //getter setter는 반드시 필요한게 아님. 없어도 컬럼으로 매핑 됨.
    //spring.jpa.show-sql=true -> db변경정보 보여줌
    //spring.jpa.properties.hibernate.format_sql=true -> 좀더 읽기 쉽게 보여줌.
    //위 두개 properties에 넣을경우 테이블정보를 보여줌.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addStudy(Study study) { // convenient method, 이런식으로 메서드를 만들어서 밑의 두개 소스를 한묶음으로 사용함.
        this.getStudies().add(study); // -> optional but, 객체 지향적으로는 필요한 코드.
        study.setOwner(this);
    }
    public void removeStudy(Study study) { // convenient method, 이런식으로 메서드를 만들어서 밑의 두개 소스를 한묶음으로 사용함.
        this.getStudies().remove(study); // -> optional but, 객체 지향적으로는 필요한 코드.
        study.setOwner(null); // study가 참조하는 애가 더이상 내가 아니게 만듬.
    }
}
