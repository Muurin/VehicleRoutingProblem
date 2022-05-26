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

import java.io.IOException;

public class DebugProba {

    public static void main(String[] args) throws IOException {

        FileWriterUtil.writeToNewOrExistingFile("/Users/marinovcaricek/Diplomski rad/Results/preanalisys/population_tuning/Popsize_50","0","adanfo;abf;angao'");
    }

}

