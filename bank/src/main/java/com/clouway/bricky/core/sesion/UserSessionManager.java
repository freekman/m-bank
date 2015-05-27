package com.clouway.bricky.core.sesion;

import com.clouway.bricky.core.db.session.SessionRepository;
import com.clouway.bricky.core.user.User;
import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserSessionManager implements SessionManager {

  private SessionRepository repository;
  private Session session;

  @Inject
  public UserSessionManager(Session session, SessionRepository repository) {
    this.session = session;
    this.repository = repository;
  }

  @Override
  public void openSessionFor(User user) {
    repository.addSession(user, session.attach());
  }

  @Override
  public boolean isUserSessionExpired() {
    Optional<String> sid = session.getSid();
    return !sid.isPresent() || repository.isSessionExpired(sid.get());
  }

  @Override
  public void refreshUserSession() {
    Optional<String> sid = session.getSid();
    if (sid.isPresent()) {
      repository.refreshSession(sid.get());
    }
  }

  @Override
  public void closeUserSession() {
    Optional<String> sid = session.getSid();
    if (sid.isPresent()) {
      repository.clearSession(sid.get());
      session.detach();
    }
  }
}
