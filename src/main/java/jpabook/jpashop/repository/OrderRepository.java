package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        // ordersearch 가 없을 경우 동적으로 처리 해야 함
//        return em.createQuery("SELECT o FROM Order o JOIN o.member m"
//                + "WHERE o.status = :status"
//                + "AND m.name LINKE :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult(100) // 페이징 처리할 때 100개씩 1000개를 가져옴
//                .setMaxResults(1000)
//            .getResultList();

        String jpql = "SELECT o FROM Order o JOIN o.member m";
        boolean isFirstCondition = true;
        if(orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " WHERE";
                isFirstCondition = false;
            } else {
                jpql += " AND";
            }
        }
        jpql += " o.status = :status";

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            if(isFirstCondition) {
                jpql += " WHERE";
                isFirstCondition = false;
            } else {
                jpql += " AND";
            }
            jpql += " m.name LIKE :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class);
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    /**
     * JPA Criteria
     * 동적 쿼리를 JPQL로 작성
     *
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member" , JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        if(orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%"+orderSearch.getMemberName());
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }


}
