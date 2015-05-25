package com.clouway.bricky.core;

import com.clouway.bricky.adapter.http.service.FormResponse;
import com.clouway.bricky.core.Validation.ValidationRule;
import com.clouway.bricky.core.Validation.Validator;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserRegistry;
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
  private Validator<FormResponse, User> validator;

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

  @NotNull
  private User testUser() {
    return new User("Marian", "pswd");
  }

  private FormResponse validFormResponse() {
    return new FormResponse(true, Lists.<String>newArrayList());
  }

  private FormResponse invalidFormResponse() {
    return new FormResponse(false, Lists.<String>newArrayList("Random error msg"));
  }


}