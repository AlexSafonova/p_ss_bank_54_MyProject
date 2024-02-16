package com.bank.authorization.service;


import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import com.bank.authorization.exeptions.NoUserException;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Optional<User> findByUserName(String profileId) {
        Optional<User> user = userRepository.findByProfileId(Long.valueOf((String.valueOf(profileId))));
        if (user.isEmpty()){
            throw new NoUserException("ProfileId = null");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String profileId) throws UsernameNotFoundException {
        User user = findByUserName(profileId).orElseThrow(() ->
                new UsernameNotFoundException(("Пользователь" + profileId + "не найден")
                ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
    }


    @Override
    public List<User> allUser() {
        log.info("Получение списка пользователей");
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public User registerUser(UserDTO userDTO) {
        User user = userMapper.REGISTER_MAPPER.toEntity(userDTO);

        if (userRepository.findByProfileId((userDTO.getProfileId())).isPresent()) {
            throw new RuntimeException("Пользователь с таким ID уже существует");
        }
        user.setProfileId(userDTO.getProfileId());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("ROLE_USER");
        log.info("Пользователь  сохранён в базу");
        return userRepository.save(user);
    }

    @Override
    public UserDTO getUser(Long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user==null) {
            throw new NoUserException(String.format("Пользователь c id: %s, не найден.", id));
        }
        log.info("Получение пользователя из базы");
        return userMapper.REGISTER_MAPPER.toDTO(userRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public void updateUser(UserDTO dto, Long id) {
        User user = userMapper.REGISTER_MAPPER.toEntity(dto);
        user.setId(id);
        user.setProfileId(dto.getProfileId());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        log.info("Изменение пользователя");
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void deleteUser (Long id) {
        userRepository.deleteById(id);
        log.info("Пользователь с id {} удален", id);
    }
}
