package com.clouway.bricky.core.db.user;

import com.clouway.bricky.core.user.User;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public abstract class UserDbContractTest {

  private UserRepository repository;

  public abstract UserRepository getRepository();

  @Before
  public void setUp() throws Exception {
    repository = getRepository();
  }

  @Test
  public void registerNonExistingUser() {
    User user = getTestUser();
    repository.register(user);
    assertThat(true, is(repository.isExisting(user.username)));
  }

  @Test
  public void lookupNonExistingUser() throws Exception {
    assertThat(false, is(repository.isExisting("unknown-user")));
  }

  @Test
  public void authenticateNonExistingUser() throws Exception {
    assertFalse(repository.isAuthentic(getTestUser()));
  }

  @Test
  public void successfullyAuthenticateUser() throws Exception {
    User user = getTestUser();
    repository.register(user);
    assertTrue(repository.isAuthentic(user));
  }

  @Test
  public void wrongPasswordAuthentication() throws Exception {
    User firstUser = new User("Marian", "123");
    User secondUser = new User("Marian", "456");

    repository.register(firstUser);
    assertFalse(repository.isAuthentic(secondUser));
  }

  @Test
  public void wrongUsernameAuthentication() throws Exception {
    User alphonse = new User("Alphonse", "123");
    User gaston = new User("Gaston", "123");

    repository.register(alphonse);
    assertFalse(repository.isAuthentic(gaston));
  }

  @NotNull
  private User getTestUser() {
    return new User("Marian", "pswd");
  }

}
