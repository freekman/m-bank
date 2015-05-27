package com.clouway.bricky.adapter.http;

import com.clouway.bricky.adapter.http.validation.ValidationRule;
import com.google.common.base.Optional;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserDTORule implements ValidationRule<UserDTO> {

  private String nameRule = "[A-z0-9]{3,30}";
  private String passwordRule = "[^\\^!@#$%^&*()_=]{3,20}";

  @Override
  public Optional<String> apply(UserDTO user) {
    if (user.password == null || user.username == null || user.repassword == null) {
      return Optional.of("Please enter all fields");
    }
    if (!user.username.matches(nameRule)) {
      return Optional.of("Please enter valid username.");
    }
    if (!user.password.equals(user.repassword)) {
      return Optional.of("Please enter passwords that match.");
    }
    if (!user.password.matches(passwordRule) || !user.repassword.matches(passwordRule)) {
      return Optional.of("Please enter valid password.");
    }
    return Optional.absent();
  }
}
