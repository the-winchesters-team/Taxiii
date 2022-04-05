package ma.ensias.winchesters.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="\"user\"")
@Inheritance
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String gender;
	private LocalDate creationDate;
	private String last_known_location;
	private String email;
	@Enumerated(EnumType.STRING)
	private CityEnum city;
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	private boolean online;
	private boolean verified;
	private String profilePic;

}
