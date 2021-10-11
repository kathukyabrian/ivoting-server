package tech.ropaki.ivoting.dto;

import lombok.Data;
import tech.ropaki.ivoting.domain.Candidate;

import java.util.List;

@Data
public class ResultDTO {

    private String position;

    private List<Candidate> candidates;

}
