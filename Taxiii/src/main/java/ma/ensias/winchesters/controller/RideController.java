package ma.ensias.winchesters.controller;

import ma.ensias.winchesters.dto.RideDto;
import ma.ensias.winchesters.model.Ride;
import ma.ensias.winchesters.service.RideService;
import org.springframework.web.bind.annotation.PostMapping;

public class RideController {
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    private Ride createRide(RideDto ride){
        return rideService.createRide(ride);
    }
}
