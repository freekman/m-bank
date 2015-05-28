package com.clouway.bricky.core;

import com.clouway.bricky.core.db.balance.BalanceRepository;
import com.clouway.bricky.core.db.balance.MongoBalanceRepository;
import com.clouway.bricky.core.db.session.MongoSessionRepository;
import com.clouway.bricky.core.db.session.SessionRepository;
import com.clouway.bricky.core.db.user.MongoUserRepository;
import com.clouway.bricky.core.db.user.UserRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class PersistentDbModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(UserRepository.class).to(MongoUserRepository.class);

    bind(SessionRepository.class).to(MongoSessionRepository.class);

    bind(BalanceRepository.class).to(MongoBalanceRepository.class);
  }

  @Singleton
  @Provides
  MongoClient mongoClientProvider() {
    return new MongoClient("localhost", 27017);
  }

  @Provides
  MongoDatabase provideBankDb(MongoClient mongoClient) {
    return mongoClient.getDatabase("bank");
  }

}
