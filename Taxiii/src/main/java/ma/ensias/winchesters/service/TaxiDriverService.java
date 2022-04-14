package ma.ensias.winchesters.service;

import ma.ensias.winchesters.dto.UserResponseDto;
import ma.ensias.winchesters.mapper.EntityToDto;
import ma.ensias.winchesters.model.TaxiDriver;
import ma.ensias.winchesters.model.User;
import ma.ensias.winchesters.respository.TaxiDriverRepository;
import org.springframework.stereotype.Service;

@Service
public class TaxiDriverService {

    private final TaxiDriverRepository taxiDriverRepository;

    public TaxiDriverService(TaxiDriverRepository taxiDriverRepository) {
        this.taxiDriverRepository = taxiDriverRepository;
    }

    public UserResponseDto signUp(User user) {
        TaxiDriver taxiDriver = new TaxiDriver(user);
        return EntityToDto.userToUserResponseDto(taxiDriverRepository.save(taxiDriver));
    }
}
