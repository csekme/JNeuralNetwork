package internalwindows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import framework.BottomPanel;
import neuralnetwork.NeuralNetwork;
/**
 * Neurális Hálózatról jelenít meg számszerű információkat 
 * @author Csekme Krisztián | KSQFYZ
 * @see NeuralNetwork
 */
public class IFrameInfo extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 1L;
	private BottomPanel bottomPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea txArea;

	/**
     * Ablak konstruktora
     */
    public IFrameInfo() {
        initGUI();
        Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
        setUI(ui);
        Timer timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (IFrameInfo.this.isVisible()) {
                    refresh();
                }
            }
        });
        timer.start();
        bottomPanel1.setParent(this);
    }
    
    /**
     * Frissítést elvégző metódus
     */
    public void refresh() {
        if (NeuralNetwork.getInstance() != null) {
            NeuralNetwork nn = NeuralNetwork.getInstance();
            txArea.setText(null);
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Neurális hálózat megnevezése: ").append(nn.getName()).append("\n");
            sb.append("Leírása: ").append(nn.getDescription()).append("\n");
            sb.append("Eddigi iterációk száma: ").append(nn.getIteration()).append("\n");
            sb.append("Rejtett rétegek száma: ").append(nn.getLayers().size()-2).append("\n");
            sb.append("Bemeneti neuronok száma: ").append(nn.getInputUnitNumber()).append("\n");
            sb.append("Kimeneti neuronok száma: ").append(nn.getOutputUnitNumber()).append("\n");
            sb.append("Össz neuronszám: ").append(nn.getSumOfUnits()).append("\n");
            sb.append("A hálózat % alkalommal volt tanítva".replace("%", Integer.toString(nn.getNumberOfTrain()))).append("\n");
            sb.append("A epizódok száma: ").append(nn.getLastTrainer()!=null?nn.getLastTrainer().getEpoch():"0").append("\n");
            sb.append("Utolsó tanítókészlet számossága: ").append(nn.getNumberOfTrainSet()).append("\n");
            txArea.setText(sb.toString());
        }
    }

    
    /**
     * Grafikus felületet leíró osztály
     */
    private void initGUI() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txArea = new javax.swing.JTextArea();
        bottomPanel1 = new  BottomPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Információ a hálózatról");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/info.png"))); // NOI18N

        jScrollPane1.setBorder(null);

        txArea.setEditable(false);
        txArea.setColumns(20);
        txArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txArea.setRows(5);
        jScrollPane1.setViewportView(txArea);

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
            .addComponent(bottomPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(bottomPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    } 

}
