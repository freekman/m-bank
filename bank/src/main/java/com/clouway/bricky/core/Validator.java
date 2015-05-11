package com.clouway.bricky.core;

import java.util.List;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public interface Validator<T> {

  List<String> validate(T value);

}
