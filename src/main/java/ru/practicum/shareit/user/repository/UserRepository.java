package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

/**
 * Класс-хранилище данных о пользователях
 */
public interface UserRepository {
    /**
     * Метод создаёт пользователя и добавляет его в хранилище
     * @param user данные пользователя
     * @return возвращает созданного пользователя
     */
    User createUser(User user);
    /**
     * Метод обновляет данные пользователя в хранилище
     * @param user данные пользователя
     * @return возвращает новые данные о пользователе
     */

    User updateUser(Long id, User user);
    /**
     * Метод получения пользователя по идентификатору
     * @param id идентификатор пользователя
     * @return возвращает полученного пользователя
     */

    User getUserById(Long id);
    /**
     * Метод удаления пользователя по идентификатору
     * @param id идентификатор пользователя
     */

    void deleteUserById(Long id);
    /**
     * Метод получения списка пользователей
     * @return возвращает список пользователей из хранилища
     */

    List<User> getUsers();
}
