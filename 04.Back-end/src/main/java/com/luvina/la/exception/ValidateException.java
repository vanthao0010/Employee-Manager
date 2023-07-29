package com.luvina.la.exception;

import java.util.Map;

public class ValidateException extends RuntimeException{
    Map<String, Object> response;

    public Map<String, Object> getResponse() {
        return response;
    }

    public ValidateException(Map<String, Object> response) {
        this.response = response;
    }
}
