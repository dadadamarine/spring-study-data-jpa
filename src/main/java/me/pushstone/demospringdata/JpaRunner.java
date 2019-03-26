package me.pushstone.demospringdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setPassword("pass");
        account.setUsername("ms");

        Study study = new Study();
        study.setName("Spring Data JPA");
        /*study.setOwner(account); // 주인인쪽에서 관계 설정 가능.*/

        /*account.getStudies().add(study);//단방향 @OneToMany 조인테이블 생성. -> 이걸로 구현할 경우, 주인이 아닌 account로 관계를 설정하는 이 코드의 경우
        * 관계에 대한 정보가 저장이 안됨.*/
        // 이렇게 onetoMany로 매핑할경우 세개의 테이블이 생김. (조인테이블)

        // 주인한테 관계를 설정해야 DB에 반영이 됨.

        // 양방향의 경우 FK를 가진쪽, 즉 ManyToOne 가진쪽이 주인
        // mappedby를 해주지 않을경우 양방향이 아니라 두개의 단방향 관계가 됨. (서로 연관관계가 없음)

        account.addStudy(study);
        //단방향 @ManyToOne은 FK생성

        Session session = entityManager.unwrap(Session.class);
        session.persist(account);
        session.save(study);// 저장할때도 account, study, account_study 3개 테이블에 각각 값을 저장함.
        //account, study 테이블은 관계에 대한 정보가 없이 깔끔함.
        //account_studies 테이블에 관계 정보가 저장됨.

    }
}
