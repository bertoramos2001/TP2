package simulator.model;

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
	
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("the max speed must be positive");
		}
		
		if ((contClass < 0) || (contClass > 10)) {
			throw new IllegalArgumentException("contamination class must be between 0 and 10");
		}
		
		// TODO itinerary y inicializar atributos
		
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
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
			int contaminationThisStep = contClass * advancedDistance;;
			
			totalCont += contaminationThisStep;
			road.addContamination(contaminationThisStep);
			
			if (location >= road.getLength()) {
				//TODO: modificar estado del vehiculo y meterlo en la cola del cruce correspondiente
			}
		}
	}
	
	void moveToNextRoad() {
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
		
		return null;
	}

}










