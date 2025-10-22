package edu.training.news_portal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import edu.training.news_portal.bean.RegistrationInfo;
import edu.training.news_portal.bean.Role;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.dao.DaoException;
import edu.training.news_portal.dao.UserDao;
import edu.training.news_portal.dao.pool.ConnectionPool;

public class DBUserDao implements UserDao {

	private final ConnectionPool pool = ConnectionPool.getInstance();

	@Override

	public User checkCredentials(String email, String password) throws DaoException {
		String sql = "SELECT u.id, ud.name, r.name AS role " + "FROM users u "
				+ "JOIN user_details ud ON u.id = ud.users_id " + "JOIN roles r ON u.roles_idroles = r.idroles "
				+ "WHERE u.email = ? AND u.password = ?";

		try (Connection con = pool.takeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			ps.setString(2, password);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					int userId = rs.getInt("id");
					String name = rs.getString("name");
					Role role = Role.valueOf(rs.getString("role").toUpperCase());
					return new User(userId, name, email, role);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Error checking credentials", e);
		}
	}

	@Override
	public boolean registration(RegistrationInfo info) throws DaoException {

		String insertUserSql = "INSERT INTO users (email, password, roles_idroles, user_status_id, registration_date) VALUES (?, ?, ?, ?, ?)";
		String insertUserDetailsSql = "INSERT INTO user_details (users_id, name) VALUES (?, ?)";
		try (Connection con = pool.takeConnection()) {
			con.setAutoCommit(false);

			try (PreparedStatement psUser = con.prepareStatement(insertUserSql,
					java.sql.Statement.RETURN_GENERATED_KEYS)) {

				psUser.setString(1, info.getEmail());
				psUser.setString(2, info.getPassword());
				psUser.setInt(3, 2);
				psUser.setInt(4, 1);
				LocalDateTime now = LocalDateTime.now();
				psUser.setTimestamp(5, Timestamp.valueOf(now));

				int affectedRows = psUser.executeUpdate();

				if (affectedRows == 0) {
					con.rollback();
					throw new DaoException("Creating user failed, no rows affected.");
				}

				try (java.sql.ResultSet generatedKeys = psUser.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						long userId = generatedKeys.getLong(1);

						try (PreparedStatement psDetails = con.prepareStatement(insertUserDetailsSql)) {
							psDetails.setLong(1, userId);
							psDetails.setString(2, info.getName());
							psDetails.executeUpdate();
						}
					} else {
						con.rollback();
						throw new DaoException("Creating user failed, no ID obtained.");
					}
				}
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public boolean emailExists(String email) throws DaoException {
		String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
		try (Connection con = pool.takeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
				return false;
			}
		} catch (SQLException e) {
			throw new DaoException("Error checking email existence", e);
		}
	}
}
