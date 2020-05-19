package com.portfolio.dictionary.mapper;

import com.portfolio.dictionary.dto.CategoryDto;
import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.model.Category;
import com.portfolio.dictionary.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    @Mapping(source = "user.id", target = "userId")
    CategoryDto toDto(Category category);
}
