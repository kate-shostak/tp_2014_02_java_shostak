package frontendwiththreads;

import frontend.FrontendWithThreads;
import messagesystem.AddressService;
import messagesystem.MessageManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sessions.UserSession;
import sessions.Randomizer;
import sessions.UserStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by kate on 26.04.14.
 */

//@RunWith(MockitoJUnitRunner.class)
public class RoutingTest {

    @Mock
    private static HttpServletRequest request = mock(HttpServletRequest.class);
    ;
    @Mock
    private static HttpServletResponse response = mock(HttpServletResponse.class);
    ;
    @Mock
    private static HttpSession session = mock(HttpSession.class);
    @Mock
    private static MessageManager messageManager = mock(MessageManager.class);
    @Mock
    private static AddressService addressService = mock(AddressService.class);
    @Mock
    private static UserSession userSession = mock(UserSession.class);
    @Mock
    private static Map<String, UserSession> sessionIdToUserSession = mock(HashMap.class);

    private FrontendWithThreads frontendWithThreads;
    private StringWriter stringWriter;
    private PrintWriter writer;
    private Randomizer randomizer;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        addressService = new AddressService();
        messageManager = new MessageManager(addressService);
        frontendWithThreads = new FrontendWithThreads(messageManager);
        randomizer = new Randomizer();
        //userSession = new UserSession(randomizer.getRandomSomething(), randomizer.getRandomSomething());
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void mainPageRoutingTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/home");
        frontendWithThreads.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Hi there!"));
    }

    @Test
    public void authNewUserRoutingPageTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/auth");
        when(request.getSession()).thenReturn(session);
        when(sessionIdToUserSession.get(session.getId())).thenReturn(null);
        frontendWithThreads.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Authentication"));
    }

    @Test
    public void authOkRoutingPageTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/auth");
        when(request.getSession()).thenReturn(session);
        String sessionId = randomizer.getRandomSomething();
        when(request.getSession().getId()).thenReturn(sessionId);

        when(request.getParameter("login")).thenReturn(randomizer.getRandomSomething());
        frontendWithThreads.createUserSession(request);
        frontendWithThreads.setUserStatus(sessionId, UserStatus.AUTHENTICATED_USER);

        frontendWithThreads.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect("/timer");
    }


    @Test
    public void authWaitRoutingPageTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/auth");
        when(request.getSession()).thenReturn(session);
        String sessionId = randomizer.getRandomSomething();
        when(request.getSession().getId()).thenReturn(sessionId);

        when(request.getParameter("login")).thenReturn(randomizer.getRandomSomething());
        frontendWithThreads.createUserSession(request);
        frontendWithThreads.setUserStatus(sessionId, UserStatus.NOT_AUTHENTICATED_YET);

        frontendWithThreads.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect("/timer");
    }

    @Test
    public void authFailRoutingPageTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/auth");
        when(request.getSession()).thenReturn(session);
        String sessionId = randomizer.getRandomSomething();
        when(request.getSession().getId()).thenReturn(sessionId);

        when(request.getParameter("login")).thenReturn(randomizer.getRandomSomething());
        frontendWithThreads.createUserSession(request);
        frontendWithThreads.setUserStatus(sessionId, UserStatus.NO_SUCH_USER_REGISTERED);

        frontendWithThreads.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("User's not been found"));
    }

    @Test
    public void authRepeatedLoginlRoutingPageTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/auth");
        when(request.getSession()).thenReturn(session);
        String sessionId = randomizer.getRandomSomething();
        when(request.getSession().getId()).thenReturn(sessionId);

        when(request.getParameter("login")).thenReturn(randomizer.getRandomSomething());
        frontendWithThreads.createUserSession(request);
        frontendWithThreads.setUserStatus(sessionId, UserStatus.REPEATED_LOGIN);

        frontendWithThreads.doGet(request, response);
        verify(response, atLeastOnce()).sendRedirect("/reg");
    }





    @Test
    public void regNewUserTest() throws SQLException, IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/reg");
        when(request.getSession()).thenReturn(session);
        when(sessionIdToUserSession.get(session.getId())).thenReturn(null);
        frontendWithThreads.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Registration"));;
    }

}
