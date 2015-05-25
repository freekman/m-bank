package com.clouway.bricky.core.db.user;


import com.clouway.bricky.core.user.User;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public interface UserRepository {

  boolean register(User user);

  boolean isAuthentic(User user);

  boolean isExisting(String username);

}
