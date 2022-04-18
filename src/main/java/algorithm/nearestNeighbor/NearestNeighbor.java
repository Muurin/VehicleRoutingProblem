package algorithm.nearestNeighbor;

import instances.Instance;
import instances.Properties.VehiclePropertyType;
import model.Location;
import model.Vehicle;
import algorithm.solution.SolutionContext;
import algorithm.solution.SolutionContextFactory;
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
	//TODO discover the source of cyclic behviour of vehicle traveling between two customers- shouldn't ever happen (happened for C15 and C16 with visit to C7 every now and then)
	public SolutionContext start(Instance instance) throws InterruptedException {
		SolutionContextFactory solutionContextFactory = new SolutionContextFactory(instance);
		SolutionContext solutionContext = solutionContextFactory.createSolutionContext();

		for(Location startLocation : solutionContext.getCustomers().values()){
			solutionContext.reset();
			System.out.println("Pocetna lokacija"+startLocation.getId());
			while (SolutionUtil.anyCustomersInNeedOfService(solutionContext)) {

				Vehicle currentVehicle = solutionContext.addVehicle();
				VehicleUtil.initializeDepot(currentVehicle);
				Location depot= solutionContext.getDepots().get(PropertiesUtil.getStringPropertyValue(currentVehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));
				log("Novi auto");
				log("Posluzeno je " +solutionContext.getServicedCustomers().size() +" customera");
//				Location nextLocation = SolutionUtil.getRandomLocation(solutionContext.getCustomers().values());
				Location nextLocation = startLocation;
				//service customers
				while(!nextLocation.equals(depot)){

					if(!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle,nextLocation)){
						nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,nextLocation);
					}
	//				log(currentVehicle.getCurrentFuel());
					log("Prema " + nextLocation.getId());
					currentVehicle.travelTo(nextLocation);

					nextLocation = VehicleUtil.getClosestReachableAndServiceableCustomer(currentVehicle,solutionContext);

					if(nextLocation!=null){
						nextLocation= SolutionUtil.findNearestLocationFromNotVisited(solutionContext.getCustomers().values(),currentVehicle.getCurrentLocation(), solutionContext.getServicedCustomers());
					}
					else{
						nextLocation=depot;
					}

				}
				log("Vraćanje doma");

				//return to depot
				while(!currentVehicle.getCurrentLocation().equals(depot)){

					log("Prema depotu");
					if(!VehicleUtil.canReachLocation(currentVehicle,depot)){

						nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,depot);
					}else {
						nextLocation=depot;
					}
					Thread.sleep(1000);
					log("Next loc je"+nextLocation.getCartesianCoordinates());
					log("Do next loc mi treba " + VehicleUtil.requiredFuel(currentVehicle,currentVehicle.getCurrentLocation(),nextLocation)+" a imam "+ currentVehicle.getCurrentFuel());
					currentVehicle.travelTo(nextLocation);
				}
			}
//			System.out.println("Gotova iteracija");
		}
		System.out.println("Gotovo");
		return solutionContext;
	}

	private void log(String message){
		if(log)System.out.println(message);
	}

}
