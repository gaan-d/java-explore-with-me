package ewm.comments.service;

import ewm.comments.Comment;
import ewm.comments.CommentMapper;
import ewm.comments.dto.CommentDto;
import ewm.comments.dto.CreateCommentDto;
import ewm.comments.repository.CommentRepository;
import ewm.events.Event;
import ewm.events.EventRepository;
import ewm.exception.BadRequestException;
import ewm.exception.NotFoundException;
import ewm.requests.RequestRepository;
import ewm.user.User;
import ewm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static ewm.events.enums.State.PUBLISHED;
import static ewm.requests.enums.RequestStatus.CONFIRMED;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    UserService userService;
    EventRepository eventRepository;
    RequestRepository requestRepository;
    CommentMapper commentMapper;
    CommentRepository commentRepository;

    @Override
    public CommentDto create(long userId, long eventId, CreateCommentDto commentDto) {
        User author = userService.getUserById(userId);
        Event event = checkEventExist(eventId);
        if (event.getState() != PUBLISHED) {
            throw new BadRequestException("You can comment only published events");
        }
        commentDto.setCreated(LocalDateTime.now());
        commentDto.setConfirmedRequests(requestRepository.countByEventIdAndStatus(eventId, CONFIRMED));

        Comment comment = commentMapper.mapCreateCommentDtoToComment(commentDto, author, event);
        return commentMapper.mapCommentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(long userId, long eventId, long commentId, CreateCommentDto commentDto) {
        Comment comment = checkCommentExist(commentId);
        if (comment.getAuthor().getId() != userId) {
            throw new BadRequestException("Only the author of the comment can edit it");
        }
        if (comment.getEvent().getId() != eventId) {
            throw new BadRequestException("The comment does not belong to this event");
        }

        comment.setText(commentDto.getText());
        comment.setEdited(LocalDateTime.now());

        return commentMapper.mapCommentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentByUser(long userId, long commentId) {
        Comment comment = checkCommentExist(commentId);
        if (comment.getAuthor().getId() != userId) {
            throw new BadRequestException("Only the author of the comment can delete it");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteCommentByAdmin(long commentId) {
        checkCommentExist(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(long commentId) {
        Comment comment = checkCommentExist(commentId);
        return commentMapper.mapCommentToCommentDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByEventId(long eventId, int from, int size) {
        checkEventExist(eventId);
        List<Comment> commentList = commentRepository.findAllByEventId(eventId, PageRequest.of(from / size, size));
        if (commentList.isEmpty()) {
            return List.of();
        }
        return commentMapper.mapCommentsToCommentDto(commentList);
    }

    @Override
    @Transactional
    public List<CommentDto> getCommentsByUserId(long userId, int from, int size) {
        userService.getUserById(userId);
        List<Comment> commentList = commentRepository.findAllByAuthorId(userId, PageRequest.of(from / size, size));
        if (commentList.isEmpty()) {
            return List.of();
        }
        return commentMapper.mapCommentsToCommentDto(commentList);
    }

    Event checkEventExist(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(("Event with id=" + eventId + " was not found")));
    }

    Comment checkCommentExist(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(("Comment with id=" + commentId + " was not found")));
    }
}
