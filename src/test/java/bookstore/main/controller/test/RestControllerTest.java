package bookstore.main.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bookstore.main.RestBookStoreApplication;
import bookstore.main.model.*;
import bookstore.main.repos.BookRepository;
import bookstore.main.repos.CategoryRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {
	
	//To map from object to string for mock request
	private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository mockRepository;
    
    @MockBean
    private CategoryRepository mockRepository1;
    
    @Before
    public void init() {
        Book book = new Book(1,"TestBookName", "TestAuthorName");
        when(mockRepository.findById(1)).thenReturn(Optional.of(book));
    }
    
    //Test to see if the end point to return a book using the given ID works correctly
	@Test
    public void getBookByIDTest() throws Exception {

        mockMvc.perform(get("/api/book/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName", is("TestBookName")))
                .andExpect(jsonPath("$.authorName", is("TestAuthorName")));

        verify(mockRepository, times(1)).findById(1);

    }
	
	//Test to check if all the books added are being returned when called the getAllBooks end point
	@Test
    public void getBooksTest() throws Exception {

        List<Book> books = Arrays.asList(
                new Book(1,"Book_1", "Author_1"),
                new Book(2,"Book_2", "Author_2"));

        when(mockRepository.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bookName", is("Book_1")))
                .andExpect(jsonPath("$[0].authorName", is("Author_1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].bookName", is("Book_2")))
                .andExpect(jsonPath("$[1].authorName", is("Author_2")));

        verify(mockRepository, times(1)).findAll();
    }
	
	//Test to see if a book can be added successfully
    @Test
    public void addBookTest() throws Exception {

        Book newBook = new Book(1,"Java Programming", "Javaman");
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);
        mockMvc.perform(post("/api/book")
                .content(objectMapper.writeValueAsString(newBook))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());       
        verify(mockRepository, times(1)).save(any(Book.class));

    }
    
    //Test to see if a category can be successfully assigned to a book
    @Test
    public void addCategoryTest() throws Exception {
    	Book newBook = new Book(1,"Java Programming", "Javaman");
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);
        
        Category category = new Category(1, "cat1");
        when(mockRepository1.save(any(Category.class))).thenReturn(category);
       
        mockMvc.perform(post("/api/book")
                .content(objectMapper.writeValueAsString(newBook)));
        mockMvc.perform(post("/api/book/1/category")
                .content(objectMapper.writeValueAsString(category))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.categoryName", is("cat1")));  	
        verify(mockRepository1, times(1)).save(any(Category.class));
    }
    
    //Test to see if a book can be edited successfully
    @Test
    public void editBookTest() throws Exception {

        Book editBook = new Book(1, "editedBook", "editedAuthor");
        when(mockRepository.save(any(Book.class))).thenReturn(editBook);

        mockMvc.perform(put("/api/book/1")
                .content(objectMapper.writeValueAsString(editBook))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.bookName", is("editedBook")))
                .andExpect(jsonPath("$.authorName", is("editedAuthor")));

        verify(mockRepository, times(1)).save(any(Book.class));
    }
    
    //Test to see if a book can be successfully deleted
    @Test
    public void deleteBookTest() throws Exception {

        doNothing().when(mockRepository).deleteById(1);

        mockMvc.perform(delete("/api/book/1"))
			     .andExpect(status().isOk());
        //to verify if the book has been found to delete
        verify(mockRepository, times(1)).findById(1);
    }
    
    //Test to see if the correct status is returned when a book is not found
	@Test
    public void bookNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/book/3")).andExpect(status().isNotFound());
    }

}
