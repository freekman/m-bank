package com.clouway.bricky.adapter.http;

import com.clouway.bricky.adapter.http.filter.SecurityFilter;
import com.google.inject.servlet.ServletModule;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class HttpServletModule extends ServletModule {

  @Override
  protected void configureServlets() {
    filter("*").through(SecurityFilter.class);
  }
}
