package com.clouway.bricky.core.db.user;

import com.clouway.bricky.core.user.UserDTO;
import com.github.fakemongo.Fongo;
import com.github.fakemongo.junit.FongoRule;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoUserRepositoryTest {

  private MongoUserRepository repository;

  @Rule
  public FongoRule fongoRule = new FongoRule();

  @Before
  public void setUp() throws Exception {
    repository = new MongoUserRepository(fongoRule.getDatabase("bank"));
  }

  @Test
  public void registerNonExistingUser() throws Exception {
    UserDTO user = getTestUserDTO();
    repository.register(user);
    assertThat(true, is(repository.isExisting(user.username)));
  }

  @Test
  public void lookupNonExistingUser() throws Exception {
    assertThat(false, is(repository.isExisting("unknown-user")));
  }

  @NotNull
  private UserDTO getTestUserDTO() {
    UserDTO user = new UserDTO();
    user.setPassword("pass");
    user.setRepassword("pass");
    user.setUsername("Marian");
    return user;
  }
}