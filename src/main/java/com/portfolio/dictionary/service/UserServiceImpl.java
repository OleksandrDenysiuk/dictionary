package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.mapper.UserMapper;
import com.portfolio.dictionary.model.AccountDetails;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.RoleRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto create(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if(optionalUser.isEmpty()){
            User user = User.builder()
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .build();
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.getRoles().add(roleRepository.findByName("USER"));
            user.setActive(true);
            return UserMapper.INSTANCE.toDto(userRepository.save(user));
        }else {
            throw new RuntimeException("User exists");
        }

    }

    @Override
    public UserDto getOneByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper.INSTANCE::toDto)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserDto getOneById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return UserMapper.INSTANCE.toDto(optionalUser.get());
        }else {
            throw new RuntimeException("User not found");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new AccountDetails(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.isActive(),
                    user.getRoles());
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
