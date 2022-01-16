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
	//TODO discover the source of cyclic behviour of vehicle traveling between two customers- shouldn't ever happen (happened for C15 and C16 with visit to C7 every now and then)
	public static SolutionContext start(Instance instance) throws InterruptedException {

		SolutionContext solutionContext = new SolutionContext(instance);
		while (SolutionUtil.anyCustomersInNeedOfService(solutionContext)) {

			Vehicle currentVehicle = solutionContext.addVehicle();
			VehicleUtil.initializeDepot(currentVehicle);
			Location depot= solutionContext.getDepots().get(PropertiesUtil.getStringPropertyValue(currentVehicle.getVehiclePropertyMap().get(VehiclePropertyType.ARRIVAL_LOCATION)));
			System.out.println("Novi auto");
			System.out.println("Ostalo je" +solutionContext.getCustomers().size()+" customera");
			Location nextLocation = SolutionUtil.getRandomLocation(solutionContext.getCustomers().values());

			//service customers
			while(!nextLocation.equals(depot)){


				if(!VehicleUtil.canReachLocationAndNearestChargingStation(currentVehicle,nextLocation)){
					nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,nextLocation);
				}
//				System.out.println(currentVehicle.getCurrentFuel());
				System.out.println("Prema " + nextLocation.getId());
				currentVehicle.travelTo(nextLocation);

				nextLocation = VehicleUtil.getClosestReachableAndServiceableCustomer(currentVehicle,solutionContext);

				if(nextLocation!=null){
					nextLocation= SolutionUtil.findNearestLocationFrom(solutionContext.getCustomers().values(),currentVehicle.getCurrentLocation());
				}
				else{
					nextLocation=depot;
				}

			}
			System.out.println("Vraćanje doma");

			//return to depot
			while(!currentVehicle.getCurrentLocation().equals(depot)){

				System.out.println("Prema depotu");
				if(!VehicleUtil.canReachLocation(currentVehicle,depot)){

					nextLocation=VehicleUtil.chooseIntermediateChargingStation(currentVehicle,depot);
				}else {
					nextLocation=depot;
				}
				Thread.sleep(1000);
				System.out.println("Next loc je"+nextLocation.getCartesianCoordinates());
				System.out.println("Do next loc mi treba " + VehicleUtil.requiredFuel(currentVehicle,currentVehicle.getCurrentLocation(),nextLocation)+" a imam "+ currentVehicle.getCurrentFuel());
				currentVehicle.travelTo(nextLocation);
			}
		}
		System.out.println("Gotovo");
		return solutionContext;
	}


}
