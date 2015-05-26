package com.clouway.bricky.core.sesion;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class CurrentSession {

  private Encrypt encrypt;
  private Provider<HttpServletRequest> requestProvider;
  private Provider<HttpServletResponse> responseProvider;

  @Inject
  public CurrentSession(Encrypt encrypt, Provider<HttpServletRequest> requestProvider, Provider<HttpServletResponse> responseProvider) {
    this.encrypt = encrypt;
    this.requestProvider = requestProvider;
    this.responseProvider = responseProvider;
  }

  public String attach() {
    HttpServletResponse response = responseProvider.get();
    String sid = encrypt.sha1(Double.toString(Math.random()));
    response.addCookie(new Cookie("sid", sid));
    return sid;
  }

  public void detach() {
    Cookie removalCookie = new Cookie("sid", "");
    removalCookie.setMaxAge(0);
    responseProvider.get().addCookie(removalCookie);
  }

  public Optional<String> getSid() {
    HttpServletRequest request = requestProvider.get();
    for (Cookie each : request.getCookies()) {
      if ("sid".equals(each.getName())) {
        return Optional.of(each.getValue());
      }
    }
    return Optional.absent();
  }


}
