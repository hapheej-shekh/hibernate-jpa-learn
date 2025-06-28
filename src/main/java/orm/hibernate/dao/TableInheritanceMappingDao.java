package orm.hibernate.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class TableInheritanceMappingDao {

	@Autowired
	@Qualifier("h2dbSessionFactory")
    private SessionFactory sessionFactory;
	
	public int save(Object object) {
		
		Session session = sessionFactory.openSession();
        Transaction trnx = session.beginTransaction();
        int id = -1;
        
        try {
        	Serializable outcome = session.save(object);
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
}
