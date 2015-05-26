package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.Registry;
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

  private Registry registry;
  private SessionManager manager;

  public String username;
  public String password;
  public List<String> messages = Lists.newArrayList();

  @Inject
  public Login(Registry registry, SessionManager manager) {
    this.registry = registry;
    this.manager = manager;
  }

  @Post
  public Reply<?> login() {
    try {
      User user = new User(username, password);
      registry.authorize(user);
      manager.openSessionFor(user);
    } catch (AuthorizationException e) {
      messages.add("Wrong username or password");
      return null;
    }
    return Reply.saying().redirect("#/account");
  }

}
