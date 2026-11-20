package com.yougotcrushed.casst.controllers;

import com.yougotcrushed.casst.config.JwtConfig;
import com.yougotcrushed.casst.dtos.JwtResponse;
import com.yougotcrushed.casst.dtos.LoginUserRequest;
import com.yougotcrushed.casst.dtos.UserDto;
import com.yougotcrushed.casst.mappers.UserMapper;
import com.yougotcrushed.casst.repositories.UserRepository;
import com.yougotcrushed.casst.services.AuthService;
import com.yougotcrushed.casst.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginUserRequest request,
                                                 HttpServletResponse response) {

        return authService.loginUser(request, response);

    }

    @PostMapping("/refresh")
    public  ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ) {
        return authService.refreshToken(refreshToken);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        return authService.me();
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
