package internalwindows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import framework.BottomPanel;
import neuralnetwork.NeuralNetwork;

/**
 * Neurális hálózatot kirajzoló ablak
 * @author Csekme Krisztián | KSQFYZ
 */
public class IFrameNeuralNetworkPlotter extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 4959250926367417923L;
	boolean drawContinuously = true;
    private Win10InternalFrameUI ui;
    private  BottomPanel bottomPanel1;
    plotter.NeuralNetworkDrawer plotter;
    private javax.swing.JScrollPane scroll;
    
    /**
     * Konstruktor 
     */
    public IFrameNeuralNetworkPlotter() {
        initGUI();
        ui = new Win10InternalFrameUI(this);
        this.setUI(ui);
        bottomPanel1.setParent(this);
        
        Timer t = new Timer(400, loop);
        t.start();
    }

    /**
     * Neurális hálózatot kirajzoló esemény
     * Konstruktorban lévő timer mozgatja
     */
    ActionListener loop = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (drawContinuously && IFrameNeuralNetworkPlotter.this.isVisible() && NeuralNetwork.getInstance() != null) {
                plotter.draw(NeuralNetwork.getInstance());
            }
        }
    };

    /**
     * Folyamatos kirajzolást beállító metódus
     * @param value
     */
    public void setDrawContinuously(boolean value) {
        this.drawContinuously = value;
    }

	/**
	 * Grafikus megjelenítést leíró metódus 
	 */
    private void initGUI() {

        scroll = new javax.swing.JScrollPane();
        plotter = new plotter.NeuralNetworkDrawer();
        bottomPanel1 = new  BottomPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setMaximizable(true);
        setResizable(true);
        setTitle("Neurális hálózat");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/machine-learning.png"))); // NOI18N

        scroll.setViewportView(plotter);

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
            .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
            .addComponent(bottomPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(bottomPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }

 
}
