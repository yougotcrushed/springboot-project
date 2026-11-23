package com.yougotcrushed.casst.services;

import com.yougotcrushed.casst.dtos.ChangePasswordRequest;
import com.yougotcrushed.casst.dtos.RegisterUserRequest;
import com.yougotcrushed.casst.dtos.UpdateUserRequest;
import com.yougotcrushed.casst.dtos.UserDto;
import com.yougotcrushed.casst.enums.Role;
import com.yougotcrushed.casst.mappers.UserMapper;
import com.yougotcrushed.casst.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public Iterable<UserDto> getAllUsers(String sortBy) {
        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDot).toList();
    }

    public ResponseEntity<UserDto> getUser(Long id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(userMapper.toDot(user));
    }

    public ResponseEntity<?> registerUser(RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email", "Email is already registered.")
            );
        }
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        var userDto = userMapper.toDot(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    public ResponseEntity<UserDto> updateUser(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDot(user));
    }

    public ResponseEntity<Void> deleteUser(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> changePassword(Long id, ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }
}
