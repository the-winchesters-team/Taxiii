package ma.ensias.winchesters.service;

import ma.ensias.winchesters.dto.RideDto;
import ma.ensias.winchesters.model.Client;
import ma.ensias.winchesters.model.Ride;
import ma.ensias.winchesters.model.TaxiDriver;
import ma.ensias.winchesters.respository.RideRepository;

public class RideService {
    private final TaxiDriverService taxiDriverService;
    private final ClientService clientService;
    private final RideRepository rideRepository;

    public RideService(TaxiDriverService taxiDriverService, ClientService clientService, RideRepository rideRepository) {
        this.taxiDriverService = taxiDriverService;
        this.clientService = clientService;
        this.rideRepository = rideRepository;
    }

    public Ride createRide(RideDto rideDto) {
        Client client = clientService.getClient(rideDto.getClient());
        TaxiDriver taxiDriver = taxiDriverService.getTaxiDriver(rideDto.getTaxiDriver());
        Ride ride = new Ride();
        ride.setClient(client);
        ride.setTaxiDriver(taxiDriver);
        return rideRepository.save(ride);
    }
}
