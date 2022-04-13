package ma.ensias.winchesters.controller.controllerAdvice.implementation;


import ma.ensias.winchesters.controller.ClientController;
import ma.ensias.winchesters.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = ClientController.class)
public class ClientControllerAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<UserError> handleException(
            ApiException exception
    ) {
        return handleUserException(HttpStatus.BAD_REQUEST.value(), new UserException(exception));
    }
}
