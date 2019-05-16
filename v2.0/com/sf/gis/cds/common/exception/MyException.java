package com.sf.gis.cds.common.exception;

public class MyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MyException(Throwable e) {
        super(e.getMessage(), e);
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String messageTemplate, Object... params) {
        super(String.format(messageTemplate, params));
    }

    public MyException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MyException(Throwable throwable, String messageTemplate, Object... params) {
        super(String.format(messageTemplate, params), throwable);
    }
}
