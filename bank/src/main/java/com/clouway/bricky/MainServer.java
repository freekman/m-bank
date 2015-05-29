package com.clouway.bricky;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class MainServer {

  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    WebAppContext appContext = new WebAppContext();
    appContext.setWar("brick-bank.war");
    server.setHandler(appContext);
    server.start();
    server.join();
  }

}
