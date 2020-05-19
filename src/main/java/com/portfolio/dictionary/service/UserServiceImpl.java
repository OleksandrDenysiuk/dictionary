package com.portfolio.dictionary.service;

import com.portfolio.dictionary.dto.UserDto;
import com.portfolio.dictionary.mapper.UserMapper;
import com.portfolio.dictionary.model.User;
import com.portfolio.dictionary.repository.RoleRepository;
import com.portfolio.dictionary.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDto save(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if(user == null){
            user = User.builder()
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .build();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.getRoles().add(roleRepository.findByName("USER"));
            user.setActive(true);
            return UserMapper.INSTANCE.toDto(userRepository.save(user));
        }else {
            throw new RuntimeException("User exists");
        }

    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return UserMapper.INSTANCE.toDto(user);
        }
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return UserMapper.INSTANCE.toDto(optionalUser.get());
        }else {
            throw new RuntimeException("User not found");
        }

    }
}
