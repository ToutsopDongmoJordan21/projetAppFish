package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.files.entities.AppFishFile;
import com.example.demo.files.entities.enumeration.DocType;
import com.example.demo.files.repositories.FileRepository;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.models.dto.UserDto;
import com.example.demo.models.enums.ERole;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.findAllUser());
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(userDetailsService.findOneUserById(userId));
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id ::" + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("User was successful delete", Boolean.TRUE);
        return response;
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long userId,
                                        @RequestBody UserDto userDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(userDetailsService.updateUser(userId, userDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        AppFishFile file = fileRepository.findByUserAndType(userRepository.getOne(userDetails.getId()), DocType.PROFILE_IMAGE);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtResponse response = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPassword(),
                userDetails.getPhoneNumber(),
                roles);

        if(Objects.nonNull(file)) {
            response.setUrl(file.getUrl());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!!"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!!"));
        }

        if(userRepository.existsByPhoneNumber(signupRequest.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: phone Number is already in use!"));
        }

        // Create new user's account
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPhoneNumber(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch(role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(modRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }


}












