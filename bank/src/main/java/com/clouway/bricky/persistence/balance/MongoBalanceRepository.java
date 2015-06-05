package com.clouway.bricky.persistence.balance;

import com.clouway.bricky.core.sesion.Session;
import com.clouway.bricky.core.user.CurrentUser;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoBalanceRepository implements BalanceRepository {

  private final MongoCollection<Document> accounts;
  private final Session session;

  @Inject
  public MongoBalanceRepository(MongoDatabase database, Session session) {
    this.session = session;
    accounts = database.getCollection("accounts");
  }

  @Override
  public CurrentUser depositToCurrentUser(double amount) {
    Optional<String> sid = session.getSid();
    accounts.updateOne(eq("session.sid", sid.get()), new Document("$inc", new Document("balance", amount)));
    return getCurrentUser();
  }

  @Override
  public CurrentUser withdrawFromCurrentUser(double amount) throws FundDeficitException {
    Optional<String> sid = session.getSid();

    CurrentUser user = getCurrentUser();
    if (user.balance < amount) {
      throw new FundDeficitException();
    }
    accounts.updateOne(eq("session.sid", sid.get()), new Document("$inc", new Document("balance", -amount)));
    return new CurrentUser(user.name, user.balance - amount);

  }

  @Override
  public CurrentUser getCurrentUser() {
    Optional<String> sid = session.getSid();
    Document user = accounts.find(new Document("session.sid", sid.get())).first();
    Double cash = user.getDouble("balance");
    if (cash == null) {
      cash = 0d;
    }
    return new CurrentUser(user.getString("username"), cash);
  }
}
