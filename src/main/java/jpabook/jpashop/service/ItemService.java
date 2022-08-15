package jpabook.jpashop.service;

import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.domain.item.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // readonly true이면 저장이 되지 않기 때문에 Transaction어노테이션 선언
    @Transactional
    public void saeItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
