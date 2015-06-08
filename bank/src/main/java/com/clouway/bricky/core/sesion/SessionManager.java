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
  void openSession(User user);

  /**
   * Performs a check if there is a opened session for the current user and if has expired.
   *
   * @return true if there is a valid session false otherwise.
   */
  boolean isUserSessionExpired();

  /**
   * Updates expiration time of the current user's session. Or opens a new one if not opened.
   */
  void refreshUserSession();

  /**
   * Remove current user session references from persistence layer and resources.
   */
  void closeUserSession();
}
