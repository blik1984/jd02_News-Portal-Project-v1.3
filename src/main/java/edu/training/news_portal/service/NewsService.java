package edu.training.news_portal.service;

import java.util.List;

import edu.training.news_portal.bean.News;

public interface NewsService {
	
	List<News> takeTopNews(int count) throws ServiceException;

	boolean addNews(News news) throws ServiceException;
	News getNews (int newsId) throws ServiceException;
	List<News> getNewsPage(int page, int pageSize) throws ServiceException;
	int countAllNews() throws ServiceException;
}
