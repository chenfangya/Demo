package com.cfy.exception;

public class ExceptionDemo extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059216186716786924L;

	public ExceptionDemo() {
		super();
	}

	public ExceptionDemo(String msg) {
		super(msg);
	}

	@Override
	public String getMessage() {
		return "Here is message";
	}
}