package com.clouway.bricky.core.user;

import com.clouway.bricky.core.validation.ValidationRule;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserRule implements ValidationRule<User> {

  private String nameRule = "[A-z0-9]{3,30}";
  private String passwordRule = "[^\\^!@#$%^&*()_=]{3,20}";

  @Override
  public List<String> apply(User user) {
    if (user.username == null || "".equals(user.username)) {
      return Lists.newArrayList("Please enter username");
    }
    if (user.password == null || "".equals(user.password)) {
      return Lists.newArrayList("Please enter password");
    }
    if (!user.username.matches(nameRule)) {
      return Lists.newArrayList("Please enter valid username");
    }
    if (!user.password.matches(passwordRule)) {
      return Lists.newArrayList("Please enter valid password");
    }
    return Lists.newArrayList();
  }

}
