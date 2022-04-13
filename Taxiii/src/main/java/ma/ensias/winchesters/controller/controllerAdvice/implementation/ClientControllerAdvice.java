package ma.ensias.winchesters.controller.controllerAdvice.implementation;


import ma.ensias.winchesters.controller.ClientController;
import ma.ensias.winchesters.controller.controllerAdvice.interfaces.ClientControllerAdviceInterface;
import ma.ensias.winchesters.dto.error.ClientError;
import ma.ensias.winchesters.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = ClientController.class)
public class ClientControllerAdvice implements ClientControllerAdviceInterface {
    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ClientError> handleException(
            ApiException exception
    ) {
        return handleUserException(HttpStatus.BAD_REQUEST.value(), exception);
    }
}
