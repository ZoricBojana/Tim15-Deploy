package rs.travel.bookingWithEase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableTransactionManagement
public class BookingWithEaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingWithEaseApplication.class, args);
	}

}
