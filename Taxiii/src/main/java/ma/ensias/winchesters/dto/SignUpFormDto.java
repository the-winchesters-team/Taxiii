package ma.ensias.winchesters.dto;

import lombok.Data;

import java.time.LocalDate;

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
