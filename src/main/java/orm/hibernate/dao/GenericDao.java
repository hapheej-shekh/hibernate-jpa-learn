package orm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import orm.hibernate.entities.HQLEntity;

@Repository
public class GenericDao {

	@Autowired
	@Qualifier("h2dbSessionFactory")
    private SessionFactory sessionFactory;
	
	public int saveObject(Object object) {
		
		Transaction trnx = null;
        int id = -1;
        
        try (Session session = sessionFactory.openSession()) {
        	
        	trnx = session.beginTransaction();
        	
        	Serializable outcome = session.save(object);
        	if(outcome instanceof Integer)
        		id = (int) outcome;
        	
        	trnx.commit(); // session closed
        	
        } catch(Exception ex) {
        	
        	if(trnx!=null)
        		trnx.rollback();
        	
        	System.out.println("Failed to persist: "+ex.getStackTrace());
        }
        
        return id;
	}
	
	public int saveObjects(List<HQLEntity> entities) {
		
		Transaction trnx = null;
        
        try (Session session = sessionFactory.openSession()) {
        	
        	trnx = session.beginTransaction();
        	
        	int count = 0;
        	
        	for(HQLEntity entity : entities) {
        		
        		session.save(entity);
        		
        		// Flush and clear every 20 inserts to avoid memory bloat
                if (++count % 10 == 0) {
                    session.flush();
                    session.clear();
                }
        	}
        	
        	trnx.commit(); // session closed
        	
        } catch(Exception ex) {
        	
        	if(trnx!=null)
        		trnx.rollback();
        	
        	System.out.println("Failed to persist: "+ex.getStackTrace());
        	return -1;
        }
        
        return 1;
	}
	
	public <T> T getObject(Integer id, Class<T> entity) {
		
        try (Session session = sessionFactory.openSession()) {
        	
        	return session.get(entity, id);
        	
        } catch(Exception ex) {
        	
        	return null;
        }
	}
	
	public <T> String  testFirstLevelCache(Integer id, Class<T> entity) {
		
		T outcome1 = null;
		T outcome2 = null;
		String isCached = "'First Level Cache' test failed";
		
        try (Session session = sessionFactory.openSession()) {
        	
        	outcome1 = session.get(entity, id);
        	outcome2 = session.get(entity, id);
        	
        } catch(Exception ex) {
        	System.err.println("Failed to fetch entity for 'First Level Cache'");
        }
        
        if(outcome1 == outcome2) {
        	
        	isCached = "'First Level Cache' test success";
        	
        	System.out.println("First Level Cache\n----------------------");
        	System.out.println("HQLEntity hashCode1: "+System.identityHashCode(outcome1));
        	System.out.println("HQLEntity hashCode2: "+System.identityHashCode(outcome2));
        }
        
        return isCached;
	}
	
	public <T> String  testSecondLevelCache(Integer id, Class<T> entity) {
		
		Statistics stats = sessionFactory.getStatistics();
		stats.clear();
		
		String isCached = "'Second Level Cache' test failed";
		
        try (Session session = sessionFactory.openSession()) {
        	
        	session.get(entity, id);
        	
        } catch(Exception ex) {
        	System.err.println("Failed to fetch entity for 'Second Level Cache'");
        }
        
        try {Thread.sleep(10);} catch(Exception ex) {}	// Delay to close Session
        
        try (Session session = sessionFactory.openSession()) {
        	
        	session.get(entity, id);
        	
        } catch(Exception ex) {
        	System.err.println("Failed to fetch entity for 'Second Level Cache'");
        }
        
        long hitCount = stats.getSecondLevelCacheHitCount();
        long missCount = stats.getSecondLevelCacheMissCount();
        
        if(hitCount > 0 && hitCount >= missCount) {
        	
        	isCached = "'Second Level Cache' test success";
        	
        	System.out.println("Second Level Cache Hit Count: " + stats.getSecondLevelCacheHitCount());
            System.out.println("Second Level Cache Miss Count: " + stats.getSecondLevelCacheMissCount());
            System.out.println("Second Level Cache Put Count: " + stats.getSecondLevelCachePutCount());
            System.out.println("Query Cache Hit Count: " + stats.getQueryCacheHitCount());
            System.out.println("Query Cache Miss Count: " + stats.getQueryCacheMissCount());
            System.out.println("Query Cache Put Count: " + stats.getQueryCachePutCount());        	
        }
        
        return isCached;
	}
}
