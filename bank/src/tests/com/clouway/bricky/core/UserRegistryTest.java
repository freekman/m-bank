package com.clouway.bricky.core;

import com.clouway.bricky.adapter.http.service.MessagesDTO;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserRegistry;
import com.clouway.bricky.core.validation.ValidationRule;
import com.clouway.bricky.core.validation.Validator;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserRegistryTest {

  private UserRegistry userRegistry;
  private UserRepository repository;
  private Validator<MessagesDTO, User> validator;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    repository = context.mock(UserRepository.class);
    validator = context.mock(Validator.class);
    userRegistry = new UserRegistry(repository, validator);
  }

  @Test
  public void authorizeValidUser() throws Exception {
    context.checking(new Expectations() {{
      oneOf(validator).validate(with(any(User.class)), with(any(ValidationRule.class)));
      will(returnValue(validFormResponse()));

      oneOf(repository).isAuthentic(with(any(User.class)));
      will(returnValue(true));
    }});

    userRegistry.authorize(testUser());
  }

  @Test(expected = AuthorizationException.class)
  public void failNonValidUserAuthorization() throws Exception {
    context.checking(new Expectations() {{
      oneOf(validator).validate(with(any(User.class)), with(any(ValidationRule.class)));
      will(returnValue(invalidFormResponse()));

      never(repository).isAuthentic(with(any(User.class)));
    }});

    userRegistry.authorize(testUser());
  }

  @Test(expected = AuthorizationException.class)
  public void failNonAuthenticUserAuthorization() throws Exception {
    context.checking(new Expectations() {{
      oneOf(validator).validate(with(any(User.class)), with(any(ValidationRule.class)));
      will(returnValue(validFormResponse()));

      oneOf(repository).isAuthentic(with(any(User.class)));
      will(returnValue(false));
    }});
    userRegistry.authorize(testUser());
  }

  //todo
//  @Test
//  public void openUserSession() throws Exception {
//    final User user = testUser();
//    context.checking(new Expectations() {{
//      oneOf(sidManager).openSessionFor(user);
//      will(returnValue("123"));
//      oneOf(repository).addSession(user, "123");
//    }});
//
//    userRegistry.attachSession(user);
//    assertFalse(userRegistry.isCurrentUserExpired());
//  }

  @NotNull
  private User testUser() {
    return new User("Marian", "pswd");
  }

  private MessagesDTO validFormResponse() {
    return new MessagesDTO(Lists.<String>newArrayList());
  }

  private MessagesDTO invalidFormResponse() {
    return new MessagesDTO("Random error msg");
  }


}