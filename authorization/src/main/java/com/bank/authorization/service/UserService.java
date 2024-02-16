package com.bank.authorization.service;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService extends UserDetailsService {

    List<User> allUser();

    User registerUser (UserDTO user);

    UserDTO getUser(Long id);

    void updateUser(UserDTO dto, Long id);

    void deleteUser(Long id);
}
