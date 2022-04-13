package ma.ensias.winchesters.dto.error;


import ma.ensias.winchesters.exceptions.ApiException;

public record ExceptionJsonFormatter(String code, String message) {
    public static ExceptionJsonFormatter convertExceptionToJson(ApiException exception) {
        return new ExceptionJsonFormatter(exception.getStatus().toString(), exception.getMessage());
    }
}
