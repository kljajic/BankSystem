package com.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.model.user.Permission;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = (Method) handlerMethod.getMethod();
		if (method.isAnnotationPresent(Permission.class)) {
			String permission = method.getAnnotation(Permission.class).permissionName();
			//
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
			//[ROLE_ANONYMOUS]
			if (SecurityContextHolder.getContext().getAuthentication().getCredentials() != null) {
				System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
				for (GrantedAuthority sga : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
					if (sga.getAuthority().equals(permission)) {
						return true;
					}
				}
				response.sendError(401, "Unauthorized request");
				return true;
			}
			response.sendError(401, "Request with no logon");
			return true;
		}
		return true;
	}

}
