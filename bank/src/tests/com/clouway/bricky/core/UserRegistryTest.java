package com.clouway.bricky.core;

import com.clouway.bricky.adapter.http.validation.ValidationRule;
import com.clouway.bricky.adapter.http.validation.Validator;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserRegistry;
import com.google.common.base.Optional;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserRegistryTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private UserRegistry userRegistry;
  private UserRepository repository;
  private Validator<User> validator;

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
      will(returnValue(validResponse()));

      oneOf(repository).isAuthentic(with(any(User.class)));
      will(returnValue(true));
    }});

    userRegistry.authorize(testUser());
  }

  @Test(expected = UnauthorizedException.class)
  public void failNonValidUserAuthorization() throws Exception {
    context.checking(new Expectations() {{
      oneOf(validator).validate(with(any(User.class)), with(any(ValidationRule.class)));
      will(returnValue(invalidResponse()));

      never(repository).isAuthentic(with(any(User.class)));
    }});

    userRegistry.authorize(testUser());
  }

  @Test(expected = UnauthorizedException.class)
  public void failNonAuthenticUserAuthorization() throws Exception {
    context.checking(new Expectations() {{
      oneOf(validator).validate(with(any(User.class)), with(any(ValidationRule.class)));
      will(returnValue(validResponse()));

      oneOf(repository).isAuthentic(with(any(User.class)));
      will(returnValue(false));
    }});
    userRegistry.authorize(testUser());
  }

  private User testUser() {
    return new User("Marian", "pswd");
  }

  private Optional validResponse() {
    return Optional.absent();
  }

  private Optional invalidResponse() {
    return Optional.of("Random error msg");
  }


}