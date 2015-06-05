package com.clouway.bricky.http;

import com.clouway.bricky.core.Registry;
import com.clouway.bricky.core.UnauthenticatedException;
import com.clouway.bricky.core.sesion.SessionManager;
import com.clouway.bricky.core.user.User;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.http.Post;

import java.util.List;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
@Show("partials/login.html")
@At("/login")
public class Login {

  public String username;
  public String password;
  public final List<String> messages = Lists.newArrayList();

  private final Registry registry;
  private final SessionManager manager;

  @Inject
  public Login(Registry registry, SessionManager manager) {
    this.registry = registry;
    this.manager = manager;
  }

  @Post
  public Reply<?> login() {
    try {
      User user = new User(username, password);
      registry.authenticate(user);
      manager.openSession(user);
    } catch (UnauthenticatedException e) {
      messages.add("Wrong username or password");
      return null;
    }

    return Reply.saying().redirect("#/account");
  }

}
