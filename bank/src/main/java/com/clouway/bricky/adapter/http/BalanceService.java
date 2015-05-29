package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.db.balance.BalanceRepository;
import com.clouway.bricky.core.db.balance.FundDeficitException;
import com.clouway.bricky.core.user.CurrentUser;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */

@Service
@At("/r/balance")
public class BalanceService {

  private BalanceRepository repository;

  @Inject
  public BalanceService(BalanceRepository repository) {
    this.repository = repository;
  }

  @Post
  @At("/deposit")
  public Reply<?> deposit(Request request) {
    AmountDTO dto = request.read(AmountDTO.class).as(Json.class);

    if (dto.amount < 0) {
      return Reply.with("Operation failed.").status(SC_BAD_REQUEST).as(Json.class);
    }
    CurrentUser user = repository.depositToCurrentUser(dto.amount);
    return Reply.with(user.balance).status(SC_CREATED).as(Json.class);

  }

  @Post
  @At("/withdraw")
  public Reply<?> withdraw(Request request) {
    AmountDTO dto = request.read(AmountDTO.class).as(Json.class);

    if (dto.amount < 0) {
      return Reply.with("Operation failed.").status(SC_FORBIDDEN).as(Json.class);
    }
    try {
      CurrentUser user = repository.withdrawFromCurrentUser(dto.amount);
      return Reply.with(user.balance).status(SC_CREATED).as(Json.class);
    } catch (FundDeficitException e) {
      return Reply.with("Operation failed.").status(SC_FORBIDDEN).as(Json.class);
    }
  }

  @Post
  @At("/info")
  public Reply<?> userInfo() {
    CurrentUser user = repository.getCurrentUser();
    return Reply.with(user).ok().as(Json.class);
  }

}
