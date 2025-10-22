package edu.training.news_portal.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import edu.training.news_portal.bean.Comment;
import edu.training.news_portal.bean.Role;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.dao.CommentDao;
import edu.training.news_portal.dao.DaoException;
import edu.training.news_portal.dao.DaoProvider;
import edu.training.news_portal.service.CommentService;
import edu.training.news_portal.service.ServiceException;

public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao = DaoProvider.getInstance().getCommentDao();

	@Override
	public List<Comment> getCommentsByNews(int newsId, User currentUser) throws ServiceException {
		try {
			List<Comment> comments = commentDao.getCommentsByNewsId(newsId);
			LocalDateTime now = LocalDateTime.now();

			for (Comment comment : comments) {
				boolean editable = false;
				if (currentUser != null && comment.getUserId() == currentUser.getId()) {
					Duration duration = Duration.between(comment.getCreatedAt(), now);
					if (duration.toMinutes() <= 30) {
						editable = true;
					}
				}
				comment.setEditable(editable);
			}
			return comments;
		} catch (DaoException e) {
			throw new ServiceException("Error retrieving comments", e);
		}
	}

	@Override
	public boolean addComment(Comment comment) throws ServiceException {
		try {
			return commentDao.addComment(comment);
		} catch (DaoException e) {
			throw new ServiceException("Error adding comment", e);
		}
	}

	@Override
	public boolean updateComment(Comment comment, User user) throws ServiceException {
		try {
			Comment existing = commentDao.getCommentById(comment.getId());
			if (existing == null)
				return false;

			boolean isOwner = existing.getUserId() == user.getId();
			boolean isAdmin = user.getRole() == Role.ADMINISTRATOR;

			if (!isOwner && !isAdmin)
				return false;

			boolean withinTime = existing.getCreatedAt().plusMinutes(30).isAfter(LocalDateTime.now());

			if (withinTime || isAdmin) {
				return commentDao.updateComment(comment);
			}

			return false;

		} catch (DaoException e) {
			throw new ServiceException("Error updating comment", e);
		}
	}

	@Override
	public boolean deleteComment(int commentId, User user) throws ServiceException {
		try {
			if (user.getRole() != Role.ADMINISTRATOR)
				return false;
			return commentDao.deleteComment(commentId);
		} catch (DaoException e) {
			throw new ServiceException("Error deleting comment", e);
		}
	}

	@Override
	public Comment getCommentById(int id) throws ServiceException {
		try {
		return commentDao.getCommentById(id);
		}catch(DaoException e) {
			throw new ServiceException(e);
		}
	}
}
