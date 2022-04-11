package simulator.view;

import java.awt.Dimension;
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
	private JLabel eventLabel = new JLabel();

	StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(statusPanel);
		
	
		showTime(0);
		statusPanel.add(timeLabel);
		statusPanel.add(Box.createRigidArea(new Dimension(125, 25)));
		statusPanel.add(new JSeparator(SwingConstants.VERTICAL));
		showEvent(null);
		statusPanel.add(eventLabel);
		

	}
	
	private void showTime(int time) {
		timeLabel.setText("Time: "+time);
	}
	
	private void showEvent(Event e) {
		if (e != null) {
			eventLabel.setText("Event added "+e.toString());
		} else {
			eventLabel.setText("");
		}
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		showTime(time);
		showEvent(null);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		showEvent(e);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
