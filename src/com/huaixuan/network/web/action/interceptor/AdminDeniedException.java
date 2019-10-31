package com.huaixuan.network.web.action.interceptor;

public class AdminDeniedException extends RuntimeException {
    private static final long serialVersionUID = 6389275810400718450L;

    public AdminDeniedException() {
        super();
    }

    public AdminDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminDeniedException(String message) {
        super(message);
    }

    public AdminDeniedException(Throwable cause) {
        super(cause);
    }

}
