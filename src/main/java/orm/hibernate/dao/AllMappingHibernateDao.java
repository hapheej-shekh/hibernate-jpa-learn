package orm.hibernate.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import orm.hibernate.entities.sub.Children;
import orm.hibernate.entities.sub.Family;
import orm.hibernate.entities.sub.Father;
import orm.hibernate.entities.sub.Relative;

@Repository
public class AllMappingHibernateDao {

	@Autowired
	@Qualifier("h2dbSessionFactory")
    private SessionFactory sessionFactory;

	public int saveChildren(Children children) {
		
		Session session = sessionFactory.openSession();
        Transaction trnx = session.beginTransaction();
        int id = -1;
        
        try {
        	
        	if(children.getFather().getId() != null) {
        		
        		// Load existing Father to prevent transient null name insert
                Father existingFather = session.get(Father.class, children.getFather().getId());
                children.setFather(existingFather);
        	}
        	
        	Serializable outcome = session.save(children);
        	
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
	

	public int saveFamily(Family family) {

		Session session = sessionFactory.openSession();
        Transaction trnx = session.beginTransaction();
        int id = -1;
        
        try {
        	
        	if(family.getRelatives() != null) {
        		
        		Set<Relative> resolvedRelatives = new HashSet<>();
        		
        		for(Relative rel : family.getRelatives()) {
        			
        			if(rel.getId() != null) {
        				
        				Relative existingRelatives = session.get(Relative.class, rel.getId());
        				if(existingRelatives != null) {
        					resolvedRelatives.add(existingRelatives);
        				} else {
        					resolvedRelatives.add(rel);
        				}
        			}
        		}
        		family.setRelatives(resolvedRelatives); // Replace original set
        	}
        	
        	Serializable outcome = session.save(family);
        	
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

	public int save(Object object) {
		
		Session session = sessionFactory.openSession();
        Transaction trnx = session.beginTransaction();
        int id = -1;
        
        handleAssociations(object, session);
        
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
	
	//Automatically replaces transient OneToOne & OneToMany stub with the managed entity from DB
	private void handleAssociations(Object entity, Session session) {
		
	    Field[] fields = entity.getClass().getDeclaredFields();
	    for (Field field : fields) {
	        field.setAccessible(true);

	        if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
	            try {
	                Object referencedObj = field.get(entity);
	                if (referencedObj != null) {
	                    Field idField = referencedObj.getClass().getDeclaredField("id");
	                    idField.setAccessible(true);
	                    Object id = idField.get(referencedObj);

	                    if (id != null) {
	                        // fetch actual persistent object
	                        Object managedRef = session.get(referencedObj.getClass(), (Serializable) id);
	                        if (managedRef != null) {
	                            field.set(entity, managedRef); // replace the transient stub with managed object
	                        }
	                    }
	                }
	            } catch (Exception e) {
	                System.err.println("Association binding error: " + e.getMessage());
	            }
	        }
	    }
	}
}
