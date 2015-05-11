package com.clouway.bricky.adapter.guice;

import com.clouway.bricky.core.db.user.PersistentUserRepository;
import com.clouway.bricky.core.db.user.UserRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class PersistentModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(UserRepository.class).to(PersistentUserRepository.class);
  }

  @RequestScoped
  @Provides
  Connection connectionProvider() {
    try {
      return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "root", "password");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }


}
