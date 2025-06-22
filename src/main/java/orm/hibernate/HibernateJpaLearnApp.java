package orm.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernateJpaLearnApp {

	/* In Spring Boot, can access the H2 web console via
	 * http://localhost:<PORT>/h2-console */
	
	/* Springboot uses properties file instead of 'hibernate.cfg.xml'	*/
	
	public static void main(String[] args) {
		
		SpringApplication.run(HibernateJpaLearnApp.class, args);
	}
}
