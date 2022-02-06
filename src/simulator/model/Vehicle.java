package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int actualSpeed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int totalCont;
	private int totalDistance;
	
	
	public Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
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
		
		//TODO ver si lo de itinerary esta bien (y ver si hay que quitar los imports de collections y arraylist)
		itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		
		this.maxSpeed = maxSpeed;
		status = VehicleStatus.PENDING;
		road = null;
		location = 0;
		this.contClass = contClass;
		totalCont = 0;
		totalDistance = 0;
	}
	
	public void setSpeed(int s) {
		if (s < 0) {
			throw new IllegalArgumentException("actual speed must not be negative");
		}
		
		actualSpeed = Math.min(s, maxSpeed);
	}
	
	public void setContaminationClass(int c) {
		if ((c < 0) || (c > 10)) {
			throw new IllegalArgumentException("contamination class must be between 0 and 10");
		}
		
		contClass = c;
	}

	@Override
	public void advance(int time) {
		if (status == VehicleStatus.TRAVELING) {
			int oldLocation = location;
			location = Math.min(location + actualSpeed, road.getLength());
			
			int advancedDistance = location - oldLocation;
			
			totalDistance += advancedDistance;
			totalCont = totalDistance * contClass;
			
			int contaminationThisStep = contClass * advancedDistance;
			road.addContamination(contaminationThisStep);
			
			if (location >= road.getLength()) {
				//TODO: modificar estado del vehiculo y meterlo en la cola del cruce correspondiente
			}
		}
	}
	
	public void moveToNextRoad() {
		if ((status != VehicleStatus.PENDING) && (status != VehicleStatus.WAITING)) {
			throw new IllegalArgumentException("Vehicle must be waitint in a junction or pending to enter a road");
		}
		
		if (status == VehicleStatus.PENDING) {
			//TODO: buscar la carretera a la que ha de ir (aun no esta en ninguna)
		}
		
		if (status == VehicleStatus.WAITING) {
			//TODO: buscar la carretera a la que ha de ir (ver si esta en el ultimo cruce de su itinerario)
		}
		
		//TODO: modificar el estado del vehiculo
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










