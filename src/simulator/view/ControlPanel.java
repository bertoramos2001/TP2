package simulator.view;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private boolean stopped;
	private RoadMap map;
	private int time;
	
	private JButton contClassButton;
	private JToolBar myToolBar;
	private JButton folderButton;
	private JButton weatherButton;
	

	ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		ctrl.addObserver(this);
		initGUI();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.map = map;
		this.time = time;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.map = map;
		this.time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.map = map;
		this.time = time;
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.map = map;
		this.time = time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.map = map;
		this.time = time;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	 private void initGUI() {
		this.setLayout(new BorderLayout());
		JPanel controlPanel = new JPanel(new BorderLayout());
		this.add(controlPanel);
		
		myToolBar = new JToolBar();
		controlPanel.add(myToolBar);
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./resources/examples"));
		
		folderButton = new JButton();
		folderButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/open.png")));
		folderButton.setToolTipText("Open a file");
		myToolBar.add(folderButton);
		//TODO: ver si esta gestión de errores ha de estar aqui
		folderButton.addActionListener((e) -> {try {
			loadFile(fc);
		} catch (IOException e1) {
			e1.printStackTrace();
		}});
		
		myToolBar.addSeparator();
		
		contClassButton = new JButton();
		contClassButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/co2class.png")));
		contClassButton.setToolTipText("Change CO2 Class of a Vehicle");
		myToolBar.add(contClassButton);
		contClassButton.addActionListener((e) -> {
			selectCont();
		});
		
		weatherButton = new JButton();
		weatherButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/weather.png")));
		weatherButton.setToolTipText("Change Weather of a Road");
		myToolBar.add(weatherButton);
		weatherButton.addActionListener((e) -> {
			selectWeather();
		});
		
		myToolBar.addSeparator();
		
		JSpinner contTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1)); //declaramos aqui el spinner para poder usarlo en run(), mas abajo lo añadimos al toolbar
		
		JButton playButton = new JButton();
		playButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/run.png")));
		playButton.setToolTipText("Run the simulator");
		myToolBar.add(playButton);
		playButton.addActionListener((e) -> {
			enableToolBar(false);
			stopped = false;
			run_sim((Integer)contTicks.getValue());
		});
		
		JButton stopButton = new JButton();
		stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/stop.png")));
		stopButton.setToolTipText("Stop the simulator");
		myToolBar.add(stopButton);
		stopButton.addActionListener((e) -> {
			stop();
		});
		//listener del boton
		
		JLabel ticks = new JLabel("Ticks");
		myToolBar.add(ticks);
		
		contTicks.setMaximumSize(new Dimension(50, 50));
		contTicks.setToolTipText("Simulation tick to run: 1-1000");
		myToolBar.add(contTicks);
		
		myToolBar.add(Box.createGlue());
		myToolBar.addSeparator();
		
		JButton exitButton = new JButton();
		exitButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/exit.png")));
		exitButton.setToolTipText("Exit the simulator");
		myToolBar.add(exitButton);
		exitButton.addActionListener((e) -> {
			int confirm = JOptionPane.showConfirmDialog(null, "Do you want to exit program?");
			if (confirm == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
		
		myToolBar.setMargin(new Insets(5, 5, 5, 5));
	}
	
		

		private void loadFile(JFileChooser fc) throws IOException {
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				ctrl.reset();
				ctrl.loadEvents(new FileInputStream(fc.getSelectedFile()));
			}
		}
		
		private void selectCont() {
			ChangeCO2ClassDialog contDialog = new ChangeCO2ClassDialog((Frame)SwingUtilities.getWindowAncestor(this));
			int status = map != null ? contDialog.open(map.getVehicles()) : 0;
			
			if (status != 0) {
				List<Pair<String, Integer>> cs = new ArrayList<>();
				cs.add(new Pair<String, Integer>(contDialog.getVehicle().getId(), contDialog.getContClass()));
				ctrl.addEvent(new SetContClassEvent(contDialog.getTime() + time, cs));
			}
			
		}
		
		private void selectWeather() {
			ChangeWeatherDialog wDialog = new ChangeWeatherDialog((Frame)SwingUtilities.getWindowAncestor(this));
			int status = map != null ? wDialog.open(map.getRoads()) : 0;
			
			if (status != 0) {
				List<Pair<String, Weather>> cs = new ArrayList<>();
				cs.add(new Pair<String, Weather>(wDialog.getRoad().getId(), wDialog.getWeatherClass()));
				ctrl.addEvent(new SetWeatherEvent(wDialog.getTime() + time, cs));
			}
			
		}
		
		private void enableToolBar(boolean enabled) {
			folderButton.setEnabled(enabled);
			contClassButton.setEnabled(enabled);
			weatherButton.setEnabled(enabled);					
		}
		
		private void run_sim(int n) {
			if (n > 0 && !stopped) {
				try {
					ctrl.run(1);
				} catch (Exception e) {
					// TODO show error message
					stopped = true;
					return;
				}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						run_sim(n - 1);
					}
				});
			} else {
				enableToolBar(true);
				stopped = true;
			}
		}
		
		private void stop() {
			stopped = true;
		}

}
