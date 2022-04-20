package ma.ensias.winchesters.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.ensias.winchesters.security.ApplicationUserRole;

import javax.persistence.*;
import java.time.LocalDate;

//@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="\"user\"")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private ApplicationUserRole role;
	private boolean online;
	private boolean verified;
	private String profilePic;

	public User(User other) {
		this.id = other.id;
		this.username = other.username;
		this.password = other.password;
		this.firstName = other.firstName;
		this.lastName = other.lastName;
		this.phoneNumber = other.phoneNumber;
		this.gender = other.gender;
		this.creationDate = other.creationDate;
		this.last_known_location = other.last_known_location;
		this.email = other.email;
		this.city = other.city;
		this.role = other.role;
		this.online = other.online;
		this.verified = other.verified;
		this.profilePic = other.profilePic;
	}
}
