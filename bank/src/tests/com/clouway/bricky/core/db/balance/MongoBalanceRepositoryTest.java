package com.clouway.bricky.core.db.balance;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.sesion.Session;
import com.clouway.bricky.core.user.CurrentUser;
import com.github.fakemongo.junit.FongoRule;
import com.google.common.base.Optional;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoBalanceRepositoryTest {

  private MongoBalanceRepository repository;
  private Session session;

  @Rule
  public FongoRule fongo = new FongoRule();

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private MongoDatabase bank;

  @Before
  public void setUp() throws Exception {
    session = context.mock(Session.class);
    bank = fongo.getDatabase("bank");
    repository = new MongoBalanceRepository(bank, session);
  }

  @Test
  public void depositToUser() throws Exception {
    pretendSessionExitsFor("John");

    CurrentUser currentUser = repository.depositToCurrentUser(10);

    assertThat(currentUser.balance, is(equalTo(10d)));
    assertThat(currentUser.name, is("John"));
  }

  @Test
  public void sequenceDeposit() throws Exception {
    pretendSessionExitsFor("Mark");

    repository.depositToCurrentUser(20);
    CurrentUser user = repository.depositToCurrentUser(30);

    assertThat(user.balance, is(equalTo(50d)));
    assertThat(user.name, is(equalTo("Mark")));
  }

  @Test(expected = AuthorizationException.class)
  public void attemptDepositOfExpiredUser() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(Optional.absent()));
    }});

    repository.depositToCurrentUser(30);
  }

  @Test
  public void withdrawFromUser() throws Exception {
    pretendSessionExitsFor("Alf");

    repository.depositToCurrentUser(30);
    CurrentUser user = repository.withdrawFromCurrentUser(10);

    assertThat(user.balance, is(equalTo(20d)));
    assertThat(user.name, is(equalTo("Alf")));
  }

  @Test
  public void sequenceWithdraw() throws Exception {
    pretendSessionExitsFor("Alf");

    repository.depositToCurrentUser(30);
    repository.withdrawFromCurrentUser(10);
    CurrentUser user = repository.withdrawFromCurrentUser(10);

    assertThat(user.balance, is(equalTo(10d)));
    assertThat(user.name, is(equalTo("Alf")));
  }

  @Test(expected = AuthorizationException.class)
  public void withdrawFromExpiredUser() throws Exception {
    context.checking(new Expectations() {{
      oneOf(session).getSid();
      will(returnValue(Optional.absent()));
    }});

    repository.withdrawFromCurrentUser(10);
  }

  @Test(expected = FundDeficitException.class)
  public void withdrawMoreThanAvailable() throws Exception {
    pretendSessionExitsFor("John");

    repository.depositToCurrentUser(20);
    repository.withdrawFromCurrentUser(30);
  }

  private void pretendSessionExitsFor(String name) {
    final String sid = "dummy_sid";
    context.checking(new Expectations() {{
      allowing(session).getSid();
      will(returnValue(Optional.of(sid)));
    }});
    bank.getCollection("accounts").insertOne(new Document("username", name).append("session", new Document("sid", sid).append("expiration", 20)));
  }
}