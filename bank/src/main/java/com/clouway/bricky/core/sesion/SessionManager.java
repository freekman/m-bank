package com.clouway.bricky.core.sesion;

import com.clouway.bricky.core.user.User;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface SessionManager {

  /**
   * Attach session id for specified user.
   *
   * @param user that will have a session opened for.
   */
  void openSessionFor(User user);

  /**
   * Performs a check if there is a opened session for the current user and if has expired.
   *
   * @return true if there is a valid session false otherwise.
   */
  boolean isCurrentUserSessionExpired();

  /**
   * Updates expiration time of the current user's id. Or opens a new one if not opened.
   */
  void refreshCurrentUserSession();
}
