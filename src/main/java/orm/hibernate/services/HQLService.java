package orm.hibernate.services;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import orm.hibernate.entities.HQLEntity;

@Service
public class HQLService {

	@Autowired
	@Qualifier("h2dbSessionFactory")
    private SessionFactory sessionFactory;
	
	public int testHQLQueries() {
		
		Session session = null;
		Transaction trnx = null;
		
		try {
			session = sessionFactory.openSession();
        	
     //Get all records   	
        	Query<HQLEntity> query1 = session.createQuery("from HQLEntity", HQLEntity.class);
        	List<HQLEntity> outcome1 = query1.list();
        	outcome1.forEach(System.out::println);
        	System.out.println("\n");
        	
     //Select specific fields   	
        	Query<Object[]> query2 = session.createQuery("select name, cost from HQLEntity", Object[].class);
        	List<Object[]> outcome2 = query2.list();
        	outcome2.forEach(e->{System.out.println(e[0]+", "+e[1]);});
        	System.out.println("\n");
        	
     //Named Parameters
        	Query<HQLEntity> query3 = session.createQuery(
        		    "from HQLEntity where cost > :minCost and city = :city", HQLEntity.class);
        	query3.setParameter("minCost", 500);
        	query3.setParameter("city", "Delhi");

        	List<HQLEntity> outcome3 = query3.list();
        	outcome3.forEach(System.out::println);
        	System.out.println("\n");
        	
     //Query with Alias
        	Query<HQLEntity> query = session.createQuery("from HQLEntity e where e.cost > 1000", HQLEntity.class);
        	
        	List<HQLEntity> outcome = query.list();
        	outcome.forEach(System.out::println);
        	System.out.println("\n");
        	
     //Exact match
        	query = session.createQuery("from HQLEntity where city = 'Delhi'", HQLEntity.class);

        	outcome = query.list();
        	outcome.forEach(System.out::println);
        	System.out.println("\n");
        	
     //Greater than/less than
        	query = session.createQuery("from HQLEntity where cost > 500", HQLEntity.class);
        	
        	outcome = query.list();
        	outcome.forEach(System.out::println);
        	System.out.println("\n");
        	
     //Between
        	query = session.createQuery("from HQLEntity where cost between 300 and 800", HQLEntity.class);
        	
        	outcome = query.list();
        	outcome.forEach(System.out::println);
        	System.out.println("\n");
        	
     //Like
        	query = session.createQuery("from HQLEntity where name like 'Shekh%'", HQLEntity.class);
        	
        	outcome = query.list();
        	outcome.forEach(System.out::println);
        	System.out.println("\n");

     //IN and NOT IN
        	query = session.createQuery("from HQLEntity where country in ('India', 'USA')", HQLEntity.class);
        	
        	outcome = query.list();
        	outcome.forEach(System.out::println);
        	System.out.println("\n");
        	
     //ORDER BY
        	query = session.createQuery("from HQLEntity order by cost desc", HQLEntity.class);
        	
        	outcome = query.list();
        	outcome.forEach(System.out::println);
        	System.out.println("\n");
        	
     //Aggregations (COUNT, SUM, AVG, MIN, MAX)
        	
        	// Total records
        	Query<Long> q1 = session.createQuery("select count(*) from HQLEntity", Long.class);
        	
        	List<Long> aggr1 = q1.list();
        	aggr1.forEach(System.out::println);
        	System.out.println("\n");
        	
        	// Average cost
        	Query<Double> q2 = session.createQuery("select avg(cost) from HQLEntity", Double.class);
        	
        	List<Double> aggr2 = q2.list();
        	aggr2.forEach(System.out::println);
        	System.out.println("\n");

        	// Max cost
        	Query<Integer> q3 = session.createQuery("select max(cost) from HQLEntity", Integer.class);
        	
        	List<Integer> aggr3 = q3.list();
        	aggr3.forEach(System.out::println);
        	System.out.println("\n");

     //Group By and Having
        	Query<Object[]> query4 = session.createQuery(
        		    "select city, avg(cost) from HQLEntity group by city having avg(cost) > 500", Object[].class);
        	
        	List<Object[]> outcome4 = query4.list();
        	outcome4.forEach(e->{System.out.println(e[0]+", "+e[1]);});
        	System.out.println("\n");
        	
            //Pagination
        	Query<HQLEntity> query7 = session.createQuery("from HQLEntity order by id", HQLEntity.class);
        	query7.setFirstResult(0);    // start row
        	query7.setMaxResults(10);    // page size
        	
        	List<HQLEntity> outcome7 = query7.list();
        	outcome7.forEach(System.out::println);
        	System.out.println("\n");
        	
    //Constructor Expression- Must have a matching constructor.
        	Query<HQLEntity> query8 = session.createQuery(
        		    "select new orm.hibernate.entities.HQLEntity(name, city) from HQLEntity", HQLEntity.class);
        	
        	List<HQLEntity> outcome8 = query8.list();
        	outcome8.forEach(System.out::println);
        	System.out.println("\n");

    //Scalar Results with Aliases
        	Query<Object[]> query9 = session.createQuery(
        		    "select e.name as name, e.cost as cost from HQLEntity e", Object[].class);

        	List<Object[]> outcome9 = query9.list();
        	outcome9.forEach(e -> System.out.println(e[0] + ", " + e[1]));

    //Subquery: Filter by Maximum Cost
        	Query<HQLEntity> query10 = 
        			session.createQuery("from HQLEntity e where e.cost = "
        					+ "(select max(cost) from HQLEntity)", HQLEntity.class);
        	
        	List<HQLEntity> outcome10 = query10.list();
        	outcome10.forEach(System.out::println);
        	System.out.println("\n");
        	
    //Subquery in IN Clause
        	Query<HQLEntity> query11 = 
        			session.createQuery("from HQLEntity e where e.city in "
        			+ "(select city from HQLEntity group by city having avg(cost) > 500)", 
        			HQLEntity.class);
        	
        	List<HQLEntity> outcome11 = query11.list();
        	outcome11.forEach(System.out::println);
        	System.out.println("\n");

    //Correlated Subquery
        	Query<HQLEntity> query12 = 
        			session.createQuery("from HQLEntity e where e.cost > "
        			+ "(select avg(hql.cost) from HQLEntity hql where hql.country = e.country)", 
        			HQLEntity.class);
        	
        	List<HQLEntity> outcome12 = query12.list();
        	outcome12.forEach(System.out::println);
        	System.out.println("\n");

    //Nested Aggregation with Grouping
        	Query<Object[]> query13 = 
        			session.createQuery("select country, city, avg(cost) from HQLEntity "
        					+ "group by country, city having sum(cost) > 1000", Object[].class);
        	
        	List<Object[]> outcome13 = query13.list();
        	outcome13.forEach(e->{System.out.println(e[0]+", "+e[1]+", "+e[2]);});
        	System.out.println("\n");
        	
    //Self-Join (using aliases)
        	Query<Object[]> query14 = 
        			session.createQuery("select e1.name, e2.name from HQLEntity e1, "
        			+ "HQLEntity e2 where e1.city = e2.city and e1.cost > e2.cost", Object[].class);
        	
        	List<Object[]> outcome14 = query14.list();
        	outcome14.forEach(e->{System.out.println(e[0]+", "+e[1]);});
        	System.out.println("\n");
        	
    //Select Entity with Subquery Result as Projection
        	Query<Object[]> query15 = 
        			session.createQuery("select e.name, e.cost, (select avg(cost) from HQLEntity "
        			+ "hql where hql.city = e.city) from HQLEntity e", Object[].class);
        	
        	List<Object[]> outcome15 = query15.list();
        	outcome15.forEach(e->{System.out.println(e[0]+", "+e[1]+", "+e[2]);});
        	System.out.println("\n");
        	
    //Nested Subquery + Case Expression
        	Query<Object[]> query16 = 
        			session.createQuery("select e.name, case when e.cost > "
        			+ "(select avg(cost) from HQLEntity hql where hql.city = e.city) "
        			+ "then 'Expensive' else 'Cheap' end from HQLEntity e", Object[].class);
        	
        	List<Object[]> outcome16 = query16.list();
        	outcome16.forEach(e->{System.out.println(e[0]+", "+e[1]);});
        	System.out.println("\n");
        	
    //Subquery with exists
        	Query<HQLEntity> query17 = 
        			session.createQuery("from HQLEntity e where exists "
        			+ "(select 1 from HQLEntity hql where hql.city = "
        			+ "e.city group by hql.city having count(*) > 2)", HQLEntity.class);
        	
        	List<HQLEntity> outcome17 = query17.list();
        	outcome17.forEach(System.out::println);
        	System.out.println("\n");

        	trnx = session.beginTransaction();
        	
     //Update Query
        	Query<?> query5 = session.createQuery(
        		    "update HQLEntity set cost = cost + 100 where city = :city");
        	query5.setParameter("city", "Mumbai");
        	int rowsUpdated = query5.executeUpdate();
        	System.out.println("Rows updated: "+rowsUpdated);

    //Delete Query
        	Query<?> query6 = session.createQuery("delete from HQLEntity where name = :name");
        	query6.setParameter("name", "Lucifer");
        	int rowsDeleted = query6.executeUpdate();
        	System.out.println("Rows deleted: "+rowsDeleted);
    
        	trnx.commit();
        	
		} catch(Exception ex) {
			
			if(trnx!=null)
        		trnx.rollback();
        	
        	System.out.println("Failed to persist: "+ex.getMessage());
        	
        	return -1;
		}
		
		if(session!=null && session.isOpen())
			session.close();
		
		return 1;
	}

	public int testCollectionTable() {

		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
	//Find entities with a specific hobby (HQL)
			
			Query<HQLEntity> query = session.createQuery(
				    "SELECT DISTINCT e FROM HQLEntity e JOIN e.hobbies h WHERE h = :hobby", HQLEntity.class);
			query.setParameter("hobby", "Cricket");
			List<HQLEntity> result = query.list();
			result.forEach(h->{
				System.out.println("["+String.join(", ", h.getHobbies())+"]");
			});
			System.out.println("\n");
			
	//Find entities with a hobby, ordered by cost descending (HQL)
			
			query = session.createQuery(
				    "SELECT DISTINCT e FROM HQLEntity e JOIN e.hobbies h WHERE h = :hobby ORDER BY e.cost DESC", HQLEntity.class);
			query.setParameter("hobby", "Chess");
			result = query.list();
			result.forEach(h->{
				System.out.println("["+String.join(", ", h.getHobbies())+"]");
			});
			System.out.println("\n");

	//Find all entities with any of multiple hobbies
			
			List<String> hobbies = Arrays.asList("Golf", "Badminton");

			query = session.createQuery(
			    "SELECT DISTINCT e FROM HQLEntity e JOIN e.hobbies h WHERE h IN (:hobbies)", HQLEntity.class);
			query.setParameter("hobbies", hobbies);
			result = query.list();
			result.forEach(h->{
				System.out.println("["+String.join(", ", h.getHobbies())+"]");
			});
			System.out.println("\n");
			
	//Count entities by hobby
			
			Query<Long> query1 = session.createQuery(
				    "SELECT COUNT(DISTINCT e.id) FROM HQLEntity e JOIN e.hobbies h WHERE h = :hobby", Long.class);
			query1.setParameter("hobby", "Chess");
			Long count = query1.uniqueResult();
			System.out.println("Chess players: "+count);
			System.out.println("\n");
			
	//Native SQL Example
				
			List<HQLEntity> list = 
					session.createNativeQuery("SELECT * FROM hqlentity h JOIN hql_hobbies hh "
					+ "ON h.id = hh.id WHERE hh.hobby = :hobby ORDER BY h.cost DESC", HQLEntity.class)
				.setParameter("hobby", "Cycling")
				.getResultList();

			list.forEach(h->{
				System.out.println("["+String.join(", ", h.getHobbies())+"]");
			});
			System.out.println("\n");
			
		} catch(Exception ex) {
			        	
        	System.out.println("Failed to persist: "+ex.getMessage());
        	
        	return -1;
		}
		
		if(session!=null && session.isOpen())
			session.close();
		
		return 1;
	}
}
