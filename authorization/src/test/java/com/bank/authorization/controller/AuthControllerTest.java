package com.bank.authorization.controller;

import com.bank.authorization.dto.JwtResponse;
import com.bank.authorization.dto.UserDTO;

import com.bank.authorization.exeptions.BadCredentialsError;
import com.bank.authorization.service.UserService;
import com.bank.authorization.util.JwtTokenUtils;
import com.bank.authorization.validator.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;


import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    UserDTO userDTO;

    @Mock
    PasswordValidator passwordValidator;

    @Mock
    BindingResult bindingResult;

    @Mock
    UserService userService;

    @Mock
    JwtTokenUtils jwtTokenUtils;

    @Mock
    UserDetails userDetails;

    @Mock
    Authentication authentication;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    AuthController authController;


    @Test
    void createTokenTest(){
        String token = "token";
        when(jwtTokenUtils.generateToken(any())).thenReturn(token);
        when(userService.loadUserByUsername(String.valueOf(userDTO.getProfileId()))).thenReturn(userDetails);

        var userDet = userService.loadUserByUsername(String.valueOf(userDTO.getProfileId()));
        var response = authController.createToken(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(jwtTokenUtils, times(1)).generateToken(userDet);
    }

    @Test
    void addUser() {

        userService.registerUser(userDTO);
        passwordValidator.validate(userDTO, bindingResult);

        var response = authController.addUser(userDTO, bindingResult);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }


    @Test
    void bindingResultErrorMessage(){
        String message = "Error";
        List<ObjectError> bindingResultList = new ArrayList<>();
        bindingResultList.add(new FieldError("user","user", "Error"));

        when(bindingResult.getAllErrors()).thenReturn(bindingResultList);
        when(messageSource.getMessage(any(), any())).thenReturn(bindingResultList.get(0).getDefaultMessage());

        var response = authController.addUser(userDTO, bindingResult);
        String messageResult = messageSource.getMessage(any(), any());

        verify(bindingResult,times(1)).getAllErrors();
        assertNotNull(response);
        assertEquals(message, messageResult);
    }

    @Test
    void bindingResultError(){

        when(bindingResult.hasErrors()).thenReturn(true);

        var response = authController.addUser(userDTO, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
}