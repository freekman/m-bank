package com.clouway.bricky.core.sesion;

import java.util.Date;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface SessionClock {

  /**
   * Generate new expiration time for a session.
   *
   * @return date when session should expire.
   */
  Date newExpirationTime();

  /**
   * Get current time.
   *
   * @return current date.
   */
  Date getTime();
}
