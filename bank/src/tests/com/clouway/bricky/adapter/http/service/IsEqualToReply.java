package com.clouway.bricky.adapter.http.service;

import com.google.common.base.Optional;
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
  private Optional<Object> optExpectedEntity;
  private Optional<Object> optActualEntity;
  private Optional<Object> optExpectedUri;
  private Optional<Object> optActualUri;


  public IsEqualToReply(Reply<?> expectedValue) {
    this.expectedValue = expectedValue;
  }

  @Override
  protected boolean matchesSafely(Reply<?> actualValue) {
    boolean match;

    optExpectedEntity = getDeclaredFieldValue(expectedValue, "entity");
    optExpectedUri = getDeclaredFieldValue(expectedValue, "redirectUri");

    optActualEntity = getDeclaredFieldValue(actualValue, "entity");
    optActualUri = getDeclaredFieldValue(actualValue, "redirectUri");

    if (optExpectedEntity.isPresent() && optActualEntity.isPresent()) {
      Object expected = optExpectedEntity.get();
      Object actual = optActualEntity.get();
      match = actual.equals(expected);
    } else {
      match = bothAreNull(optExpectedEntity.orNull(), optActualEntity.orNull());
    }

    if (match && optExpectedUri.isPresent() && optActualUri.isPresent()) {
      Object expected = optExpectedUri.get();
      Object actual = optActualUri.get();
      match = actual.equals(expected);
    } else if (match) {
      match = bothAreNull(optExpectedUri.orNull(), optActualUri.orNull());
    }

    return match;
  }

  private boolean bothAreNull(Object first, Object second) {
    return (first == null && second == null);
  }

  private Optional<Object> getDeclaredFieldValue(Object item, String fieldName) {
    try {
      Field field = item.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      Object value = field.get(item);
      return (value == null ? Optional.absent() : Optional.of(value));
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return Optional.absent();
  }

  @Override
  public void describeTo(Description description) {
    description
            .appendText("entity:'" + optActualEntity.orNull() + "'")
            .appendText(" redirectUri:'" + optActualUri.orNull() + "'")
            .appendText(" to equal \n\t\t")
            .appendText(" entity:'" + optExpectedEntity.orNull() + "'")
            .appendText(" redirectUri:'" + optExpectedUri.orNull() + "'");
  }

  @Factory
  public static Matcher<Reply<?>> isEqualToReply(Reply<?> operand) {
    return new IsEqualToReply(operand);
  }

  @Override
  protected void describeMismatchSafely(Reply<?> item, Description mismatchDescription) {
    mismatchDescription
            .appendText("was entity:'" + optExpectedEntity.orNull() + "'")
            .appendText("redirectUri:'" + optExpectedUri.orNull() + "'");
  }


}
