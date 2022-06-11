package algorithm.nearestNeighbor.executables;

import instanceLoaders.EVRP_CTWInstanceLoader;
import instanceLoaders.InstanceLoader;
import instances.Instance;
import algorithm.nearestNeighbor.NearestNeighbor;
import solution.SolutionContext;
import solution.VRPInstancePaths;
import solution.evaluators.SimpleDistanceEvaluator;
import solution.evaluators.SolutionEvaluator;
import org.jdom2.JDOMException;

import java.io.IOException;

public class EVRP_CTW_NearestNeighbor {

	public static void main(String[] args) throws IOException, JDOMException, InterruptedException {

		InstanceLoader instanceLoader = new EVRP_CTWInstanceLoader();
		Instance instance = instanceLoader.load(VRPInstancePaths.getEVRP_CTWPath2());

		SolutionContext solutionContext = new NearestNeighbor().start(instance);
		solutionContext.getVehicles().values().stream().forEach(vehicle -> vehicle.getVehiclePath().forEach(route ->System.out.println(route.getDestinationLocation())));
		SolutionEvaluator solutionEvaluator = new SimpleDistanceEvaluator();
		double score = solutionEvaluator.evaluate(solutionContext);
		System.out.println(score);

	}

}
