package tech.ropaki.ivoting.domain;

import lombok.Data;
import tech.ropaki.ivoting.domain.enumerations.CandidateStatus;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String position;

    private String university;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private CandidateStatus status;

    private Long voteCount;
}
