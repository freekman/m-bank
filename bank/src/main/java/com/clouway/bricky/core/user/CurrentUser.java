package com.clouway.bricky.core.user;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class CurrentUser {

  public final String name;
  public final double balance;

  public CurrentUser(String name, double balance) {
    this.name = name;
    this.balance = balance;
  }
}
