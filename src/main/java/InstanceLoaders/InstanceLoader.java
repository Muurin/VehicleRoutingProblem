package InstanceLoaders;

import Instances.Instance;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface InstanceLoader {

	Instance load(String path) throws IOException;
}
