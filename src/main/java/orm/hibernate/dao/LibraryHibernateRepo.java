package orm.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import orm.hibernate.entities.Library;

@Repository
public class LibraryHibernateRepo {

	@Autowired
	@Qualifier("h2dbSessionFactory")
    private SessionFactory sessionFactory;
	
	public void save(Library library) {
		
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(library);
        session.getTransaction().commit();
        session.close();
    }

    public Library findById(int id) {
    	
        Session session = sessionFactory.openSession();
        Library library = session.get(Library.class, id);
        session.close();
        return library;
    }

    public List<Library> findAll() {
    	
        Session session = sessionFactory.openSession();
        List<Library> list = session.createQuery("from Library", Library.class).list();
        session.close();
        return list;
    }

    public void update(Library library) {
    	
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(library);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(int id) {
    	
        Session session = sessionFactory.openSession();
        Library library = session.get(Library.class, id);
        if (library != null) {
            session.beginTransaction();
            session.delete(library);
            session.getTransaction().commit();
        }
        session.close();
    }
}
