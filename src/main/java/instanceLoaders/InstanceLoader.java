package instanceLoaders;

import instances.Instance;
import org.jdom2.JDOMException;

import java.io.IOException;

public interface InstanceLoader {

	Instance load(String path) throws IOException, JDOMException;
}
