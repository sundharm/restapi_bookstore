package bookstore.main.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import bookstore.main.model.Book;


public interface BookRepository extends JpaRepository<Book, Integer> {
	//CRUD operations on Book repository
	
	@Transactional
	void deleteByIdIn(List<Integer> bookList);
}

