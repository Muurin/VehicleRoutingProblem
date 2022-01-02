package algorithm.heuristics;

import Instances.Instance;
import algorithm.solution.SolutionContext;

public class NearestNeighbor {

	/*
	 *Izaberi random depot
	 *  ponavljaj
	 *      ako sam potrosio capacity ili ako vise nema customera postavi zastavicu za vracanje u depot
	 *      ako je zastavica postavljena izaberi depot
	 *      ako ne izaberi sljedeceg najblizeg customera
	 *      provjeri uvjet- imam li dovoljno baterije da odem do najbliže postaje nakon customera
	 *      ako ne izaberi najblizu postaju za punjenje
	 *
	 *Problem ako su neki customeri predaleko pa auti zapnu, pretpostavka da toga nema?
	 */

	public static Object start(Instance instance){

		SolutionContext solutionContext= new SolutionContext(instance);




		return null;
	}


}
