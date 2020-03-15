package bookstore.main.controller;

import bookstore.main.model.Category;
import bookstore.main.repos.CategoryRepository;
import bookstore.main.repos.BookRepository;
import bookstore.main.exception.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	//End point to add a category using book id to assign book with the category
	@PostMapping("/book/{book_id}/category")
	public Category addCategory(@PathVariable int book_id, @RequestBody Category category) {
		return bookRepo.findById(book_id).map(book->{
				category.setBook(book);
				return categoryRepo.save(category);
				}).orElseThrow(() -> new NotFoundException("Book not found"));
	
	}
	
	//End point to delete category using category ID, from a book given the book ID
	@DeleteMapping("/book/{book_id}/category/{category_id}")
    public String deleteCategory(@PathVariable int book_id,
                                   @PathVariable int category_id) {
		
		//Return book not found if the book with given ID cannot be found
        if(!bookRepo.existsById(book_id)) {
            throw new NotFoundException("Book not found!");
        }       
        return categoryRepo.findById(category_id)
                .map(category -> {
                    categoryRepo.delete(category);
                    return "Deleted Successfully";
                }).orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
	
