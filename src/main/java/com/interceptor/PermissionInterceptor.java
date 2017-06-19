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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = (Method) handlerMethod.getMethod();
		if (method.isAnnotationPresent(Permission.class)) {
			String permission = method.getAnnotation(Permission.class).permissionName();
			if (!SecurityContextHolder.getContext().getAuthentication().getCredentials().equals("")) {
				for (GrantedAuthority sga : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
					if (sga.getAuthority().equals(permission)) {
						log.error("ACCES GRANTED FOR USER: [{}], METHOD TYPE: [{}] ON PATH: [{}].",SecurityContextHolder.getContext().getAuthentication().getName(), request.getMethod(), request.getRequestURI());
						return true;
					}
				}
				log.error("ACCES DENIED FOR USER: [{}], METHOD TYPE: [{}] ON PATH: [{}], REASON: [UNAUTHORIZED REQUES].",SecurityContextHolder.getContext().getAuthentication().getName(), request.getMethod(), request.getRequestURI());
				response.sendError(401, "Unauthorized request");
				return true;
			}
			log.error("ACCES DENIED: [USER NOT LOGGED IN], METHOD TYPE [{}] ON PATH: [{}].", request.getMethod(), request.getRequestURI());
			response.sendError(403, "Request with no logon");
			return true;
		}
		return true;
	}

}
