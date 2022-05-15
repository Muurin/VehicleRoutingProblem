package algorithm.nearestNeighbor;

import instances.Instance;
import instances.Properties.VehiclePropertyType;
import model.Location;
import model.Vehicle;
import solution.SolutionContext;
import solution.SolutionContextFactory;
import util.PropertiesUtil;
import util.SolutionUtil;
import util.VehicleUtil;

/**
 * Customers are visited only once and are not used for traversal to other locations
 */
public class NearestNeighbor {

	private final boolean log;

	public NearestNeighbor(boolean log){
		this.log=log;
	}

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
	public SolutionContext start(Instance instance) throws InterruptedException {
		SolutionContextFactory solutionContextFactory = new SolutionContextFactory(instance);
		SolutionContext solutionContext = solutionContextFactory.createSolutionContext();

			while (SolutionUtil.anyCustomersInNeedOfService(solutionContext)) {

				Vehicle currentVehicle = solutionContext.addVehicle();
				Location depot= solutionContext.getDepots().get(PropertiesUtil.getStringPropertyValue(currentVehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));

				Location nextLocation = SolutionUtil.getRandomLocation(solutionContext.getCustomers().values());
				//service customers

				while(!nextLocation.equals(depot)){

					if(!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle,nextLocation)){
						nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,nextLocation);
					}
					currentVehicle.travelTo(nextLocation);

					nextLocation = VehicleUtil.getClosestReachableAndServiceableCustomer(currentVehicle,solutionContext);

					if(nextLocation!=null){
						nextLocation= SolutionUtil.findNearestLocationFromNotVisited(solutionContext.getCustomers().values(),currentVehicle.getCurrentLocation(), solutionContext.getServicedCustomers());
					}
					else{
						nextLocation=depot;
					}
				}

				//return to depot
				while(!currentVehicle.getCurrentLocation().equals(depot)){

					if(!VehicleUtil.canReachLocation(currentVehicle,depot)){

						nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,depot);
					}else {
						nextLocation=depot;
					}
					currentVehicle.travelTo(nextLocation);
				}
			}

		return solutionContext;
	}

}
