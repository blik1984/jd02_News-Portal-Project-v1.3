package edu.training.news_portal.web;

import edu.training.news_portal.bean.Role;

public enum RequestPath {
	@AllowedRoles({ Role.GUEST })
	PAGE_AUTH,

	@AllowedRoles({ Role.GUEST, Role.USER, Role.ADMINISTRATOR })
	PAGE_MAIN,

	@AllowedRoles({ Role.GUEST })
	PAGE_REGISTRATION,

	@AllowedRoles({ Role.GUEST, Role.USER, Role.ADMINISTRATOR })
	PAGE_PRIVACY,

	@AllowedRoles({ Role.USER, Role.ADMINISTRATOR })
	PAGE_NEWS,

	@AllowedRoles({ Role.ADMINISTRATOR })
	PAGE_CREATE_NEWS,

	@AllowedRoles({ Role.USER, Role.ADMINISTRATOR })
	PAGE_USER_HOME,

	@AllowedRoles({ Role.GUEST })
	DO_AUTH,

	@AllowedRoles({ Role.GUEST })
	DO_REGISTRATION,

	@AllowedRoles({ Role.USER, Role.ADMINISTRATOR })
	LOG_OUT,

	@AllowedRoles({ Role.USER, Role.ADMINISTRATOR })
	ADD_COMMENT,
	
	@AllowedRoles({ Role.USER, Role.ADMINISTRATOR })
	UPDATE_COMMENT,

	@AllowedRoles({ Role.ADMINISTRATOR })
	ADD_NEWS,

	@AllowedRoles({ Role.GUEST, Role.USER, Role.ADMINISTRATOR })
	NOCOMMAND
	
}
