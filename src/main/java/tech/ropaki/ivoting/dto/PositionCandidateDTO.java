package tech.ropaki.ivoting.dto;

import lombok.Data;
import tech.ropaki.ivoting.domain.Candidate;

import java.util.List;
import java.util.Map;

@Data
public class PositionCandidateDTO {

    private String position;

    private String university;
}
