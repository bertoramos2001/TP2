package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel timeLabel = new JLabel();
	private JPanel statusPanel = new JPanel();

	StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(statusPanel);
	
		showTime(0);
		statusPanel.add(timeLabel);
		
		//TODO: falta poner el get time y el getEvent

	}
	
	private void showTime(int time) {
		timeLabel.setText("Time: "+time);
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		showTime(time);
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		showTime(time);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		showTime(time);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		showTime(time);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		showTime(time);
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
