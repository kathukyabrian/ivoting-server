package tech.ropaki.ivoting.repository;

import org.springframework.data.repository.CrudRepository;
import tech.ropaki.ivoting.domain.Candidate;
import tech.ropaki.ivoting.domain.Position;
import tech.ropaki.ivoting.domain.enumerations.CandidateStatus;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    Iterable<Candidate> findByUniversity(String universityCode);
    Iterable<Candidate> findByPositionAndUniversityAndStatusOrderByVoteCountDesc(String position, String university, CandidateStatus candidateStatus);
}
