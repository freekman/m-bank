package com.clouway.bricky.http.filter;

import com.clouway.bricky.JsonM;
import com.clouway.bricky.JsonParseException;
import com.google.sitebricks.headless.Reply;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
public class ServiceInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {

    try {
      return methodInvocation.proceed();
    } catch (JsonParseException e) {
      return Reply.with(e.getMessage()).status(SC_BAD_REQUEST).as(JsonM.class);
    }
  }

}
