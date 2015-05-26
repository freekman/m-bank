package com.clouway.bricky.core.user;

import com.clouway.bricky.adapter.http.service.MessagesDTO;
import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.Registry;
import com.clouway.bricky.core.db.user.UserRepository;
import com.clouway.bricky.core.validation.Validator;
import com.google.inject.Inject;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserRegistry implements Registry {
  private final UserRepository repository;
  private final Validator<MessagesDTO, User> validator;

  @Inject
  public UserRegistry(UserRepository repository, Validator<MessagesDTO, User> validator) {
    this.repository = repository;
    this.validator = validator;
  }

  @Override
  public void authorize(User user) throws AuthorizationException {
    MessagesDTO response = validator.validate(user, new UserRule());
    if (response.messages.isEmpty() && repository.isAuthentic(user)) {
      return;
    }
    throw new AuthorizationException();
  }

}
