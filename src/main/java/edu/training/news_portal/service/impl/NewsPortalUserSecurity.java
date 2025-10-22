package edu.training.news_portal.service.impl;

import edu.training.news_portal.bean.RegistrationInfo;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.dao.DaoException;
import edu.training.news_portal.dao.DaoProvider;
import edu.training.news_portal.dao.UserDao;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.service.UserSecurity;

public class NewsPortalUserSecurity implements UserSecurity {

	private final UserDao userDao = DaoProvider.getInstance().getUserDao();

	@Override
	public User singIn(String login, String password) throws ServiceException {

		try {
			return userDao.checkCredentials(login, password);

		} catch (DaoException e) {
			throw new ServiceException("Failed to authenticate user", e);
		}
	}

	@Override
	public boolean registration(RegistrationInfo info) throws ServiceException {

		// написать валидацию данных

		try {
			return userDao.registration(info);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean emailExists(String email) throws ServiceException {
		try {
			return userDao.emailExists(email);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
