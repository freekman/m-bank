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
public class MongoUserRepositoryTest extends UserDbContractTest {

  @Rule
  public FongoRule fongoRule = new FongoRule();

  @Override
  public UserRepository getRepository() {
    return new MongoUserRepository(fongoRule.getDatabase("bank"));
  }

}