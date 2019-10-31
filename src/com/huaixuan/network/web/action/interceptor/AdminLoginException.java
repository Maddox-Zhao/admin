package com.huaixuan.network.web.action.interceptor;

public class AdminLoginException extends RuntimeException {

    private static final long serialVersionUID = 6389275810400718450L;

    public AdminLoginException() {
        super();
    }

    public AdminLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminLoginException(String message) {
        super(message);
    }

    public AdminLoginException(Throwable cause) {
        super(cause);
    }
}
