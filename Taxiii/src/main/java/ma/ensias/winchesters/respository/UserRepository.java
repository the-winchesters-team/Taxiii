package ma.ensias.winchesters.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ensias.winchesters.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
