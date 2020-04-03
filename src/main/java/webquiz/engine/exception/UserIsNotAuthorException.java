package webquiz.engine.exception;

import org.springframework.security.core.AuthenticationException;

public class UserIsNotAuthorException extends AuthenticationException {

    public UserIsNotAuthorException(String msg) {
        super(msg);
    }
}