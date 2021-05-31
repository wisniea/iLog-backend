package pl.wit.ilog.user.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wit.ilog.internals.exception.ResourceNotFoundException;
import pl.wit.ilog.security.CurrentUser;
import pl.wit.ilog.security.jwt.UserPrincipal;
import pl.wit.ilog.user.model.IUserRepository;
import pl.wit.ilog.user.model.UserEntity;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final IUserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserEntity getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "id", userPrincipal.getId()));
    }
}
