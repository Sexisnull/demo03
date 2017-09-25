package com.gsww.uids.gateway.exception;

public class LoginException extends Exception {
	private static final long serialVersionUID = 1L;

	public LoginException(String arg0) {
		super(arg0);
	}

	public LoginException() {
	}

	public LoginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public LoginException(Throwable arg0) {
		super(arg0);
	}
}