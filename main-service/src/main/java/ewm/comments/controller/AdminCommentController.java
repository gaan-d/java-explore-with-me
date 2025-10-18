package ewm.comments.controller;

import ewm.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AdminCommentController {
    CommentService commentService;

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long commentId) {
        log.info("Admin delete comment with id= {}", commentId);
        commentService.deleteCommentByAdmin(commentId);
    }
}
