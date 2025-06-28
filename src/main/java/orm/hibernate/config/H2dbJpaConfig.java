package orm.hibernate.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	    basePackages = "orm.hibernate.repositories", 
	    entityManagerFactoryRef = "h2dbJpaEntityManagerFactory",
	    transactionManagerRef = "h2dbJpaTransactionManager"
)
/*	@EnableJpaRepositories works only with EntityManagerFactory, 
 * 	not with Hibernate's SessionFactory */
public class H2dbJpaConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	
	/* JPA & Hibernate use same DataSource, It is used for-
	 * Create connections, Communicate with DB, Run SQL.
	 * SessionFactory & EntityManagerFactory both uses this DataSource */

	@Primary
	@Bean(name="h2dbJpaDataSource")
    public DataSource dataSource() {
		
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl(dbUrl);
        ds.setUsername("shekh");
        ds.setPassword("shekh");
        return ds;
    }

	// JPA uses EntityManagerFactory
	@Primary
    @Bean(name="h2dbJpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    	
        LocalContainerEntityManagerFactoryBean emf = 
        		new LocalContainerEntityManagerFactoryBean();
        
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("orm.hibernate.entities"); // Adjust to your entities
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(hibernateProperties());
        
        return emf;
    }

    /*
    @Bean(name="h2dbJpaTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    	
        return new JpaTransactionManager(emf);
    }*/
    
	@Primary
    @Bean(name="h2dbJpaTransactionManager")
    public PlatformTransactionManager transactionManager(
    	@Qualifier("h2dbJpaEntityManagerFactory") EntityManagerFactory emf) {
    	
        return new JpaTransactionManager(emf);
    }

    private Properties hibernateProperties() {
    	
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update");  // Use 'validate' or 'none' for prod
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        //props.setProperty("hibernate.show_sql", "true");
        //props.setProperty("hibernate.format_sql", "true");
        return props;
    }
}
