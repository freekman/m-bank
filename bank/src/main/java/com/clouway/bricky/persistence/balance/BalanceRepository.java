package com.clouway.bricky.persistence.balance;

import com.clouway.bricky.core.user.CurrentUser;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface BalanceRepository {

  CurrentUser depositToCurrentUser(double amount);

  CurrentUser withdrawFromCurrentUser(double amount) throws FundDeficitException;

  CurrentUser getCurrentUser();
}
