package ewm.comments;

import ewm.comments.dto.CommentDto;
import ewm.comments.dto.CreateCommentDto;
import ewm.events.Event;
import ewm.events.EventMapper;
import ewm.user.User;
import ewm.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CommentMapper {
    UserMapper userMapper;
    EventMapper eventMapper;

    public Comment mapCreateCommentDtoToComment(CreateCommentDto commentDto, User author, Event event) {
        return Comment.builder()
                .text(commentDto.getText())
                .author(author)
                .event(event)
                .created(commentDto.getCreated() != null ? commentDto.getCreated() : null)
                .edited(commentDto.getEdited() != null ? commentDto.getEdited() : null)
                .confirmedRequests(commentDto.getConfirmedRequests())
                .build();
    }

    public CommentDto mapCommentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .edited(comment.getEdited() != null ? comment.getEdited() : null)
                .author(userMapper.mapUserToShortDto(comment.getAuthor()))
                .event(eventMapper.mapEventToEventShortDto(comment.getEvent(), comment.getConfirmedRequests()))
                .build();
    }

    public List<CommentDto> mapCommentsToCommentDto(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(mapCommentToCommentDto(comment));
        }
        return commentDtos;
    }
}
