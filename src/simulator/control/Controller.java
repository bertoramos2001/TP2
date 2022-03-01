package simulator.control;

import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;

public class Controller {
	protected TrafficSimulator simulator;
	protected Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if (sim == null || eventsFactory == null) {
			throw new IllegalArgumentException("traffic simulator and events factory cannot be null");
		}
		
		this.simulator = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		//TODO: no se si este casting esta bien
		JSONArray arr = (JSONArray) jo.get("events");
		
		if (arr == null) {
			throw new IllegalArgumentException("input information is not valid");
		}
		
		for (int i = 0; i < arr.length(); i++) {
			//TODO: ver si estos pasos estan bien (bien accedido al array??)
			JSONObject obj = arr.getJSONObject(i);
			Event ev = eventsFactory.createInstance(obj);
			simulator.addEvent(ev);
		}
	}
	
	public void run(int n, OutputStream out) {
		//TODO: ver si esta todo bien con los arrays de objetos
		JSONObject jo, finalObj;
		JSONArray arr = new JSONArray();
		for (int i = 0; i < n; i++) {
			simulator.advance();
			jo = simulator.report();
			arr.put(jo);
		}
		
		finalObj = new JSONObject();
		finalObj.put("states", arr);
		
		PrintStream p = new PrintStream(out);
		p.println(finalObj.toString(3));
		
	}
	
	public void reset() {
		simulator.reset();
	}
}
