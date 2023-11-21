package ru.practicum.shateit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shateit.util.Create;
import ru.practicum.shateit.util.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "Поле имени не должно быть пустым", groups = {Create.class})
    private String name;
    @Email(message = "Невалидный почтовый ящик", groups = {Create.class, Update.class})
    @NotBlank(message = "Поле почтового ящика не должно быть пустым", groups = {Create.class})
    private String email;
}
