package com.clouway.bricky.adapter.http;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.db.balance.BalanceRepository;
import com.clouway.bricky.core.db.balance.FundDeficitException;
import com.clouway.bricky.core.user.CurrentUser;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

import javax.servlet.http.HttpServletResponse;

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
    try {
      if (dto.amount < 0) {
        return Reply.with("Operation failed.").status(HttpServletResponse.SC_BAD_REQUEST).as(Json.class);
      }
      CurrentUser user = repository.depositToCurrentUser(dto.amount);
      return Reply.with(user.balance).status(HttpServletResponse.SC_CREATED).as(Json.class);
    } catch (AuthorizationException e) {
      return Reply.with("Operation failed.").status(HttpServletResponse.SC_UNAUTHORIZED).as(Json.class);
    }
  }

  @Post
  @At("/withdraw")
  public Reply<?> withdraw(Request request) {

    AmountDTO dto = request.read(AmountDTO.class).as(Json.class);

    if (dto.amount < 0) {
      return Reply.with("Operation failed.").status(HttpServletResponse.SC_FORBIDDEN).as(Json.class);
    }
    try {
      CurrentUser user = repository.withdrawFromCurrentUser(dto.amount);
      return Reply.with(user.balance).status(HttpServletResponse.SC_CREATED).as(Json.class);
    } catch (FundDeficitException e) {
      return Reply.with("Operation failed.").status(HttpServletResponse.SC_FORBIDDEN).as(Json.class);
    } catch (AuthorizationException e) {
      return Reply.with("Operation failed.").status(HttpServletResponse.SC_UNAUTHORIZED).as(Json.class);
    }
  }

  @Post
  @At("/info")
  public Reply<?> userInfo(Request request){
    return null;
  }

}
