package ma.ensias.winchesters.controller.controllerAdvice.interfaces;

import ma.ensias.winchesters.dto.error.ClientError;
import ma.ensias.winchesters.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static ma.ensias.winchesters.dto.error.ExceptionJsonFormatter.convertExceptionToJson;

public interface ClientControllerAdviceInterface extends ControllerAdvice{
    default <E extends ApiException> ResponseEntity<ClientError> handleUserException(
            int statusCode, E exception) {

        LOG.debug("error response {}", exception.getMessage());
        ClientError err = new ClientError(
                HttpStatus.resolve(statusCode),
                Instant.now(),
                convertExceptionToJson(exception)
        );
        return ResponseEntity
                .status(statusCode)
                .body(err);
    }
}
