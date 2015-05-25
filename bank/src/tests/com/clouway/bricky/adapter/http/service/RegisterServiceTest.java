package com.clouway.bricky.adapter.http.service;

import com.clouway.bricky.core.Validation.Validator;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.User;
import com.google.common.collect.Lists;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
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
public class RegisterServiceTest {

  private RegisterService service;
  private UserRepository repository;
  private Request request;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

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
    assertThat(reply, isEqualToReply(Reply.with(new FormResponse(false, Lists.newArrayList("Username exists")))));
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
    assertThat(reply, isEqualToReply(Reply.with(new FormResponse(true, Lists.newArrayList("Username is free")))));
  }

}