package bookstore.main.controller;

import bookstore.main.exception.NotFoundException;
import bookstore.main.model.Book;
import bookstore.main.repos.BookRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class BookController {
	
	@Autowired
	private BookRepository bookRepo;
	
	@PostMapping("/book")
	public String addBook(@RequestBody Book book) {
		bookRepo.save(book);
		return "Book saved successfully";
	}
	
    @GetMapping("/book/{id}")
    public Book getBookByID(@PathVariable int id) {
    	Optional<Book> optionalBook = bookRepo.findById(id);
    	if(optionalBook.isPresent()) {
    		return optionalBook.get();
    	}else {
    		throw new NotFoundException("Book with id " + id + " cannot be found");
    	}
    }
	
	@GetMapping("/books")
	public List<Book> getBooks() {
		return bookRepo.findAll();
	}
	
	 @PostMapping("/books")
	 public String addBooks(@RequestBody List<Book> bookList){
	    bookRepo.saveAll(bookList);
	    return "Books saved successfully";
	 }
	
	@DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable int id) {
        return bookRepo.findById(id)
                .map(book -> {
                    bookRepo.delete(book);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Book with id " + id + " cannot be found"));
    }

}