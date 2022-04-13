package ma.ensias.winchesters.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@JsonFormat
public record ClientError(
        HttpStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "GMT+1")
        Instant timestamp,
        ExceptionJsonFormatter exception
) {
}
