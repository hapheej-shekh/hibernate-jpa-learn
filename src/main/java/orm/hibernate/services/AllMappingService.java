package orm.hibernate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orm.hibernate.beans.sub.Children;
import orm.hibernate.beans.sub.Family;
import orm.hibernate.beans.sub.Husband;
import orm.hibernate.beans.sub.Parent;
import orm.hibernate.dao.AllMappingHibernateDao;
import orm.hibernate.util.ObjectMapper;

@Service
public class AllMappingService {

	@Autowired
	private AllMappingHibernateDao mappingDao;
	
	
	public int saveHusband(Husband husband) {
		
		orm.hibernate.entities.sub.Husband res = 
				ObjectMapper.map(husband, orm.hibernate.entities.sub.Husband.class);
		
		return mappingDao.save(res);
	}

	public int saveParent(Parent parent) {
		
		orm.hibernate.entities.sub.Parent res = 
				ObjectMapper.map(parent, orm.hibernate.entities.sub.Parent.class);
		
		return mappingDao.save(res);
	}

	public int saveChildren(Children children) {
		
		orm.hibernate.entities.sub.Children res = 
				ObjectMapper.map(children, orm.hibernate.entities.sub.Children.class);
		
		return mappingDao.saveChildren(res);
	}

	public int saveFamily(Family family) {
		
		orm.hibernate.entities.sub.Family res = 
				ObjectMapper.map(family, orm.hibernate.entities.sub.Family.class);
		
		return mappingDao.saveFamily(res);
	}
}
