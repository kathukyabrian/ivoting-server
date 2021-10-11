package tech.ropaki.ivoting.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ropaki.ivoting.domain.User;
import tech.ropaki.ivoting.service.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){

        return userService.register(user);
    }
}
