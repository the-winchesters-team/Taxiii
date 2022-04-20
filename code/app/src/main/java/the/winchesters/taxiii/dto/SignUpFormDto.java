package the.winchesters.taxiii.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SignUpFormDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private LocalDate birthDate;
    private String role;
    private String password;
    private String passwordConfirmation;
}
