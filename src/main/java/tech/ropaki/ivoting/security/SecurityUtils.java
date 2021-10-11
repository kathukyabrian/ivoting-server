package tech.ropaki.ivoting.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import tech.ropaki.ivoting.domain.enumerations.Authority;
import tech.ropaki.ivoting.security.jwt.CustomAuthentication;

import java.util.Optional;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Optional<String> getCurrentUserLogin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if(authentication.getPrincipal() instanceof UserDetails){
                        UserDetails springUser = (UserDetails) authentication.getPrincipal();
                        return springUser.getUsername();
                    }else if(authentication.getPrincipal() instanceof String){
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
    }

    public static Optional<String> getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(authentication.getName());
    }

    public static Authority getCurrentUserAuthority(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof CustomAuthentication){
            if(authentication.getAuthorities().contains(new CustomAuthority(Authority.VOTER.name()))){
                return Authority.VOTER;
            }else if(authentication.getAuthorities().contains(new CustomAuthority(Authority.CANDIDATE.name()))){
                return Authority.CANDIDATE;
            }else if(authentication.getAuthorities().contains(new CustomAuthority(Authority.ADMIN.name()))){
                return Authority.ADMIN;
            }
        }
        return Authority.VOTER;
    }

    public static Optional<String> getCurrentUserJWT(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    public static boolean isAuthenticated(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).isPresent();
    }

    public static boolean isCurrentUserInRole(String authority){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
                .orElse(false);
    }
}
