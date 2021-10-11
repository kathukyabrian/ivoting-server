package tech.ropaki.ivoting.dto;

import lombok.Data;

import java.util.List;

@Data
public class VoteDTO {

    private Long userId;

    private List<Long> candidates;
}
