package com.clouway.bricky.http;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
class UserDTO {

  @NotNull
  @Size(min = 3, max = 30)
  final String username;

  @NotNull
  @Size(min = 3, max = 20)
  final String password;

  @NotNull
  @Size(min = 3, max = 20)
  final String repassword;

  public UserDTO(String repassword, String password, String username) {
    this.repassword = repassword;
    this.password = password;
    this.username = username;
  }

  @AssertTrue(message = "The password fields must match")
  private boolean isValid() {
    return this.password.equals(this.repassword);
  }
}
