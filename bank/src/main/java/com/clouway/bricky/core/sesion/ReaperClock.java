package com.clouway.bricky.core.sesion;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class ReaperClock implements SessionClock {

  private final int durationInSeconds;

  public ReaperClock(long duration, TimeUnit timeUnit) {
    if (duration < 1) {
      throw new IllegalStateException();
    }
    this.durationInSeconds = (int) TimeUnit.SECONDS.convert(duration, timeUnit);
  }


  @Override
  public Date newExpirationTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.SECOND, durationInSeconds);
    return calendar.getTime();
  }

  @Override
  public Date getTime() {
    return new Date();
  }
}
