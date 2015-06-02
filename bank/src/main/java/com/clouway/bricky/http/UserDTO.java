package com.clouway.bricky.http;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
class UserDTO {

  public String username;
  public String password;
  public String repassword;

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRepassword(String repassword) {
    this.repassword = repassword;
  }

  @Override
  public String toString() {
    return "UserDTO{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", repassword='" + repassword + '\'' +
            '}';
  }
}
