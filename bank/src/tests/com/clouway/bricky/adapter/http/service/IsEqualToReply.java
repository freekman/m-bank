package com.clouway.bricky.adapter.http.service;

import com.google.sitebricks.headless.Reply;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Field;


/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class IsEqualToReply extends TypeSafeMatcher<Reply<?>> {

  private Reply<?> expectedValue;
  private Object expected;
  private Object actual;


  public IsEqualToReply(Reply<?> expectedValue) {
    this.expectedValue = expectedValue;
  }

  @Override
  protected boolean matchesSafely(Reply<?> actualValue) {
    try {
      Field expectedField = expectedValue.getClass().getDeclaredField("entity");
      Field actualField = actualValue.getClass().getDeclaredField("entity");
      expectedField.setAccessible(true);
      actualField.setAccessible(true);
      actual = actualField.get(actualValue);
      expected = expectedField.get(expectedValue);
      return actual.equals(expected);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("Reply '" + actual.toString() + "' to equal Reply '" + expected.toString() + "'");
  }

  @Factory
  public static Matcher<Reply<?>> isEqualToReply(Reply<?> operand) {
    return new IsEqualToReply(operand);
  }

  @Override
  protected void describeMismatchSafely(Reply<?> item, Description mismatchDescription) {
    mismatchDescription.appendText("was '" + expected.toString() + "'");
  }
}
