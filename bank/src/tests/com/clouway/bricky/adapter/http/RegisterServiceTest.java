package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.db.user.UserRepository;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.clouway.bricky.adapter.http.IsEqualToReply.isEqualToReply;
import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class RegisterServiceTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private RegisterService service;
  private UserRepository repository;
  private Request request;

  @Before
  public void setUp() throws Exception {
    repository = context.mock(UserRepository.class);
    service = new RegisterService(repository, null);
    request = context.mock(Request.class);
  }

  @Test
  public void lookupRegisteredUser() throws Exception {
    final String username = "Marian";
    context.checking(new Expectations() {{
      oneOf(request).param("username");
      will(returnValue(username));

      oneOf(repository).isExisting(username);
      will(returnValue(true));
    }});

    Reply<?> reply = service.lookupUser(request);
    assertThat(reply, isEqualToReply(Reply.with("Username exists").status(403)));
  }

  @Test
  public void lookupMissingUser() throws Exception {
    final String username = "Marian";

    context.checking(new Expectations() {{
      oneOf(request).param("username");
      will(returnValue(username));

      oneOf(repository).isExisting(username);
      will(returnValue(false));
    }});

    Reply<?> reply = service.lookupUser(request);
    assertThat(reply, isEqualToReply(Reply.with("Username is free").status(202)));
  }

}