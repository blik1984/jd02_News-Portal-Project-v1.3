package edu.training.news_portal.service;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(Exception ex) {
		super(ex);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Exception ex) {
		super(message, ex);
	}

}
