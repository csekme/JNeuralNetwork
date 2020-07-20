package internalwindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import application.App;
import framework.BottomPanel;
import internalwindows.DialogClassifierStarter.dialogResult;
import math.LinearMath;
import neuralnetwork.DataSet;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Trainer;
import plotter.Plotter;
import plotter.Point;
import plotter.Range;
import plotter.Series;

/**
 * Osztályozói térkép
 * @author Csekme Krisztián | KSQFYZ
 * @see Trainer
 */
public class IFrameClassifier extends javax.swing.JInternalFrame {
 
	private static final long serialVersionUID = 1357039479426072825L;
	private BottomPanel bottomPanel;
	private JMenuItem menNewTask;
	private JMenu menClassifier;
	private JMenuBar menBar;
	public Plotter plotter;

	/**
	 * Konstruktor
	 */
	public IFrameClassifier() {		
		Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
		setUI(ui);
		initGUI();
		bottomPanel.setParent(this);
		initControls();
	}
	
	/**
	 * Kontrollok inicializálása
	 */
	private void initControls() {
		
		menNewTask.addActionListener( (ActionListener)->{
			
			if (NeuralNetwork.getInstance() != null) {
				 if (NeuralNetwork.getInstance().getNumberOfTrain()>0) {
				dialogResult res = DialogClassifierStarter.showClassifier();
				if (res == DialogClassifierStarter.dialogResult.okButton) {
					NeuralNetwork nn = NeuralNetwork.getInstance();
					

					plotter.setHorizontal(new Range(nn.getClassXAxis().getFrom(), nn.getClassXAxis().getTo()));
					plotter.setVertical(new Range(nn.getClassYAxis().getFrom(), nn.getClassYAxis().getTo()));
					plotter.setPolygonSize(8);
					plotter.setPolygonThin(1.5f);
					 
					plotter.setClBackground(Color.WHITE);
					plotter.setClYTitleColor(new Color(80, 80, 80));
					plotter.setClXTitleColor(new Color(80, 80, 80));
					plotter.setClFontColor(new Color(80, 80, 80));
					plotter.setClPlotterShadow(new Color(220, 220, 220));
					plotter.setClPlotterBorder(new Color(180, 180, 180));
					plotter.setClPlotterArea(new Color(240, 240, 240));
					
					
					plotter.series.clear();
					plotter.setShowDiv(true);
					Series test = plotter.addLayer();
					Series train = plotter.addLayer();
					
					train.setClPoligons(Color.BLACK);
				 

					for (double x = nn.getClassXAxis().getFrom() + 0.1; x <= nn.getClassXAxis().getTo(); x += nn.getClassMapDesnity()) {
						for (double y = nn.getClassYAxis().getFrom() + 0.1; y <= nn.getClassYAxis().getTo(); y += nn.getClassMapDesnity()) {
							NeuralNetwork.getInstance().verbose = false;
							NeuralNetwork.getInstance().stimulus(new double[] { x, y });
							double[] o = NeuralNetwork.getInstance().getY(NeuralNetwork.getInstance().getLayers().size() - 1);
							int index = 0;
							double min = Double.MAX_VALUE;
							for (int k = 0; k < NeuralNetwork.getInstance().getDataSet().size(); k++) {
								double dist = LinearMath.euclideanDistance(o,
										NeuralNetwork.getInstance().getDataSet().get(k).getTargetVector());
								if (dist < min) {
									min = dist;
									index = k;
								}
							}
							double[] s = NeuralNetwork.getInstance().getDataSet().get(index).getTargetVector();
							Point po = new Point(x, y);
							po.setColor(nn.getClm().get(LinearMath.vectorToString(s)));
							test.polygons.add(po);
						}
					}
					for (int s = 0; s < NeuralNetwork.getInstance().getDataSet().size(); s++) {
						DataSet ds = NeuralNetwork.getInstance().getDataSet().get(s);
						Point _p = new Point(ds.getTrainVector()[0], ds.getTrainVector()[1],
								Plotter.PoligonForm.TRIANGLE);
						_p.setShowValue(true);
						
						train.polygons.add(_p);
					}

					plotter.repaint();
				} //ok vége
				 } else {
					 JOptionPane.showMessageDialog(App.getInstance(), "A hálózat nem volt tanítva, addig nem lehetséges osztályozási feladat indítása!", "Figyelem!", JOptionPane.WARNING_MESSAGE);
				 }
			}//null ellenörzés vége
			
		}  );
		
	}
	
	/**
	 * Generált grafikus felületet leíró
	 */
	private void initGUI() {
        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setTitle("Osztályozási térkép");
        
		this.setPreferredSize(new java.awt.Dimension(721, 492));
        GridBagLayout thisLayout = new GridBagLayout();
        this.setBounds(0, 0, 721, 492);
         
        getContentPane().setLayout(thisLayout);
        this.setResizable(true);
        this.setClosable(true);
        {
        	menBar = new JMenuBar();
        	setJMenuBar(menBar);
        	{
        		menClassifier = new JMenu();
        		menBar.add(menClassifier);
        		menClassifier.setText("Osztályozás");
        		{
        			menNewTask = new JMenuItem();
        			menClassifier.add(menNewTask);
        			menNewTask.setText("Új feladat");
        		}
        	}
        }
        {
        	bottomPanel = new BottomPanel();
        	getContentPane().add(bottomPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        	bottomPanel.setMinimumSize(new java.awt.Dimension(10, 24));
        	bottomPanel.setMaximumSize(new java.awt.Dimension(32767, 24));
        	bottomPanel.setPreferredSize(new java.awt.Dimension(10, 24));
        }
        {
        	plotter = new Plotter();
        	getContentPane().add(plotter, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        }

	}

}
