package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // JPA의 EntityManager를 주입
//    @PersistenceContext
    private final EntityManager em;

      // 직접 주입 할 수도 있음
//    @PersistenceUnit
//    private EntityManagerFactory factory;


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // SQL과 기능적으로 동일하지만 조금 다름.
    // Table을 대상으로 하는 쿼리가 아닌 엔티티를 대상으로 쿼리 조회
    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
