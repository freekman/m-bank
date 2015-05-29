package com.clouway.bricky.adapter.http;

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
  private Optional<Object> optActualStatus;
  private Optional<Object> optExpectedStatus;


  public IsEqualToReply(Reply<?> expectedValue) {
    this.expectedValue = expectedValue;
  }

  @Factory
  public static Matcher<Reply<?>> isEqualToReply(Reply<?> operand) {
    return new IsEqualToReply(operand);
  }

  @Override
  protected boolean matchesSafely(Reply<?> actualValue) {
    boolean match;

    optExpectedEntity = getDeclaredFieldValue(expectedValue, "entity");
    optActualEntity = getDeclaredFieldValue(actualValue, "entity");

    optExpectedUri = getDeclaredFieldValue(expectedValue, "redirectUri");
    optActualUri = getDeclaredFieldValue(actualValue, "redirectUri");

    optExpectedStatus = getDeclaredFieldValue(actualValue, "status");
    optActualStatus = getDeclaredFieldValue(expectedValue, "status");

    match = areEqual(optExpectedEntity, optActualEntity);

    if (match) {
      match = areEqual(optExpectedUri, optActualUri);
    }
    if (match) {
      match = areEqual(optExpectedStatus, optActualStatus);
    }

    return match;
  }

  @Override
  public void describeTo(Description description) {
    description
            .appendText("entity:'" + optActualEntity.orNull() + "'")
            .appendText(" redirectUri:'" + optActualUri.orNull() + "'")
            .appendText(" status:'" + optActualStatus.orNull() + "'")
            .appendText(" to equal \n\t\t")
            .appendText(" entity:'" + optExpectedEntity.orNull() + "'")
            .appendText(" redirectUri:'" + optExpectedUri.orNull() + "'")
            .appendText(" status:'" + optExpectedStatus.orNull() + "'");
  }

  @Override
  protected void describeMismatchSafely(Reply<?> item, Description mismatchDescription) {
    mismatchDescription
            .appendText("was entity:'" + optExpectedEntity.orNull() + "'")
            .appendText("redirectUri:'" + optExpectedUri.orNull() + "'");
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

  private boolean areEqual(Optional optExpected, Optional optActual) {
    boolean areEqual;
    if (optExpected.isPresent() && optActual.isPresent()) {
      Object expected = optExpected.get();
      Object actual = optActual.get();
      areEqual = actual.equals(expected);
    } else {
      areEqual = bothAreNull(optExpected.orNull(), optActual.orNull());
    }
    return areEqual;
  }

  private boolean bothAreNull(Object first, Object second) {
    return (first == null && second == null);
  }

}
