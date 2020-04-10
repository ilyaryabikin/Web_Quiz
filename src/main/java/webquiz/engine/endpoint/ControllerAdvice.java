package webquiz.engine.endpoint;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import webquiz.engine.exception.UserAlreadyExistsException;
import webquiz.engine.exception.UserIsNotAuthorException;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(Exception e) {
        return getErrorDetails(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoSuchElementException(Exception e) {
        return getErrorDetails(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleJsonParseException(Exception e) {
        return getErrorDetails(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserAlreadyExistsException(Exception e) {
        return getErrorDetails(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsNotAuthorException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleUserIsNotAuthorException(Exception e) {
        return getErrorDetails(e, HttpStatus.FORBIDDEN);
    }

    private Map<String, String> getErrorDetails(Exception e, HttpStatus status) {
        return Map.of(
                "timestamp", Instant.now().toString(),
                "status", String.valueOf(status.value()),
                "error", status.getReasonPhrase(),
                "message", e.getMessage()
        );
    }
}
