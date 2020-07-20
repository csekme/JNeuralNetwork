package internalwindows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import framework.DoubleField;
import neuralnetwork.NeuralNetwork;

/**
 * Véletlenszám beírása a hálózatra
 * @author Csekme Krisztián | KSQFYZ
 */
public class IFrameRandomInput extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = -4972052486346729979L;
	Timer timer;
	JButton btnok;
	JCheckBox chkContinue;
	JTextField inputFrom;
	JTextField inputTo;
	JLabel jLabel1;
	JPanel jPanel2;
	JLabel lblBetween;
	JLabel lblContinuously;
	JLabel lblInput;
	JPanel pnlBase;
    

    /**
     * Creates new form IFrameRandomInput
     */
    public IFrameRandomInput() {
        initGUI();
        Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
        setUI(ui);
        timer = new Timer(500, a);
    }

        ActionListener a = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            double from = Double.parseDouble(inputFrom.getText());
            double to = Double.parseDouble(inputTo.getText());
            double diff = to - from;
            Random r = new Random();
            if (NeuralNetwork.getInstance() != null) {
              
                    double[] IN = new double[NeuralNetwork.getInstance().getInputUnitNumber()];
                    for (int i = 0; i < IN.length; i++) {
                        IN[i] = from + r.nextDouble() * diff;
                    }
                    
                    NeuralNetwork.getInstance().stimulus(IN);
              
            }
            if (!chkContinue.isSelected()) {
                timer.stop();
            }
        }
    };
        
 
    /**
     * Képernyő inicializáció
     */
    private void initGUI() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlBase = new javax.swing.JPanel();
        lblInput = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        inputFrom = new DoubleField();
        lblBetween = new javax.swing.JLabel();
        inputTo = new DoubleField();
        lblContinuously = new javax.swing.JLabel();
        chkContinue = new javax.swing.JCheckBox();
        btnok = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Random érték beírás bemenetre");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/input16.png")));

        pnlBase.setLayout(new java.awt.GridBagLayout());

        lblInput.setText("Érték tartomány");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        pnlBase.add(lblInput, gridBagConstraints);

        jLabel1.setText("Random érték beírása a neurális háló bemeneti rétegére");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 12, 0);
        pnlBase.add(jLabel1, gridBagConstraints);

        inputFrom.setMinimumSize(new java.awt.Dimension(60, 20));
        inputFrom.setPreferredSize(new java.awt.Dimension(60, 22));
        jPanel2.add(inputFrom);

        lblBetween.setText("-");
        jPanel2.add(lblBetween);

        inputTo.setPreferredSize(new java.awt.Dimension(60, 22));
        jPanel2.add(inputTo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlBase.add(jPanel2, gridBagConstraints);

        lblContinuously.setText("Folytonos beírás");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        pnlBase.add(lblContinuously, gridBagConstraints);

        chkContinue.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkContinue.setMargin(new java.awt.Insets(2, 0, 2, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        pnlBase.add(chkContinue, gridBagConstraints);

        btnok.setText("Beírás");
        btnok.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnokActionPerformed();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlBase.add(btnok, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBase, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    } 

    void btnokActionPerformed() { 

        if (chkContinue.isSelected()) {
            timer.start();
        } else {
            timer.stop();
            a.actionPerformed(null);
        }
    } 
 
    
}
