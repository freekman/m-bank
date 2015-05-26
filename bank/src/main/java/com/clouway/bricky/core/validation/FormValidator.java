package com.clouway.bricky.core.validation;


import com.clouway.bricky.adapter.http.service.MessagesDTO;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class FormValidator<T> implements Validator<MessagesDTO, T> {

  @Override
  public MessagesDTO validate(T value, ValidationRule<T> rule) {
    List<String> messages = rule.apply(value);
    if (messages.isEmpty()) {
      return new MessagesDTO(Lists.<String>newArrayList());
    }
    return new MessagesDTO(messages);
  }

}
