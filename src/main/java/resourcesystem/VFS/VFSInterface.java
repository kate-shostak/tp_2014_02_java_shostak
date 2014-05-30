package resourcesystem.VFS;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by kate on 25.05.14.
 */
public interface VFSInterface {
    boolean isExist(String path);
    boolean isDirectory(String path);
    String getAbsolutePath(String file);
    byte[] getBytes(String file) throws IOException;
    String getUFT8Text(String file) throws IOException;
    Iterator<String> getIterator(String startDir);

}
