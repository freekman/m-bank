package com.clouway.bricky.adapter.http;

import com.clouway.bricky.AmountDTO;
import com.clouway.bricky.core.db.balance.BalanceRepository;
import com.clouway.bricky.core.user.CurrentUser;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */

@At("/r/deposit")
@Service
public class DepositService {

  private BalanceRepository repository;

  @Inject
  public DepositService(BalanceRepository repository) {
    this.repository = repository;
  }

  @Post
  public Reply<?> deposit(Request request) {
    AmountDTO dto = request.read(AmountDTO.class).as(Json.class);
    CurrentUser user = repository.depositToCurrentUser(dto.amount);
    return Reply.with(user.balance).status(HttpServletResponse.SC_CREATED).as(Json.class);
  }

}
