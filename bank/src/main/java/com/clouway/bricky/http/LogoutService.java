package com.clouway.bricky.http;

import com.clouway.bricky.core.sesion.SessionManager;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
@At("/r/logout")
@Service
public class LogoutService {

  private final SessionManager manager;

  @Inject
  public LogoutService(SessionManager manager) {
    this.manager = manager;
  }

  @Get
  @Post
  public Reply<?> logout() {
    manager.closeUserSession();
    return Reply.saying().redirect("/login");
  }
}