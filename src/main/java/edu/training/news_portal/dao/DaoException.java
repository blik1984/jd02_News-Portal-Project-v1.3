package edu.training.news_portal.dao;

public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DaoException() {
		super();
	}
	public DaoException(String message) {
		super(message);
	}
	public DaoException(Exception ex) {
		super(ex);
	}
	public DaoException(String message, Exception ex) {
		super(message, ex);
	}


}
