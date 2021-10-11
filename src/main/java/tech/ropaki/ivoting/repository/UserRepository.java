package tech.ropaki.ivoting.repository;

import org.springframework.data.repository.CrudRepository;
import tech.ropaki.ivoting.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmailIgnoreCase(String email);
}
