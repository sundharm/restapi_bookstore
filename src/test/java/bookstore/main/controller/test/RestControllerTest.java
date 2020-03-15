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
	
	private static final ObjectMapper om = new ObjectMapper();

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
    
	@Test
    public void getBookByIDTest() throws Exception {

        mockMvc.perform(get("/api/book/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName", is("TestBookName")))
                .andExpect(jsonPath("$.authorName", is("TestAuthorName")));

        verify(mockRepository, times(1)).findById(1);

    }
	
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
	
    @Test
    public void addBookTest() throws Exception {

        Book newBook = new Book(1,"Java Programming", "Javaman");
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);
        mockMvc.perform(post("/api/book")
                .content(om.writeValueAsString(newBook))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());       
        verify(mockRepository, times(1)).save(any(Book.class));

    }
    
    @Test
    public void addCategoryTest() throws Exception {
    	Book newBook = new Book(1,"Java Programming", "Javaman");
        when(mockRepository.save(any(Book.class))).thenReturn(newBook);
        
        Category category = new Category(1, "cat1");
        when(mockRepository1.save(any(Category.class))).thenReturn(category);
       
        mockMvc.perform(post("/api/book")
                .content(om.writeValueAsString(newBook)));
        mockMvc.perform(post("/api/book/1/category")
                .content(om.writeValueAsString(category))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.categoryName", is("cat1")));  	
        verify(mockRepository1, times(1)).save(any(Category.class));
    }
    
	@Test
    public void bookNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/book/3")).andExpect(status().isNotFound());
    }

}
