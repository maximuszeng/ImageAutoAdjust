package com.suncorp.image.autoadjust.exception;

public class AutoAdjustException extends RuntimeException {
	
	private static final long serialVersionUID = -8234158499775669343L;

	public AutoAdjustException() {
		super();
	}

	public AutoAdjustException(String message) {
		super(message);
	}

	public AutoAdjustException(String message, Throwable cause) {
		super(message, cause);
	}

	public AutoAdjustException(Throwable cause) {
		super(cause);
	}
	
}
