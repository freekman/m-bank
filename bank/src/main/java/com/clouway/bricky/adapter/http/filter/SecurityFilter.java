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
import java.io.IOException;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
@Singleton
public class SecurityFilter implements Filter {

  @Inject
  public SecurityFilter(SessionManager manager) {

  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

  }

  @Override
  public void destroy() {

  }
}
