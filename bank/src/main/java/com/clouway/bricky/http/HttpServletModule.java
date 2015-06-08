package com.clouway.bricky.http;

import com.clouway.bricky.http.filter.SecurityFilter;
import com.clouway.bricky.http.filter.ServiceInterceptor;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.headless.Service;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class HttpServletModule extends ServletModule {

  @Override
  protected void configureServlets() {
    filter("/*").through(SecurityFilter.class);
    bindInterceptor(Matchers.annotatedWith(Service.class), Matchers.any(), new ServiceInterceptor());
  }

}
