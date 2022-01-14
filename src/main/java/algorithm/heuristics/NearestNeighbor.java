package algorithm.heuristics;

import Instances.Instance;
import Instances.Properties.VehiclePropertyType;
import Model.Location;
import Model.Vehicle;
import algorithm.solution.SolutionContext;
import util.PropertiesUtil;
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
		while (SolutionUtil.anyCustomersInNeedOfService(solutionContext)) {

			Vehicle currentVehicle = solutionContext.addVehicle();
			VehicleUtil.initializeDepot(currentVehicle);
			Location depot= solutionContext.getDepots().get(PropertiesUtil.getStringPropertyValue(currentVehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));

			Location nextLocation = SolutionUtil.getRandomLocation(solutionContext.getCustomers().values());

			//service customers
			while(!nextLocation.equals(depot)){

				if(!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle,nextLocation)){
					nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,nextLocation);
				}

				currentVehicle.travelTo(nextLocation);

				if(VehicleUtil.canServiceAtLeastOneCustomer(currentVehicle)){
					nextLocation= SolutionUtil.findNearestLocationFrom(solutionContext.getCustomers().values(),currentVehicle.getCurrentLocation());
				}
				else{
					nextLocation=depot;
				}

			}
			//return to depot
			while(!currentVehicle.getCurrentLocation().equals(depot)){
				if(!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle,depot)){
					nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,depot);
				}
				currentVehicle.travelTo(nextLocation);
			}
		}

		return solutionContext;
	}


}
