package com.cfy.exception;

public class DemoException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059216186716786924L;

	public DemoException() {
		super();
	}

	public DemoException(String msg) {
		super(msg);
	}

	@Override
	public String getMessage() {
		return "Here is message";
	}
}