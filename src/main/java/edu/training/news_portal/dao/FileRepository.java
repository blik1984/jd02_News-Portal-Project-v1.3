package edu.training.news_portal.dao;

public interface FileRepository {
	
	String saveNews (String news)throws DaoException;
	String getNews (String path) throws DaoException;

}
