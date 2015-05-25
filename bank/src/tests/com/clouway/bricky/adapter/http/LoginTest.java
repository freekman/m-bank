package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.Registry;
import com.clouway.bricky.core.sesion.SessionManager;
import com.clouway.bricky.core.user.User;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.clouway.bricky.adapter.http.service.IsEqualToReply.isEqualToReply;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class LoginTest {

  private Login login;
  private Registry registry;
  private SessionManager manager;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    registry = context.mock(Registry.class);
    manager = context.mock(SessionManager.class);
    login = new Login(registry, manager);
  }

  @Test
  public void happyPath() throws Exception {
    context.checking(new Expectations() {{
      oneOf(registry).authorize(with(any(User.class)));
      oneOf(manager).openSessionFor(with(any(User.class)));
    }});

    Reply<?> reply = login.login();
    assertThat(reply, isEqualToReply(Reply.saying().redirect("#")));
    assertThat(login.messages, is(empty()));
  }

  @Test
  public void userAuthorizationFail() throws Exception {
    context.checking(new Expectations() {{
      oneOf(registry).authorize(with(any(User.class)));
      will(throwException(new AuthorizationException()));
    }});

    Reply<?> reply = this.login.login();
    assertThat(reply, is(equalTo(null)));
    assertThat(login.messages, contains("Wrong username or password"));
  }


}