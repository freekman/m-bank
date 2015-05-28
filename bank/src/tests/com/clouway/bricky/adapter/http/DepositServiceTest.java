package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.db.balance.BalanceRepository;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static com.clouway.bricky.adapter.http.service.IsEqualToReply.isEqualToReply;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class DepositServiceTest {

  private DepositService service;
  private Request request;
  private BalanceRepository repository;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    repository = context.mock(BalanceRepository.class);
    service = new DepositService(repository);
    request = context.mock(Request.class);
  }

  @Test
  public void happyPath() throws Exception {
    final double amount = 10;
    context.checking(new Expectations() {{
      oneOf(request).param("amount");
      will(returnValue("10"));
      oneOf(repository).depositToCurrentUser(amount);
      will(returnValue(amount));
    }});
    Reply<?> result = service.deposit(request);
    assertThat(result, isEqualToReply(Reply.with(amount).status(HttpServletResponse.SC_CREATED)));
  }
}