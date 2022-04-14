package ma.ensias.winchesters.respository;

import ma.ensias.winchesters.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends UserBaseRepository<User> {
}
