package edu.training.news_portal.service;

import edu.training.news_portal.service.impl.CommentServiceImpl;
import edu.training.news_portal.service.impl.NewsPortalUserSecurity;
import edu.training.news_portal.service.impl.NewsServiceImpl;

public class ServiceProvider {
	private static final ServiceProvider instance = new ServiceProvider();
	private final UserSecurity security = new NewsPortalUserSecurity();
	private final NewsService newsService = new NewsServiceImpl();
	private final CommentService commentService = new CommentServiceImpl();


	private ServiceProvider() {
	}

	public UserSecurity getUserSecurity() {
		return security;
	}

	public UserSecurity getSecurity() {
		return security;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public static ServiceProvider getInstance() {
		return instance;
	}
	
	public CommentService getCommentService() {
		return commentService;
	}

}
