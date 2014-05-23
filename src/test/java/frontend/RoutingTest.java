package frontend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kate on 05.04.14.
 */
public class RoutingTest {

    final private static FrontendWithDataBase frontend = new FrontendWithDataBase();
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter writer = new PrintWriter(stringWriter);

    final private static HttpServletRequest request = mock(HttpServletRequest.class);
    final private static HttpServletResponse response = mock(HttpServletResponse.class);
    final private static HttpSession session = mock(HttpSession.class);

    @Before
    public void setUp() throws IOException {
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void mainPageRoutingTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/hello");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Hi there!"));
    }

    @Test
    public void authOkRoutingPageTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/timer");
        when(request.getSession()).thenReturn(session);


        when(session.getAttribute("userID")).thenReturn(6);
        when(session.getAttribute("userName")).thenReturn("FakeUser");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("I'm a timer"));
    }

    @Test
    public void authFailRoutingrPageTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/authfail");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userID")).thenReturn(6);
        when(session.getAttribute("userName")).thenReturn("FakeUser");
        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("You shall not pass!"));
    }
}


