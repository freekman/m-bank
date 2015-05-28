package com.clouway.bricky.core.db.balance;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.sesion.Session;
import com.clouway.bricky.core.user.CurrentUser;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoBalanceRepository implements BalanceRepository {

  private final MongoCollection<Document> accounts;
  private Session session;

  @Inject
  public MongoBalanceRepository(MongoDatabase database, Session session) {
    this.session = session;
    accounts = database.getCollection("accounts");
  }

  @Override
  public CurrentUser depositToCurrentUser(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Can not deposit negative amount: " + amount);
    }
    Optional<String> sid = session.getSid();
    if (sid.isPresent()) {
      accounts.updateOne(eq("session.sid", sid.get()), new Document("$inc", new Document("balance", amount)));
      return getCurrentUser(sid.get());
    }
    throw new AuthorizationException();
  }

  @Override
  public CurrentUser withdrawFromCurrentUser(double amount) throws FundDeficitException {
    if (amount < 0) {
      throw new IllegalArgumentException("Can not withdraw negative amount: " + amount);
    }
    Optional<String> sid = session.getSid();
    if (sid.isPresent()) {
      CurrentUser user = getCurrentUser(sid.get());
      if (user.balance < amount) {
        throw new FundDeficitException();
      }
      accounts.updateOne(eq("session.sid", sid.get()), new Document("$inc", new Document("balance", -amount)));
      return new CurrentUser(user.name, user.balance - amount);
    }

    throw new AuthorizationException();
  }

  @NotNull
  private CurrentUser getCurrentUser(String sid) {
    Document user = accounts.find(new Document("session.sid", sid)).first();
    return new CurrentUser(user.getString("username"), user.getDouble("balance"));
  }
}
