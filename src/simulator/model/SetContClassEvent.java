package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	private List<Pair<String, Integer>> cs;

	public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		
		if (cs == null) {
			throw new IllegalArgumentException("contamination-road list cannot be null");
		}
		
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> c : cs) {
			Vehicle v = map.getVehicle(c.getFirst());
			
			if (v == null) {
				throw new IllegalArgumentException("Vehicle does not exist in map");
			}
		
			v.setContClass(c.getSecond());
		}
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		if (cs.size() > 1) {
			str.append("Change Cont Class: [");
			
			for (int i = 0; i < cs.size() - 1; i++) {
				str.append("(" + cs.get(i).getFirst() + ", " + cs.get(i).getSecond() + "), ");
			}
			
			str.append("(" + cs.get(cs.size() - 1).getFirst() + ", " + cs.get(cs.size() - 1).getSecond() + ")");
			
			str.append("]");
		} else {
			str.append("(" + cs.get(0).getFirst() + ", " + cs.get(0).getSecond() + ")");
		}
		
		
		return str.toString();
	}

}
