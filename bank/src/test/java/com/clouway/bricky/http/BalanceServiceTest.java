package com.clouway.bricky.http;

import com.clouway.bricky.JsonM;
import com.clouway.bricky.persistence.balance.BalanceRepository;
import com.clouway.bricky.persistence.balance.FundDeficitException;
import com.clouway.bricky.core.user.CurrentUser;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Request.RequestRead;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static com.clouway.bricky.http.IsEqualToReply.isEqualToReply;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class BalanceServiceTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private BalanceService service;
  private Request request;
  private RequestRead requestRead;
  private BalanceRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = context.mock(BalanceRepository.class);
    service = new BalanceService(repository);
    request = context.mock(Request.class);
    requestRead = context.mock(RequestRead.class);
  }

  @Test
  public void depositHappyPath() throws Exception {
    final double amount = 10;
    pretendDepositAmountIs(amount);
    context.checking(new Expectations() {{
      oneOf(repository).depositToCurrentUser(amount);
      will(returnValue(new CurrentUser("Test", amount)));
    }});
    Reply<?> result = service.deposit(request);
    assertThat(result, isEqualToReply(Reply.with(amount).status(HttpServletResponse.SC_CREATED)));
  }

  @Test
  public void negativeAmountDeposit() throws Exception {
    pretendDepositAmountIs(-20);

    Reply<?> result = service.deposit(request);

    assertThat(result, isEqualToReply(Reply.with("Operation failed.").status(HttpServletResponse.SC_BAD_REQUEST)));
  }

  @Test
  public void withdrawHappyPath() throws Exception {
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
  public void queryUserInfo() throws Exception {
    context.checking(new Expectations() {{
      oneOf(repository).getCurrentUser();
      will(returnValue(userWithBalance(20)));
    }});
    Reply<?> result = service.userInfo();
    assertThat(result, isEqualToReply(Reply.with(userWithBalance(20)).ok()));
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

  private void pretendDepositAmountIs(double amount) {
    final AmountDTO dto = new AmountDTO();
    dto.setAmount(amount);
    context.checking(new Expectations() {{
      oneOf(request).read(AmountDTO.class);
      will(returnValue(requestRead));
      oneOf(requestRead).as(JsonM.class);
      will(returnValue(dto));
    }});
  }

}