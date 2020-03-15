package bookstore.main.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.main.model.Book;


public interface BookRepository extends JpaRepository<Book, Integer> {
	//CRUD operations on Book repository
}
