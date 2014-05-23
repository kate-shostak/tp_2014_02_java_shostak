package templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.io.Writer;

/**
 * Created by kate on 17.03.14.
 */
public class PageGenerator {
    private static String HTML_DIR = "tml";
    private static final Configuration cfg = new Configuration();

    public static String renderPage(String filename, Map<String, Object> pagevar) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);

            template.process(pagevar, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }
}
