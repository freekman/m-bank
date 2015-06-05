package com.clouway.bricky.core;

import com.clouway.bricky.core.user.User;

/**
 * Convenience class that can be used to perform user credential authentication.
 *
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface Registry {

  /**
   * Perform authentication upon user credentials. If authentication fails {@link UnauthenticatedException} will be thrown.
   *
   * @param user who's credentials will be authenticated.
   * @throws UnauthenticatedException If authentication fails exception will be thrown.
   */
  void authenticate(User user) throws UnauthenticatedException;

}
