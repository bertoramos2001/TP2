package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		if (q.isEmpty()) {
			return null;
		}
		vehicleList.add(q.get(0));
		
		return vehicleList;
	}

}
