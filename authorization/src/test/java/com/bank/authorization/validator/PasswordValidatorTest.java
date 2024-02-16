package com.bank.authorization.validator;

import com.bank.authorization.dto.UserDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    @Mock
    UserDTO userDTO;

    @Mock
    Errors errors;

    @InjectMocks
    PasswordValidator passwordValidator;


    @Test
    void supports(){
        boolean supports = passwordValidator.supports(UserDTO.class);
        assertTrue(supports);
    }

    @Test
    void validate() {
        errors = new BeanPropertyBindingResult(userDTO, "userDTO");
        when(userDTO.getPassword()).thenReturn("1");

        passwordValidator.validate(userDTO, errors);

        assertTrue(errors.hasFieldErrors("password"));
    }
}