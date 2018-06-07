package com.browser.tools.exception;

public class BrowserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String retCode;

	private String retMsg;

	public BrowserException() {
		super();
	}

	public BrowserException(String retMsg) {
		super();
		this.retMsg = retMsg;
	}

	public BrowserException(String retCode, String retMsg) {
		super();
		this.retCode = retCode;
		this.retMsg = retMsg;
	}

	public String getRetCode() {
		return retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}
}
