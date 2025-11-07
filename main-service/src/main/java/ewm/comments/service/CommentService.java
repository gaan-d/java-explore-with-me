package ewm.comments.service;

import ewm.comments.dto.CommentDto;
import ewm.comments.dto.CreateCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(long userId, long eventId, CreateCommentDto commentDto);

    CommentDto update(long userId, long eventId, long commentId, CreateCommentDto commentDto);

    void deleteCommentByUser(long userId, long commentId);

    void deleteCommentByAdmin(long commentId);

    CommentDto getCommentById(long commentId);

    List<CommentDto> getCommentsByEventId(long eventId, int from, int size);

    List<CommentDto> getCommentsByUserId(long userId, int from, int size);
}
