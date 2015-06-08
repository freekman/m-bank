package com.clouway.bricky.persistence;

import com.clouway.bricky.PropertyReader;
import com.clouway.bricky.persistence.balance.BalanceRepository;
import com.clouway.bricky.persistence.balance.MongoBalanceRepository;
import com.clouway.bricky.persistence.session.MongoSessionRepository;
import com.clouway.bricky.persistence.session.SessionRepository;
import com.clouway.bricky.persistence.user.MongoUserRepository;
import com.clouway.bricky.persistence.user.UserRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class PersistentDbModule extends AbstractModule {
  private PropertyReader properties;

  public PersistentDbModule(PropertyReader reader) {
    this.properties = reader;
  }

  @Override
  protected void configure() {
    bind(UserRepository.class).to(MongoUserRepository.class).in(Singleton.class);

    bind(SessionRepository.class).to(MongoSessionRepository.class).in(Singleton.class);

    bind(BalanceRepository.class).to(MongoBalanceRepository.class).in(Singleton.class);
  }

  @Singleton
  @Provides
  MongoClient mongoClientProvider() {
    return new MongoClient(properties.getDbHost(), properties.getDbPort());
  }

  @Provides
  MongoDatabase provideBankDb(MongoClient mongoClient) {
    return mongoClient.getDatabase(properties.getDbName());
  }

}
