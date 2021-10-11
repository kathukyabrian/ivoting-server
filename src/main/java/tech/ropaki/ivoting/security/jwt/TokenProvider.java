package tech.ropaki.ivoting.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import tech.ropaki.ivoting.config.ApplicationProperties;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String UNIVERSITY_KEY = "uni";
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private final ApplicationProperties applicationProperties ;
    private String secretKey;
    // in milliseconds
    private long tokenValidity;
    // in milliseconds
    private long longTokenValidity;

    public TokenProvider(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }


    // after construct, fetch the application properties
    @PostConstruct
    public void init(){
        this.secretKey = applicationProperties.getJwtSecret();
        this.tokenValidity = 1000 *  applicationProperties.getTokenValidity();
    }

    public String createToken(Authentication authentication, String code){
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;

        long now = (new Date()).getTime();
        Date validity;
        validity = new Date(now+tokenValidity);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .claim(UNIVERSITY_KEY,code)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString()
                .split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        logger.debug("Principal = {}, Claims = {}" ,principal, claims);

        return new CustomAuthentication(principal,null,true, principal.getUsername(),
                principal.getAuthorities(), (String) claims.get(UNIVERSITY_KEY));
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        }catch(SignatureException e){
            logger.info("Invalid JWT Signature");
        }catch(MalformedJwtException e){
            logger.info("Invalid JWT token");
        }catch(ExpiredJwtException e){
            logger.info("Expired JWT exception");
        }catch(UnsupportedJwtException e){
            logger.info("Unsupported JWT exception");
        }catch (IllegalArgumentException e){
            logger.info("JWT token compact of handler are invalid");
        }
        return false;
    }
}
