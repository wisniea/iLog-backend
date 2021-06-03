package pl.wit.ilog.user.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wit.ilog.internals.exception.BadRequestException;
import pl.wit.ilog.internals.payload.ApiResponse;
import pl.wit.ilog.internals.payload.AuthResponse;
import pl.wit.ilog.internals.payload.LoginRequest;
import pl.wit.ilog.internals.payload.SignUpRequest;
import pl.wit.ilog.security.jwt.TokenProvider;
import pl.wit.ilog.user.model.AuthProvider;
import pl.wit.ilog.user.model.IUserRepository;
import pl.wit.ilog.user.model.RoleEnum;
import pl.wit.ilog.user.model.UserEntity;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setRoles(new HashSet<>() {{ add(RoleEnum.ROLE_USER); }});
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "UserEntity registered successfully@"));
    }

}
