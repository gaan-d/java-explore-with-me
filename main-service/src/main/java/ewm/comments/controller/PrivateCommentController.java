package ewm.comments.controller;

import ewm.comments.dto.CommentDto;
import ewm.comments.dto.CreateCommentDto;
import ewm.comments.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/comments")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PrivateCommentController {
    CommentService commentService;

    @GetMapping
    public List<CommentDto> getCommentsByUser(
            @PathVariable long userId,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
        log.info("Получение комментариев пользователя с id= {}", userId);
        return commentService.getCommentsByUserId(userId, from, size);
    }

    @PostMapping("/event/{eventId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable long userId,
                                 @PathVariable long eventId,
                                 @RequestBody @Valid CreateCommentDto commentDto) {
        log.info("Добавление комментария {} пользователем с id={} к событию с id={}", commentDto, userId, eventId);
        return commentService.create(userId, eventId, commentDto);
    }

    @PatchMapping("/event/{eventId}/comment/{commentId}")
    public CommentDto updateComment(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @PathVariable long commentId,
                                    @RequestBody @Valid CreateCommentDto commentDto) {
        log.info("Обновление комментария пользователем с id= {}, commentId= {}", userId, commentId);
        return commentService.update(userId, eventId, commentId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCommentByUser(@PathVariable long userId,
                                    @PathVariable long commentId) {
        log.info("Удаление комментария с id= {} пользователем с id= {}", commentId, userId);
        commentService.deleteCommentByUser(userId, commentId);
    }
}
