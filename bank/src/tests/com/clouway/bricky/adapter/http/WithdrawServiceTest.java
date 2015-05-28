package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.db.balance.BalanceRepository;
import com.clouway.bricky.core.db.balance.FundDeficitException;
import com.clouway.bricky.core.user.CurrentUser;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Request.RequestRead;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static com.clouway.bricky.adapter.http.IsEqualToReply.isEqualToReply;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class WithdrawServiceTest {

  private WithdrawService service;
  private BalanceRepository repository;
  private Request request;
  private RequestRead requestRead;

  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    repository = context.mock(BalanceRepository.class);
    request = context.mock(Request.class);
    requestRead = context.mock(RequestRead.class);
    service = new WithdrawService(repository);
  }


  @Test
  public void happyPath() throws Exception {
    final double amount = 10, balance = 20;
    pretendWithdrawAmountIs(amount);

    context.checking(new Expectations() {{
      oneOf(repository).withdrawFromCurrentUser(amount);
      will(returnValue(userWithBalance(balance)));
    }});

    Reply<?> reply = service.withdraw(request);
    assertThat(reply, isEqualToReply(Reply.with(balance).status(HttpServletResponse.SC_CREATED)));
  }

  @Test
  public void withdrawNegativeAmount() throws Exception {
    pretendWithdrawAmountIs(-10);

    Reply<?> reply = service.withdraw(request);
    assertThat(reply, isEqualToReply(Reply.with("Operation failed.").status(HttpServletResponse.SC_FORBIDDEN)));
  }

  @Test
  public void withdrawMoreThanAvailable() throws Exception {
    final double amount = 25;
    pretendWithdrawAmountIs(amount);

    context.checking(new Expectations() {{
      oneOf(repository).withdrawFromCurrentUser(amount);
      will(throwException(new FundDeficitException()));
    }});

    Reply<?> reply = service.withdraw(request);
    assertThat(reply, isEqualToReply(Reply.with("Operation failed.").status(HttpServletResponse.SC_FORBIDDEN)));
  }

  @Test
  public void unauthorizedWithdraw() throws Exception {
    pretendWithdrawAmountIs(20);
    context.checking(new Expectations() {{
      oneOf(repository).withdrawFromCurrentUser(20);
      will(throwException(new AuthorizationException()));
    }});

    Reply<?> result = service.withdraw(request);
    assertThat(result, isEqualToReply(Reply.with("Operation failed.").status(HttpServletResponse.SC_UNAUTHORIZED)));
  }

  private void pretendWithdrawAmountIs(double amount) {
    final AmountDTO dto = new AmountDTO();
    dto.setAmount(amount);
    context.checking(new Expectations() {{
      oneOf(request).read(AmountDTO.class);
      will(returnValue(requestRead));
      oneOf(requestRead).as(Json.class);
      will(returnValue(dto));
    }});
  }

  private CurrentUser userWithBalance(double balance) {
    return new CurrentUser("DummyUser", balance);
  }

}