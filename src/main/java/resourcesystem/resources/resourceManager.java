package resourcesystem.resources;

import java.util.HashMap;
import java.util.Map;

import static resourcesystem.ReadXMLFile.readXML;

/**
 * Created by kate on 25.05.14.
 */
public class resourceManager {
    private static resourceManager instance;
    private Map<String, Resource> resources = new HashMap<>();

    private resourceManager(){
    }

    public static resourceManager getInstance(){
        if (instance == null){
            instance = new resourceManager();
        }
        return instance;
    }

    public Resource getResource(String path) {
        Resource res = instance.resources.get(path);
        if (res != null)
            return res;
        addResource(path, get(path));
        return getResource(path);
    }

    public void addResource(String path, Resource resource){
        instance.resources.put(path, resource);
    }

    public Resource get(String path){
        return (Resource)readXML(path);
    }
}
