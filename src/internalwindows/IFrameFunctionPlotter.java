package internalwindows;
import java.awt.Color;
import javax.swing.SwingUtilities;
import framework.BottomPanel;
import neuralnetwork.ActivationModule;
import neuralnetwork.Neuron;
import neuralnetwork.ReLU;
import neuralnetwork.Sigmoid;
import neuralnetwork.SoftPlus;
import neuralnetwork.TanH;
import plotter.Series;
import plotter.Plotter;
import plotter.Point;
import plotter.Range;
/**
 * Aktivációs függvényeket kirajzoló belső ablak
 * @author Csekme Krisztián | KSQFYZ
 */
public class IFrameFunctionPlotter extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = -7548566509373937526L;
	public ActivationModule activationModule;
    private  BottomPanel bottomPanel1;
    private plotter.Plotter plotter;
    

	/**
	 * Konstruktor
	 */
	public IFrameFunctionPlotter() {
		Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
		setUI(ui);
		initGUI();
		bottomPanel1.setParent(this);
	}
    
	/**
	 * Show függvény megjelenítés elött inicializálja
	 * beállítja  a plottert.
	 * @param a
	 */
    public void show(Neuron.Activation a) {
        Series function = plotter.addLayer();
        Series derivate = plotter.addLayer();
        plotter.setMargin(60);
        plotter.setShowLegend(true);
        function.setClPoligons(new Color(0,162,232));
        derivate.setClPoligons(Color.yellow);
        
        switch (a) {
            case RELU:
                activationModule = new ReLU();
                plotter.horizontal = new Range(-10, 10);
                plotter.vertical = new Range(-10, 10);
                plotter.setTitle(getTitle());
                function.setTitle("ReLU függvény");
                derivate.setTitle("ReLU deriváltja");
                for (double i = -10; i < 10; i += 0.01) {
                    function.polygons.add(new Point(i, activationModule.activate(i), new Color(0,162,232), Plotter.PoligonForm.DOT, true ));
                    derivate.polygons.add(new Point(i, activationModule.derivate(i), new Color(255,255,0), Plotter.PoligonForm.DOT, true ));
                }
                break;
            case SIGMOID:
                activationModule = new Sigmoid();
                plotter.setTitle(getTitle());
                plotter.horizontal = new Range(-10, 10);
                plotter.vertical = new Range(-2, 2);
                function.setTitle("Sigmoid függvény");
                derivate.setTitle("Sigmoid deriváltja");
                for (double i = -10; i < 10; i += 0.01) {
                    function.polygons.add(new Point(i, activationModule.activate(i), new Color(0,162,232), Plotter.PoligonForm.DOT, true ));
                    derivate.polygons.add(new Point(i, activationModule.derivate(i), new Color(255,255,0), Plotter.PoligonForm.DOT, true ));
                }
                break;
            case SOFTPLUS:
                activationModule = new SoftPlus();
                plotter.setTitle(getTitle());
                plotter.horizontal = new Range(-10, 10);
                plotter.vertical = new Range(-5, 10);
                function.setTitle("SoftPlus függvény");
                derivate.setTitle("SoftPlus deriváltja");
                for (double i = -10; i < 10; i += 0.01) {
                    function.polygons.add(new Point(i, activationModule.activate(i), new Color(0,162,232), Plotter.PoligonForm.DOT, true ));
                    derivate.polygons.add(new Point(i, activationModule.derivate(i), new Color(255,255,0), Plotter.PoligonForm.DOT, true ));
                }
                break;
            case TANH:
                activationModule = new TanH();
                plotter.setTitle(getTitle());
                plotter.horizontal = new Range(-10, 10);
                plotter.vertical = new Range(-2, 2);
                function.setTitle("Hiperbolikus tangens függvény");
                derivate.setTitle("Hiperbolikus tangens deriváltja");
                for (double i = -10; i < 10; i += 0.01) {
                    function.polygons.add(new Point(i, activationModule.activate(i), new Color(0,162,232), Plotter.PoligonForm.DOT, true ));
                    derivate.polygons.add(new Point(i, activationModule.derivate(i), new Color(255,255,0), Plotter.PoligonForm.DOT, true ));
                }
                break;
		default:
			break;
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
        setVisible(true);
    }

    /**
     * Grafikus megjelenést leíró
     */
    private void initGUI() {

        plotter = new plotter.Plotter();
        bottomPanel1 = new BottomPanel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        bottomPanel1.setMaximumSize(new java.awt.Dimension(32767, 24));
        bottomPanel1.setMinimumSize(new java.awt.Dimension(0, 24));
        bottomPanel1.setPreferredSize(new java.awt.Dimension(0, 24));

        javax.swing.GroupLayout bottomPanel1Layout = new javax.swing.GroupLayout(bottomPanel1);
        bottomPanel1.setLayout(bottomPanel1Layout);
        bottomPanel1Layout.setHorizontalGroup(
            bottomPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bottomPanel1Layout.setVerticalGroup(
            bottomPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plotter, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
            .addComponent(bottomPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(plotter, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(bottomPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }
 

 
}
