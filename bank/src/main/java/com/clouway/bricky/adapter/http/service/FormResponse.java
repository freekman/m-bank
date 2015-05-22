package com.clouway.bricky.adapter.http.service;

import java.util.List;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class FormResponse {
  public final boolean valid;
  public final List<String> messages;

  public FormResponse(boolean valid, List<String> messages) {
    this.valid = valid;
    this.messages = messages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FormResponse)) return false;

    FormResponse that = (FormResponse) o;

    if (valid != that.valid) return false;
    return messages.equals(that.messages);
  }

  @Override
  public int hashCode() {
    int result = (valid ? 1 : 0);
    result = 31 * result + messages.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "FormResponse{" +
            "valid=" + valid +
            ", messages=" + messages +
            '}';
  }
}
