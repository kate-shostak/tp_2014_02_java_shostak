package redir;

import frontend.FrontendWithDataBase;
import db_interaction.MyConnector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;
import java.sql.Connection;

/**
 * Created by kate on 17.03.14.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Servlet frontend = new FrontendWithDataBase();
        Server server = new Server(8080);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);//Why do we need .SESSION?
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler static_handler = new ResourceHandler();
        static_handler.setDirectoriesListed(false);
        static_handler.setResourceBase("static");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{static_handler, context});

        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
