package com.innoveller.roomseller.ui.booking;

public class Result<T> {

    private T data;
    private Error error;

    public enum ErrorType {
        UNAUTHORIZED, BUSINESS_LOGIC, UNKNOWN
    }

    public Result(T data, Error error) {
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return data != null;
    }

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public static class Error {
        private int code;
        private ErrorType type;
        private String message;

        public int getCode() {
            return code;
        }

        public ErrorType getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }



    public static <TO> Result<TO> success(TO obj) {
        return new Result<TO>(obj, null);
    }

    public static <TO> Result<TO> error(ErrorType errorType, int code, String message) {
        Error error = new Error();
        error.code = 0;
        error.type = errorType;
        error.message = message;
        return new Result<TO>(null, error);
    }

    public static <TO> Result<TO> error(Error error) {
        return new Result<TO>(null, error);
    }
}
