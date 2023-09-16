package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

@SpringBootTest
public class UserControllerTest {
    private final UserService userService;

    @Autowired
    public UserControllerTest(UserService userService) {
        this.userService = userService;
    }

    private final User user = new User("Josh", "joshesmail@mail.ru");

    @Test
    void create_shouldCreateUser() {
        User thisUser = userService.create(user);

        Assertions.assertEquals(1L, thisUser.getId());
    }

    @Test
    void update_shouldUpdateUser() {
        User thisUser = userService.getById(1L);
        thisUser.setName("Andrew");
        User updatedUser = userService.update(thisUser.getId(), thisUser);

        Assertions.assertEquals(thisUser.getEmail(), updatedUser.getEmail());
    }
}
