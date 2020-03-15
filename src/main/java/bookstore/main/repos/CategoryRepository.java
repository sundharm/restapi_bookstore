package bookstore.main.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.main.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	//CRUD operations on Category repository
}
