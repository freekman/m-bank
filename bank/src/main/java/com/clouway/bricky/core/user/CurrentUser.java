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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CurrentUser)) return false;

    CurrentUser that = (CurrentUser) o;

    if (Double.compare(that.balance, balance) != 0) return false;
    return !(name != null ? !name.equals(that.name) : that.name != null);

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = name != null ? name.hashCode() : 0;
    temp = Double.doubleToLongBits(balance);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
