package com.clouway.bricky.http.validation;

import com.google.common.base.Optional;

/**
 * Class used to apply some validation constraints upon specified value. Applied constrains are defined by a {@link ValidationRule}.
 *
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface Validator<T> {

  /**
   * Apply a set of criteria defined by a given rule to a specified value.
   *
   * @param value that will be validated.
   * @param rule  defining validation criteria.
   * @return Optional of String containing error message if criteria was not met, absent otherwise.
   */
  Optional<String> validate(T value, ValidationRule<T> rule);

}
