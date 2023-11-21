package ru.practicum.shateit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shateit.util.Create;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long id;
    @NotBlank(message = "Описание запроса не должно быть пустым", groups = {Create.class})
    private String description;
    private LocalDateTime created;
}