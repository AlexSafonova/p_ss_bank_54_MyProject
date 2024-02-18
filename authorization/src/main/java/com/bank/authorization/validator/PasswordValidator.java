package com.bank.authorization.validator;

import com.bank.authorization.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Класс для проверки длины пароля объекта UserDTO
 */

@Component
public class PasswordValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 3;


    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (userDTO.getPassword() != null
                && userDTO.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
            errors.rejectValue("password", "field.min.length",
                    "Пароль должен быть меньше трёх символов");
        }
    }
}
