package tech.ropaki.ivoting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tech.ropaki.ivoting.domain.enumerations.Authority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String admissionNumber;

    private String universityName;

    private String phoneNumber;

    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private Boolean hasVoted;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", admissionNumber='" + admissionNumber + '\'' +
                ", universityName='" + universityName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", authority=" + authority +
                ", hasVoted=" + hasVoted +
                '}';
    }
}
