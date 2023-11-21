package ru.practicum.shateit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shateit.request.dto.RequestDto;
import ru.practicum.shateit.util.Create;

@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @Validated(Create.class) @RequestBody RequestDto request) {
        return requestClient.create(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getByOwner(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return requestClient.getByOwner(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @RequestParam(defaultValue = "0") Integer from,
                                   @RequestParam(defaultValue = "10") Integer size) {
        return requestClient.getAll(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                   @PathVariable("requestId") Long requestId) {
        return requestClient.getById(userId, requestId);
    }
}