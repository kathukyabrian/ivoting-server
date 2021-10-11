package tech.ropaki.ivoting.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tech.ropaki.ivoting.domain.Candidate;
import tech.ropaki.ivoting.service.CandidateService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final CandidateService candidateService;

    private final Logger logger = LoggerFactory.getLogger(CandidateResource.class);

    public CandidateResource(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/candidates")
    public Candidate apply(@RequestBody Candidate candidate){

        return candidateService.apply(candidate);
    }

    @PutMapping("/candidates")
    public Candidate changeState(@RequestBody Candidate candidate) throws Exception {

        return candidateService.changeState(candidate);
    }

    @GetMapping("/candidates/{}")
    public List<Candidate> getByUniversity(@PathVariable String universityCode){

        return candidateService.getAllByUniversity(universityCode);
    }

    @GetMapping("/candidates")
    public List<Candidate> getAll(){

        return candidateService.getAll();
    }
}
