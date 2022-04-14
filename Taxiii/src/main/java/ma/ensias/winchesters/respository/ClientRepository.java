package ma.ensias.winchesters.respository;

import ma.ensias.winchesters.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends UserBaseRepository<Client>  {
}
