package com.clouway.bricky.core.db.user;

import com.github.fakemongo.junit.FongoRule;
import org.junit.Rule;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MongoUserRepositoryTest extends UserDbContractTest {

  @Rule
  public FongoRule fongoRule = new FongoRule();

  @Override
  public UserRepository getRepository() {
    return new MongoUserRepository(fongoRule.getDatabase("bank"));
  }

}