package temp;

import algorithm.geneticAlgorithm.executables.preanalisys.Constants;
import algorithm.nearestNeighbor.NearestNeighbor;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instances.Instance;
import solution.SolutionContext;
import solution.evaluators.SimpleDistanceEvaluator;
import solution.evaluators.SolutionEvaluator;
import solution.evaluators.TimeWindowEvaluator;
import util.ComputerPaths;

import java.io.File;
import java.io.IOException;

public class Proba4 {

    public static void main(String[] args) throws IOException {

        String pathToInstances = ComputerPaths.pathToInstances;
        String[] filenames = new File(pathToInstances).list();

        for (String filename : filenames) {
            System.out.println(filename);
            Instance instance = new EVRP_CTWInstanceLoader().load(pathToInstances + "/" + filename);
            SolutionContext solutionContext = new NearestNeighbor().start(instance);
            SolutionEvaluator solutionEvaluator = new SimpleDistanceEvaluator();//new SimpleDistanceEvaluator();
            double score = solutionEvaluator.evaluate(solutionContext);
            System.out.println(score);
        }




    }
}
