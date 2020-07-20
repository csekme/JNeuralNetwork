package internalwindows;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.Timer;
import framework.BottomPanel;
import framework.F;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Trainer;
import plotter.Plotter;
import plotter.Range;

/**
 * Neurális Hálózatot tanító algoritmus tanítási görbéje
 * @author Csekme Krisztián |KSQFYZ
 * @see NeuralNetwork
 * @see Trainer
 */
public class IFrameLearnDiagram extends javax.swing.JInternalFrame {
	
	private static final long serialVersionUID = 5999115699724012482L;
	private BottomPanel bottomPanel;
	private Plotter plotter;

	/**
	 * Konstruktor
	 */
	public IFrameLearnDiagram() {
		Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
		setUI(ui);
		initGUI();
		bottomPanel.setParent(this);
		Timer timer = new Timer(300, (ActionLitener) -> {
			if ( NeuralNetwork.getInstance() != null && NeuralNetwork.getInstance().getLearnRatePoints() != null && NeuralNetwork.getInstance().getLastTrainer()!=null ) {
				 
					NeuralNetwork.getInstance().getLearnRatePoints().setClPoligons(new Color(105, 193, 253));
					plotter.setTitle("Tanulási görbe");
					plotter.setyTitle("Hálózat energiája: ||e||^2 =" + F.doubleFormat(NeuralNetwork.getInstance().getLastTrainer().getActualLearnRateEnergy(),4));
					double sec = (double)NeuralNetwork.getInstance().getLastTrainer().executeTimeInMilliSecond() / 1000;
					plotter.setxTitle("Epizódok (?/#)"
							.replace("?", Integer.toString(NeuralNetwork.getInstance().getLastTrainer().getEpoch()))
							.replace("#", Integer.toString(NeuralNetwork.getInstance().getLastTrainer().getEpochIndex()+1))
							+ " (" + sec + " mp.)");
					plotter.setyTitleFontSize(14f);
					plotter.setxTitleFontSize(14f);
					plotter.setTitleFontSize(20f);
					plotter.setMrTop(50);
					plotter.setMrLeft(60);
					plotter.setMrBottom(50);
					plotter.setPolygonSize(2);
					plotter.setPolygonThin(1f);
					plotter.setClBackground(Color.WHITE);
					plotter.setClYTitleColor(new Color(80, 80, 80));
					plotter.setClXTitleColor(new Color(80, 80, 80));
					plotter.setClFontColor(new Color(80, 80, 80));
					plotter.setClPlotterShadow(new Color(220, 220, 220));
					plotter.setClPlotterBorder(new Color(180, 180, 180));
					plotter.setClPlotterArea(new Color(240, 240, 240));
					plotter.setVertical(NeuralNetwork.getInstance().getLearnRateEnergy());
					plotter.setHorizontal(NeuralNetwork.getInstance().getLearnRateEpisode());
					plotter.setSeries(NeuralNetwork.getInstance().getLearnRatePoints());
				 
			} else {
				plotter.setTitle("Nincsen hálózat tanítás alatt!");
				plotter.setMrTop(50);
				plotter.setTitleFontSize(20f);
				plotter.setClFontColor(new Color(80, 80, 80));
				plotter.setClBackground(Color.WHITE);
				plotter.setClPlotterShadow(new Color(220, 220, 220));
				plotter.setClPlotterBorder(new Color(180, 180, 180));
				plotter.setClPlotterArea(new Color(240, 240, 240));
				plotter.setVertical(new Range(0,0));
				plotter.setHorizontal(new Range(0,0));
				
			}
		});
		timer.start();
	}
	
	/**
	 * Grafikai megjelenítés
	 */
	public void initGUI() {
		{
	        setClosable(true);
	        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
	        setMaximizable(true);
	        setResizable(true);
	        setTitle("Tanulási görbe");
			this.setPreferredSize(new java.awt.Dimension(704, 552));
			BorderLayout thisLayout = new BorderLayout();
			this.setBounds(0, 0, 704, 552);
			getContentPane().setLayout(thisLayout);
			{
				plotter = new Plotter();
				getContentPane().add(plotter, BorderLayout.CENTER);
			}
			{
				bottomPanel = new BottomPanel();
				getContentPane().add(bottomPanel, BorderLayout.SOUTH);
				bottomPanel.setPreferredSize(new java.awt.Dimension(10, 24));
			}
		}

	}
	
	/**
	 * Visszaadja a grafikus megjelenítő komponenst
	 * függvény plotter
	 * @return {@link #plotter}
	 */
	public Plotter getPlotter() {
		return plotter;
	}

	/**
	 * Grafikus megjelenítő komponens
	 * függvény plotter
	 * @param plotter
	 */
	public void setPlotter(Plotter plotter) {
		this.plotter = plotter;
	}
	
	

}
