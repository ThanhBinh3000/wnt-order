package vn.com.gsoft.order.exception;

import java.io.IOException;

public class RestTemplateException extends IOException {
    public RestTemplateException(String message) {
        super(message);
    }
}
