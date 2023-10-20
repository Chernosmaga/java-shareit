package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.exception.ObjectAlreadyExistsException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {
    @Mock
    private final UserRepository userRepository;
    private UserService userService;
    private final UserMapper mapper = new UserMapper();

    private final UserDto user = new UserDto(1L, "Alexander", "alex@ya.ru");


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, mapper);
    }

    @Test
    void getById_shouldThrowExceptionIfUserIdIsInvalid() {
        when(userRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        final ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class, () -> userService.getById(999L));
        assertEquals("Пользователь не найден", exception.getMessage());
    }

    @Test
    void shouldExceptionWhenCreateUserWithExistEmail() {
        when(userRepository.save(any()))
                .thenThrow(new DataIntegrityViolationException(""));
        final ObjectAlreadyExistsException exception = assertThrows(
                ObjectAlreadyExistsException.class, () -> userService.create(user));
        assertEquals("Данные о пользователе уже есть в системе", exception.getMessage());
    }
}