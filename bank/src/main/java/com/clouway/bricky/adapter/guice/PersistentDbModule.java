package com.clouway.bricky.adapter.guice;

import com.clouway.bricky.core.db.user.MongoUserRepository;
import com.clouway.bricky.core.db.user.UserRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class PersistentDbModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(MongoClient.class).toInstance(new MongoClient("localhost", 27017));

    bind(UserRepository.class).to(MongoUserRepository.class);
  }

  @Provides
  MongoDatabase provideBankDb(MongoClient mongoClient) {
    return mongoClient.getDatabase("bank");
  }

}
