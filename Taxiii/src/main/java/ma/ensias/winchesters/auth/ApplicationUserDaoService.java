package ma.ensias.winchesters.auth;

import ma.ensias.winchesters.model.User;
import ma.ensias.winchesters.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("real")
public class ApplicationUserDaoService implements ApplicationUserDao {
    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new IllegalStateException(String.format("user with username %s not found.",username))
        );
        return Optional.of(new ApplicationUser(
                user.getUsername(),
                user.getPassword(),
                user.getRole().getGrantedAuthorities(),
                //TODO change this !!
                true,
                true,
                true,
                true
                )
        );
    }
}
