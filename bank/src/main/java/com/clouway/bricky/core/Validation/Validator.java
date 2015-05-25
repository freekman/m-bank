package com.clouway.bricky.core.Validation;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface Validator<T, V> {

  T validate(V value, ValidationRule<V> rule);

}
