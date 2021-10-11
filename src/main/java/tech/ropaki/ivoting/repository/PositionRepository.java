package tech.ropaki.ivoting.repository;

import org.springframework.data.repository.CrudRepository;
import tech.ropaki.ivoting.domain.Position;

public interface PositionRepository extends CrudRepository<Position,Long> {
    Iterable<Position> findByUniversity(String university);
}
