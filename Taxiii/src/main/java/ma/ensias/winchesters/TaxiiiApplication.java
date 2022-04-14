package ma.ensias.winchesters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TaxiiiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiiiApplication.class, args);
	}

}
