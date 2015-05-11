package com.clouway.bricky.adapter.guice;

import com.clouway.bricky.core.UserValidator;
import com.clouway.bricky.core.Validator;
import com.clouway.bricky.core.user.UserDTO;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class CoreModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(new TypeLiteral<Validator<UserDTO>>() {
    }).toInstance(new UserValidator());
  }
}
