package com.clouway.bricky.persistence.user;

import com.clouway.bricky.core.user.User;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoUserRepository implements UserRepository {

  private final MongoCollection<Document> collection;

  @Inject
  public MongoUserRepository(MongoDatabase db) {
    collection = db.getCollection("accounts");
  }

  @Override
  public boolean register(User user) {
    if (isExisting(user.username)) {
      return false;
    }
    collection.insertOne(new Document("username", user.username).append("password", user.password));
    return true;
  }

  @Override
  public boolean isAuthentic(User user) {
    Document doc = collection.find(and(eq("username", user.username), eq("password", user.password))).first();
    return doc != null;
  }

  @Override
  public boolean isExisting(String username) {
    Document doc = collection.find(eq("username", username)).first();
    return doc != null;
  }
}
