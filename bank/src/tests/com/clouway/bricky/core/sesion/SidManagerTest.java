package com.clouway.bricky.core.sesion;

import com.clouway.bricky.core.db.session.SessionRepository;
import com.clouway.bricky.core.user.User;
import org.jetbrains.annotations.NotNull;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class SidManagerTest {

  private SidManager manager;
  private HttpServletResponse response;
  private HttpServletRequest request;
  private SessionRepository repository;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    repository = context.mock(SessionRepository.class);
    response = context.mock(HttpServletResponse.class);
    request = context.mock(HttpServletRequest.class);
    manager = new SidManager(new FakeSession(request,response), repository);
  }

  @Test
  public void attachSidCookie() throws Exception {
    context.checking(new Expectations() {{
      oneOf(response).addCookie(with(any(Cookie.class)));
      oneOf(repository).addSession(with(any(User.class)), with(any(String.class)));
    }});

    manager.openSessionFor(dummyUser());
  }

  @Test
  public void lookupExistingSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).getCookies();
      will(returnValue(dummySession()));
      oneOf(repository).isSessionExpired(with(any(String.class)));
      will(returnValue(false));
    }});

    assertFalse(manager.isUserSessionExpired());
  }

  @Test
  public void lookupNonExistingSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).getCookies();
      will(returnValue(new Cookie[]{}));
    }});

    assertTrue(manager.isUserSessionExpired());
  }

  @Test
  public void refreshCurrentUserSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).getCookies();
      will(returnValue(dummySession()));
      oneOf(repository).refreshSession(with(any(String.class)));
    }});

    manager.refreshUserSession();
  }

  @Test
  public void removeRedundantSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).getCookies();
      will(returnValue(dummySession()));
      oneOf(response).addCookie(with(any(Cookie.class)));
      oneOf(repository).clearSession(with(any(String.class)));
    }});

    manager.closeUserSession();
  }

  @NotNull
  private User dummyUser() {
    return new User("Marian", "pswd");
  }

  @NotNull
  private Cookie[] dummySession() {
    return new Cookie[]{new Cookie("sid", "session_id")};
  }

}