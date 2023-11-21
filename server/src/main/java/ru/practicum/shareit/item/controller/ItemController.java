package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CommentShortDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController("ServerItemController")
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestBody ItemShortDto item) {
        return itemService.create(userId, item);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @PathVariable Long itemId,
                          @RequestBody ItemShortDto item) {
        return itemService.update(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public ItemDto getById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        return itemService.getById(userId, itemId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        itemService.deleteById(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @RequestParam(defaultValue = "0") Integer offset,
                                          @RequestParam(required = false) Integer limit) {
        return itemService.getByUserId(userId, offset, limit);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text,
                                     @RequestParam(defaultValue = "0") Integer offset,
                                     @RequestParam(required = false) Integer limit) {
        return itemService.search(text, offset, limit);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestBody CommentShortDto comment,
                                    @PathVariable Long itemId,
                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.createComment(itemId, userId, comment);
    }
}
