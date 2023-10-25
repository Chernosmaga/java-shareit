package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.exception.ObjectAlreadyExistsException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {
    private final UserService userService;
    private final UserDto user = new UserDto(1L, "Alexander", "alex@ya.ru");

    @Test
    void getById_shouldReturnUserById() {
        UserDto thisUser = userService.create(user);

        assertThat(thisUser.getName(), equalTo(user.getName()));
        assertThat(thisUser.getEmail(), equalTo(user.getEmail()));
    }

    @Test
    void deleteById_shouldThrowExceptionIfIdIsIncorrect() {
        assertThrows(ObjectNotFoundException.class,
                () -> userService.deleteById(999L));
    }

    @Test
    void deleteById_shouldDeleteUser() {
        UserDto thisUser = userService.create(user);
        userService.deleteById(thisUser.getId());
        List<UserDto> users = userService.getUsers();

        assertTrue(users.isEmpty());
    }

    @Test
    void update_shouldUpdateUserData() {
        UserDto thisUser = userService.create(new UserDto(2L, "Matthew", "matthew@mail.ru"));
        thisUser.setName("Ivan");
        UserDto updatedUser = userService.update(thisUser.getId(), thisUser);

        assertThat(updatedUser.getName(), equalTo("Ivan"));
        assertEquals(thisUser.getEmail(), updatedUser.getEmail());
    }
}
