package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import templater.PageGenerator;
import db_interaction.MyConnector;
import db_interaction.UserDataSet;
import db_interaction.UsersDAO;

/**
 * Created by kate on 17.03.14.
 */
public class FrontendWithDataBase extends HttpServlet {

    private Map<String, Object> pageVariables = new HashMap<>();

    //Connection establishing.
    Connection connection = MyConnector.setConnection();
    UsersDAO usersDAO = new UsersDAO(connection);
    UserDataSet result;


    //Rewrite, using cases, I guess..
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (request.getPathInfo().equals("/auth")) {

            if (request.getSession().getAttribute("userID") != null) {
                response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));
            } else {
                pageVariables.put("login", " ");
                pageVariables.put("password", " ");
                response.getWriter().println(PageGenerator.renderPage("authform.tml", pageVariables));
            }

            return;
        } else if (request.getPathInfo().equals("/reg")) {
            if (request.getSession().getAttribute("userID") != null) {
                response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));
            } else {
                pageVariables.put("login", " ");
                pageVariables.put("password", " ");
                response.getWriter().println(PageGenerator.renderPage("regform.tml", pageVariables));
            }
            return;
        } else if (request.getPathInfo().equals("/timer")) {
            if (request.getSession().getAttribute("userName") != null) {
                pageVariables.put("message", "Hello, ");
                pageVariables.put("userName", (String) request.getSession().getAttribute("userName"));
                pageVariables.put("userID", request.getSession().getAttribute("userID"));
                response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));
            } else {
                response.sendRedirect("/authfail");
            }
            return;

        } else if (request.getPathInfo().equals("/authfail")) {
            response.getWriter().println(PageGenerator.renderPage("authfail.tml", pageVariables));

        } else if (request.getPathInfo().equals("/regfail")) {
            response.getWriter().println(PageGenerator.renderPage("regfail.tml", pageVariables));

        } else {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.renderPage("index.tml", pageVariables));
            return;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        //Receiving the login and password  submitted by the user.
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        //Checking if the POST request was to login the existing user or to create the new one.
        if (request.getPathInfo().equals("/auth") || request.getPathInfo().equals("/authfail")) {

            //Checking if the user with such login exists.
            try {
                result = usersDAO.getBylogin(login);

                //Checking if the password for this login is correct.
                if (result.getPassword().equals(password)) {
                    //Creating the new session for our user.
                    HttpSession session = request.getSession();
                    session.setAttribute("userName", login);
                    session.setAttribute("userID", result.getId());
                    session.setAttribute("sessionID", session.getId());

                    //Fullfilling the template for correct page rendering.
                    pageVariables.put("sometext", "IT'S OK");
                    pageVariables.put("message", "Hello, ");
                    pageVariables.put("userName", login);
                    pageVariables.put("userID", result.getId());
                    response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));
                    response.sendRedirect("/timer");
                } else {
                    //If the submited password apperead to be wrong, redirect the user to the authentication page.
                    response.sendRedirect("/authfail");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                //If the user with submitted login doesn't present in our database.
                response.sendRedirect("/authfail");
            }
        } else if (request.getPathInfo().equals("/reg") || request.getPathInfo().equals("/regfail")) {

            if (login.isEmpty() || password.isEmpty()) {
                response.getWriter().println(PageGenerator.renderPage("regfail.tml", pageVariables));
                response.sendRedirect("/regfail");
            } else {
                try {
                    result = new UserDataSet(login, password);
                    // CHECK IN THE DB PY ID!!
                    usersDAO.AddUser(result);
                    response.getWriter().println(PageGenerator.renderPage("authform.tml", pageVariables));
                    response.sendRedirect("/auth");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}