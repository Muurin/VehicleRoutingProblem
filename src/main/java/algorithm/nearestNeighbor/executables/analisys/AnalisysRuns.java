package algorithm.nearestNeighbor.executables.analisys;

import algorithm.geneticAlgorithm.executables.analisys.EvaluatorInfo;
import algorithm.nearestNeighbor.NearestNeighbor;
import instanceLoaders.EVRP_CTWInstanceLoader;
import instanceLoaders.InstanceLoader;
import instances.Instance;
import org.jdom2.JDOMException;
import solution.SolutionContext;
import solution.VRPInstancePaths;
import solution.evaluators.SimpleDistanceEvaluator;
import solution.evaluators.SimpleTimeEvaluator;
import solution.evaluators.SolutionEvaluator;
import solution.evaluators.TimeWindowEvaluator;
import util.FileWriterUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static algorithm.geneticAlgorithm.executables.preanalisys.Constants.gaAnalisysPath;
import static algorithm.geneticAlgorithm.executables.preanalisys.Constants.instancesPath;

public class AnalisysRuns {


    private static String[] criteria = {"DISTANCE","TIME", "TIME_WINDOWS"};

    public static void main(String[] args) throws IOException, JDOMException {

        String pathToInstances = instancesPath;
        String[] filenames = new File(pathToInstances).list();
        InstanceLoader instanceLoader = new EVRP_CTWInstanceLoader();
        SolutionEvaluator solutionEvaluator1 = new SimpleDistanceEvaluator();
        SolutionEvaluator solutionEvaluator2 = new SimpleTimeEvaluator();
        SolutionEvaluator solutionEvaluator3 = new TimeWindowEvaluator();


        for (String filename : filenames) {
            if (filename.startsWith("read")) {
                continue;
            }
            for (int i = 0; i < 10; i++) {
                Instance instance = instanceLoader.load(instancesPath + "/" + filename);

                SolutionContext solutionContext = new NearestNeighbor().start(instance);
                FileWriterUtil.writeToNewOrExistingFile(gaAnalisysPath + "/" + "NN", criteria[0] + i, String.valueOf(solutionEvaluator1.evaluate(solutionContext)));
                FileWriterUtil.writeToNewOrExistingFile(gaAnalisysPath + "/" + "NN", criteria[1] + i, String.valueOf(solutionEvaluator2.evaluate(solutionContext)));
                FileWriterUtil.writeToNewOrExistingFile(gaAnalisysPath + "/" + "NN", criteria[2] + i, String.valueOf(solutionEvaluator3.evaluate(solutionContext)));

            }


        }

    }
    }
