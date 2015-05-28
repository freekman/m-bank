package com.clouway.bricky.adapter.http;

import com.google.sitebricks.SitebricksModule;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class BrickModule extends SitebricksModule {

  @Override
  protected void configureSitebricks() {
    at("/r/register").serve(RegisterService.class);
    at("/r/logout").serve(LogoutService.class);
    at("/r/deposit").serve(DepositService.class);
    at("/login").show(Login.class);
  }
}
