package bookstore.main.controller;

import bookstore.main.exception.NotFoundException;
import bookstore.main.model.Book;
import bookstore.main.repos.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class BookController {
	
	@Autowired
	private BookRepository bookRepo;
	
	//End point to add a single book
	@PostMapping("/book")
	public String addBook(@RequestBody Book book) {
		bookRepo.save(book);
		return "Book saved successfully";
	}
	
	//End point to search for a book using book ID
    @GetMapping("/book/{id}")
    public Book getBookByID(@PathVariable int id) {
    	//using optional to find if a book record is present
    	Optional<Book> optionalBook = bookRepo.findById(id);
    	if(optionalBook.isPresent()) {
    		return optionalBook.get();
    	}else {
    		throw new NotFoundException("Book with id " + id + " cannot be found");
    	}
    }
	
    //End point to get all books
	@GetMapping("/books")
	public List<Book> getBooks() {
		return bookRepo.findAll();
	}
	
	//End point to add multiple books
	@PostMapping("/books")
	public String addBooks(@RequestBody List<Book> bookList){
	   bookRepo.saveAll(bookList);
	   return "Books saved successfully";
	}
	
	//End point to delete a book using book ID
	@DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable int id) {
		//get the book using the given ID and delete if found
        return bookRepo.findById(id)
                .map(book -> {
                    bookRepo.delete(book);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Book with id " + id + " cannot be found"));
    }
	
	//End point to edit book details
    @PutMapping("/book/{id}")
    public Book editBook(@PathVariable int id,
                                   @RequestBody Book editedBook) {
        return bookRepo.findById(id)
                .map(book -> {
                    book.setBookName(editedBook.getBookName());
                    book.setAuthorName(editedBook.getAuthorName());
                    return bookRepo.save(book);
                }).orElseThrow(() -> new NotFoundException("Book with id " + id + " cannot be found"));
    }
    
    @PostMapping("/deletebooks")
    public String deleteMultipleBooks(@RequestBody List<Book> bookList) {
    	
    	List<Integer> a = new ArrayList<Integer>(); 
    	for(Book b:bookList) {
    		a.add(b.getId());
    	}
    	bookRepo.deleteByIdIn(a);
    	return "Deleted successfully";
    }

}