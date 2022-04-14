package ma.ensias.winchesters.service;

import ma.ensias.winchesters.auth.AuthenticationFacade;
import ma.ensias.winchesters.dto.SignUpFormDto;
import ma.ensias.winchesters.dto.UserResponseDto;
import ma.ensias.winchesters.exceptions.InvalidEmailException;
import ma.ensias.winchesters.exceptions.InvalidUsernameException;
import ma.ensias.winchesters.mapper.EntityToDto;
import ma.ensias.winchesters.model.User;
import ma.ensias.winchesters.respository.UserRepository;
import ma.ensias.winchesters.security.ApplicationUserRole;
import ma.ensias.winchesters.security.PasswordConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ClientService clientService;
    private final AuthenticationFacade authenticationFacade;
    private final TaxiDriverService taxiDriverService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ClientService clientService, AuthenticationFacade authenticationFacade, PasswordEncoder passwordEncoder, TaxiDriverService taxiDriverService, PasswordEncoder passwordEncoder1) {
        this.userRepository = userRepository;
        this.clientService = clientService;
        this.authenticationFacade = authenticationFacade;
        this.taxiDriverService = taxiDriverService;
        this.passwordEncoder = passwordEncoder1;
    }

    public UserResponseDto getUser() {
        String username = authenticationFacade.getAuthenticatedUsername();

        if (!username.equals("anonymousUser")) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException(String.format("user %s not found", username)));
            return EntityToDto.userToUserResponseDto(user);
        }
        throw new IllegalStateException("user must be authenticated");
    }

    public List<UserResponseDto> getUsers() {
        return EntityToDto.userToUserResponseDto(userRepository.findAll());
    }

    public UserResponseDto adminCreateUser(User user) {

        return EntityToDto.userToUserResponseDto(userRepository.save(user));
    }

    public UserResponseDto singUp(SignUpFormDto signUpForm) {
        userRepository.findByUsername(signUpForm.getUsername())
                .ifPresent(
                        (u) -> {
                            throw new InvalidUsernameException(String.format("username %s is already taken.", u.getUsername()));
                        });
        userRepository.findByEmail(signUpForm.getEmail())
                .ifPresent(
                        (u) -> {
                            throw new InvalidEmailException(String.format("email %s is already taken.", u.getEmail()));
                        });
        String password = signUpForm.getPassword();
        String passwordConfirmation = signUpForm.getPasswordConfirmation();
        if (!password.equals(passwordConfirmation))
            throw new IllegalStateException("password and password confirmation are not the same.");
        if (!PasswordConfig.isValid(password))
            throw new IllegalStateException("password must contain ..TODO.....");

        User user = new User();
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        user.setFirstName(signUpForm.getFirstName());
        user.setLastName(signUpForm.getLastName());
        user.setUsername(signUpForm.getUsername());

        //TODO: add email validation
        user.setEmail(signUpForm.getEmail());
        user.setRole(ApplicationUserRole.fromName(signUpForm.getRole()));

        switch (Objects.requireNonNull(ApplicationUserRole.fromName(signUpForm.getRole()))){
            case TAXI_DRIVER -> {
                return taxiDriverService.signUp(user);
            }
            case CLIENT -> {
                return clientService.signUp(user);
            }
            case ADMIN -> {
                //TODO
            }
        }
        return new UserResponseDto();
    }

    @Transactional
    public void updateName(Long userId, String firstName, String lastName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.format("user with id %d not found", userId)));
        if (firstName != null)
            user.setFirstName(firstName);
        if (lastName != null)
            user.setLastName(lastName);
    }

    @Transactional
    public void updateEmail(Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.format("user with id %d not found", userId)));
        user.setEmail(email);
    }


    public Map<String, String> getAuthenticatedUser() {
        return Map.of(
                "username", authenticationFacade.getAuthenticatedUsername(),
                "role", authenticationFacade.getAuthenticatedUserRole()
        );
    }
}
