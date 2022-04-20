package ma.ensias.winchesters.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<T> extends JpaRepository<T, Long> {
    Optional<T> findByUsername(String username);
    Optional<T> findByEmail(String email);
}
