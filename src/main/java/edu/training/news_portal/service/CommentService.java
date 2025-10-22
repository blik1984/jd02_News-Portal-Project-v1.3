package edu.training.news_portal.service;

import java.util.List;

import edu.training.news_portal.bean.Comment;
import edu.training.news_portal.bean.User;

public interface CommentService {
	List<Comment> getCommentsByNews(int newsId, User currentUser) throws ServiceException;

	boolean addComment(Comment comment) throws ServiceException;

	boolean updateComment(Comment comment, User user) throws ServiceException;

	boolean deleteComment(int commentId, User user) throws ServiceException;
	
	Comment getCommentById (int id) throws ServiceException;
}
