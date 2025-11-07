package ewm.comments.controller;

import ewm.comments.dto.CommentDto;
import ewm.comments.service.CommentService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PublicCommentController {
    CommentService commentService;

    @GetMapping("event/{eventId}")
    List<CommentDto> getCommentsByEvent(
            @PathVariable long eventId,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
        log.info("Получение комментариев к событию с id= {}", eventId);
        return commentService.getCommentsByEventId(eventId, from, size);
    }

    @GetMapping("/{commentId}")
    CommentDto getCommentById(@PathVariable long commentId) {
        log.info("Получение комментария с id= {}", commentId);
        return commentService.getCommentById(commentId);
    }
}
