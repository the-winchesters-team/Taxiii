package ma.ensias.winchesters.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value={ResourceNotFoundException.class})
    public ResponseEntity<Object> handleApiResourceNotFoundException(ResourceNotFoundException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException,status);
    }
    @ExceptionHandler(value={InvalidUsernameException.class,InvalidEmailException.class})
    public ResponseEntity<Object> handleApiInvalidUsernameOrInvalidEmailExceptions(RuntimeException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException,status);
    }
    @ExceptionHandler(value={JwtTokenNotValidException.class})
    public ResponseEntity<Object> handleApiInvalidTokenException(JwtTokenNotValidException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "invalid token",
                status,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException,status);
    }
}
