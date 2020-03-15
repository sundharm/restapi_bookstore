package bookstore.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "HttpStatus.Not_Found")
public class NotFoundException extends RuntimeException {
	
	//Generic Exception method
    public NotFoundException(String message) {
        super(message);
    }
}