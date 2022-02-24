package simulator.model;


import java.util.List;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	protected RoadMap roadMap;
	protected List<Event> eventList; //ordenada por el tiempo de los eventos
	protected int time;
	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>();
		time = 0;
		
	}
	
	public void addEvent(Event e) {
		//TODO hay que ordenar la lista pero nom se donde/como
		eventList.add(e);
	}
	
	public void advance() {
		time++;

		for(int i = 0; i < eventList.size(); i++) {
			if(time == eventList.get(i).getTime()) {
				eventList.get(i).execute(roadMap);
				eventList.remove(i);
			}
		}
		
		for(int i = 0; i < roadMap.getJunctions().size() ; i++) {
			roadMap.junctionList.get(i).advance(time);
		}
		
		for(int i = 0; i < roadMap.getRoads().size() ; i++) {
			roadMap.roadList.get(i).advance(time);
		}
	
	}
	
	public void reset() {
		roadMap.reset();
		eventList.clear();
		time = 0;
		
	}
	
	public JSONObject report() {
		JSONObject obj = new JSONObject();
	
		obj.put("time", time);
		obj.put("state", roadMap.report());
		
		return obj;
	}
	

}