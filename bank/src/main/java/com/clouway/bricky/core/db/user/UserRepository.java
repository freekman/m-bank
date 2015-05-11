package com.clouway.bricky.core.db.user;


import com.clouway.bricky.core.user.UserDTO;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public interface UserRepository {

  boolean register(UserDTO user);

  boolean isExisting(String userName);

}
