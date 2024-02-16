package com.bank.authorization.controller;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import com.bank.authorization.service.UserService;

import com.bank.authorization.validator.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;



import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    User user;

    @Mock
    UserDTO userDTO;

    @Mock
    UserService userService;

    @Mock
    PasswordValidator passwordValidator;

    @Mock
    BindingResult bindingResult;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    AdminController controller;

    @Test
    void allUsers() {
        List<User> users = List.of(user, user);
        when(userService.allUser()).thenReturn(users);

        var response = controller.allUsers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    void allRoles(){
        List<String> roles = List.of("ROLE_USER","ROLE_ADMIN");

        var response = controller.allRoles();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
    }

    @Test

    void getUser() {
        when(userService.getUser(user.getId())).thenReturn(userDTO);

        var response = controller.getUser(user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());

    }

    @Test
    void updateUser() {
        userService.updateUser(userDTO, user.getId());
        passwordValidator.validate(userDTO, bindingResult);

        var response = controller.updateUser(user.getId(), userDTO, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    void bindingResultErrorMessage(){
        String message = "Error";
        List<ObjectError> bindingResultList = new ArrayList<>();
        bindingResultList.add(new FieldError("user","user", "Error"));

        when(bindingResult.getAllErrors()).thenReturn(bindingResultList);
        when(messageSource.getMessage(any(), any())).thenReturn(bindingResultList.get(0).getDefaultMessage());

        var response = controller.updateUser(user.getId(), userDTO, bindingResult);
        String messageResult = messageSource.getMessage(any(), any());

        verify(bindingResult,times(1)).getAllErrors();
        assertNotNull(response);
        assertEquals(message, messageResult);
    }


    @Test
    void bindingResultError(){

        when(bindingResult.hasErrors()).thenReturn(true);

        var response = controller.updateUser(user.getId(), userDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void deleteUser() {

        doNothing().when(this.userService).deleteUser(any());

        var response = controller.deleteUser(user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}