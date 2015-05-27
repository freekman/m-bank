package com.clouway.bricky.adapter.http.validation;

import com.google.common.base.Optional;

/**
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
