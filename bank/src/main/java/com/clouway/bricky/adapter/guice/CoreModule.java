package com.clouway.bricky.adapter.guice;

import com.clouway.bricky.core.Registry;
import com.clouway.bricky.core.user.UserRegistry;
import com.google.inject.AbstractModule;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class CoreModule extends AbstractModule {
  @Override
  protected void configure() {

    bind(Registry.class).to(UserRegistry.class);
  }
}
