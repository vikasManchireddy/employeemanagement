package com.cg.util;

//Follow TODOs (if available)
/**
 * 
 * This is a ServiceLocatorException class
 * @see java.lang.Object
 * @author Vikas & Prasoona
 * 
 *
 */
 
 //TODO 1 Implement it as a Unchecked Exception
@SuppressWarnings("serial")
public class ServiceLocatorException extends RuntimeException{

	public ServiceLocatorException() {
        super();
    }

    public ServiceLocatorException(String message) {
        super(message);
    }

    public ServiceLocatorException(Throwable cause) {
        super(cause);
    }

    public ServiceLocatorException(String message, Throwable cause) {
        super(message, cause);
    }

}
