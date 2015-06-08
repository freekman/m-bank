package com.clouway.bricky.http;

import com.google.sitebricks.SitebricksModule;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class HttpModule extends SitebricksModule {

  @Override
  protected void configureSitebricks() {
    at("/r/register").serve(RegisterService.class);
    at("/r/logout").serve(LogoutService.class);
    at("/r/balance").serve(BalanceService.class);
    at("/login").show(Login.class);
    at("/register").show(Register.class);
  }
}
