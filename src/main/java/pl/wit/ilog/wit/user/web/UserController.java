package pl.wit.ilog.wit.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wit.ilog.wit.user.model.UserEntity;
import pl.wit.ilog.wit.user.model.UserRepository;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository user;

    @Transactional(readOnly = true)
    @GetMapping(value = "/id/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity getUserById(@PathVariable Long id) throws Exception {
        return user.findById(id).orElseThrow(Exception::new);
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/{username}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity getUserByUsername(@PathVariable String username) throws Exception {
        return user.findByUsername(username).orElseThrow(Exception::new);
    }
}
