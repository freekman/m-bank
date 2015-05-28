package com.clouway.bricky.core.db.balance;

import com.clouway.bricky.core.UnauthorizedException;
import com.clouway.bricky.core.user.CurrentUser;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface BalanceRepository {

  CurrentUser depositToCurrentUser(double amount) throws UnauthorizedException;

  CurrentUser withdrawFromCurrentUser(double amount) throws FundDeficitException, UnauthorizedException;

  CurrentUser getCurrentUser();
}
