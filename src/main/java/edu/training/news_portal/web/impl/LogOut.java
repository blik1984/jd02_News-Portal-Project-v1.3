package edu.training.news_portal.web.impl;

import java.io.IOException;

import edu.training.news_portal.bean.User;
import edu.training.news_portal.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("Controller?command=page_main");
			return;
		}
		User user = (User) session.getAttribute("auth");
		if (user == null) {
			session.removeAttribute("auth");
			response.sendRedirect("Controller?command=page_main");
			return;
		}
		session.removeAttribute("auth");
		response.sendRedirect("Controller?command=page_main");
	}
}
