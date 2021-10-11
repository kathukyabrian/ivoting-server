package tech.ropaki.ivoting.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tech.ropaki.ivoting.domain.User;
import tech.ropaki.ivoting.domain.enumerations.Authority;
import tech.ropaki.ivoting.security.error.UserNotFoundException;
import tech.ropaki.ivoting.security.jwt.CustomAuthentication;
import tech.ropaki.ivoting.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationManager.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // get principal
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Optional<User> optionalUser = userService.findUserByEmail(email);

        // if there is no user with that email
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        // check on the password
        if(password.isEmpty() || !passwordEncoder.matches(password,user.getPassword())){
            throw  new BadCredentialsException("Invalid credentials");
        }

        // check authorities
        Authority authority = user.getAuthority();

        // add authorities
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        // set BUYER as default authority
        GrantedAuthority grantedAuthority = new CustomAuthority(Authority.VOTER.name());
        if(authority==Authority.ADMIN){
            grantedAuthority = new CustomAuthority(Authority.ADMIN.name());
        } else if(authority==Authority.CANDIDATE){
            grantedAuthority = new CustomAuthority(Authority.CANDIDATE.name());
        } else if(authority==Authority.VOTER){
            grantedAuthority = new CustomAuthority(Authority.VOTER.name());
        }

        grantedAuthorityList.add(grantedAuthority);

        // authenticate with CustomAuth
        return new CustomAuthentication(new Object(),null,true,email,grantedAuthorityList,
                user.getUniversityName());
    }
}
