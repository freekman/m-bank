package com.clouway.bricky.core.user;

import com.clouway.bricky.http.validation.Validator;
import com.clouway.bricky.core.Registry;
import com.clouway.bricky.core.UnauthorizedException;
import com.clouway.bricky.core.db.user.UserRepository;
import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserRegistry implements Registry {
  private final UserRepository repository;
  private final Validator<User> validator;

  @Inject
  public UserRegistry(UserRepository repository, Validator<User> validator) {
    this.repository = repository;
    this.validator = validator;
  }

  @Override
  public void authorize(User user) throws UnauthorizedException {
    Optional<String> error = validator.validate(user, new UserRule());
    if (!error.isPresent() && repository.isAuthentic(user)) {
      return;
    }
    throw new UnauthorizedException();
  }

}
