package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.UserManager;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserDTO;
import com.google.sitebricks.headless.Reply;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.clouway.bricky.adapter.http.service.IsEqualToReply.isEqualToReply;
import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class LoginTest {

  private Login login;
  private UserRepository repository;
  private UserManager manager;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    repository = context.mock(UserRepository.class);
    manager = context.mock(UserManager.class);
    login = new Login(repository);
  }

  @Test
  public void authenticateAndLoginUser() throws Exception {

    context.checking(new Expectations() {{
      oneOf(manager).authorize(with(any(User.class)));
    }});

    Reply<?> reply = this.login.login();
    assertThat(reply, isEqualToReply(Reply.saying().redirect("#")));

  }
}