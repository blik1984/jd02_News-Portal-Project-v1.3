package edu.training.news_portal.web;

import java.util.HashMap;
import java.util.Map;

import edu.training.news_portal.web.impl.AddComment;
import edu.training.news_portal.web.impl.AddNews;
import edu.training.news_portal.web.impl.DoAuth;
import edu.training.news_portal.web.impl.DoRegistration;
import edu.training.news_portal.web.impl.LogOut;
import edu.training.news_portal.web.impl.NoCommand;
import edu.training.news_portal.web.impl.PageAuth;
import edu.training.news_portal.web.impl.PageCreateNews;
import edu.training.news_portal.web.impl.PageMain;
import edu.training.news_portal.web.impl.PageNews;
import edu.training.news_portal.web.impl.PagePrivacy;
import edu.training.news_portal.web.impl.PageRegistration;
import edu.training.news_portal.web.impl.PageUserHome;
import edu.training.news_portal.web.impl.UpdateComment;

public class CommandProvider {
	private final Map<RequestPath, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(RequestPath.PAGE_AUTH, new PageAuth());
		commands.put(RequestPath.PAGE_MAIN, new PageMain());
		commands.put(RequestPath.PAGE_REGISTRATION, new PageRegistration());
		commands.put(RequestPath.PAGE_PRIVACY, new PagePrivacy());
		commands.put(RequestPath.PAGE_NEWS, new PageNews());
		commands.put(RequestPath.PAGE_CREATE_NEWS, new PageCreateNews());
		commands.put(RequestPath.DO_AUTH, new DoAuth());
		commands.put(RequestPath.DO_REGISTRATION, new DoRegistration());
		commands.put(RequestPath.PAGE_USER_HOME, new PageUserHome());
		commands.put(RequestPath.LOG_OUT, new LogOut());
		commands.put(RequestPath.ADD_COMMENT, new AddComment());
		commands.put(RequestPath.UPDATE_COMMENT, new UpdateComment());
		commands.put(RequestPath.ADD_NEWS, new AddNews());
		commands.put(RequestPath.NOCOMMAND, new NoCommand());

	}

	public Command take(String path) {
		System.out.println(path);
		try {
			return commands.get(RequestPath.valueOf(path.toUpperCase()));

		} catch (IllegalArgumentException | NullPointerException e) {
			return null;
		}
	}
}
