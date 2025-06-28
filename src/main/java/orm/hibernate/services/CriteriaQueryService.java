package orm.hibernate.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import orm.hibernate.entities.HQLEntity;

@Service
public class CriteriaQueryService {

	@Autowired
	@Qualifier("h2dbSessionFactory")
    private SessionFactory sessionFactory;
	
	// JPA latest Criteria Hibernate 5+
	public int testHQLQueries() {

		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<HQLEntity> cq = builder.createQuery(HQLEntity.class);
			Root<HQLEntity> root = cq.from(HQLEntity.class);
			
	//Criteria Query with Restrictions and Ordering
			
			// WHERE cost > 500 AND city = 'Delhi'
		    Predicate predicate = builder.and(
		    		builder.gt(root.get("cost"), 500),
		    		builder.equal(root.get("city"), "Delhi")
		    );
			
		    // ORDER BY cost descending
		    cq.select(root)
		      .where(predicate)
		      .orderBy(builder.desc(root.get("cost")));
			
		    List<HQLEntity> outcome = session.createQuery(cq).getResultList();
			
		    outcome.forEach(System.out::println);
		    System.out.println("\n");
		    
	//Projection - Selecting only name and city
		    CriteriaQuery<Object[]> cq1 = builder.createQuery(Object[].class);
		    Root<HQLEntity> root1 = cq1.from(HQLEntity.class);
		    
		    cq1.multiselect(root1.get("name"), root1.get("city"));

		    List<Object[]> outcome1 = session.createQuery(cq1).getResultList();
		    outcome1.forEach(e->{System.out.println(e[0]+", "+e[1]);});
		    System.out.println("\n");
		    
	//Aggregation - Average cost by city (GROUP BY + HAVING)
		    CriteriaQuery<Object[]> aggregationQuery = builder.createQuery(Object[].class);
		    Root<HQLEntity> aggRoot = aggregationQuery.from(HQLEntity.class);
		    Expression<String> cityExp = aggRoot.get("city");
		    Expression<Double> avgCost = builder.avg(aggRoot.get("cost"));

		    aggregationQuery.multiselect(cityExp, avgCost).groupBy(cityExp)
		    	.having(builder.gt(avgCost, 500));

		    List<Object[]> outcome2 = session.createQuery(aggregationQuery).getResultList();
		    outcome2.forEach(e -> System.out.println(e[0] + ", " + e[1]));
		    System.out.println("\n");
			
		    
	//Max Cost Across All Records
		    CriteriaQuery<Integer> cq2 = builder.createQuery(Integer.class);
		    Root<HQLEntity> root2 = cq2.from(HQLEntity.class);

		    cq2.select(builder.max(root2.get("cost")));
		    int outcome3 = session.createQuery(cq2).getSingleResult();
		    System.out.println("Max cost: "+outcome3);
		    System.out.println("\n");
		    
		    
	//Count of records by country
		    CriteriaQuery<Object[]> cq3 = builder.createQuery(Object[].class);
		    Root<HQLEntity> root3 = cq3.from(HQLEntity.class);

		    cq3.multiselect(root3.get("country"), builder.count(root))
		    	.groupBy(root3.get("country"));

		    List<Object[]> outcome4 = session.createQuery(cq3).getResultList();
		    outcome4.forEach(e->{System.out.println(e[1]+" -> "+e[0]);});
		    System.out.println("\n");
		    
		    
	//Convert Criteria to TypedQuery with Pagination
		    CriteriaQuery<HQLEntity> cq4 = builder.createQuery(HQLEntity.class);
		    Root<HQLEntity> root4 = cq4.from(HQLEntity.class);
		    
		    cq4.select(root4).orderBy(builder.asc(root4.get("id")));

		    TypedQuery<HQLEntity> query = session.createQuery(cq4);
		    query.setFirstResult(2 * 3); // page 2, size 3 → starting from 6th record
		    query.setMaxResults(5);

		    List<HQLEntity> coutcome5 = query.getResultList();
		    coutcome5.forEach(System.out::println);
		    System.out.println("\n");
		    
	//Dynamic Queries: Build queries based on optional parameters at runtime [search/filter]
		    
		    CriteriaQuery<HQLEntity> cq5 = builder.createQuery(HQLEntity.class);
		    Root<HQLEntity> root5 = cq5.from(HQLEntity.class);
		    
		    List<Predicate> predicates = new ArrayList<>();

		    HQLEntity e = new HQLEntity("Arham", "Narsinghpur");
		    e.setCost(500);
		    
		    if (e.getName() != null) {
		        predicates.add(builder.equal(root5.get("name"), e.getName()));
		    }
		    if (e.getCost() != null) {
		        predicates.add(builder.gt(root5.get("cost"), e.getCost()));
		    }

		    cq5.where(builder.and(predicates.toArray(new Predicate[0])));
		    List<HQLEntity> outcome6 = session.createQuery(cq5).getResultList();
		    outcome6.forEach(System.out::println);
		    System.out.println("\n");
		    
	//Using Tuple for Stronger Typed Projection [Instead of raw Object[] projections]
		    CriteriaQuery<Tuple> cq6 = builder.createTupleQuery();
		    Root<HQLEntity> root6 = cq6.from(HQLEntity.class);
		    cq6.multiselect(root6.get("name").alias("Name"), root6.get("city").alias("City"));

		    List<Tuple> result = session.createQuery(cq6).getResultList();
		    for (Tuple tuple : result) {
		        String name = tuple.get("Name", String.class);
		        String city = tuple.get("City", String.class);
		        System.out.println("Tuple["+name+"->"+city+"]");
		    }
		    System.out.println("\n");
		    
	//DTO Projection (Constructor Expressions): Map results directly into DTOs
		    CriteriaQuery<HQLEntity> cq7 = builder.createQuery(HQLEntity.class);
		    Root<HQLEntity> root7 = cq7.from(HQLEntity.class);
		    cq7.select(builder.construct(HQLEntity.class, root7.get("name"), root7.get("city")));

		    List<HQLEntity> results = session.createQuery(cq7).getResultList();
		    results.forEach(System.out::println);
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

/*	CriteriaQuery<HQLEntity> → full entity result
	CriteriaQuery<Object[]> → scalar projection
	CriteriaQuery<Tuple> → named projections with aliases (recommended for multi-select)
*/


