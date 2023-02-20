package ru.practicum.explorewithmemain.mapper;

import ru.practicum.explorewithmemain.dto.NewUserRequestDto;
import ru.practicum.explorewithmemain.dto.UserDto;
import ru.practicum.explorewithmemain.entity.User;

public class UserMapper {
    public static UserDto fromNewUserRequestToUserDto(NewUserRequestDto userRequestDto) {
        return UserDto
                .builder()
                .id(null)
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .build();
    }

    public static User toUser(UserDto user) {
        return new User(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

    public static UserDto toUserDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
