package orm.hibernate.config;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ImportAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class) // For Second level cache
public class H2dbHibernateConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	
	/* JPA & Hibernate use same DataSource, It is used for-
	 * Create connections, Communicate with DB, Run SQL.
	 * SessionFactory & EntityManagerFactory both uses this DataSource */
	
	@Bean(name = "h2dbDataSource")
    public DriverManagerDataSource dataSource() {
		
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername("shekh");
        dataSource.setPassword("shekh");
        
        return dataSource;
    }

	// Hibernate use SessionFactory
    @Bean(name = "h2dbSessionFactory")
    public SessionFactory sessionFactory() {
    	
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("orm.hibernate.entities");
        factoryBean.setHibernateProperties(hibernateProperties());
        
        try {
			factoryBean.afterPropertiesSet(); // Important to initialize cache
		} catch (IOException ex) {
			System.err.println("Cache enable issue: "+ex.getMessage());
		}
        
        SessionFactory sessionFactory = factoryBean.getObject();

        if (sessionFactory != null) {
            sessionFactory.getStatistics().setStatisticsEnabled(true); //Enable stats
        }

        return sessionFactory;
    }

    @Bean(name = "h2dbTransactionManager")
    public PlatformTransactionManager transactionManager(
    		@Qualifier("h2dbSessionFactory") SessionFactory sessionFactory) {
    	
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
    	
        Properties props = new Properties();
        
        // Enable Second-Level Cache
        props.setProperty("hibernate.cache.use_second_level_cache", "true");
        props.setProperty("hibernate.cache.use_query_cache", "true");
        props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

        // Use custom EhCache configuration
        props.setProperty("net.sf.ehcache.configurationResourceName", "ehcache.xml");

        // Enable Hibernate statistics
        props.setProperty("hibernate.generate_statistics", "true");
        props.setProperty("hibernate.session_factory_name", "h2dbSessionFactory");
        
        return props;
    }
}
