package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void innit(){
        userMapper = UserMapper.REGISTER_MAPPER;
    }


    @Test
    void toDTO() {
        User user = new User(1L, "1", 1L, "1");

        UserDTO dto = userMapper.toDTO(user);

        assertEquals(user.getProfileId(), dto.getProfileId());
        assertEquals(user.getPassword(), dto.getPassword());
    }

    @Test
    void toEntity() {
        UserDTO dto = new UserDTO(1L, "1", "1");

        User user = userMapper.toEntity(dto);

        assertEquals(user.getProfileId(), dto.getProfileId());
        assertEquals(user.getPassword(), dto.getPassword());
    }
}