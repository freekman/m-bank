package com.clouway.bricky.core;

import com.clouway.bricky.core.user.User;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface Registry {

  void authorize(User user) throws AuthorizationException;

}
