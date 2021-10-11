package tech.ropaki.ivoting.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_universities")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 200, nullable = false)
    private String name;

    @Column(length = 10, unique = true, nullable = false)
    private String code;

    @Column(length = 10)
    private Boolean isOpen;
}
