package ma.ensias.winchesters.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "taxi_driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxiDriver extends User {

    private String idCard;
    private String driverLicense;
    private String insuranceCard;
    @OneToMany(mappedBy = "taxiDriver")
    private Collection<Ride> rides;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxi_id")
    private Taxi taxi;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CLIENT_TAXIDRIVER", joinColumns = {@JoinColumn(name = "client_id")}, inverseJoinColumns = {
            @JoinColumn(name = "taxi_driver_id")})
    private Set<Client> clients;

    public TaxiDriver(User user) {
        super(user);
    }
}
