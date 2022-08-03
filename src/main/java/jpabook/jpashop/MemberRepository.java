package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
//        command 와 query를 분리하여 저장을 하고 다시 조회할 id 반환
        return member.getId();
    }

     public Member find(Long id) {
        return em.find(Member.class, id);
     }

}
