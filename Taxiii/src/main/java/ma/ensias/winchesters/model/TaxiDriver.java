package ma.ensias.winchesters.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "taxi_driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxiDriver extends User {
	@Column(name = "taxi_driver_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String idCard;
	private String driverLicense;
	private String insuranceCard;
	@OneToMany(mappedBy = "taxiDriver")
	private Collection<Ride> rides;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taxi_id")
	private Taxi taxi;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CLIENT_TAXIDRIVER", joinColumns = { @JoinColumn(name = "client_id") }, inverseJoinColumns = {
			@JoinColumn(name = "taxi_driver_id") })
	private Set<Client> clients;

}
