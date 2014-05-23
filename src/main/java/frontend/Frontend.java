package frontend;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import templater.PageGenerator;


/**
 * Created by kate on 17.03.14.
 */
public class Frontend extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();


        if (request.getPathInfo().equals("/timer")) {
            pageVariables.put("message", "Hello World");
            response.getWriter().println(PageGenerator.renderPage("timer.tml", pageVariables));
            return;

        }
    }
}
