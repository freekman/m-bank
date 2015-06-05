package com.clouway.bricky.core.sesion;

import com.google.common.base.Optional;

/**
 * User of this class has control over users session.
 * <pre>An active session is required in order to gain access to protected resources.
 * Such session can be acquired by attaching a session to user.
 * Restricting access can be achieved by detaching an already attached session.</pre>
 *
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
