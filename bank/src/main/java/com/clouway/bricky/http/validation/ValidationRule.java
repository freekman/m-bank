package com.clouway.bricky.http.validation;

import com.google.common.base.Optional;

/**
 * Class used to define criteria that a given object must meet in order to be successfully validated.
 * This class is designed to be used in conjunction with {@link Validator}.
 *
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface ValidationRule<T> {

  /**
   * Apply set of criteria in order to validate item and return a displayable string message.
   *
   * @param item to be validated.
   * @return single error message or absent if item meets criteria.
   */
  Optional<String> apply(T item);

}
