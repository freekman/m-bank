package com.clouway.bricky.core.Validation;

import java.util.List;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface ValidationRule<T> {

  List<String> apply(T value);

}
