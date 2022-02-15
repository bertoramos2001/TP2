package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	private int timeSlot;
	//TODO: no se si este constructor es package protected o no (en el enunciado no lo pone)
	RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		if (roads.isEmpty()) {
			return -1;
		}
		
		if (currGreen == -1) {
			return 0;
		}
		
		if ((currTime - lastSwitchingTime) < timeSlot) {
			return currGreen;
		}
		
		return ((currGreen + 1) % roads.size());
	}

}
