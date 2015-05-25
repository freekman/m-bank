package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.Registry;
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
  private Registry manager;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    manager = context.mock(Registry.class);
    login = new Login(manager);
  }

  @Test
  public void userAuthorizationAndLogin() throws Exception {
    context.checking(new Expectations() {{
      oneOf(manager).authorize(with(any(User.class)));
    }});

    Reply<?> reply = login.login();
    assertThat(reply, isEqualToReply(Reply.saying().redirect("/home")));
    assertThat(login.messages, is(empty()));
  }

  @Test
  public void userAuthorizationFail() throws Exception {
    context.checking(new Expectations() {{
      oneOf(manager).authorize(with(any(User.class)));
      will(throwException(new AuthorizationException()));
    }});

    Reply<?> reply = this.login.login();
    assertThat(reply, is(equalTo(null)));
    assertThat(login.messages, contains("Wrong username or password"));
  }

}