package com.clouway.bricky.core.sesion;

import com.clouway.bricky.core.db.session.SessionRepository;
import com.clouway.bricky.core.user.User;
import com.google.common.base.Optional;
import org.jetbrains.annotations.NotNull;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserSessionManagerTest {

  private UserSessionManager manager;
  private Session session;
  //  private HttpServletResponse response;
//  private HttpServletRequest request;
  private SessionRepository repository;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    repository = context.mock(SessionRepository.class);
    session = context.mock(Session.class);
//    response = context.mock(HttpServletResponse.class);
//    request = context.mock(HttpServletRequest.class);
    manager = new UserSessionManager(session, repository);
  }

  @Test
  public void attachSidCookie() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).attach();
      oneOf(repository).addSession(with(any(User.class)), with(any(String.class)));
    }});

    manager.openSessionFor(dummyUser());
  }

  @Test
  public void lookupExistingSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(dummySession()));
//      oneOf(request).getCookies();
//      will(returnValue(dummySession()));
      oneOf(repository).isSessionExpired(with(any(String.class)));
      will(returnValue(false));
    }});

    assertFalse(manager.isUserSessionExpired());
  }

  @Test
  public void lookupNonExistingSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(Optional.absent()));
//      oneOf(request).getCookies();
//      will(returnValue(new Cookie[]{}));
    }});

    assertTrue(manager.isUserSessionExpired());
  }

  @Test
  public void refreshCurrentUserSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(Optional.of("123")));
//      oneOf(request).getCookies();
//      will(returnValue(dummySession()));
      oneOf(repository).refreshSession("123");
    }});

    manager.refreshUserSession();
  }

  @Test
  public void removeRedundantSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(dummySession()));
      oneOf(session).detach();
//      oneOf(request).getCookies();
//      will(returnValue(dummySession()));
//      oneOf(response).addCookie(with(any(Cookie.class)));
      oneOf(repository).clearSession(with(any(String.class)));
    }});

    manager.closeUserSession();
  }

  @NotNull
  private User dummyUser() {
    return new User("Marian", "pswd");
  }

  @NotNull
  private Optional<String> dummySession() {
    return Optional.of("session_id");
  }

}