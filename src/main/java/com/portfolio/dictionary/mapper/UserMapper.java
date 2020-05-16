package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
