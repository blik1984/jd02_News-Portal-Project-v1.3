package edu.training.news_portal.web.filters;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import edu.training.news_portal.bean.Role;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.web.AllowedRoles;
import edu.training.news_portal.web.RequestPath;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class SecurityFilter implements Filter {

	private static final Map<Role, Set<RequestPath>> roleAccessMap = new EnumMap<>(Role.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);

		String commandParam = httpRequest.getParameter("command");

		if (commandParam != null) {
			try {
				RequestPath command = RequestPath.valueOf(commandParam.toUpperCase());

				Role userRole = Role.GUEST; // по умолчанию

				if (session != null) {
					User user = (User) session.getAttribute("auth");

					if (user != null && user.getRole() != null) {
						userRole = user.getRole();
					}
				}

				if (!isCommandAllowedForRole(command, userRole)) {
					request.setAttribute("errorMessage", "Доступ запрещён. Недостаточно прав.");
					request.getRequestDispatcher("Controller?command=page_auth").forward(request, response);
					return;
				}

			} catch (IllegalArgumentException e) {
				request.setAttribute("errorMessage", "Неизвестная команда.");
				request.getRequestDispatcher("Controller?command=page_main").forward(request, response);
				return;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) {
		for (Role role : Role.values()) {
			roleAccessMap.put(role, EnumSet.noneOf(RequestPath.class));
		}

		for (RequestPath path : RequestPath.values()) {
			AllowedRoles annotation = getAllowedRoles(path);
			if (annotation != null) {
				for (Role role : annotation.value()) {
					roleAccessMap.get(role).add(path);
				}
			}
		}
	}

	private boolean isCommandAllowedForRole(RequestPath command, Role role) {
		AllowedRoles annotation = getAllowedRoles(command);
		if (annotation == null) {
			return false;
		}

		return Arrays.asList(annotation.value()).contains(role);
	}

	private AllowedRoles getAllowedRoles(RequestPath path) {

		try {
			Field field = RequestPath.class.getField(path.name());
			return field.getAnnotation(AllowedRoles.class);

		} catch (NoSuchFieldException e) {
			return null;
		}
	}

}
