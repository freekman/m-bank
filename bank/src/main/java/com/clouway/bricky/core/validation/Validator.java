package com.clouway.bricky.core.validation;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface Validator<T, V> {

  T validate(V value, ValidationRule<V> rule);

}
