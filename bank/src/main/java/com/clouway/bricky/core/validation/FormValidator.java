package com.clouway.bricky.core.validation;


import com.clouway.bricky.adapter.http.service.FormResponse;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class FormValidator<T> implements Validator<FormResponse, T> {

  @Override
  public FormResponse validate(T value, ValidationRule<T> rule) {
    List<String> messages = rule.apply(value);
    if (messages.isEmpty()) {
      return new FormResponse(true, Lists.newArrayList("Operation successful"));
    }
    return new FormResponse(false, messages);
  }

}
