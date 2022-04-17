package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		// tables
		JPanel eventsView = createViewPanel((new JTable(new EventsTableModel(_ctrl))), "Events");
		JPanel junctionsView = createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		JPanel vehicleView = createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		JPanel roadView = createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		
		eventsView.setPreferredSize(new Dimension(500, 200));
		junctionsView.setPreferredSize(new Dimension(500, 200));
		vehicleView.setPreferredSize(new Dimension(500, 200));
		roadView.setPreferredSize(new Dimension(500, 200));
		
		tablesPanel.add(eventsView);
		tablesPanel.add(vehicleView);
		tablesPanel.add(roadView);
		tablesPanel.add(junctionsView);
		// TODO add other tables
		// ...
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		
		mapView.setPreferredSize(new Dimension(500, 400));
		
		mapsPanel.add(mapView);
		// TODO add a map for MapByRoadComponent esto es de prueba
		
		JPanel mapView2 = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
		
		mapView2.setPreferredSize(new Dimension(500, 400));
		
		mapsPanel.add(mapView2);
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		}
	
		private JPanel createViewPanel(JComponent c, String title) {
			JPanel p = new JPanel( new BorderLayout() );
			
			// TODO add a framed border to p with title
			TitledBorder titlee;
			titlee = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), title);
			p.setBorder(titlee);
			//
			
			p.add(new JScrollPane(c));
			return p;
		}
}


