package com.clouway.bricky.core.sesion;

import com.clouway.bricky.core.db.session.SessionRepository;
import com.clouway.bricky.core.user.User;
import com.google.common.base.Optional;
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
  public boolean isUserSessionExpired() {
    Optional<String> sid = getSid();
    return !sid.isPresent() || repository.isSessionExpired(sid.get());
  }

  @Override
  public void refreshUserSession() {
    Optional<String> sid = getSid();
    if (sid.isPresent()) {
      repository.refreshSession(sid.get());
    }
  }

  private Optional<String> getSid() {
    HttpServletRequest request = requestProvider.get();
    for (Cookie each : request.getCookies()) {
      if ("sid".equals(each.getName())) {
        return Optional.of(each.getValue());
      }
    }
    return Optional.absent();
  }

  @Override
  public void closeUserSession() {
    Optional<String> sid = getSid();
    if (sid.isPresent()) {
      repository.clearSession(sid.get());
      Cookie removalCookie = new Cookie("sid", "");
      removalCookie.setMaxAge(0);
      responseProvider.get().addCookie(removalCookie);
    }
  }
}
