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
}
