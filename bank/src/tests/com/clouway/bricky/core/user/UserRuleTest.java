package com.clouway.bricky.core.user;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
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
  public void emptyUserName() throws Exception {
    List<String> messages = rule.apply(user("", "123"));
    assertThat(messages, hasItem("Please enter username"));

    List<String> messages2 = rule.apply(user(null, "123"));
    assertThat(messages2, hasItem("Please enter username"));
  }

  @Test
  public void emptyPasswordName() throws Exception {
    List<String> messages = rule.apply(user("Alphonse", ""));
    assertThat(messages, hasItem("Please enter password"));

    List<String> messages2 = rule.apply(user("Alphonse", null));
    assertThat(messages2, hasItem("Please enter password"));
  }

  @Test
  public void shortName() throws Exception {
    List<String> messages = rule.apply(user("aa", "123"));
    assertThat(messages,hasItem("Please enter valid username"));
  }
  @Test
  public void shortPassword() throws Exception {
    List<String> messages = rule.apply(user("Alphonse", "12"));
    assertThat(messages,hasItem("Please enter valid password"));
  }

  @Test
  public void name() throws Exception {

  }

  private User user(String name, String password) {
    return new User(name, password);
  }

}