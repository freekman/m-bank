package com.clouway.bricky.core.db.balance;

import com.clouway.bricky.core.AuthorizationException;
import com.clouway.bricky.core.user.CurrentUser;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface BalanceRepository {

  CurrentUser depositToCurrentUser(double amount) throws AuthorizationException;

  CurrentUser withdrawFromCurrentUser(double amount) throws FundDeficitException, AuthorizationException;
}
