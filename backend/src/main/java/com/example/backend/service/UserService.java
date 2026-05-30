package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserResponseDto;
import com.example.backend.entity.User;
import com.example.backend.exception.*;
import com.example.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (userRepository.findByUpiId(userDto.getUpiId()).isPresent()) {
            throw new UpiIdAlreadyExistsException(userDto.getUpiId());
        }

        User newUser = new User(
                userDto.getFullName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword())
        );

        newUser.setUpiId(userDto.getUpiId());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setBalance(userDto.getBalance());

        return userRepository.save(newUser);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(!user.isActive()) {
            throw new AccountDisabledException();
        }

        return user;
    }

    public UserResponseDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(!user.isActive()) {
            throw new AccountDisabledException();
        }

        return new UserResponseDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getUpiId(),
                user.getPhoneNumber(),
                user.getBalance(),
                user.isActive()
        );

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public User registerPayFlowUser(User user) {
        if (userRepository.findByUpiId(user.getUpiId()).isPresent()) {
            throw new UpiIdAlreadyExistsException(user.getUpiId());
        }

        return userRepository.save(user);
    }

    public List<User> getAllPayFlowUsers() {
        return userRepository.findAll();
    }

    public User getPayFlowUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }

    public User findPayFlowUserByUpiId(String upiId) {
        return userRepository.findByUpiId(upiId)
                .orElseThrow(() -> new UpiIdNotFoundException(upiId));
    }

    public List<User> findPayFlowUsersWithBalanceGreaterThan(BigDecimal amount) {
        return userRepository.findUsersWithBalanceGreaterThan(amount);
    }
}
