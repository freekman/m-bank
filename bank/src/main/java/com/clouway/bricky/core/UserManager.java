package com.clouway.bricky.core;

import com.clouway.bricky.core.user.User;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface UserManager {

  void authorize(User user);

}
