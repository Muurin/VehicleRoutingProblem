package algorithm.solution.evaluators;

import model.Vehicle;
import algorithm.solution.SolutionContext;

public class SimpleDistanceEvaluator implements SolutionEvaluator{

	@Override
	public double evaluate(SolutionContext solution) {
		double score=0;
		for(Vehicle vehicle:solution.getVehicles().values()){
			score+=vehicle.getCurrentDistance();
		}
		return score;
	}
}
