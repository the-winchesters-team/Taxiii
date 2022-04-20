package ma.ensias.winchesters.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private LocalDate birthdate;
    private String role;
    private String status;
}
