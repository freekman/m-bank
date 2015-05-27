package com.clouway.bricky.core.db.session;

import com.clouway.bricky.core.sesion.SessionClock;
import com.clouway.bricky.core.user.User;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoSessionRepository implements SessionRepository {

  private final MongoCollection<Document> collection;
  private final SessionClock clock;

  @Inject
  public MongoSessionRepository(MongoDatabase database, SessionClock clock) {
    this.clock = clock;
    collection = database.getCollection("accounts");
  }

  @Override
  public void addSession(User user, String sid) {
    collection.updateOne(eq("username", user.username),
            new Document("$set",
                    new Document("session",
                            new Document("sid", sid)
                                    .append("expiration", clock.newExpirationTime().getTime()))));
  }

  @Override
  public boolean isSessionExpired(String sid) {
    Document user = collection.find(eq("session.sid", sid)).projection(new Document("session.expiration", 1).append("_id", 0)).first();
    if (user == null) {
      return true;
    }
    long expiration = ((Document) user.get("session")).getLong("expiration");
    long current = clock.getTime().getTime();
    return current > expiration;
  }

  @Override
  public void refreshSession(String sid) {
    collection.updateOne(eq("session.sid", sid),
            new Document("$set",
                    new Document("session.expiration", clock.newExpirationTime().getTime())));
  }

  @Override
  public void clearSession(String sid) {
    collection.updateOne(eq("session.sid", sid),
            new Document("$set",
                    new Document("session.expiration", 0l)));
  }

}