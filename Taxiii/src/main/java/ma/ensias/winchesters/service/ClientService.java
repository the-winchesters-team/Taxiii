package ma.ensias.winchesters.service;

import ma.ensias.winchesters.dto.UserResponseDto;
import ma.ensias.winchesters.mapper.EntityToDto;
import ma.ensias.winchesters.model.Client;
import ma.ensias.winchesters.model.User;
import ma.ensias.winchesters.respository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public UserResponseDto signUp(User user) {
        Client client = new Client(user);
        return EntityToDto.userToUserResponseDto(clientRepository.save(client));
    }
}
