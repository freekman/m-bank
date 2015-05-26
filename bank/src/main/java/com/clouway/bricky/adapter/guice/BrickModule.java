package com.clouway.bricky.adapter.guice;

import com.clouway.bricky.adapter.http.Login;
import com.clouway.bricky.adapter.http.service.RegisterService;
import com.google.sitebricks.SitebricksModule;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class BrickModule extends SitebricksModule {

  @Override
  protected void configureSitebricks() {
    at("/register/new").serve(RegisterService.class);
    at("/login").show(Login.class);


  }
}
