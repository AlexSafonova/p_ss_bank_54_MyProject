package com.bank.authorization.controller;


import com.bank.authorization.dto.JwtResponse;
import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import com.bank.authorization.exeptions.BadCredentialsError;
import com.bank.authorization.service.UserService;
import com.bank.authorization.util.JwtTokenUtils;
import com.bank.authorization.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordValidator passwordValidator;
    private final MessageSource messageSource;


    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestBody UserDTO userDTO) {
       try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getProfileId(), userDTO.getPassword()));
       } catch (BadCredentialsException e) {
           return new ResponseEntity<>(new BadCredentialsError(
                   HttpStatus.UNAUTHORIZED.value(), "Некорректные данные", new Date()),
                   HttpStatus.UNAUTHORIZED);

       }

        UserDetails userDetails = userService.loadUserByUsername(String.valueOf(userDTO.getProfileId()));
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        passwordValidator.validate(userDTO,bindingResult);
        String message = null;
        for (Object object: bindingResult.getAllErrors()) {
            if (object instanceof FieldError fieldError) {
                message = messageSource.getMessage(fieldError, null);
            }
        }

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(message);
        }

        User user = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
