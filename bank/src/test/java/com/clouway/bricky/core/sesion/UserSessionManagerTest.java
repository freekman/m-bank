package com.clouway.bricky.core.sesion;

import com.clouway.bricky.persistence.session.SessionRepository;
import com.clouway.bricky.core.user.User;
import com.google.common.base.Optional;
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

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private UserSessionManager manager;
  private Session session;
  private SessionRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = context.mock(SessionRepository.class);
    session = context.mock(Session.class);
    manager = new UserSessionManager(session, repository);
  }

  @Test
  public void attachSidCookie() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).attach();
      oneOf(repository).addSession(with(any(User.class)), with(any(String.class)));
    }});

    manager.openSession(dummyUser());
  }

  @Test
  public void lookupExistingSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(dummySession()));
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
    }});

    assertTrue(manager.isUserSessionExpired());
  }

  @Test
  public void refreshCurrentUserSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(Optional.of("123")));
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
      oneOf(repository).clearSession(with(any(String.class)));
    }});

    manager.closeUserSession();
  }

  private User dummyUser() {
    return new User("Marian", "pswd");
  }

  private Optional<String> dummySession() {
    return Optional.of("session_id");
  }

}