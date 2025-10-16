package ewm.user;

import ewm.user.dto.UserCreateDto;
import ewm.user.dto.UserDto;
import ewm.user.dto.UserShortDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserMapper {
    public User mapCreateDtoToUser(UserCreateDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public UserDto mapUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserShortDto mapUserToShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public List<UserDto> mapListToDto(Collection<User> users) {
        List<UserDto> list = new ArrayList<>();

        for (User user : users) {
            list.add(mapUserToDto(user));
        }

        return list;
    }
}
