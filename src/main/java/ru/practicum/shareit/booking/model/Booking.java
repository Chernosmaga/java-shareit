package ru.practicum.shareit.booking.model;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс-модель для создания объекта бронирования со свойствами <b>id</b>, <b>startTime</b>, <b>endTime</b>,
 * <b>isBooked</b>, <b>item</b>, <b>booker</b>, <b>status</b>
 */
@Data
public class Booking {
    private Long id;
    @NotNull(message = "Время начала бронирования не должно быть пустым")
    @FutureOrPresent(message = "Невозможно начать бронирование товара в прошлом")
    private LocalDateTime startTime;
    @NotNull(message = "Время окончания бронирования не должно быть пустым")
    @FutureOrPresent(message = "Невозможно закончить бронирование товар в прошлом")
    private LocalDateTime endTime;
    @NotNull(message = "Статус наличия брони не должен быть пустым")
    private Boolean isBooked;
    @NotNull(message = "Поле бронируемого товара не может быть пустым")
    private Item item;
    @NotNull(message = "Поле пользователя бронирующего товар не может быть пустым")
    private User booker;
    @NotNull(message = "Статус не может быть пустым")
    private Status status;
}
