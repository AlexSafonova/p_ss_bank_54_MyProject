package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper REGISTER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO (User user);

    User toEntity(UserDTO userDTO);
}
