package redir;

import dbwiththreads.DBservice;
import dbwiththreads.MyConnector;
import frontend.FrontendWithThreads;
import messagesystem.AddressService;
import messagesystem.MessageManager;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resourcesystem.VFS.VFS;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Created by kate on 19.04.14.
 */
public class MainWIthThreads {
    public static void main(String[] args) throws Exception {

        VFS vfs = new VFS("");
        Iterator<String> files = vfs.getIterator("data");
        while (files.hasNext()){
            String nextFile = files.next();
            if (!vfs.isDirectory(nextFile)){
                resourcesystem.resources.resourceManager.getInstance().addResource(
                        nextFile,
                        resourcesystem.resources.resourceManager.getInstance().get(vfs.getAbsolutePath(nextFile))
                );
            }
        }

        AddressService addressService = new AddressService();
        MessageManager messageManager = new MessageManager(addressService);

        MyConnector connector = new MyConnector();
        Connection connection = connector.setConnection();
        DBservice dbService = new DBservice(connection, messageManager);

        FrontendWithThreads frontend = new FrontendWithThreads(messageManager);
        (new Thread(frontend)).start();
        (new Thread(dbService)).start();


        Server server = new Server(8081);


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler static_handler = new ResourceHandler();
        static_handler.setDirectoriesListed(false);
        static_handler.setResourceBase("static");


        HandlerList handlers = new HandlerList();


        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(true);
        rewriteHandler.setOriginalPathAttribute("requestedPath");
        RedirectRegexRule rule = new RedirectRegexRule();
        rule.setRegex("/");
        rule.setReplacement("/home");
        rewriteHandler.addRule(rule);

        handlers.setHandlers(new Handler[]{rewriteHandler, static_handler, context});

        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
