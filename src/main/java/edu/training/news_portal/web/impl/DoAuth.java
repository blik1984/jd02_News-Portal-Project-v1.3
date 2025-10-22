package edu.training.news_portal.web.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import edu.training.news_portal.bean.User;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.service.UserSecurity;
import edu.training.news_portal.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoAuth implements Command {

	private final UserSecurity security = ServiceProvider.getInstance().getUserSecurity();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("email");
		String password = request.getParameter("password");
		User user;

		try {

			if ((user = security.singIn(login, password)) != null) {

				HttpSession session = (HttpSession) request.getSession(true);
				session.setAttribute("auth", user);
				response.sendRedirect("Controller?command=PAGE_USER_HOME");
			} else {
				String errorMessage = "Неправильный логин или пароль!";
				String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
				response.sendRedirect("Controller?command=page_auth&message=" + encodedMessage);
			}
		} catch (ServiceException e) {
			response.sendRedirect("error.jsp");
		}
	}
}
