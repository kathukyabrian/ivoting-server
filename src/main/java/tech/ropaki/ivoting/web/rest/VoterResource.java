package tech.ropaki.ivoting.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tech.ropaki.ivoting.domain.Candidate;
import tech.ropaki.ivoting.dto.PositionCandidateDTO;
import tech.ropaki.ivoting.dto.ResultDTO;
import tech.ropaki.ivoting.dto.VoteDTO;
import tech.ropaki.ivoting.service.VotingService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class VoterResource {

    private final VotingService votingService;

    public VoterResource(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping("vote/load")
    public List<Candidate> loadCandidates(@RequestBody PositionCandidateDTO positionCandidateDTO){
;

        return votingService.loadPositionsAndCandidates(positionCandidateDTO);
    }

    @PostMapping("vote/positions")
    public List<String> loadPositions(@RequestBody String university){


        return votingService.loadPositions(university);
    }

    @PostMapping("vote")
    public String vote(@RequestBody VoteDTO voteDTO) throws Exception {

        return votingService.vote(voteDTO);
    }

    @GetMapping("vote/results")
    public List<ResultDTO> getResults(@RequestParam(required = false) String university){

        return votingService.results(university);
    }
}
