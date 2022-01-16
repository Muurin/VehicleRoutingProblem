package algorithm.solution.executables;

import InstanceLoaders.EVRP_CTWInstanceLoader;
import InstanceLoaders.InstanceLoader;
import Instances.Instance;
import algorithm.heuristics.NearestNeighbor;
import algorithm.solution.SolutionContext;
import algorithm.solution.VRPInstancePaths;
import algorithm.solution.evaluators.SimpleDistanceEvaluator;
import algorithm.solution.evaluators.SolutionEvaluator;
import org.jdom2.JDOMException;

import java.io.IOException;

public class EVRP_CTW_NearestNeighbor {

	public static void main(String[] args) throws IOException, JDOMException, InterruptedException {

		InstanceLoader instanceLoader = new EVRP_CTWInstanceLoader();
		Instance instance = instanceLoader.load(VRPInstancePaths.getEVRP_CTWPath1());
		SolutionContext solutionContext = NearestNeighbor.start(instance);
		SolutionEvaluator solutionEvaluator = new SimpleDistanceEvaluator();
		double score = solutionEvaluator.evaluate(solutionContext);
		System.out.println(score);


	}

}
