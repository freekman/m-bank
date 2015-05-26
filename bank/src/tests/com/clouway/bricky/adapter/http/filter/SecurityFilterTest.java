package com.clouway.bricky.adapter.http.filter;

import com.clouway.bricky.core.sesion.SessionManager;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class SecurityFilterTest {


  private SecurityFilter filter;


  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private SessionManager manager;
  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain chain;

  @Before
  public void setUp() throws Exception {
    manager = context.mock(SessionManager.class);
    request = context.mock(HttpServletRequest.class);
    response = context.mock(HttpServletResponse.class);
    chain = context.mock(FilterChain.class);

    filter = new SecurityFilter(manager);
  }

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(manager).isUserSessionExpired();
      will(returnValue(false));
      oneOf(manager).refreshUserSession();
      oneOf(request).getRequestURI();
      oneOf(chain).doFilter(request, response);
    }});

    filter.doFilter(request, response, chain);
  }

  @Test
  public void removeRedundantSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(manager).isUserSessionExpired();
      will(returnValue(true));
      oneOf(request).getRequestURI();
      will(returnValue("/welcome"));
      oneOf(manager).closeUserSession();
      oneOf(response).sendRedirect("/login");
    }});

    filter.doFilter(request, response, chain);
  }

  @Test
  public void denyLoginPage() throws Exception {
    context.checking(new Expectations() {{
      oneOf(manager).isUserSessionExpired();
      will(returnValue(false));
      oneOf(manager).refreshUserSession();
      oneOf(request).getRequestURI();
      will(returnValue("/login"));
      oneOf(response).sendRedirect("#/account");
    }});

    filter.doFilter(request, response, chain);
  }

}