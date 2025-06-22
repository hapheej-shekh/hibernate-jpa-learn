package orm.hibernate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orm.hibernate.dao.LibraryHibernateRepo;
import orm.hibernate.entities.Library;

@Service
public class LibraryService {

	@Autowired
    private LibraryHibernateRepo libraryRepo;

	public void save(Library library) {
		
		libraryRepo.save(library);
	}

	public Library findById(Integer id) {

		return libraryRepo.findById(id);
	}

	public List<Library> findAll() {

		return libraryRepo.findAll();
	}

	public void update(Library library) {

		libraryRepo.update(library);
	}

	public void delete(Integer id) {

		libraryRepo.delete(id);
	}
}
