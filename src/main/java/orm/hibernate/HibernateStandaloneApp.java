package orm.hibernate;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import orm.hibernate.entities.Person;

/**	Session Factory creates/holds, database connection using hibernate.cfg.xml configuration
 *	Session Factory is heavy object so create it once if possible
 */
public class HibernateStandaloneApp {

	private static SessionFactory factory;
	private static Session session;
	
	/*	hibernate.cfg.xml is for standalone app only, not needed in spring-boot*/
	
	public static void main(String[] args) {

		Configuration config = new Configuration();
		
		// by default also loads 'hibernate.cfg.xml'
        config.configure("hibernate.cfg.xml");
        
        /* Can also add entity here if not want to add in hibernate.cfg.xml
        config.addAnnotatedClass(Employee.class); */
        
        factory = config.buildSessionFactory();
        
        // below is shortcut
		//factory =  new Configuration().configure().buildSessionFactory();
		
        session =  factory.openSession();
		
		session.beginTransaction();
		session.persist(getPerson());
		session.getTransaction().commit();
		session.close();
		
		try {
			Thread.sleep(100);	// To give time to close session, before re-open
		} catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
		
		session =  factory.openSession();

		Person p = null;
		p = session.get(Person.class, 1);
		System.out.println(p);		
		
		close();
	}
	
	private static void close() {

		if(session.isOpen())
			session.close();
		
		if(null!=factory)
			factory.close();
	}
	
	private static Person getPerson() {
		Person p = new Person(0, "Arham", "Narsinghpur");
		p.setDate(LocalDate.now());
		p.setTime(LocalTime.now());
		p.setWifeName("NA");
		p.setDescription("Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum");
		
		return p;
	}
}
