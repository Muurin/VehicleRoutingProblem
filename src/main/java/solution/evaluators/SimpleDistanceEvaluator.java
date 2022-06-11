package solution.evaluators;

import model.Vehicle;
import solution.SolutionContext;

public class SimpleDistanceEvaluator implements SolutionEvaluator{

	@Override
	public double evaluate(SolutionContext solution) {
		double score=0;
		for(Vehicle vehicle:solution.getVehicles().values()){
			score+=vehicle.getCurrentDistance();
		}
		solution.reset();
		return score;
	}
}
