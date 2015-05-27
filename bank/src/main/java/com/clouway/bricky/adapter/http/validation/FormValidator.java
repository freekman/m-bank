package com.clouway.bricky.adapter.http.validation;

import com.google.common.base.Optional;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class FormValidator<T> implements Validator<T> {
  @Override
  public Optional<String> validate(T value, ValidationRule<T> rule) {
    return rule.apply(value);
  }
}
