package simulator.view;

import java.awt.*;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

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
	private static final Color _ROAD_COLOR = Color.BLACK;
	
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
	}
	
	private void drawRoads(Graphics g) {
		for (int i = 0; i < map.getRoads().size(); i++) {
			// the road goes from (x1,y) to (x2,y)
			int x1 = 50;
			int x2 = getWidth() - 100;
			int y = (i+1) * 50;
			Road actualRoad = map.getRoads().get(i);
			
			// choose a color for the junction depending on the traffic light of the road
			Color destJunctColor = _RED_LIGHT_COLOR;
			int idx = actualRoad.getDest().getGreenLightIndex();
			if (idx != -1 && actualRoad.equals(actualRoad.getDest().getInRoads().get(idx))) {
				destJunctColor = _GREEN_LIGHT_COLOR;
			}
			
			// draw the lines representing the roads
			g.setColor(_ROAD_COLOR);
			g.drawString(actualRoad.getId(), x1 - 30, y + 5);
			g.drawLine(x1, y, x2, y);
			
			// draw a blue colored circle at the beginning of the road (the source junction)
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(actualRoad.getSrc().getId(), x1 - 5, y - 15);
			
			
			// draw a red or green colored circle at the end of the road (the dest junction)
			g.setColor(destJunctColor);
			g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(actualRoad.getDest().getId(), x2 - 5, y - 15);
			
			//draw the vehicles in the road
			drawVehicles(g, x1, x2, y, actualRoad);
			
			//draw weather icon depending on weather of the road
			Image weatherImg;
			switch(actualRoad.getWeather()) {
				case SUNNY: {
					weatherImg = loadImage("sun.png");
				}
				break;
				case CLOUDY: {
					weatherImg = loadImage("cloud.png");
				}
				break;
				case RAINY: {
					weatherImg = loadImage("rain.png");
				}
				break;
				case WINDY: {
					weatherImg = loadImage("wind.png");
				}
				break;
				default: {
					weatherImg = loadImage("storm.png");
				}
				
			}
			g.drawImage(weatherImg, x2 + 10, y - 15, 32, 32, this);
			
			//draw contamination icon depending on the current contamination of the road
			int c = (int) Math.floor(Math.min((double) actualRoad.getTotalCO2()/(1.0 + (double) actualRoad.getContLimit()),1.0) / 0.19);
			Image contImg = loadImage("cont_"+c+".png");
			g.drawImage(contImg, x2 + 47, y - 15, 32, 32, this);
			
		}
	}
	
	private void drawVehicles(Graphics g, int x1, int x2, int y, Road road) {
		//paint all the vehicles in a road
		for (Vehicle v : road.getVehicles()) {
			int x = x1 + (int)((x2 - x1) *((double) v.getLocation() / (double) road.getLength()));
			g.drawImage(car, x, y - 10, 16, 16, this);
			g.drawString(v.getId(), x, y - 15);
		}
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
		
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			this.map = map;
			repaint();
		});
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
