package com.clouway.bricky.core.sesion;

import com.google.inject.Provider;
import org.jetbrains.annotations.NotNull;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class CurrentSessionTest {

  private class FakeProvider<T> implements Provider<T> {

    private final T item;

    FakeProvider(T item) {
      this.item = item;
    }

    @Override
    public T get() {
      return item;
    }
  }

  private CurrentSession session;
  private HttpServletResponse response;
  private HttpServletRequest request;

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Before
  public void setUp() throws Exception {
    response = context.mock(HttpServletResponse.class);
    request = context.mock(HttpServletRequest.class);
    session = new CurrentSession(new Encrypt(), new FakeProvider<HttpServletRequest>(request), new FakeProvider<HttpServletResponse>(response));
  }

  @Test
  public void attachSidCookie() throws Exception {
    context.checking(new Expectations() {{
      oneOf(response).addCookie(with(any(Cookie.class)));
    }});

    session.attach();
  }

  @Test
  public void lookupExistingSid() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).getCookies();
      will(returnValue(dummyCookie()));
    }});

    assertTrue(session.getSid().isPresent());
  }

  @Test
  public void lookupNonExistingSid() throws Exception {
    context.checking(new Expectations() {{
      oneOf(request).getCookies();
      will(returnValue(new Cookie[]{}));
    }});

    assertFalse(session.getSid().isPresent());
  }

  @Test
  public void removeSid() throws Exception {
    context.checking(new Expectations() {{
      oneOf(response).addCookie(with(any(Cookie.class)));
    }});

    session.detach();
  }

  @NotNull
  private Cookie[] dummyCookie() {
    return new Cookie[]{new Cookie("sid", "session_id")};
  }

}