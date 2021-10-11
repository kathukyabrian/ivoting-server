package tech.ropaki.ivoting.service;

import jdk.jfr.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.ropaki.ivoting.domain.Candidate;
import tech.ropaki.ivoting.domain.Position;
import tech.ropaki.ivoting.domain.User;
import tech.ropaki.ivoting.dto.PositionCandidateDTO;
import tech.ropaki.ivoting.dto.ResultDTO;
import tech.ropaki.ivoting.dto.VoteDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VotingService {

    private final CandidateService candidateService;

    private final PositionService positionService;

    private final UserService userService;

    public VotingService(CandidateService candidateService, PositionService positionService, UserService userService) {
        this.candidateService = candidateService;
        this.positionService = positionService;
        this.userService = userService;
    }

    public List<Candidate> loadPositionsAndCandidates(PositionCandidateDTO positionCandidateDTO){

        return candidateService.findByPositionByUniversity(positionCandidateDTO.getUniversity(), positionCandidateDTO.getPosition());
    }

    public List<String> loadPositions(String university){
        List<String> positionList = new ArrayList<>();

        for(Position position : positionService.findByUniversity(university)){
            positionList.add(position.getName());
        }
         return positionList;
    }

    public String vote(VoteDTO voteDTO) throws Exception {

        // id
        User user = userService.findById(voteDTO.getUserId());

        log.info("Retrieved user : {}",user);
        // list of candidates

        if(!user.getHasVoted()){
            for(Long id : voteDTO.getCandidates()){
                Candidate candidate = candidateService.getById(id);
                candidate.setVoteCount(candidate.getVoteCount()+1);
                candidateService.save(candidate);
            }
        }else{
            throw new Exception("You have already voted");
        }

        user.setHasVoted(Boolean.TRUE);

        log.info("Set user hasvoted to true : {}",user);

        userService.update(user);

        return "voted";
    }

    public List<ResultDTO> results(String university){

        List<ResultDTO> candidateDTOS = new ArrayList<>();

        for(Position position : positionService.findByUniversity(university)){

            log.info("About to load candidates by position : {} for university : {}",position.getName(),university);
            List<Candidate> candidates = candidateService.findByPositionByUniversity(university,position.getName());

            ResultDTO ResultDTO = new ResultDTO();

            ResultDTO.setPosition(position.getName());
            ResultDTO.setCandidates(candidates);

            candidateDTOS.add(ResultDTO);
        }

        return candidateDTOS;
    }
}
