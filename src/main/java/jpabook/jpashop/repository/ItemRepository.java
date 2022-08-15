package jpabook.jpashop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // id값이 없다면 새로 생성한 객체를 의미함
        if(item.getId() == null) {
            // 신규로 등록
            em.persist(item);
        } else {
            // id의 값이 있다면 update와 유사하게 동작하는 merge사용
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM item i", Item.class)
                .getResultList();
    }
}
