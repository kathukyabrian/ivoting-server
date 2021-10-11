package tech.ropaki.ivoting.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.ropaki.ivoting.domain.User;
import tech.ropaki.ivoting.security.CustomAuthenticationManager;
import tech.ropaki.ivoting.security.jwt.JWTConfig;
import tech.ropaki.ivoting.security.jwt.TokenProvider;
import tech.ropaki.ivoting.service.UserService;
import tech.ropaki.ivoting.web.rest.vm.ChangePasswordVM;
import tech.ropaki.ivoting.web.rest.vm.LoginVM;

@RestController
@RequestMapping("/api")
public class AccountResource {
    private final Logger logger = LoggerFactory.getLogger(AccountResource.class);

    private final TokenProvider tokenProvider;
    private final CustomAuthenticationManager authenticationManager;
    private final UserService userService;

    public AccountResource(TokenProvider tokenProvider, CustomAuthenticationManager authenticationManager, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<JWTToken> authorize(@RequestBody LoginVM loginVM){
        logger.debug("Request to authorize user : {} ",loginVM.getEmail());
        User user = userService.findUserByEmail(loginVM.getEmail()).get();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginVM.getEmail(),loginVM.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication,user.getUniversityName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfig.AUTHORIZATION_HEADER,"Bearer "+jwt);
        return new ResponseEntity<>(new JWTToken(jwt),httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/account")
    public User getAccount(){
        User user = userService.getUserDTOWithAuthorities().get();
        logger.info("Retrieved User : {}",user);
        return user;
    }

    @PostMapping("/account/change-password")
    public void changePassword(@RequestBody ChangePasswordVM changePasswordVM){
        logger.debug("REST request to change password");

        userService.changePassword(changePasswordVM.getNewPassword());
    }

    static class JWTToken{
        private String idToken;

        JWTToken(String idToken){
            this.idToken = idToken;
        }

        @JsonProperty
        String getIdToken(){
            return idToken;
        }

        void setIdToken(String idToken){
            this.idToken = idToken;
        }
    }
}
