package edu.training.news_portal.dao;

import java.util.List;

import edu.training.news_portal.bean.Comment;

public interface CommentDao {

	List<Comment> getCommentsByNewsId(int newsId) throws DaoException;

	boolean addComment(Comment comment) throws DaoException;

	boolean updateComment(Comment comment) throws DaoException;

	boolean deleteComment(int commentId) throws DaoException;

	Comment getCommentById(int commentId) throws DaoException;

}
