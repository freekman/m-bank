package com.clouway.bricky;

import com.clouway.bricky.adapter.http.BrickModule;
import com.clouway.bricky.adapter.http.HttpModule;
import com.clouway.bricky.adapter.http.HttpServletModule;
import com.clouway.bricky.core.PersistentDbModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class GuiceListener extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new HttpServletModule(), new HttpModule(), new BrickModule(), new PersistentDbModule());
  }
}
