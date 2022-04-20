package ma.ensias.winchesters.config;

import ma.ensias.winchesters.model.User;
import ma.ensias.winchesters.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ma.ensias.winchesters.security.ApplicationUserRole.ADMIN;
import static ma.ensias.winchesters.security.ApplicationUserRole.CLIENT;

@Configuration
public class AppConfig {
    private final PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;


    @Autowired
    public AppConfig(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            User hamza = new User();
            hamza.setId(1L);
            hamza.setUsername("benyazidhamza");
            hamza.setRole(ADMIN);
            hamza.setPassword(passwordEncoder.encode("12345678"));
            User meriem = new User();
            meriem.setId(2L);
            meriem.setUsername("meriem");
            meriem.setPassword(passwordEncoder.encode("12345678"));
            meriem.setRole(CLIENT);
            userRepository.save(hamza);
            userRepository.save(meriem);
        };
    }
}
