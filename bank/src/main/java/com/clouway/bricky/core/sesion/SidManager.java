package com.clouway.bricky.core.sesion;

import com.clouway.bricky.core.db.session.SessionRepository;
import com.clouway.bricky.core.user.User;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class SidManager implements SessionManager {

  private Encrypt encrypt;
  private Provider<HttpServletResponse> responseProvider;
  private Provider<HttpServletRequest> requestProvider;
  private SessionRepository repository;

  //todo reduce dependencies
  @Inject
  public SidManager(Encrypt encrypt, Provider<HttpServletRequest> requestProvider, Provider<HttpServletResponse> responseProvider, SessionRepository repository) {
    this.encrypt = encrypt;
    this.responseProvider = responseProvider;
    this.requestProvider = requestProvider;
    this.repository = repository;
  }

  @Override
  public void openSessionFor(User user) {
    HttpServletResponse response = responseProvider.get();
    String sid = encrypt.sha1(Double.toString(Math.random()));
    response.addCookie(new Cookie("sid", sid));
    repository.addSession(user, sid);
  }

  @Override
  public boolean isCurrentUserSessionExpired() {
    HttpServletRequest request = requestProvider.get();
    for (Cookie each : request.getCookies()) {
      if ("sid".equals(each.getName())) {
        return repository.isSessionExpired(each.getValue());
      }
    }
    return true;
  }

  @Override
  public void refreshCurrentUserSession() {

  }
}
