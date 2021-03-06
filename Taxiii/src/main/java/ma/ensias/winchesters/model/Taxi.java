package ma.ensias.winchesters.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Taxi {
	@Column(name = "taxi_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String plateNumber;
	private boolean disabled;
	@OneToMany(mappedBy = "taxi")
	private Collection<TaxiDriver> drivers;
}
