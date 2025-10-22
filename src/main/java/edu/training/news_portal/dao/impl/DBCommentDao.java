package edu.training.news_portal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.training.news_portal.bean.Comment;
import edu.training.news_portal.dao.CommentDao;
import edu.training.news_portal.dao.DaoException;
import edu.training.news_portal.dao.pool.ConnectionPool;

public class DBCommentDao implements CommentDao {

	private final ConnectionPool pool = ConnectionPool.getInstance();

	@Override
	public List<Comment> getCommentsByNewsId(int newsId) throws DaoException {
		List<Comment> comments = new ArrayList<>();
		String sql = "SELECT c.id, c.content, c.created_date, c.updated_date, c.users_id, c.commentaries_status_id, "
				+ "ud.name AS user_name, cs.value AS status " + "FROM Commentaries c "
				+ "JOIN user_details ud ON c.users_id = ud.users_id "
				+ "JOIN commentaries_status cs ON c.commentaries_status_id = cs.id " + "WHERE c.news_id = ? "
				+ "ORDER BY COALESCE(c.updated_date, c.created_date) DESC";

		try (Connection con = pool.takeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, newsId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Comment comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setNewsId(newsId);
					comment.setUserId(rs.getInt("users_id"));
					comment.setUserName(rs.getString("user_name"));
					comment.setText(rs.getString("content"));
					comment.setCreatedAt(rs.getTimestamp("created_date").toLocalDateTime());
					Timestamp updated = rs.getTimestamp("updated_date");
					if (updated != null) {
						comment.setUpdatedAt(updated.toLocalDateTime());
					}
					comment.setStatusId(rs.getInt("commentaries_status_id"));
					comment.setStatus(rs.getString("status"));
					comments.add(comment);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to load comments", e);
		}
		return comments;
	}

	@Override
	public boolean addComment(Comment comment) throws DaoException {
		String sql = "INSERT INTO Commentaries (content, created_date, users_id, news_id, commentaries_status_id) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection con = pool.takeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, comment.getText());
			ps.setTimestamp(2, Timestamp.valueOf(comment.getCreatedAt()));
			ps.setInt(3, comment.getUserId());
			ps.setInt(4, comment.getNewsId());
			ps.setInt(5, comment.getStatusId());
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Failed to add comment", e);
		}
	}

	@Override
	public boolean updateComment(Comment comment) throws DaoException {
		String sql = "UPDATE Commentaries SET content = ?, updated_date = ? WHERE id = ?";
		try (Connection con = pool.takeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, comment.getText());
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(3, comment.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DaoException("Failed to update comment", e);
		}
	}

	@Override
	public boolean deleteComment(int commentId) throws DaoException {
		String sql = "DELETE FROM Commentaries WHERE id = ?";
		try (Connection con = pool.takeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, commentId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DaoException("Failed to delete comment", e);
		}
	}

	@Override
	public Comment getCommentById(int commentId) throws DaoException {
		String sql = "SELECT * FROM Commentaries WHERE id = ?";
		try (Connection con = pool.takeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, commentId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Comment comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setText(rs.getString("content"));
					comment.setUserId(rs.getInt("users_id"));
					comment.setNewsId(rs.getInt("news_id"));
					comment.setStatusId(rs.getInt("commentaries_status_id"));
					comment.setCreatedAt(rs.getTimestamp("created_date").toLocalDateTime());
					Timestamp updated = rs.getTimestamp("updated_date");
					if (updated != null) {
						comment.setUpdatedAt(updated.toLocalDateTime());
					}
					return comment;
				}
				return null;
			}
		} catch (SQLException e) {
			throw new DaoException("Failed to get comment", e);
		}
	}
}
