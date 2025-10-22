package edu.training.news_portal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.training.news_portal.bean.News;
import edu.training.news_portal.bean.NewsGroups;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.dao.DaoException;
import edu.training.news_portal.dao.NewsDao;
import edu.training.news_portal.dao.pool.ConnectionPool;

public class DBNewsDao implements NewsDao {
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();

	@Override
	public List<News> topNews(int count) throws DaoException {
		List<News> topNews = new ArrayList<>();
		String sql = "SELECT id, news_group_id, title, brief, content_path, create_date, updated_date, publish_date, user_id "
				+ "FROM news " + "ORDER BY id ASC " + "LIMIT ?";

		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, count);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					News news = new News();
					news.setId(rs.getInt("id"));

					int groupId = rs.getInt("news_group_id");
					NewsGroups group = NewsGroups.fromId(groupId);
					news.setGroup(group);

					news.setTitle(rs.getString("title"));
					news.setBrief(rs.getString("brief"));
					news.setFileContentPath(rs.getString("content_path"));
					news.setCreateDateTime(rs.getObject("create_date", java.time.LocalDateTime.class));
					news.setUpdateDateTime(rs.getObject("updated_date", java.time.LocalDateTime.class));
					news.setPublishingDateTime(rs.getObject("publish_date", java.time.LocalDateTime.class));

					int userId = rs.getInt("user_id");
					if (!rs.wasNull()) {
						// Здесь поставь логику по установке пользователя в news.setPublisher(...)
						// Например, можно загружать пользователя по id или просто сохранять id
					}
					topNews.add(news);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Ошибка при получении топ новостей", e);
		}

		return topNews;
	}

	@Override
	public boolean addNews(News news) throws DaoException {
		String sql = "INSERT INTO news (news_group_id, title, brief, content_path, create_date, updated_date, publish_date, user_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, news.getGroup().getId());
			ps.setString(2, news.getTitle());
			ps.setString(3, news.getBrief());
			ps.setString(4, news.getFileContentPath());
			ps.setObject(5, news.getCreateDateTime());
			ps.setObject(6, news.getUpdateDateTime());
			ps.setObject(7, news.getPublishingDateTime());

			if (news.getPublisher() != null) {
				ps.setInt(8, news.getPublisher().getId());
			} else {
				ps.setNull(8, java.sql.Types.INTEGER);
			}
			int result = ps.executeUpdate();

			return result > 0;

		} catch (SQLException e) {
			throw new DaoException("Ошибка при добавлении новости", e);
		}
	}

	@Override
	public List<News> getNewsPaged(int limit, int offset) throws DaoException {
		String sql = "SELECT id, news_group_id, title, brief, content_path, create_date, updated_date, publish_date, user_id "
				+ "FROM news ORDER BY id DESC LIMIT ? OFFSET ?";
		List<News> newsList = new ArrayList<>();

		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, limit);
			ps.setInt(2, offset);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					News news = new News();
					news.setId(rs.getInt("id"));
					news.setTitle(rs.getString("title"));
					news.setBrief(rs.getString("brief"));
					news.setFileContentPath(rs.getString("content_path"));

					int groupId = rs.getInt("news_group_id");
					NewsGroups group = NewsGroups.fromId(groupId);
					news.setGroup(group);

					news.setCreateDateTime(rs.getObject("create_date", LocalDateTime.class));
					news.setUpdateDateTime(rs.getObject("updated_date", LocalDateTime.class));
					news.setPublishingDateTime(rs.getObject("publish_date", LocalDateTime.class));

					newsList.add(news);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Ошибка при получении новостей с пагинацией", e);
		}

		return newsList;
	}

	@Override
	public int countNews() throws DaoException {
		String sql = "SELECT COUNT(*) FROM news";
		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new DaoException("Ошибка при подсчёте количества новостей", e);
		}
	}

	@Override
	public News getNewsById(int newsId) throws DaoException {
		String sql = "SELECT n.id, n.title, n.brief, n.content_path, n.create_date, n.updated_date, n.publish_date, "
				+ "n.news_group_id, n.user_id, ng.value AS group_value, "
				+ "u.id AS user_id, u.email, u.password, u.roles_idroles, u.user_status_id, u.registration_date, u.updated_date AS user_updated_date "
				+ "FROM news n " + "JOIN news_group ng ON n.news_group_id = ng.id "
				+ "LEFT JOIN users u ON n.user_id = u.id " + "WHERE n.id = ?";

		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, newsId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					News news = new News();
					news.setId(rs.getInt("id"));
					news.setTitle(rs.getString("title"));
					news.setBrief(rs.getString("brief"));

					String groupValue = rs.getString("group_value");
					news.setGroup(NewsGroups.valueOf(groupValue.toUpperCase()));

					news.setCreateDateTime(rs.getObject("create_date", LocalDateTime.class));
					news.setUpdateDateTime(rs.getObject("updated_date", LocalDateTime.class));
					news.setPublishingDateTime(rs.getObject("publish_date", LocalDateTime.class));

					int userId = rs.getInt("user_id");
					if (!rs.wasNull()) {
						User publisher = new User();
						publisher.setId(userId);
						publisher.setEmail(rs.getString("email"));
						news.setPublisher(publisher);
					}

					String contentPath = rs.getString("content_path");
					news.setFileContentPath(contentPath);

					return news;
				} else {
					return null; // Новость с таким id не найдена
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Error fetching news by id", e);
		}
	}

}
