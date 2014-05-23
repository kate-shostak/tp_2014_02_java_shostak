package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.atomic.AtomicLong;

import templater.PageGenerator;

/**
 * Created by kate on 17.03.14.
 */
public class Frontend extends HttpServlet {

    private Map<String, Object> pageVariables = new HashMap<>();
    private AtomicLong userIdGenerator = new AtomicLong();

    //Static data to simulate database.
    private static Map<String, String> users = new HashMap<>();

    static {
        users.put("Bill", "bill");
        users.put("llib", "llib");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/auth")) {
            if (request.getSession().getAttribute("userID") != null) {
                response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));
            } else {
                pageVariables.put("login", " ");
                pageVariables.put("password", " ");
                pageVariables.put("sometext", "Authentication");
                response.getWriter().println(PageGenerator.renderPage("authform.tml", pageVariables));
            }
            return;
        } else if (request.getPathInfo().equals("/timer")) {
            if (request.getSession().getAttribute("userName") != null) {
                String loggedUserName = (String) request.getSession().getAttribute("userName");
                pageVariables.put("message", "Hello, ");
                pageVariables.put("userName", loggedUserName);
                response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));
            } else {
                pageVariables.put("sometext", "You are to auth first");
                response.getWriter().println(PageGenerator.renderPage("authform.tml", pageVariables));
            }
            return;
        } else {
            response.getWriter().println("html");//How to redirect?
            return;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        //Why 'de heck this sends the POST three times in a row?!

        //Receiving the login and password  submitted by the user.
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        //Checking if the user exists in our fake B.
        if (users.containsKey(login) && users.containsValue(password)) {
            HttpSession session = request.getSession();
            Long userID = (Long) session.getAttribute("userID");

            if (userID == null) {
                userID = userIdGenerator.getAndIncrement();
                session.setAttribute("userName", login);
                session.setAttribute("userID", userID);
            }
            pageVariables.put("message", "Hello, ");
            pageVariables.put("userID", userID);
            pageVariables.put("userName", login);
            response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));

        } else {
            pageVariables.put("sometext", "Wrong credentials, try again.");
            response.getWriter().println(PageGenerator.renderPage("authform.tml", pageVariables));
        }
    }
}
