package com.clouway.bricky.core.sesion;

import java.util.Date;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public interface SessionClock {

  Date newExpirationTime();

  Date getTime();
}
