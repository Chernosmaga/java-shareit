package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.comment.dto.CommentShortDto;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceTest {
    private final ItemService itemService;
    private final UserService userService;
    private final UserMapper userMapper;

    private final User matthew = new User(
            1L,
            "Matthew",
            "matthew@mail.ru");
    private final UserDto andrew = new UserDto(
            2L,
            "Andrew",
            "andrew@ya.ru");
    private final UserDto anna = new UserDto(
            2L,
            "Anna",
            "anna@yandex.ru");
    private final ItemShortDto toy = new ItemShortDto(
            5L,
            "A toy",
            "An old ugly toy",
            true,
            null);
    private final ItemShortDto laptop = new ItemShortDto(
            6L,
            "An old laptop",
            "Maybe someone needs this cowboy",
            true,
            null);

    @Test
    void create_shouldCreateAnItem() {
        UserDto thisUser = userService.create(userMapper.toUserDto(matthew));
        ItemDto thisItem = itemService.create(thisUser.getId(), toy);

        assertEquals(thisItem.getDescription(), toy.getDescription());
        assertEquals(userMapper.toUser(thisUser), thisItem.getOwner());
    }

    @Test
    void deleteById_shouldNotDeleteIfUserIsNotAnOwner() {
        UserDto annaDto = userService.create(anna);
        UserDto andrewDto = userService.create(andrew);
        ItemDto thisItem = itemService.create(andrewDto.getId(), toy);

        assertThrows(ObjectNotFoundException.class,
                () -> itemService.deleteById(thisItem.getId(), annaDto.getId()));
    }

    @Test
    void update_shouldUpdateAnItem() {
        UserDto andrewDto = userService.create(andrew);
        ItemDto thisItem = itemService.create(andrewDto.getId(), toy);
        ItemShortDto itemToUpdate = new ItemShortDto(
                thisItem.getId(),
                "An old toy",
                "I think I might sell it",
                true,
                null
        );
        ItemDto updatedItem = itemService.update(andrewDto.getId(), thisItem.getId(), itemToUpdate);

        assertThat(updatedItem.getName(), equalTo("An old toy"));
        assertThat(updatedItem.getDescription(), equalTo("I think I might sell it"));
        assertTrue(updatedItem.getAvailable());
    }

    @Test
    void deleteById_shouldThrowExceptionIfItemDoesNotExists() {
        UserDto andrewDto = userService.create(andrew);

        assertThrows(ObjectNotFoundException.class,
                () -> itemService.deleteById(andrewDto.getId(), 999L));
    }

    @Test
    void update_shouldThrowExceptionIfNotAnOwnerOfUpdatingItem() {
        UserDto annaDto = userService.create(anna);
        UserDto andrewDto = userService.create(andrew);
        ItemDto thisItem = itemService.create(andrewDto.getId(), toy);
        ItemShortDto itemToUpdate = new ItemShortDto(
                thisItem.getId(),
                "An old toy",
                "I think I might sell it",
                true,
                null
        );

        assertThrows(ObjectNotFoundException.class,
                () -> itemService.update(annaDto.getId(), thisItem.getId(), itemToUpdate));
    }

    @Test
    void getById_shouldReturnItem() {
        UserDto andrewDto = userService.create(andrew);
        ItemDto thisItem = itemService.create(andrewDto.getId(), toy);
        ItemDto returnedItem = itemService.getById(andrewDto.getId(), thisItem.getId());

        assertEquals(thisItem, returnedItem);
    }

    @Test
    void search_shouldReturnItems() {
        UserDto andrewDto = userService.create(andrew);
        itemService.create(andrewDto.getId(), toy);
        itemService.create(andrewDto.getId(), laptop);
        List<ItemDto> items = itemService.search("laptop", 0, null);

        assertEquals(1, items.size());
    }

    @Test
    void search_shouldReturnAnEmptyList() {
        UserDto andrewDto = userService.create(andrew);
        itemService.create(andrewDto.getId(), toy);
        itemService.create(andrewDto.getId(), laptop);
        List<ItemDto> items = itemService.search("magazine", 0, null);

        assertEquals(0, items.size());
    }

    @Test
    void createComment_shouldThrowExceptionIfUserIsNotABooker() {
        UserDto andrewDto = userService.create(andrew);
        UserDto annaDto = userService.create(anna);
        ItemDto thisItem = itemService.create(andrewDto.getId(), toy);
        CommentShortDto comment = new CommentShortDto(3L,
                "I user your toy for some nasty games",
                annaDto.getName(),
                LocalDateTime.now());

        assertThrows(AccessException.class,
                () -> itemService.createComment(thisItem.getId(), andrewDto.getId(), comment));
    }
}
