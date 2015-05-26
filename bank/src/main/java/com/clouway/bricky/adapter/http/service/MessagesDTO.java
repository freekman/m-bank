package com.clouway.bricky.adapter.http.service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class MessagesDTO {

  public final List<String> messages;

  public MessagesDTO(String... messages) {
    this.messages = Arrays.asList(messages);
  }

  public MessagesDTO(List<String> messages) {
    this.messages = messages;
  }
}
