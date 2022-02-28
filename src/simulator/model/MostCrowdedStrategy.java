package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		if (roads.isEmpty()) {
			return -1;
		}
		
		if (currGreen == -1) {
			return getFirstLongestQueue(qs);
		}
		
		if ((currTime - lastSwitchingTime) < timeSlot) {
			return currGreen;
		}

		return getLongestQueueCircular(qs, currGreen);
	}
	
	private int getFirstLongestQueue(List<List<Vehicle>> qs) {
		int currInd = 0, maxQueueLength = 0; //inicializamos el tamño máximo de la lista a 0
		
		for (int i = 0; i < qs.size(); i++) {
			if (qs.get(i).size() > maxQueueLength) {
				currInd = i;
				maxQueueLength = qs.get(i).size();
			}
		}
		return currInd;
	}
	
	private int getLongestQueueCircular(List<List<Vehicle>> qs, int currGreen) {
		int i = currGreen + 1, currInd = currGreen + 1, maxQueueLength = 0;
		boolean found = false;
		
		while (i != currGreen && !found) {
			if (qs.get(i).size() > maxQueueLength) {
				currInd = i;
				maxQueueLength = qs.get(i).size();
			}
			i = (i + 1) % qs.size();
		}
		
		if (qs.get(currGreen).size() > maxQueueLength) {
			currInd = currGreen;
			maxQueueLength = qs.get(currGreen).size();
		}
		
		
		return currInd;
	}

}
