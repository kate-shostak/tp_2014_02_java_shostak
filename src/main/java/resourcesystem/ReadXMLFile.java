package resourcesystem;

/**
 * Created by kate on 25.05.14.
 */
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFile {
    public static Object readXML(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlFile, handler);
            return handler.getObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}