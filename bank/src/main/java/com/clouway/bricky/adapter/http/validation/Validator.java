package com.clouway.bricky.adapter.http.validation;

import com.google.common.base.Optional;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface Validator<T> {

  Optional<String> validate(T value, ValidationRule<T> rule);

}
