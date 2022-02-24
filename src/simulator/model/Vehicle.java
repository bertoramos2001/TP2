package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	
	protected List<Junction> itinerary;
	protected int maxSpeed;
	protected int actualSpeed;
	protected VehicleStatus status;
	protected Road road;
	protected int location;
	protected int contClass;
	protected int totalCont;
	protected int totalDistance;
	protected int actualJuntion;
	
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("the max speed must be positive");
		}
		
		if ((contClass < 0) || (contClass > 10)) {
			throw new IllegalArgumentException("contamination class must be between 0 and 10");
		}
		
		if (itinerary.size() < 2) {
			throw new IllegalArgumentException("itinerary size must be at least 2");
		}
		
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.maxSpeed = maxSpeed;
		status = VehicleStatus.PENDING;
		road = null;
		location = 0;
		this.contClass = contClass;
		totalCont = 0;
		totalDistance = 0;
	}
	
	void setSpeed(int s) {
		if (s < 0) {
			throw new IllegalArgumentException("actual speed must not be negative");
		}
		
		actualSpeed = Math.min(s, maxSpeed);
	}
	
	void setContaminationClass(int c) {
		if ((c < 0) || (c > 10)) {
			throw new IllegalArgumentException("contamination class must be between 0 and 10");
		}
		
		contClass = c;
	}

	@Override
	void advance(int time) {
		if (status == VehicleStatus.TRAVELING) {
			int oldLocation = location;
			location = Math.min(location + actualSpeed, road.getLength());
			
			int advancedDistance = location - oldLocation;
			
			totalDistance += advancedDistance;
			totalCont = totalDistance * contClass;
			
			int contaminationThisStep = contClass * advancedDistance;
			road.addContamination(contaminationThisStep);
			
			if (location >= road.getLength()) {
				
				actualSpeed = 0;
				status = VehicleStatus.TRAVELING;
				road.destination.enter(this);
				
			}
		}
	}
	
	void moveToNextRoad() {
		if ((status != VehicleStatus.PENDING) && (status != VehicleStatus.WAITING)) {
			throw new IllegalArgumentException("Vehicle must be waitint in a junction or pending to enter a road");
		}
		
		location = 0;
		actualSpeed = 0;
		
		if (status == VehicleStatus.PENDING) {
			road = itinerary.get(0).roadTo(itinerary.get(0));
			status = VehicleStatus.TRAVELING;
			actualJuntion = 1;
			road.enter(this);
		}
		
		if (status == VehicleStatus.WAITING) {
			if(actualJuntion == itinerary.size()) {
				road.exit(this);
				status = VehicleStatus.ARRIVED;	
			}
			else {
				road.exit(this);
				road = road.destination.roadTo(itinerary.get(actualJuntion));
				actualJuntion++;
				road.enter(this);
				status = VehicleStatus.TRAVELING;	
			}
		}
		//TODO: modificar el estado del vehiculo (creo que es lo quye esta arriba)
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		
		obj.put("id", _id);
		obj.put("speed", actualSpeed);
		obj.put("distance", totalDistance);
		obj.put("co2", totalCont);
		obj.put("class", contClass);
		obj.put("status", status.toString());
		
		if (status != VehicleStatus.PENDING && status != VehicleStatus.ARRIVED ) {
			obj.put("road", road.getId());
			obj.put("location", location);
		}
		return obj;
	}

	public List<Junction> getItinerary() {
		return itinerary;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getSpeed() {
		return actualSpeed;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public Road getRoad() {
		return road;
	}

	public int getLocation() {
		return location;
	}

	public int getContClass() {
		return contClass;
	}

	public int getTotalCO2() {
		return totalCont;
	}
}










