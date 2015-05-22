package com.clouway.bricky.core.db.user;

import com.clouway.bricky.core.user.UserDTO;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoUserRepository implements UserRepository {

  private MongoCollection<Document> collection;

  @Inject
  public MongoUserRepository(MongoDatabase db) {
    collection = db.getCollection("bank");
  }

  @Override
  public boolean register(UserDTO user) {
    if (isExisting(user.username)) {
      return false;
    }
    collection.insertOne(new Document("username", user.username).append("password", user.password));
    return true;
  }

  @Override
  public boolean isExisting(String username) {
    Document doc = collection.find(eq("username", username)).first();
    return doc != null;
  }

}
