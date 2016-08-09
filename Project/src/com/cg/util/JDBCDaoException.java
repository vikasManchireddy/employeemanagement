package com.cg.util;


@SuppressWarnings("serial")
public class JDBCDaoException extends RuntimeException {

    public JDBCDaoException() {
        super();
    }

    public JDBCDaoException(String message) {
        super(message);
    }

    public JDBCDaoException(Throwable cause) {
        super(cause);
    }

    public JDBCDaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
