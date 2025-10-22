package edu.training.news_portal.web.listners;

import java.sql.SQLException;

import edu.training.news_portal.dao.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolListner implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {

		try {
			ConnectionPool.getFirstInstance("jdbc:mysql://127.0.0.1/nowa_news_2?useSSL=false", "root", "49801218", 5);
		} catch (SQLException | ClassNotFoundException e) {
			// получить респонс и отправить ответ на страницу ошибок
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		try {
			ConnectionPool.getInstance().close();
		} catch (SQLException e) {

			// добавлять логгер фатал эррор
		}
	}
}
