package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int ticks;
	private static String startUpMode = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop (default value is 10)\".build()").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Run mode, can be either run on batch or GUI mode").build());
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	
	private static void parseTicksOption(CommandLine line) throws ParseException {
		if(line.hasOption('t'))
			ticks = Integer.parseInt(line.getOptionValue('t'));
		else
			ticks = _timeLimitDefaultValue;
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException {
		
		if (line.hasOption("m")) {
			startUpMode = line.getOptionValue("m");
		} else {
			startUpMode = "gui";
		}
		
	}

	private static void initFactories() {

			List<Builder<Event>> eventBuilders = new ArrayList<>();

			eventBuilders.add(new NewJunctionEventBuilder(createLSSFactory(),createDQSFactory()));
			eventBuilders.add(new NewCityRoadEventBuilder());
			eventBuilders.add(new NewInterCityRoadEventBuilder());
			eventBuilders.add(new NewVehicleEventBuilder());
			eventBuilders.add(new SetWeatherEventBuilder());
			eventBuilders.add(new SetContClassEventBuilder());
			
			_eventsFactory = new BuilderBasedFactory<Event>(eventBuilders);

	}
	
	private static  Factory<LightSwitchingStrategy> createLSSFactory() {
		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		return new BuilderBasedFactory<>(lsbs);
	}
	
	private static  Factory<DequeuingStrategy> createDQSFactory() {
		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		return new BuilderBasedFactory<>(dqbs);
	}

	private static void startBatchMode() throws IOException {
		TrafficSimulator simulator = new TrafficSimulator();
		Controller controller = new Controller(simulator, _eventsFactory);
		
		if (_inFile == null) { //añadimos ahora aqui la excepcion de que falta el fichero de entrada, porque solo se debe tener en cuenta cuando estamos en modo batch
			throw new IOException("An events file is missing");
		}
		
		controller.loadEvents(new FileInputStream(_inFile));
		controller.run(ticks, new FileOutputStream(_outFile));
	}
	
	private static void startGUIMode() throws IOException {
		TrafficSimulator simulator = new TrafficSimulator();
		Controller controller = new Controller(simulator, _eventsFactory);
		
		if (_inFile != null) {
			controller.loadEvents(new FileInputStream(_inFile));
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			new MainWindow(controller);
			}
		});
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);

		if (startUpMode == "console") {
			startBatchMode();
		} else if (startUpMode == "gui"){
			startGUIMode();
		} else {
			throw new IOException("Startup mode is not valid. Must be gui or console");
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
