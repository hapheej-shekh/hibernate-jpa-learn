package orm.hibernate.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class H2dbHibernateConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	/* JPA & Hibernate use same DataSource, It is used for-
	 * Create connections, Communicate with DB, Run SQL.
	 * SessionFactory & EntityManagerFactory both uses this DataSource */
	
	@Bean(name = "h2dbDataSource")
    public DriverManagerDataSource prodDataSource() {
		
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername("shekh");
        dataSource.setPassword("shekh");
        
        return dataSource;
    }

	// Hibernate use SessionFactory
    @Bean(name = "h2dbSessionFactory")
    public LocalSessionFactoryBean prodSessionFactory() {
    	
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(prodDataSource());
        sessionFactory.setPackagesToScan("orm.hibernate.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "h2dbTransactionManager")
    public PlatformTransactionManager prodTransactionManager(
    		@Qualifier("h2dbSessionFactory") SessionFactory sessionFactory) {
    	
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
    	
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        
        return props;
    }
}
