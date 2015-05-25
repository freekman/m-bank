package com.clouway.bricky.adapter.http.filter;

import com.clouway.bricky.core.sesion.SessionManager;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

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
      oneOf(manager).isCurrentUserSessionExpired();
      will(returnValue(false));
      oneOf(manager).refreshCurrentUserSession();
      oneOf(chain).doFilter(request, response);
    }});

    filter.doFilter(request, response, chain);

  }
}