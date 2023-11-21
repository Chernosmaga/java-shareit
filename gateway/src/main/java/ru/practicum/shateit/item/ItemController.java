package ru.practicum.shateit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shateit.item.dto.CommentShortDto;
import ru.practicum.shateit.item.dto.ItemShortDto;
import ru.practicum.shateit.util.Create;

import javax.validation.Valid;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @Validated(Create.class) @RequestBody ItemShortDto item) {
        return itemClient.create(userId, item);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable Long itemId,
                                         @RequestBody ItemShortDto item) {
        return itemClient.update(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        return itemClient.getById(userId, itemId);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId) {
        return itemClient.deleteById(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @RequestParam(defaultValue = "0") Integer offset,
                                          @RequestParam(defaultValue = "20") Integer limit) {
        return itemClient.getItemsByUserId(userId, offset, limit);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam String text,
                                         @RequestParam(defaultValue = "0") Integer offset,
                                         @RequestParam(defaultValue = "20") Integer limit) {
        return itemClient.search(text, userId, offset, limit);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentShortDto comment,
                                    @PathVariable Long itemId,
                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.createComment(itemId, userId, comment);
    }
}