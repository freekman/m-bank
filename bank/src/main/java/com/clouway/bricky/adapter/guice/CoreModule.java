package com.clouway.bricky.adapter.guice;

import com.clouway.bricky.adapter.http.service.FormResponse;
import com.clouway.bricky.core.Registry;
import com.clouway.bricky.core.sesion.SandClock;
import com.clouway.bricky.core.sesion.SessionClock;
import com.clouway.bricky.core.sesion.SessionManager;
import com.clouway.bricky.core.sesion.SidManager;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserDTO;
import com.clouway.bricky.core.user.UserRegistry;
import com.clouway.bricky.core.validation.FormValidator;
import com.clouway.bricky.core.validation.Validator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.concurrent.TimeUnit;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class CoreModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Registry.class).to(UserRegistry.class);
    bind(SessionManager.class).to(SidManager.class);
  }

  @Provides
  Validator<FormResponse, UserDTO> provideUserDTOValidator() {
    return new FormValidator<UserDTO>();
  }

  @Provides
  Validator<FormResponse, User> provideUserValidator() {
    return new FormValidator<User>();
  }

  @Provides
  SessionClock provideClock() {
    return new SandClock(1, TimeUnit.MINUTES);
  }

}
