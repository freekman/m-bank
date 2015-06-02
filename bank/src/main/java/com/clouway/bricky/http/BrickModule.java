package com.clouway.bricky.http;

import com.clouway.bricky.http.validation.FormValidator;
import com.clouway.bricky.http.validation.Validator;
import com.clouway.bricky.core.Registry;
import com.clouway.bricky.core.sesion.CurrentSession;
import com.clouway.bricky.core.sesion.SandClock;
import com.clouway.bricky.core.sesion.Session;
import com.clouway.bricky.core.sesion.SessionClock;
import com.clouway.bricky.core.sesion.SessionManager;
import com.clouway.bricky.core.sesion.UserSessionManager;
import com.clouway.bricky.core.user.User;
import com.clouway.bricky.core.user.UserRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.concurrent.TimeUnit;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class BrickModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Registry.class).to(UserRegistry.class);
    bind(Session.class).to(CurrentSession.class);
    bind(SessionManager.class).to(UserSessionManager.class);
  }

  @Provides
  Validator<UserDTO> provideUserDTOValidator() {
    return new FormValidator<UserDTO>();
  }

  @Provides
  Validator<User> provideUserValidator() {
    return new FormValidator<User>();
  }

  @Provides
  SessionClock provideClock() {
    return new SandClock(1, TimeUnit.MINUTES);
  }
}
