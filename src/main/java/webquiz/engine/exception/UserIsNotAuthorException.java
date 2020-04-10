package webquiz.engine.exception;

public class UserIsNotAuthorException extends RuntimeException {

    public UserIsNotAuthorException(String msg) {
        super(msg);
    }
}