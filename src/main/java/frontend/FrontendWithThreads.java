package frontend;

import dbwiththreads.UserDataSet;
import interfaces.Abonent;
import messagesystem.*;
import resourcesystem.resources.URL;
import resourcesystem.resources.resourceManager;
import sessions.UserSession;
import sessions.UserStatus;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static frontend.PathMap.AUTH_PAGE;

/**
 * Created by kate on 19.04.14.
 */
public class FrontendWithThreads extends HttpServlet implements Abonent, Runnable {
    private MessageManager messageManager;
    private Address frontendaddress;
    private Map<String, UserSession> sessionIdToUserSession = new HashMap<>();
    private Map<String, Object> pageVariables = new HashMap<>();

    public FrontendWithThreads(MessageManager messageManager) {
        this.messageManager = messageManager;
        this.frontendaddress = new Address();
        messageManager.addService(this);
        messageManager.getAddressService().setFrontendWithThreadsAddress(this);
    }

    public void setId(String sessionId, UserDataSet result) {
        sessionIdToUserSession.get(sessionId).setUserId(result.getId());
    }

    public Address getAddress() {
        return frontendaddress;
    }

    public void sendResponse(HttpServletResponse response, String pageName, Map<String, Object> pageVariables) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.renderPage(pageName, pageVariables));
    }

    public void createUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userSessionId = session.getId();
        String login = request.getParameter("login");
        UserSession userSession = new UserSession(userSessionId, login);
        sessionIdToUserSession.put(userSessionId, userSession);
    }
//think about it.and about db transactiacdfvfgvuhsfvuahdfvs dunno how this is spelt :(
  /*  public void setPageVariables(String[] variable, String value) {
        pageVariables.put(variable, value);
    }
    */

    public void setUserStatus(String sessionId, UserStatus userStatus) {
        sessionIdToUserSession.get(sessionId).setUserStatus(userStatus);
    }

    private void checkUserSession(HttpServletRequest request) {
        if (sessionIdToUserSession.get(request.getSession().getId()) == null)
            createUserSession(request);
        else return;
    }

    private boolean checkSubmittedData(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("login").isEmpty() || request.getParameter("password").isEmpty())
            return true;
        else {
            pageVariables.put("login", request.getParameter("login"));
            return false;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        /*URL urlRes =(URL) resourceManager.getInstance().getResource("data/urls.xml");
        public final static String homepage = urlRes.getHOME();*/
        switch (request.getPathInfo()) {
            case PathMap.HOME_PAGE:
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                try {
                    response.getWriter().println(PageGenerator.renderPage("index.tml", pageVariables));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case PathMap.AUTH_PAGE:
                if (sessionIdToUserSession.get(request.getSession().getId()) == null) {
                    pageVariables.put("infoText", "Authentication");
                    try {
                        sendResponse(response, "authform.tml", pageVariables);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                } else {
                    switch (sessionIdToUserSession.get(request.getSession().getId()).getUserStatus()) {
                        case NOT_AUTHENTICATED_YET:
                            try {
                                response.sendRedirect(PathMap.TIMER_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case FRESHER:
                            try {
                                response.sendRedirect(PathMap.TIMER_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case AUTHENTICATED_USER:

                            try {
                                response.sendRedirect(PathMap.TIMER_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case NO_SUCH_USER_REGISTERED:
                            pageVariables.put("infoText", "User's not been found. Maybe the credentials mistake.try to auth again");
                            try {
                                setUserStatus(request.getSession().getId(), UserStatus.NOT_AUTHENTICATED_YET);
                                sendResponse(response, "authform.tml", pageVariables);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case REPEATED_LOGIN:
                            try {
                                response.sendRedirect(PathMap.REG_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                    }
                }
                break;

            case PathMap.REG_PAGE:
                if (sessionIdToUserSession.get(request.getSession().getId()) == null) {
                    pageVariables.put("infoText", "Registration");
                    try {
                        sendResponse(response, "regform.tml", pageVariables);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                } else {
                    switch (sessionIdToUserSession.get(request.getSession().getId()).getUserStatus()) {
                        case AUTHENTICATED_USER:
                            try {
                                response.sendRedirect(PathMap.TIMER_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case REPEATED_LOGIN:
                            pageVariables.put("infoText", "This login is already used. Pick another one.");
                            try {
                                setUserStatus(request.getSession().getId(), UserStatus.FRESHER);
                                sendResponse(response, "regform.tml", pageVariables);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case FRESHER:
                            try {
                                response.sendRedirect(PathMap.TIMER_PAGE);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                        case REGISTERED_OK:
                            pageVariables.put("infoText", "Registration");
                            try {
                                sendResponse(response, "regform.tml", pageVariables);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        case NOT_AUTHENTICATED_YET:
                            try {
                                response.sendRedirect(PathMap.TIMER_PAGE);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                    }
                }
                break;

            case PathMap.TIMER_PAGE:
                if (sessionIdToUserSession.get(request.getSession().getId()) == null) {
                    try {
                        response.sendRedirect(AUTH_PAGE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                } else {
                    //
                    switch (sessionIdToUserSession.get(request.getSession().getId()).getUserStatus()) {
                        case AUTHENTICATED_USER:

                            pageVariables.put("message", "Hello, ");
                            pageVariables.put("userName", sessionIdToUserSession.get(request.getSession().getId()).getUserName());
                            pageVariables.put("userId", sessionIdToUserSession.get(request.getSession().getId()).getUserId());
                            try {

                                sendResponse(response, "timer.tml", pageVariables);

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                            break;
                        case NOT_AUTHENTICATED_YET:
                            pageVariables.put("message", "Hello, Sweety!We're processing your request...");
                            pageVariables.put("userName", ".");
                            pageVariables.put("userId", ".");
                            try {

                                sendResponse(response, "timer.tml", pageVariables);

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                            break;
                        case REPEATED_LOGIN:
                            try {
                                response.sendRedirect(PathMap.REG_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case NO_SUCH_USER_REGISTERED:
                            try {
                                response.sendRedirect(PathMap.AUTH_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                        case FRESHER:
                            pageVariables.put("message", "Hello, Sweety!We're processing your request...");
                            pageVariables.put("userName", ".");
                            pageVariables.put("userId", ".");
                            try {

                                sendResponse(response, "timer.tml", pageVariables);

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                            break;
                        case REGISTERED_OK:
                            try {
                                setUserStatus(request.getSession().getId(), UserStatus.NO_SUCH_USER_REGISTERED);
                                response.sendRedirect(PathMap.AUTH_PAGE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                    }
                }

                break;
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        if (checkSubmittedData(request, response)) {
            pageVariables.put("infoText", "Neither of fields should be empty, babes <3.");

            switch (request.getPathInfo()) {
                case PathMap.AUTH_PAGE:
                    try {
                        sendResponse(response, "authform.tml", pageVariables);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case PathMap.REG_PAGE:
                    try {
                        sendResponse(response, "regform.tml", pageVariables);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            switch (request.getPathInfo()) {
                case PathMap.AUTH_PAGE:
                    doAuth(request, response);
                    break;

                case PathMap.REG_PAGE:
                    doRegistrate(request, response);
                    break;
            }
        }

    }

    public void doAuth(HttpServletRequest request, HttpServletResponse response) {
        checkUserSession(request);
        messageManager.sendMessage(new MessageToAuthenticate(this.getAddress(), messageManager.getAddressService().getDbServiceAddressAddress(), request.getParameter("login"), request.getParameter("password"), request.getSession().getId()));
        sessionIdToUserSession.get(request.getSession().getId()).changeUserName(request.getParameter("login"));
        try {
            response.sendRedirect(PathMap.TIMER_PAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doRegistrate(HttpServletRequest request, HttpServletResponse response) {
        createUserSession(request);
        setUserStatus(request.getSession().getId(), UserStatus.FRESHER);
        messageManager.sendMessage(new MessageToRegistrate(this.getAddress(), messageManager.getAddressService().getDbServiceAddressAddress(), request.getParameter("login"), request.getParameter("password"), request.getSession().getId()));
        try {
            response.sendRedirect(PathMap.TIMER_PAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true) {
            try {
                messageManager.executeForAbonent(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TimeHelper.sleep(100);
        }
    }

}
