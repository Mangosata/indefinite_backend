package com.overwall.demo.core;

public class CoreResponseBody<T> {
    private T result;
    private Exception error;
    private String message;


    public CoreResponseBody(T result, String message, Exception error) {
        this.result = result;
        this.error = error;
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
