package bookstore.model;

import javax.persistence.*;

@Entity
@Table(name="books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="book_name")
	private String bookName;
	
	@Column(name="author_name")
	private String authorName;
	
	public Book() {
		
	}
	
	public Book(int id, String bookName, String authorName) {
		this.id = id;
		this.bookName = bookName;
		this.authorName = authorName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

}
