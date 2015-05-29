package com.clouway.bricky.adapter.http.filter;

import com.clouway.bricky.core.sesion.SessionManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
@Singleton
public class SecurityFilter implements Filter {

  private SessionManager manager;

  @Inject
  public SecurityFilter(SessionManager manager) {
    this.manager = manager;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;

    String requestURI = req.getRequestURI();
    boolean sessionExpired = manager.isUserSessionExpired();

    if (sessionExpired) {
      manager.closeUserSession();
      if (requestURI.contains("/login") || requestURI.contains("/register") || requestURI.contains(".css") || requestURI.contains(".js")) {
        filterChain.doFilter(req, resp);
        return;
      }
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    manager.refreshUserSession();
    if ("/login".equalsIgnoreCase(requestURI)) {
      resp.sendRedirect("/#/account");
      return;
    }

    filterChain.doFilter(req, resp);
  }

  @Override
  public void destroy() {

  }
}
