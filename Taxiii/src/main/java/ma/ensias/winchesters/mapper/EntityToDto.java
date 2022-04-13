package ma.ensias.winchesters.mapper;

import ma.ensias.winchesters.dto.UserResponseDto;
import ma.ensias.winchesters.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EntityToDto {
    public static UserResponseDto userToUserResponseDto(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }
    public static List<UserResponseDto> userToUserResponseDto(Collection<User> users){
        return users.stream().map(EntityToDto::userToUserResponseDto).collect(Collectors.toList());
    }
}
