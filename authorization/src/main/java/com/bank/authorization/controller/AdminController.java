package com.bank.authorization.controller;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import com.bank.authorization.service.UserService;
import com.bank.authorization.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordValidator passwordValidator;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.allUser());
    }

    @GetMapping("/role")
    public ResponseEntity<List<String>> allRoles(){
        List<String> role = new ArrayList<>();
        role.add("ROLE_USER");
        role.add("ROLE_ADMIN");
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO dto,
                                              BindingResult bindingResult) {
        passwordValidator.validate(dto, bindingResult);
        String message = null;
        for (Object object: bindingResult.getAllErrors()) {
            if (object instanceof FieldError fieldError) {
                message = messageSource.getMessage(fieldError, null);
            }
        }
        if (bindingResult.hasErrors()){
         return ResponseEntity.badRequest().body(message);
        }
        userService.updateUser(dto,id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
