package com.clouway.bricky;

import com.clouway.bricky.core.PersistentDbModule;
import com.clouway.bricky.http.BrickModule;
import com.clouway.bricky.http.HttpModule;
import com.clouway.bricky.http.HttpServletModule;
import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class MainServer {

  public static void main(String[] args) throws Exception {
    PropertyReader reader = getPropertyReader(args);
    Guice.createInjector(new HttpServletModule(), new HttpModule(), new BrickModule(), new PersistentDbModule(reader));
    Server jetty = getJetty(reader);
    jetty.start();
    jetty.join();
  }


  public static Server getJetty(PropertyReader reader) {
    Server server = new Server(reader.getJettyPort());

    ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
    servletContextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

    // You MUST add DefaultServlet or your server will always return 404s
    servletContextHandler.addServlet(DefaultServlet.class, "/");
    servletContextHandler.setResourceBase("src/main/webapp");
    return server;
  }

  private static PropertyReader getPropertyReader(String[] args) {
    String propName;
    if (args != null && args.length != 0) {
      propName = args[0];
    } else {
      propName = "config.properties";
    }

    return new PropertyReader(propName);
  }

}
