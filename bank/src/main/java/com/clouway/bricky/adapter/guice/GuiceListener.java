package com.clouway.bricky.adapter.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class GuiceListener extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new FilterModule(), new BrickModule(), new CoreModule(), new PersistentDbModule());
  }
}
