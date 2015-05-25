package com.clouway.bricky.core.user;

import com.clouway.bricky.core.Validation.ValidationRule;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserDTORule implements ValidationRule<UserDTO> {

  private String nameRule = "[A-z0-9]{3,30}";
  private String passwordRule = "[^\\^!@#$%^&*()_=]{3,20}";

  @Override
  public List<String> apply(UserDTO user) {

    if (user.password == null || user.username == null || user.repassword == null) {
      return Lists.newArrayList("Please enter all fields");
    }

    List<String> errorList = Lists.newArrayList();

    if (!user.username.matches(nameRule)) {
      errorList.add("Please enter valid username.");
    }
    if (!user.password.equals(user.repassword)) {
      errorList.add("Please enter passwords that match.");
    }
    if (!user.password.matches(passwordRule) || !user.repassword.matches(passwordRule)) {
      errorList.add("Please enter valid password.");
    }

    return errorList;
  }
}
