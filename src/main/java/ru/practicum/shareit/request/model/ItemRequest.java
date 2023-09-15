package ru.practicum.shareit.request.model;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * Класс-модель для создания объекта запроса бронирования товара со свойствами <b>id</b>, <b>description</b>,
 * <b>requester</b>, <b>creationTime</b>
 */
@Data
public class ItemRequest {
    private Long id;
    private String description;
    private User requester;
    private LocalDateTime creationTime;
}
