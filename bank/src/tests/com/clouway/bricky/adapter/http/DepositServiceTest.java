package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.db.balance.BalanceRepository;
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

import static com.clouway.bricky.adapter.http.service.IsEqualToReply.isEqualToReply;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class DepositServiceTest {

  private DepositService service;
  private Request request;
  private RequestRead requestRead;
  private BalanceRepository repository;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    repository = context.mock(BalanceRepository.class);
    service = new DepositService(repository);
    request = context.mock(Request.class);
    requestRead = context.mock(RequestRead.class);
  }

  @Test
  public void happyPath() throws Exception {
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
  public void unauthorizedDeposit() throws Exception {
    pretendDepositAmountIs(20);
    context.checking(new Expectations() {{
      oneOf(repository).depositToCurrentUser(20);
      will(throwException(new AuthorizationException()));
    }});

    Reply<?> result = service.deposit(request);
    assertThat(result, isEqualToReply(Reply.with("Operation failed.").status(HttpServletResponse.SC_UNAUTHORIZED)));
  }

  @Test
  public void negativeAmountDeposit() throws Exception {
    pretendDepositAmountIs(-20);

    Reply<?> result = service.deposit(request);

    assertThat(result, isEqualToReply(Reply.with("Operation failed.").status(HttpServletResponse.SC_BAD_REQUEST)));
  }

  private void pretendDepositAmountIs(double amount) {
    final AmountDTO dto = new AmountDTO();
    dto.setAmount(amount);
    context.checking(new Expectations() {{
      oneOf(request).read(AmountDTO.class);
      will(returnValue(requestRead));
      oneOf(requestRead).as(Json.class);
      will(returnValue(dto));
    }});
  }

}