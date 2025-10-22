package edu.training.news_portal.dao;

import edu.training.news_portal.dao.impl.DBCommentDao;
import edu.training.news_portal.dao.impl.DBNewsDao;
import edu.training.news_portal.dao.impl.DBUserDao;
import edu.training.news_portal.dao.impl.FileRepositoryImpl;

public class DaoProvider {

	private static final DaoProvider instance = new DaoProvider();

	private final NewsDao newsDao = new DBNewsDao();
	private final UserDao userDao = new DBUserDao();
	private final FileRepository fileRepository = new FileRepositoryImpl();
	private final CommentDao commentDao = new DBCommentDao();


	private DaoProvider() {
	}

	public static DaoProvider getInstance() {
		return instance;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	public CommentDao getCommentDao() {
		return commentDao;
	}
	public FileRepository getFileRepository() {
		return fileRepository;
	}
}
