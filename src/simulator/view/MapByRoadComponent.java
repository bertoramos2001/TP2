package simulator.view;

import java.awt.*;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private Image car;
	private RoadMap map;
	
	MapByRoadComponent(Controller ctrl) {
		this.setPreferredSize(new Dimension(300, 200));
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		car = loadImage("car.png");
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if (map == null || map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			//updatePrefferedSize(); TODO: esto se hace? me dice que ponga el setPreferred en el constructor
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
		drawWeather();
		drawContClass();
	}
	
	private void drawRoads(Graphics g) {
		for (Road r : map.getRoads()) {
			// the road goes from (x1,y) to (x2,y)
			int x1 = r.getSrc().getX();
			int x2 = r.getDest().getX();
			int y = r.getSrc().getY();
			
			// choose a color for the junction depending on the traffic light of the road
			Color destJunctColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				destJunctColor = _GREEN_LIGHT_COLOR;
			}
			
			// draw a blue colored circle at the beginning of the road (the source junction)
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			// draw a red or green colored circle at the end of the road (the dest junction)
			g.setColor(destJunctColor);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			g.drawLine(x1, y, x2, y);
		}
	}
	
	private void drawVehicles(Graphics g) {
		
	}
	
	private void drawWeather() {
		
	}
	
	private void drawContClass() {
		
	}
	
	// loads an image from a file
		private Image loadImage(String img) {
			Image i = null;
			try {
				return ImageIO.read(new File("resources/icons/" + img));
			} catch (IOException e) {
			}
			return i;
		}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
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
