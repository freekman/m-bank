package com.clouway.bricky.core.db.session;

import com.clouway.bricky.core.user.User;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface SessionRepository {
  void addSession(User user, String sid);

  boolean isSessionExpired(String sid);

  void refreshSession(String sid);

  void clearSession(String sid);
}
