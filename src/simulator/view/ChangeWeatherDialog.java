package simulator.view;

import java.awt.Dimension;
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
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JComboBox<Road> roads;
	private DefaultComboBoxModel<Road> roadsModel;
	private JComboBox<Weather> weather;
	private DefaultComboBoxModel<Weather> weatherModel;
	private int status;
	
	public ChangeWeatherDialog(ControlPanel controlPanel) {
		//TODO: controlPanel va en el constructor? hay que llamar a super con el controlPanel?
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
		roadsModel = new DefaultComboBoxModel<>();
		roads = new JComboBox<>(roadsModel);
		middlePanel.add(roads);
		
		
		JLabel ticksLabel = new JLabel("Ticks: ");
		middlePanel.add(ticksLabel);
		
		JSpinner contTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
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
		setResizable(false);
		setVisible(true);
		pack();
				
	}
	
	public int open(List<Road> roads) {
		roadsModel.removeAllElements();
		for (Road v : roads)
			roadsModel.addElement(v);

		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
		return status;
	}
	
}
