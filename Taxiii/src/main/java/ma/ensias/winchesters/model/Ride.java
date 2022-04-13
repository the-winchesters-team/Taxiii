package ma.ensias.winchesters.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ride")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ride {
	@Column(name = "ride_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	private Location source;
//	private Location destination;
	private Double price;
//	private Review review; 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taxi_driver_id")
	private TaxiDriver taxiDriver;

}
