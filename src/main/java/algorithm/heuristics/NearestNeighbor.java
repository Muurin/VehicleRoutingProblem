package algorithm.heuristics;

import Instances.Instance;
import Model.Location;
import Model.Vehicle;
import algorithm.paths.Route;
import algorithm.solution.SolutionContext;
import util.SolutionUtil;
import util.VehicleUtil;

/**
 * Customers are visited only once and are not used for traversal to other locations
 */
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

	public static SolutionContext start(Instance instance) {

		SolutionContext solutionContext = new SolutionContext(instance);
		while (SolutionUtil.customersInNeedOfService(solutionContext)) {

			boolean returnToDepot = false;
			Vehicle currentVehicle = solutionContext.addVehicle();
			VehicleUtil.initializeDepot(solutionContext, currentVehicle);

			Location nextLocation = SolutionUtil.getRandomLocation(solutionContext.getCustomers().values());

			//vehicle can't reach nearest customer from depot - return null?
			//TODO heuristic for calculating moves in advance, using charging stations as intermediates
			if (!SolutionUtil.hasEnoughFuelToVisit(currentVehicle, nextLocation)) {
				return null;
			}

			while (!returnToDepot) {


				//vehicle doesn't have enough fuel to visit customer, needs refueling
				if (!SolutionUtil.hasEnoughFuelToVisit(currentVehicle, nextLocation)) {
					Location nearestChargingStation = SolutionUtil.findNearestLocationFrom(solutionContext.getChargingStations().values(), currentVehicle.getVehiclePath().peek().getDestinationLocation());
					//vehicle doesn't have enough fuel to visit nearest charging station
					if (!SolutionUtil.hasEnoughFuelToVisit(currentVehicle, nearestChargingStation)) {
						//backtrace
						currentVehicle.removeLastRoute();
						continue;
					}
					else{
						currentVehicle.addRoute(Route.builder().build());
					}
				}
				else{
					SolutionUtil.serviceCustomer(solutionContext,nextLocation,currentVehicle);
				}

			}

			if (!SolutionUtil.customersInNeedOfService(solutionContext)) {
				returnToDepot = true;
			}

			//return to depot
			//TODO heuristic for returning to the depot with refueling?
		}


		return solutionContext;
	}


}
