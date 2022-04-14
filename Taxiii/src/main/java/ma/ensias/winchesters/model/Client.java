package ma.ensias.winchesters.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {

	private boolean disabled;
	@OneToMany(mappedBy = "client")
	private Collection<Ride> rides;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CLIENT_TAXIDRIVER", joinColumns = { @JoinColumn(name = "client_id") }, inverseJoinColumns = {
			@JoinColumn(name = "taxi_driver_id") })
	private Set<TaxiDriver> taxiDrivers;

	public Client(User user){
		super(user);
	}
}
