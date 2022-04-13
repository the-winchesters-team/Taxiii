package ma.ensias.winchesters.respository;

import ma.ensias.winchesters.model.TaxiDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiDriverRepository extends JpaRepository<TaxiDriver, Long> {
}
