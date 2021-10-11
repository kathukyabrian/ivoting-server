package tech.ropaki.ivoting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ropaki.ivoting.domain.User;
import tech.ropaki.ivoting.domain.enumerations.Authority;
import tech.ropaki.ivoting.repository.UserRepository;
import tech.ropaki.ivoting.security.SecurityUtils;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user){
        logger.info("Saving user with email : {} from {}",user.getEmail(), user.getUniversityName());

        user.setAuthority(Authority.VOTER);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User findById(Long id) throws Exception {
        logger.info("Finding user with id : {} ",id);

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else{
            throw new Exception("User with specified id could not be found");
        }
    }

    public User save(User user){
        logger.info("Saving user : {}",user);

        user.setHasVoted(false);

        return userRepository.save(user);
    }


    public User update(User user){

        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email){

        return userRepository.findByEmailIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserDTOWithAuthorities(){
        Optional<String> email = SecurityUtils.getCurrentUserEmail();
        logger.debug("Retrieved user email : {}",email.orElse(null));
        Optional<User> user = email.flatMap(userRepository::findByEmailIgnoreCase);
        logger.info("Retrieved user : {}",user);

        return user;
    }

    public void changePassword(String password){
        logger.debug("Request to change password");

        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmailIgnoreCase)
                .ifPresent(user -> {
                    String passwordHash = passwordEncoder.encode(password);
                    user.setPassword(passwordHash);
                    userRepository.save(user);
                    logger.debug("Changed password for user : {} ",user);
                });
    }

}
