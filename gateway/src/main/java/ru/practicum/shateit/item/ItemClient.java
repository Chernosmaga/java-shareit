package ru.practicum.shateit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shateit.client.BaseClient;
import ru.practicum.shateit.item.dto.CommentShortDto;
import ru.practicum.shateit.item.dto.ItemShortDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> create(Long userId, ItemShortDto itemDto) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> update(Long userId, Long itemId, ItemShortDto item) {
        return patch("/" + itemId, userId, item);
    }

    public ResponseEntity<Object> getById(Long userId, Long itemId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> deleteById(Long userId, Long itemId) {
        return delete("/" + userId, itemId);
    }

    public ResponseEntity<Object> search(String text, Long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("text", text, "from", from, "size", size);
        return get("/search?text={text}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getItemsByUserId(Long userId, Integer offset, Integer limit) {
        Map<String, Object> parameters = Map.of("from", offset, "size", limit);
        return get("/?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> createComment(Long itemId, Long userId, CommentShortDto commentDto) {
        return post("/" + itemId + "/comment", userId, commentDto);
    }
}