package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.enums.State;

import java.util.List;

@RestController("ServerBookingController")
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestBody BookingShortDto booking) {
        return bookingService.create(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateStatus(@PathVariable Long bookingId,
                                   @RequestHeader("X-Sharer-User-Id") Long userId,
                                   @RequestParam Boolean approved) {
        return bookingService.updateStatus(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getById(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getById(bookingId, userId);
    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam String state,
                                               @RequestParam Integer from,
                                               @RequestParam Integer size) {
        return bookingService.getBookingsByOwner(userId, State.valueOf(state), from, size);
    }

    @GetMapping
    public List<BookingDto> getBookingsByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestParam String state,
                                              @RequestParam Integer from,
                                              @RequestParam Integer size) {
        return bookingService.getBookingsByUser(userId, State.valueOf(state), from, size);
    }
}
