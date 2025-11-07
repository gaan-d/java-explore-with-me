package ewm.comments.dto;

import ewm.events.dto.EventShortDto;
import ewm.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CommentDto {
    Long id;
    String text;
    UserShortDto author;
    EventShortDto event;
    LocalDateTime created;
    LocalDateTime edited;
}
