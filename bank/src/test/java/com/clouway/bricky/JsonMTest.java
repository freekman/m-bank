package com.clouway.bricky;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.inject.TypeLiteral;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class JsonMTest {

  private class TestObject {

    public final String param1;
    public final String param2;
    public final String param3;

    public TestObject(String param1, String param2, String param3) {
      this.param1 = param1;
      this.param2 = param2;
      this.param3 = param3;
    }

  }

  private Gson gson = new Gson();

  private JsonM jsonM = new JsonM();

  @Test
  public void parseTestObject() throws Exception {

    String expected = gson.toJson(new TestObject("Param1", "Param2", "Param3"));

    TestObject in = jsonM.in(toInputStream(expected), TestObject.class);

    String actual = gson.toJson(in);

    assertThat(expected, is(equalTo(actual)));
  }

  @Test
  public void parseLiteralObject() throws Exception {

    String expected = gson.toJson(Lists.newArrayList(new TestObject("Param1", "Param2", "Param3")));

    List<TestObject> in = jsonM.in(toInputStream(expected), new TypeLiteral<List<TestObject>>() {
    });

    String actual = gson.toJson(in);

    assertThat(expected, is(equalTo(actual)));
  }

  private InputStream toInputStream(String string) {
    return new ByteArrayInputStream(string.getBytes());
  }

}