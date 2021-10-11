package tech.ropaki.ivoting.web.rest.vm;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginVM {

    private String email;

    @NotNull
    private String password;


}
