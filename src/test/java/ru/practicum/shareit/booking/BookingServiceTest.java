package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.enums.State;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.service.ItemService;
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
public class BookingServiceTest {
    private final BookingService bookingService;
    private final UserService userService;
    private final ItemService itemService;

    private final UserDto arina = new UserDto(
            2L,
            "Arina",
            "arina@mail.ru");
    private final UserDto ilya = new UserDto(
            3L,
            "Iliya",
            "iliya@ya.ru");
    private final ItemShortDto flour = new ItemShortDto(
            1L,
            "Baking flour",
            "In addition, I'll give you a baking powder",
            true,
            null);
    private final BookingShortDto booking = new BookingShortDto(
            LocalDateTime.of(2024, 10, 10, 10, 10, 0),
            LocalDateTime.of(2024, 12, 10, 10, 10, 0),
            1L);

    @Test
    void create_shouldThrowExceptionIfOwnerIsBooking() {
        UserDto thisArina = userService.create(arina);
        ItemDto thisFlour = itemService.create(thisArina.getId(), flour);
        BookingShortDto booking = new BookingShortDto(
                LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now().plusSeconds(5),
                thisFlour.getId());
        assertThrows(ObjectNotFoundException.class,
                () -> bookingService.create(thisArina.getId(), booking));
    }

    @Test
    void create_shouldCreateBooking() {
        UserDto thisArina = userService.create(arina);
        UserDto thisIlya = userService.create(ilya);
        ItemDto thisItem = itemService.create(thisArina.getId(), flour);
        BookingShortDto booking = new BookingShortDto(
                LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now().plusSeconds(5),
                thisItem.getId());
        BookingDto thisBooking = bookingService.create(thisIlya.getId(), booking);

        assertEquals(thisBooking.getBooker().getId(), thisIlya.getId());
        assertEquals(thisBooking.getItem().getId(), thisItem.getId());
    }

    @Test
    void getById_shouldReturnBookingIfOwnerRequesting() {
        UserDto thisArina = userService.create(arina);
        UserDto thisIlya = userService.create(ilya);
        ItemDto thisItem = itemService.create(thisArina.getId(), flour);
        BookingShortDto booking = new BookingShortDto(
                LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now().plusSeconds(5),
                thisItem.getId());
        BookingDto thisBooking = bookingService.create(thisIlya.getId(), booking);
        BookingDto returnedBooking = bookingService.getById(thisBooking.getId(), thisArina.getId());

        assertThat(returnedBooking.getItem(), equalTo(returnedBooking.getItem()));
    }

    @Test
    void getBookingsByUser_shouldReturnBookingsByUserAndSizeNotNull() {
        UserDto thisArina = userService.create(arina);
        UserDto thisIlya = userService.create(ilya);
        ItemDto thisItem = itemService.create(thisArina.getId(), flour);
        BookingShortDto booking = new BookingShortDto(
                LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now().plusSeconds(5),
                thisItem.getId());
        BookingDto thisBooking = bookingService.create(thisIlya.getId(), booking);
        List<BookingDto> bookings = bookingService.getBookingsByUser(thisIlya.getId(), State.ALL, 0, 1);

        assertTrue(bookings.contains(thisBooking));
        assertEquals(1, bookings.size());
    }

    @Test
    void getBookingsByUser_shouldReturnBookingsByUserWithStatusRejectedAndSizeNotNull() {
        UserDto thisArina = userService.create(arina);
        UserDto thisIlya = userService.create(ilya);
        ItemDto thisItem = itemService.create(thisArina.getId(), flour);
        BookingShortDto booking = new BookingShortDto(
                LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now().plusSeconds(5),
                thisItem.getId());
        bookingService.create(thisIlya.getId(), booking);
        List<BookingDto> bookings = bookingService.getBookingsByUser(thisIlya.getId(), State.REJECTED, 0, 1);

        assertEquals(0, bookings.size());
    }

    @Test
    void getBookingsByOwner_shouldReturnBookingsByOwnerWithStatusRejectedAndSizeNotNull() {
        UserDto thisArina = userService.create(arina);
        UserDto thisIlya = userService.create(ilya);
        ItemDto thisItem = itemService.create(thisArina.getId(), flour);
        BookingShortDto booking = new BookingShortDto(
                LocalDateTime.now().plusSeconds(1),
                LocalDateTime.now().plusSeconds(5),
                thisItem.getId());
        bookingService.create(thisIlya.getId(), booking);
        List<BookingDto> bookings = bookingService.getBookingsByUser(thisIlya.getId(), State.REJECTED, 0, 1);

        assertEquals(0, bookings.size());
    }
}
