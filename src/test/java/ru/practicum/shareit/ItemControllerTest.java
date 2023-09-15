package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@SpringBootTest
public class ItemControllerTest {
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public ItemControllerTest(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    private final ItemDto item = new ItemDto("laptop", "xiaomi laptop", true);
    private final User user = new User("Matthew", "matthew@mail.ru");

    @Test
    void createItem_shouldCreateItem() {
        User thisUser = userService.createUser(user);
        ItemDto thisItem = itemService.createItem(thisUser.getId(), item);

        Assertions.assertEquals(true, thisItem.getAvailable());
    }

    @Test
    void search_shouldFindAnItem() {
        User thisUser = userService.getUserById(1L);
        ItemDto thisItem = itemService.createItem(thisUser.getId(), item);
        List<ItemDto> list = itemService.search("Lap");

        Assertions.assertTrue(list.contains(thisItem));
    }
}
