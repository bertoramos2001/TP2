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
		//TODO: pon en verde el semaforo con la cola mas larga DESDE currGreen + 1
		return 0;
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

}
