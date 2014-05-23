package frontend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import templater.PageGenerator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by kate on 05.04.14.
 */
public class RenderingTest {

    Random random = new Random();

    final Map<String, Object> pageVariables = new HashMap<>();
    final StringWriter stringWriter = new StringWriter();
    final PrintWriter writer = new PrintWriter(stringWriter);

    @Test
    public void mainPageRenderTest() {
        String page = PageGenerator.renderPage("index.tml", pageVariables);
        Assert.assertTrue(page.contains("Hi there!"));
    }

    @Test
    public void authFailRenderPageTest() {
        String page = PageGenerator.renderPage("authfail.tml", pageVariables);
        Assert.assertTrue(page.contains("You're to auth first"));
    }

    @Test
    public void authOkRenderPageTest() {
        Integer userID = random.nextInt();
      /* byte temp[] = new byte[40];
       String userName = random.nextBytes(temp);*/
        pageVariables.put("userID", userID.toString());
        pageVariables.put("message", " ");
        pageVariables.put("userName", "Homer");
        String page = PageGenerator.renderPage("timer.tml", pageVariables);
        Assert.assertTrue(page.contains(userID.toString()));
    }

}
