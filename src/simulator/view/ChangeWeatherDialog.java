package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JComboBox<Road> roads;
	private DefaultComboBoxModel<Road> roadsModel;
	private JComboBox<Weather> weather;
	private DefaultComboBoxModel<Weather> weatherModel;
	private int status;
	private JSpinner contTicks;
	
	public ChangeWeatherDialog(Frame parent) {
		super(parent, true);
		initGUI();
	}
	
	private void initGUI() {
		
		status = 0;
		
		setTitle("Change road weather");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel infoMsg = new JLabel("Schedule an event to change the weather of a road after a given number of simulation ticks from now");
		infoMsg.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(infoMsg);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel middlePanel = new JPanel();
		middlePanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(middlePanel);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel buttonsPanel = new JPanel();
		middlePanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		JLabel roadLabel = new JLabel("Road: ");
		middlePanel.add(roadLabel);
		
		roadsModel = new DefaultComboBoxModel<>();
		roads = new JComboBox<>(roadsModel);
		middlePanel.add(roads);
		
		JLabel weatherLabel = new JLabel("Weather: ");
		middlePanel.add(weatherLabel);
		weather = new JComboBox<>(Weather.values());
		middlePanel.add(weather);
		
		
		JLabel ticksLabel = new JLabel("Ticks: ");
		middlePanel.add(ticksLabel);
		
		contTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		contTicks.setMaximumSize(new Dimension(20, 40));
		middlePanel.add(contTicks);
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			status = 0;
			setVisible(false);
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			status = 1;
			setVisible(false);
		});
		buttonsPanel.add(okButton);
		
		
		setLocation(300, 300);
		setSize(500, 200);
		pack();
		setResizable(false);
		setVisible(false);
				
	}
	
	public int open(List<Road> roads) {
		roadsModel.removeAllElements();
		for (Road v : roads)
			roadsModel.addElement(v);
		

		setLocation(getParent().getLocation().x + ((getParent().getWidth() / 2) - (getWidth() /2)), getParent().getLocation().y + ((getParent().getHeight() / 2) - (getHeight() /2)));

		setVisible(true);
		return status;
	}
	
	public Road getRoad() {
		return (Road) roadsModel.getSelectedItem();
	}

	public Weather getWeatherClass() {
		return  (Weather) weatherModel.getSelectedItem();
	}
	
	public Integer getTime() {
		return (Integer) contTicks.getValue();
	}
	
	
}
