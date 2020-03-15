package bookstore.main.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
	
	private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository mockRepository;
    
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

}
