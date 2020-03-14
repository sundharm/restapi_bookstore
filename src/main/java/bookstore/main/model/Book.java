package bookstore.main.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="bookName")
	private String bookName;
	
	@Column(name="authorName")
	private String authorName;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Category> categories;
	
	public Book() {
		
	}
	
	public Book(int id, String bookName, String authorName) {
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
	
	public void setCategory(Set<Category> categories) {
		this.categories=categories;
	}
	
	public Set<Category> getCategory(){
		return this.categories;
	}

}
