package com.clouway.bricky.http.filter;

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

  private final SessionManager manager;

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

    if (manager.isUserSessionExpired()) {
      manager.closeUserSession();
      if (isRequestingStaticResource(requestURI)) {
        filterChain.doFilter(req, resp);
        return;
      }
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    manager.refreshUserSession();
    if (isRequestingStaticPages(requestURI)) {
      resp.sendRedirect("/#/account");
      return;
    }

    filterChain.doFilter(req, resp);
  }

  @Override
  public void destroy() {

  }

  private boolean isRequestingStaticResource(String requestURI) {
    return isRequestingStaticPages(requestURI) || requestURI.contains(".css") || requestURI.contains(".js");
  }

  private boolean isRequestingStaticPages(String requestURI) {
    return requestURI.contains("/login") || requestURI.contains("/register");
  }
}
