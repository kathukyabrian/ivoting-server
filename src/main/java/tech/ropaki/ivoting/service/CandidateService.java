package tech.ropaki.ivoting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.ropaki.ivoting.domain.Candidate;
import tech.ropaki.ivoting.domain.Position;
import tech.ropaki.ivoting.domain.User;
import tech.ropaki.ivoting.domain.enumerations.Authority;
import tech.ropaki.ivoting.domain.enumerations.CandidateStatus;
import tech.ropaki.ivoting.repository.CandidateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final Logger logger = LoggerFactory.getLogger(CandidateService.class);

    private final CandidateRepository candidateRepository;

    private final UserService userService;

    public CandidateService(CandidateRepository candidateRepository, UserService userService) {
        this.candidateRepository = candidateRepository;
        this.userService = userService;
    }

    public Candidate apply(Candidate candidate){
        logger.info("Applying for candidature. Candidate : {}",candidate);

        candidate.setStatus(CandidateStatus.PENDING);

        candidate.setVoteCount(0L);

        return candidateRepository.save(candidate);
    }

    public Candidate changeState(Candidate candidate) throws Exception {
        logger.info("About to change state {} for position {} in {}. Changing state to {}",candidate.getName(),
                candidate.getPosition(), candidate.getUniversity(), candidate.getStatus());

        if(candidate.getStatus()==CandidateStatus.APPROVED){
            User user = userService.findById(candidate.getUserId());

            user.setAuthority(Authority.CANDIDATE);

            userService.save(user);
        }

        candidate.setVoteCount(0L);

        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllByUniversity(String universityCode){
        logger.info("About to get all candidates for : {}",universityCode);

        List<Candidate> candidates = new ArrayList<>();

        for(Candidate  candidate : candidateRepository.findByUniversity(universityCode)){
            candidates.add(candidate);
        }

        return candidates;
    }

    public List<Candidate> getAll(){
        logger.info("About to get all candidates applications");

        List<Candidate> candidates = new ArrayList<>();

        for(Candidate  candidate : candidateRepository.findAll()){
            candidates.add(candidate);
        }

        return candidates;
    }

    public List<Candidate> findByPositionByUniversity(String university, String position){

        List<Candidate> candidateList = new ArrayList<>();

        for(Candidate candidate : candidateRepository.findByPositionAndUniversityAndStatusOrderByVoteCountDesc(position,university,
                CandidateStatus.APPROVED)){
            candidateList.add(candidate);
        }

        return candidateList;

    }

    public Candidate getById(Long id) throws Exception {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);

        if(optionalCandidate.isPresent()){
            return optionalCandidate.get();
        }else{
            throw new Exception("Candidate with specified id not found");
        }
    }

    public Candidate save(Candidate candidate){
        return candidateRepository.save(candidate);
    }
}
