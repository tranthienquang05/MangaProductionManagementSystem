package com.manga.mangaproductionmanagement.module.user;

import com.manga.mangaproductionmanagement.entity.User;
import com.manga.mangaproductionmanagement.module.user.dto.LoginRequest;
import com.manga.mangaproductionmanagement.module.user.dto.LoginResponse;
import com.manga.mangaproductionmanagement.security.JwtService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final com.manga.mangaproductionmanagement.module.user.UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserController(com.manga.mangaproductionmanagement.module.user.UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String rawPassword = request.getPassword();

        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByFullName(username);
        }

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOpt.get();
        String storedHash = user.getPasswordHash();

        boolean matches = false;
        if (storedHash != null) {
            if (storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$")) {
                matches = passwordEncoder.matches(rawPassword, storedHash);
            } else {
                matches = storedHash.equals(rawPassword);
            }
        }

        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        LoginResponse resp = new LoginResponse(token, user.getId(), user.getFullName(), user.getEmail());
        return ResponseEntity.ok(resp);
    }
}
