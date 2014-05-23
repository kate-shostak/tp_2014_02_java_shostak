package db;

import frontend.FrontendWithDataBase;
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
import java.util.Random;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kate on 05.04.14.
 */
public class RegistrationTest {

    private static FrontendWithDataBase frontend = new FrontendWithDataBase();
    private static StringWriter stringWriter = new StringWriter();
    private static PrintWriter writer = new PrintWriter(stringWriter);

    private static HttpServletRequest request = mock(HttpServletRequest.class);
    private static HttpServletResponse response = mock(HttpServletResponse.class);
    private static HttpSession session = mock(HttpSession.class);

    @Before
    public void setUp() throws IOException {
        when(response.getWriter()).thenReturn(writer);
        when(request.getPathInfo()).thenReturn("/reg");
    }

    @Test
    public void okRegistrationTest() throws IOException, ServletException {
        Random random = new Random();
        char randomChar[] = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<10;++i) {
            char c = randomChar[random.nextInt(randomChar.length)];
            stringBuilder.append(c);
        }
        String login = stringBuilder.toString();

        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("testpass");
        frontend.doPost(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Authentication"));
    }

    @Test
    public void emptySetCaseTest() throws IOException, ServletException {
        when(request.getParameter("login")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        frontend.doPost(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Your login and password must be not empty"));
    }

    @Test
    public void failedRegistrationTest() throws IOException, ServletException {
        when(request.getParameter("login")).thenReturn("testlog");
        when(request.getParameter("password")).thenReturn("testpass");
        frontend.doPost(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Your login and password must be not empty"));
    }
}
