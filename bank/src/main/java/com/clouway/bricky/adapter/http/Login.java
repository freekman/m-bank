package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.db.user.UserRepository;
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

  private final UserRepository repository;
  public List<String> messages = Lists.newArrayList();

  public String username;
  public String password;

  @Inject
  public Login(UserRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> login() {

    return Reply.saying().redirect("#");
  }

}
