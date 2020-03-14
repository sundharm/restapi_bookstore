package bookstore.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import bookstore.model.Book;


public interface BookRepository extends JpaRepository<Book, Integer> {

}
