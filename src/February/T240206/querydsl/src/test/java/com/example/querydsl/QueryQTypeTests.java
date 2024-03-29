package com.example.querydsl;

import com.example.querydsl.entity.Item;
import com.example.querydsl.entity.QItem;
import com.example.querydsl.entity.Shop;
import com.example.querydsl.repo.ItemRepository;
import com.example.querydsl.repo.ShopRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.querydsl.entity.QItem.item;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class QueryQTypeTests {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    // @BeforeEach : 각 테스트 전에 실행할 코드를 작성하는 영역
    @BeforeEach
    public void beforeEach() {
        Shop shopA = shopRepository.save(Shop.builder()
                .name("shopA")
                .description("shop A description")
                .build());
        Shop shopB = shopRepository.save(Shop.builder()
                .name("shopB")
                .description("shop B description")
                .build());

        itemRepository.saveAll(List.of(
                Item.builder()
                        .shop(shopA)
                        .name("itemA")
                        .price(5000)
                        .stock(20)
                        .build(),
                Item.builder()
                        .shop(shopA)
                        .name("itemB")
                        .price(6000)
                        .stock(30)
                        .build(),
                Item.builder()
                        .shop(shopB)
                        .name("itemC")
                        .price(8000)
                        .stock(40)
                        .build(),
                Item.builder()
                        .shop(shopB)
                        .name("itemD")
                        .price(10000)
                        .stock(50)
                        .build(),
                Item.builder()
                        .name("itemE")
                        .price(11000)
                        .stock(10)
                        .build(),
                Item.builder()
                        .price(10500)
                        .stock(25)
                        .build()
        ));
    }

    @Test
    public void qType() {
        QItem qItem = new QItem("item");
        Item found = jpaQueryFactory
                .select(qItem)
                .from(qItem)
                .where(qItem.name.eq("itemA"))
                .fetchOne();
        assertEquals("itemA", found.getName());

        found = jpaQueryFactory
                // SELECT * FROM 절
                .selectFrom(qItem)
                .where(qItem.name.eq("itemB"))
                .fetchOne();
        assertEquals("itemB", found.getName());

        // QItem 생성자의 인자가 Ailas로 동작
        QItem qItem2 = new QItem("item2");
        found = jpaQueryFactory
                .selectFrom(qItem2)
                .where(qItem2.name.eq("itemC"))
                .fetchOne();
        assertEquals("itemC", found.getName());

        // 함부로 섞어쓰면 예외 발생
        assertThrows(Exception.class, () -> {
            jpaQueryFactory
                    // SELECT item FROM Item item
                    .selectFrom(qItem)
                    // WHERE item2.name = "itemD" -> ?????
                    .where(qItem2.name.eq("itemD"))
                    .fetchOne();
        });
        // 섞어서 써야하는 경우는 나 자신과의 연관관계
        // 친구, 팔로우 User - ManyToMany - User

        // 평소에는 기본 정적 QItem 인스턴스를 사용
        found = jpaQueryFactory
                .selectFrom(QItem.item)
                .where(QItem.item.name.eq("itemA"))
                .fetchOne();
        assertEquals("itemA", found.getName());

        // import static 으로 바로 사용 가능
        found = jpaQueryFactory
                .selectFrom(item)
                .where(item.name.eq("itemB"))
                .fetchOne();
        assertEquals("itemB", found.getName());
    }
}
