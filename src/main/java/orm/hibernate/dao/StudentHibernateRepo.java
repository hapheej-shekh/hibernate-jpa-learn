package orm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import orm.hibernate.entities.Student;

@Repository
public class StudentHibernateRepo {

	@Autowired
	@Qualifier("h2dbSessionFactory")
    private SessionFactory sessionFactory;
	
	public int save(Student student) {
		
        Session session = sessionFactory.openSession();
        Transaction trnx = session.beginTransaction();
        int id = -1;
        
        try {
        	Serializable outcome = session.save(student);
        	if(outcome instanceof Integer)
        		id = (int) outcome;
        	
        	trnx.commit();
        } catch(Exception ex) {
        	trnx.rollback();
        	ex.printStackTrace();
        }
        session.close();
        
        return id;
    }

    public Student findById(int id) {
    	
        Session session = sessionFactory.openSession();
        Student student = session.get(Student.class, id);
        session.close();
        return student;
    }

    public List<Student> findAll() {
    	
    	String hql = "FROM Student";
    	
        Session session = sessionFactory.openSession();
        List<Student> list = session.createQuery(hql, Student.class).list();
        session.close();
        return list;
    }

    public void update(Student student) {
    	
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(student);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(int id) {
    	
        Session session = sessionFactory.openSession();
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }
        session.close();
    }
    
    public Student findByIdHql(int id) {
    	
    	String hql = "seleft from Student where id="+id;
    	
        Session session = sessionFactory.openSession();
        Student student = session.createQuery(hql, Student.class).uniqueResult();
        session.close();
        
        return student;
    }

    public void deleteHql(int id) {
    	
    	String hql1 = "from Student where id=:id";
    	String hql2 = "delete from Student where id=:id";
    	
        Session session = sessionFactory.openSession();


        Query<Student> query = session.createQuery(hql1, Student.class);
        query.setParameter("id", id);
        Student student = query.uniqueResult();
        
        if (student != null) {
        	
            session.beginTransaction();

            Query<?> deleteQuery = session.createQuery(hql2);
            deleteQuery.setParameter("id", id);
            deleteQuery.executeUpdate();
            
            session.getTransaction().commit();
        }
        session.close();
    }
}
