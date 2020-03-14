package bookstore.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import bookstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
