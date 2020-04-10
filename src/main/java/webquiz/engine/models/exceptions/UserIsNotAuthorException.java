package webquiz.engine.models.exceptions;

public class UserIsNotAuthorException extends RuntimeException {

    public UserIsNotAuthorException(String msg) {
        super(msg);
    }
}