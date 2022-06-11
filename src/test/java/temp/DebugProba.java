package temp;

import algorithm.geneticAlgorithm.executables.preanalisys.Constants;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instances.Instance;
import model.Location;
import model.Vehicle;
import model.paths.Route;
import solution.SolutionContext;
import solution.SolutionContextFactory;
import util.FileWriterUtil;
import util.MathUtil;
import util.SolutionUtil;
import util.VehicleUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class DebugProba {

    public static void main(String[] args) throws IOException {

        Collection<String> resultingFiles = new HashSet<>(Arrays.asList(new File(Constants.gaAnalisysPath).list()));

        Arrays.stream(new File(Constants.instancesPath).list()).filter(s -> !resultingFiles.contains(s)).forEach(s -> System.out.println(s));

    }

}

