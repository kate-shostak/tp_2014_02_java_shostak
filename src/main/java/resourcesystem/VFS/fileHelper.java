package resourcesystem.VFS;

import java.io.*;

/**
 * Created by kate on 25.05.14.
 */
public class fileHelper {

    public static byte[] readBytesFromFile(String file) throws IOException{
        FileInputStream fs = new FileInputStream(file);
        DataInputStream ds = new DataInputStream(fs);
        byte[] bytes = new byte[ds.available()];
        ds.read(bytes);
        ds.close();
        return bytes;
    }

    public static String readTextFromFile(String file) throws IOException{
        FileInputStream fs = new FileInputStream(file);
        BufferedInputStream bs = new BufferedInputStream(fs);
        DataInputStream ds = new DataInputStream(bs);
        InputStreamReader sr = new InputStreamReader(ds, "UTF-8");
        BufferedReader br = new BufferedReader(sr);
        StringBuilder lines = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)   {
            lines.append(line);
        }
        br.close();
        return lines.toString();
    }


}
