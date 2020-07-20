package internalwindows;
import java.awt.Color;
import javax.swing.SwingUtilities;
import neuralnetwork.ActivationModule;
import neuralnetwork.Linear;
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
 * Függvény kirajzolása dialógus ablakban
 * @author Csekme Krisztián | KSQFYZ
 */
public class DialogFunctionPlotter extends javax.swing.JDialog {

	private static final long serialVersionUID = 3265275429315504298L;
	public ActivationModule activationModule;
    private plotter.Plotter plotter;
    
    /**
     * Dialógus ablak konstruktora
     * @param parent szülőablak
     * @param modal a modalitást állítja be
     */
    public DialogFunctionPlotter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initGUI();
    }

    /**
     * Megnyitja az ablakot megjelenítésre a kiválasztott
     * aktivációs függvény kirajzolásával.
     * @param a Neuron aktivációs függvénye
     * @see Neuron
     * @see #Neuron.Activation
     */
    public void show(Neuron.Activation a) {
        Series function = plotter.addLayer();
        Series derivate = plotter.addLayer();
        plotter.setMargin(100);
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
            case LINEAR:
            	activationModule = new Linear();
            	plotter.setTitle(getTitle());
                plotter.horizontal = new Range(-10, 10);
                plotter.vertical = new Range(-10, 10);
                function.setTitle("Lineáris függvény");
                derivate.setTitle("Lineáris deriváltja");
                for (double i = -10; i < 10; i += 0.01) {
                    function.polygons.add(new Point(i, activationModule.activate(i), new Color(0,162,232), Plotter.PoligonForm.DOT, true ));
                    derivate.polygons.add(new Point(i, activationModule.derivate(i), new Color(255,255,0), Plotter.PoligonForm.DOT, true ));
                }
			break;
		case NO_ACTIVATION:
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
     * Generált initGUI
     */
    private void initGUI() {

        plotter = new plotter.Plotter();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plotter, javax.swing.GroupLayout.DEFAULT_SIZE, 1089, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plotter, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );

        pack();
    }

}
