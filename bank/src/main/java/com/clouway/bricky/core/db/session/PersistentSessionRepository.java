package com.clouway.bricky.core.db.session;

import com.clouway.bricky.core.sesion.SessionClock;
import com.clouway.bricky.core.user.User;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class PersistentSessionRepository implements SessionRepository {


  private final MongoCollection<Document> collection;
  private final SessionClock clock;

  @Inject
  public PersistentSessionRepository(MongoDatabase database, SessionClock clock) {
    this.clock = clock;
    collection = database.getCollection("accounts");
  }

  @Override
  public void addSession(User user, String sid) {
    Date time = clock.newExpirationTime();
    collection.updateOne(eq("username", user.username),
            new Document("$set",
                    new Document("session",
                            new Document("sid", sid)
                                    .append("expiration", time.getTime()))));
  }

  @Override
  public boolean isSessionExpired(String sid) {
    Document user = collection.find(eq("session.sid", sid)).projection(new Document("session.expiration", 1).append("_id", 0)).first();
    if (user == null) {
      return true;
    }
    long expiration = ((Document) user.get("session")).getLong("expiration");
    long current = clock.getTime().getTime();
    return expiration < current;
  }
}
