package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestServiceTest {
    private final RequestService requestService;
    private final UserService userService;

    private final UserDto andrew = new UserDto(
            1L,
            "Andrew",
            "Andrew@mail.ru");
    private final UserDto anna = new UserDto(
            2L,
            "Anna",
            "anna@ya.ru");
    private final RequestDto request = new RequestDto(
            21L,
            "I need a pillow to sleep on",
            andrew,
            LocalDateTime.of(2022, 10, 12, 21, 40, 0),
            null);
    private final RequestDto secondRequest = new RequestDto(
            22L,
            "I need a comb",
            anna,
            LocalDateTime.of(2020, 10, 12, 21, 40, 0),
            null);

    @Test
    void create_shouldCreateRequest() {
        UserDto thisUser = userService.create(andrew);
        RequestDto thisRequest = requestService.create(thisUser.getId(), request,
                LocalDateTime.of(2022, 10, 12, 21, 40, 0));

        assertThat(thisRequest.getDescription(), equalTo(request.getDescription()));
    }

    @Test
    void getById_shouldThrowExceptionIfIdIsIncorrect() {
        UserDto thisUser = userService.create(andrew);

        assertThrows(ObjectNotFoundException.class,
                () -> requestService.getRequestById(thisUser.getId(), 999L));
    }

//    @Test
//    void getExistingRequests_shouldReturnItemRequests() {
//        UserDto thisAndrew = userService.create(andrew);
//        UserDto thisAnna = userService.create(anna);
//        RequestDto andrewsRequest = requestService.create(thisAndrew.getId(), request,
//                LocalDateTime.of(2022, 10, 12, 21, 40, 0));
//        RequestDto annasRequest = requestService.create(thisAnna.getId(), secondRequest,
//                LocalDateTime.of(2020, 10, 12, 21, 40, 0));
//        List<RequestDto> requests = requestService.getExistingRequests(thisAndrew.getId(), 0, 10);
//
//        assertEquals(1, requests.size());
//    }

    @Test
    void getRequestsByOwner_shouldReturnOwnerRequests() {
        UserDto thisAndrew = userService.create(andrew);
        RequestDto andrewsRequest = requestService.create(thisAndrew.getId(), request,
                LocalDateTime.of(2022, 10, 12, 21, 40, 0));
        RequestDto annasRequest = requestService.create(thisAndrew.getId(), secondRequest,
                LocalDateTime.of(2020, 10, 12, 21, 40, 0));
        List<RequestDto> requests = requestService.getRequestsByOwner(thisAndrew.getId());

        assertEquals(2, requests.size());
        assertTrue(requests.contains(andrewsRequest));
        assertTrue(requests.contains(annasRequest));
    }

    @Test
    void getRequestById_shouldReturnRequest() {
        UserDto thisAndrew = userService.create(andrew);
        RequestDto andrewsRequest = requestService.create(thisAndrew.getId(), request,
                LocalDateTime.of(2022, 10, 12, 21, 40, 0));
        RequestDto returnedRequest = requestService.getRequestById(thisAndrew.getId(), andrewsRequest.getId());

        assertThat(returnedRequest.getDescription(), equalTo(request.getDescription()));
    }
}
