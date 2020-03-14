package bookstore.controller;

import bookstore.model.Book;
import bookstore.repos.BookRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public Book addBook(@RequestBody Book book) {
		return bookRepo.save(book);
	}
	
	@GetMapping("/book")
	public List<Book> getBooks() {
		return bookRepo.findAll();
	}

}
