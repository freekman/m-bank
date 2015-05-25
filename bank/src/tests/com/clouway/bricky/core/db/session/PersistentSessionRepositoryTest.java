package com.clouway.bricky.core.db.session;

import com.clouway.bricky.core.sesion.SessionClock;
import com.clouway.bricky.core.user.User;
import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class PersistentSessionRepositoryTest {

  private SessionClock clock;
  private PersistentSessionRepository repository;

  @Rule
  public FongoRule fongo = new FongoRule();

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private MongoDatabase db;

  @Before
  public void setUp() throws Exception {
    clock = context.mock(SessionClock.class);
    db = fongo.getDatabase("bank");
    repository = new PersistentSessionRepository(db, clock);
  }

  @Test
  public void lookupNonExpiredSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(clock).newExpirationTime();
      will(returnValue(firstOfJanuary(2015, 10, 10)));
      oneOf(clock).getTime();
      will(returnValue(firstOfJanuary(2015, 10, 5)));
    }});

    User alphonse = new User("Alphonse", "password");
    pretendUserHasAccount(alphonse);
    repository.addSession(alphonse, "sid123");
    assertFalse(repository.isSessionExpired("sid123"));
  }

  @Test
  public void lookupExpiredSession() throws Exception {
    context.checking(new Expectations() {{
      oneOf(clock).newExpirationTime();
      will(returnValue(firstOfJanuary(2015, 10, 10)));
      oneOf(clock).getTime();
      will(returnValue(firstOfJanuary(2015, 10, 15)));
    }});

    User alphonse = new User("Alphonse", "password");
    pretendUserHasAccount(alphonse);
    repository.addSession(alphonse, "123sid");
    assertTrue(repository.isSessionExpired("123sid"));
  }

  @Test
  public void lookupAccountWithNoSession() throws Exception {
    assertTrue(repository.isSessionExpired("foo"));
  }

  private void pretendUserHasAccount(User user) {
    MongoCollection<Document> accounts = db.getCollection("accounts");
    accounts.insertOne(new Document("username", user.username).append("password", user.password));
  }

  private Date firstOfJanuary(int year, int hour, int minutes) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, Calendar.JANUARY, 1, hour, minutes, 1);
    return cal.getTime();
  }

}