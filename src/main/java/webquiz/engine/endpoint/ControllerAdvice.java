package webquiz.engine.endpoint;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class QuizRestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(Exception e) {
        return getBadRequestResponse(e);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoSuchElementException(Exception e) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", e.getMessage());
        return response;
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleJsonParseException(Exception e) {
        return getBadRequestResponse(e);
    }

    private Map<String, String> getBadRequestResponse(Exception e) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("message", e.getMessage());
        return response;
    }
}
