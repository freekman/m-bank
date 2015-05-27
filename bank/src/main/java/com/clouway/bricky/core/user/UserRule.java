package com.clouway.bricky.core.user;

import com.clouway.bricky.adapter.http.validation.ValidationRule;
import com.google.common.base.Optional;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserRule implements ValidationRule<User> {

  private String nameRule = "[A-z0-9]{3,30}";
  private String passwordRule = "[^\\^!@#$%^&*()_=]{3,20}";

  @Override
  public Optional<String> apply(User user) {
    if (user.username == null || "".equals(user.username)) {
      return Optional.of("Please enter username");
    }
    if (user.password == null || "".equals(user.password)) {
      return Optional.of("Please enter password");
    }
    if (!user.username.matches(nameRule)) {
      return Optional.of("Please enter valid username");
    }
    if (!user.password.matches(passwordRule)) {
      return Optional.of("Please enter valid password");
    }
    return Optional.absent();
  }

}
