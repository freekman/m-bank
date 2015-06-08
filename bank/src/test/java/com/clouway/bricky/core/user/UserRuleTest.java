package com.clouway.bricky.core.user;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class UserRuleTest {

  private UserRule rule;

  @Before
  public void setUp() throws Exception {
    rule = new UserRule();
  }

  @Test
  public void happyPath() throws Exception {
    Optional<String> response = rule.apply(user("Marian", "123"));
    assertTrue(!response.isPresent());
  }

  @Test
  public void emptyUserName() throws Exception {
    Optional<String> response = rule.apply(user("", "123"));
    assertThat(response.get(), is(equalTo("Please enter username")));

    Optional<String> response2 = rule.apply(user(null, "123"));
    assertThat(response2.get(), is(equalTo("Please enter username")));
  }

  @Test
  public void emptyPasswordName() throws Exception {
    Optional<String> response = rule.apply(user("Alphonse", ""));
    assertThat(response.get(), is(equalTo("Please enter password")));

    Optional<String> response2 = rule.apply(user("Alphonse", null));
    assertThat(response2.get(), is(equalTo("Please enter password")));
  }

  @Test
  public void shortName() throws Exception {
    Optional<String> response = rule.apply(user("aa", "123"));
    assertThat(response.get(), is(equalTo("Please enter valid username")));
  }

  @Test
  public void shortPassword() throws Exception {
    Optional<String> response = rule.apply(user("Alphonse", "12"));
    assertThat(response.get(), is(equalTo("Please enter valid password")));
  }

  private User user(String name, String password) {
    return new User(name, password);
  }

}