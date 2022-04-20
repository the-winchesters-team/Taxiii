package ma.ensias.winchesters.dto;

import lombok.Data;

@Data
public class RideDto {
    private String taxiDriver;
    private String client;
    private String from;
    private String to;
}
