package tech.ropaki.ivoting.web.rest.vm;

import lombok.Data;

@Data
public class ChangePasswordVM {

    private String newPassword;

    private String passwordConfirmation;
}
