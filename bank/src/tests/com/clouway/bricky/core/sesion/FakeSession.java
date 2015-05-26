package com.clouway.bricky.core.sesion;

import com.google.inject.Provider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class FakeSession extends CurrentSession {

  private static class FakeProvider<T> implements Provider<T> {

    private final T item;

    private FakeProvider(T item) {
      this.item = item;
    }

    @Override
    public T get() {
      return item;
    }
  }

  public FakeSession(HttpServletRequest request, HttpServletResponse response) {
    super(new Encrypt(), new FakeProvider<HttpServletRequest>(request), new FakeProvider<HttpServletResponse>(response));
  }
}
