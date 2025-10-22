package edu.training.news_portal.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import edu.training.news_portal.bean.Role;

@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedRoles {

	Role[] value();

}
