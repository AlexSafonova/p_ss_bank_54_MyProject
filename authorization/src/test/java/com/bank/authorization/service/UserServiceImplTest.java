package com.bank.authorization.service;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import com.bank.authorization.exeptions.NoUserException;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final User USER = new User(1L, "ROLE_USER", 1L, "100");
    private  final UserDTO USER_DTO = new UserDTO(1L, "100", "ROLE_USER");
    private  final UserDTO UPDATE_USER_DTO = new UserDTO(2L, "101", "ROLE_ADMIN");

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;



    @Test
    void findByUserName() {
        Long id = 1L;
        User user = new User(id, "ROLE_USER", 1L, "100");

        doReturn(Optional.of(user)).when(userRepository).findByProfileId(id);

        Optional<User> userOptional = userRepository.findByProfileId(id);

        assertNotNull(userOptional);
        assertEquals(userOptional, userService.findByUserName(id.toString()));
        assertNotNull(userOptional);
    }

    @Test
    void findByUserNameTrowsNoUserException(){
        var exception = assertThrows(NoUserException.class, () -> userService.findByUserName(USER.getProfileId().toString()));
        assertEquals(exception.getMessage(), "ProfileId = null");
    }

    @Test
    void loadUserByUsername() {
        Long id = 1L;
        User user = new User(id, "ROLE_USER", 1L, "100");

        doReturn(Optional.of(user)).when(userRepository).findByProfileId(id);

        UserDetails userDetails1 = userService.loadUserByUsername(id.toString());

        assertEquals("1", userDetails1.getUsername());

    }



    @Test
    void allUser() {
        List<User> accounts = List.of(USER, USER);
        when(userRepository.findAll()).thenReturn(accounts);

        List<User> listResult = userService.allUser();

        assertEquals(accounts, listResult);
        assertNotNull(listResult);
    }

    @Test
    void registerUser() {

       doReturn(USER).when(userRepository).save(USER);

       userRepository.save(USER);
       userService.registerUser(USER_DTO);

       assertEquals(USER.getProfileId(), USER_DTO.getProfileId());
       assertEquals(USER.getRole(), USER_DTO.getRole());
       assertEquals(USER.getPassword(), USER_DTO.getPassword());
    }


    @Test
    void getUser() {
        Optional<User> optionalUser = Optional.of(USER);

        when(userRepository.findById(USER.getId())).thenReturn(optionalUser);
        userRepository.findById(USER.getId());

        var result = userService.getUser(USER.getId());

        assertNull(result);
        verify(userRepository).getReferenceById(USER.getId());
    }

    @Test
    void trowsExceptionInGetUserIsNull(){
       var exceptions =  assertThrows(NoUserException.class, () -> userService.getUser(USER.getId()));
       assertEquals(exceptions.getMessage(), String.format("Пользователь c id: %s, не найден.", USER.getId()));
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId(USER.getId());
        user.setPassword(encoder.encode(UPDATE_USER_DTO.getPassword()));
        user.setProfileId(UPDATE_USER_DTO.getProfileId());
        user.setRole(UPDATE_USER_DTO.getRole());
        lenient().when(userMapper.toEntity(UPDATE_USER_DTO)).thenReturn(user);

        userService.updateUser(UPDATE_USER_DTO, user.getId());

        verify(userRepository, times(1)).save(user);

    }

    @Test
    void deleteUser() {
        userService.deleteUser(USER.getId());

        verify(userRepository, times(1)).deleteById(USER.getId());
    }
}