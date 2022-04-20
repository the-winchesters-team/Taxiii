package ma.ensias.winchesters.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Location {
 private String longitude;
 private String latitude;
}
