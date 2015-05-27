package com.clouway.bricky.core.sesion;

import com.google.common.base.Optional;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface Session {

  /**
   * Generate and attach sid for new session.
   *
   * @return generated sid.
   */
  String attach();

  /**
   * Remove attached sid.
   */
  void detach();

  /**
   * Get attached sid if any.
   *
   * @return attached sid.
   */
  Optional<String> getSid();
}
