package tech.ropaki.ivoting.repository;

import org.springframework.data.repository.CrudRepository;
import tech.ropaki.ivoting.domain.University;

public interface UniversityRepository extends CrudRepository<University, Integer> {
}
