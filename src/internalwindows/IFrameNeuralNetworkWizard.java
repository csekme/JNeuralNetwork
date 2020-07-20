package internalwindows;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableModel;
import application.App;
import neuralnetwork.ActivationModule;
import neuralnetwork.Layer;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Neuron;
/**
 * Neurális Hálózat létrehozását segítő varázsló
 * @author Csekme Krisztián | KSQFYZ
 * @see NeuralNetwork
 * @see Layer
 * @see Neuron
 * @see ActivationModule
 */
public class IFrameNeuralNetworkWizard extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 7492251460396962024L;
	private static final Object[] columnNames = {"Réteg száma", "Típusa", "Neuronok száma", "Aktiváló függvény", "Bias" ,"Bias súly állítható"};
	
    javax.swing.JButton btnAdd;
    javax.swing.JButton btnNext;
    javax.swing.JButton btnPrev;
    javax.swing.JCheckBox chkBias;
    javax.swing.JComboBox<String> cmbActivator;
    framework.DoubleField dblBias;
    javax.swing.JLabel lblAktivator;
    javax.swing.JLabel lblLeiras;
    javax.swing.JLabel lblNN;
    javax.swing.JLabel lblName;
    javax.swing.JLabel lblNumberofNeuron;
    javax.swing.JLabel lblText;
    javax.swing.JLabel lblTopology;
    javax.swing.JLabel lblWelcome;
    javax.swing.JTextField nbrNeuron;
    javax.swing.JPanel pnlPage_1;
    javax.swing.JPanel pnlPage_2;
    JButton btnClear;
    JCheckBox chkBiasWeightable;
    javax.swing.JScrollPane scrollLeiras;
    javax.swing.JScrollPane scrollTable;
    javax.swing.JButton showGraph;
    javax.swing.JTabbedPane tab;
    javax.swing.JTable table;
    javax.swing.JTextArea txaLeiras;
    javax.swing.JTextField txfMegnevezes;

	DefaultTableModel model;
    List<Object> biases;
    boolean showTabsHeader = false;

    
    /**
     * Ablak megnyitása a showWindow függvény segítségével
     * mely a megjelenítést megelőzően inicializálsa a képernyő komponenseit
     */
	public void showWindow() {
		model = new DefaultTableModel(columnNames, 0);
		biases.clear();
		tab.setSelectedIndex(0);
		txfMegnevezes.setText("");
		txaLeiras.setText("");
		table.setModel(model);
		tab.setSelectedIndex(0);
		btnPrev.setText("Mégse");
		btnNext.setText("Tovább");
		setLocation( App.getInstance().getWidth()/2 - getWidth()/2 , App.getInstance().getRibbonBar().getHeight() + App.getInstance().getDesktop().getHeight()/2-getHeight());
		if (getLocation().y<0) {
			setLocation( getLocation().x ,10 );				
		}
		setVisible(true);
	}

    /**
     * Konstruktor
     */
    public IFrameNeuralNetworkWizard() {
        Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
        setUI(ui);
        model = new DefaultTableModel(columnNames, 0);
        
        initGUI();

        //Tab komponens felülvezérlése annak érdekében hogy úgynevezett
        //flow layout-ként funkcionáljon
        tab.setUI(new BasicTabbedPaneUI() {

            @Override
            public void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.white);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }

            @Override
            public void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h,
                    boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.white);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }

            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
				if (showTabsHeader) {
					return super.calculateTabAreaHeight(tabPlacement, horizRunCount, maxTabHeight);
				}
				return -5;
			}
        });

        tab.setBorder(null);
        tab.setToolTipText(null);
        tab.setOpaque(false);
        tab.setBackground(Color.white);

        dblBias.setEnabled(false);
        biases = new ArrayList<>();

        nbrNeuron.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9')
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }

        });

    }

    /**
     * Grafikai komponensek inicializációja
     */
    private void initGUI() {

    	{
    	    setClosable(true);
            setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
            setIconifiable(true);
            setTitle("Neurális hálózat varázsló");
            setToolTipText("");
            setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/wizard.png"))); // NOI18N

    	}
        tab = new javax.swing.JTabbedPane();
        pnlPage_1 = new javax.swing.JPanel();
        pnlPage_2 = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        
        
        {
	        GridBagLayout pnlPage_1Layout = new GridBagLayout();
	        pnlPage_1.setLayout(pnlPage_1Layout);
	        lblName = new javax.swing.JLabel();
	        txfMegnevezes = new javax.swing.JTextField();
	        lblLeiras = new javax.swing.JLabel();
	        pnlPage_1.add(lblLeiras, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
	        scrollLeiras = new javax.swing.JScrollPane();
	        pnlPage_1.add(scrollLeiras, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
	        pnlPage_1.add(txfMegnevezes, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
	        pnlPage_1.add(lblName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
	        txaLeiras = new javax.swing.JTextArea();
	        lblNN = new javax.swing.JLabel();
	        pnlPage_1.add(lblNN, new GridBagConstraints(3, 1, 1, 2, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	        lblWelcome = new javax.swing.JLabel();
	        pnlPage_1.add(lblWelcome, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 4, 16, 2), 0, 0));
	        lblText = new javax.swing.JLabel();
	        pnlPage_1.add(lblText, new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 12, 4), 0, 0));
        }
        {
	        GridBagLayout pnlPage_2Layout = new GridBagLayout();
	        pnlPage_2.setLayout(pnlPage_2Layout);
	        lblTopology = new javax.swing.JLabel();
	        lblNumberofNeuron = new javax.swing.JLabel();
	        nbrNeuron = new javax.swing.JTextField();
	        lblAktivator = new javax.swing.JLabel();
	        cmbActivator = new javax.swing.JComboBox<>();
	        showGraph = new javax.swing.JButton();
	        chkBias = new javax.swing.JCheckBox();
	        dblBias = new framework.DoubleField();
	        scrollTable = new javax.swing.JScrollPane();
	        table = new javax.swing.JTable();
	        btnAdd = new javax.swing.JButton();
	        pnlPage_2.add(btnAdd, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
	        pnlPage_2.add(cmbActivator, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
	        pnlPage_2.add(lblAktivator, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
	        pnlPage_2.add(showGraph, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
	        pnlPage_2.add(dblBias, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
	        pnlPage_2.add(chkBias, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 12, 2, 2), 0, 0));
	        pnlPage_2.add(nbrNeuron, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
	        pnlPage_2.add(lblNumberofNeuron, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
	        pnlPage_2.add(lblTopology, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 4, 16, 4), 0, 0));
	        pnlPage_2.add(scrollTable, new GridBagConstraints(0, 3, 5, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));     
	        chkBias.setEnabled(false);
        }
        {
        	chkBiasWeightable = new JCheckBox();
        	pnlPage_2.add(chkBiasWeightable, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 12, 2, 2), 0, 0));
        	chkBiasWeightable.setText("Eltolás súlyozható");
        	chkBiasWeightable.setHorizontalTextPosition(SwingConstants.LEADING);
        	chkBiasWeightable.setEnabled(false);
        }
        {
        	btnClear = new JButton();
        	pnlPage_2.add(btnClear, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
        	btnClear.setText("Sorok törlése");
        	btnClear.addActionListener( ActionListener->{  
        		if (model!=null) {
        			while (model.getRowCount()>0) {
        				model.removeRow(model.getRowCount()-1);
        			}		
        		}
        		chkBias.setEnabled(false);
				dblBias.setEnabled(false);
				chkBiasWeightable.setEnabled(false);
				biases.clear();
        		
        	});
        	
        }

        tab.setBackground(new java.awt.Color(255, 255, 255));
        tab.setTabPlacement(SwingConstants.BOTTOM);
        tab.setToolTipText("");
        tab.setOpaque(true);

        pnlPage_1.setBackground(new java.awt.Color(255, 255, 255));

        lblName.setText("Megnevezés");
        lblLeiras.setText("Leírása");
        txaLeiras.setColumns(20);
        txaLeiras.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txaLeiras.setRows(5);
        scrollLeiras.setViewportView(txaLeiras);

        lblNN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/neural_network.png")));
        lblWelcome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblWelcome.setText("Neurális hálózat létrehozása");

        lblText.setText("<html>A Neurális hálózat varázslóval könnyedén létrehozhatja saját mesterséges neurális hálózatát igényeinek megfelelően. Adja meg röviden a hálózat nevét valamint leírását. Ezt követően a következő oldalon megszerkeztheti a szükséges topológiát, rétegeket és processzáló egységeket.</html>");

        

        tab.addTab("tab1", pnlPage_1);

        pnlPage_2.setBackground(new java.awt.Color(255, 255, 255));

        lblTopology.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTopology.setText("Rétegek létrehozása");

        lblNumberofNeuron.setText("Processzáló egységek (neuronok) száma:");

        lblAktivator.setText("Aktivációs függvény:");

        cmbActivator.setModel(new javax.swing.DefaultComboBoxModel<>( ActivationModule.activationNames ));
        cmbActivator.setEnabled(false);

        showGraph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/graph.png"))); // NOI18N
        showGraph.setToolTipText("Függvény megtekintése");
        showGraph.setAlignmentY(0.0F);
        showGraph.setIconTextGap(0);
        showGraph.setMargin(new java.awt.Insets(0, 0, 0, 0));
        showGraph.setMaximumSize(new java.awt.Dimension(21, 22));
        showGraph.setMinimumSize(new java.awt.Dimension(21, 22));
        showGraph.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                showGraphActionPerformed();
            }
        });

        chkBias.setText("Eltolás");
        chkBias.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBias.setIconTextGap(8);
        chkBias.setMargin(new java.awt.Insets(0, 0, 0, 2));
       
       
        chkBias.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBiasActionPerformed();
            }
        });

       
        scrollTable.setViewportView(table);

        btnAdd.setText("Hozzáadás");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed();
            }
        });

       
        tab.addTab("tab2", pnlPage_2);

        btnNext.setText("Következő");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setText("Mégse");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        this.setPreferredSize(new java.awt.Dimension(797, 365));
        this.setBounds(0, 0, 797, 365);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnPrev))
                .addContainerGap())
        );

        pack();
    }
    
    
   

    /**
     * A következő gomb eseménye, mely az utolsó állapotnál
     * megváltozik az új tulajdon neve "Létrehoz" lesz.
     * @param evt
     */
    void btnNextActionPerformed(java.awt.event.ActionEvent evt) {
        
    	//Az első oldal a megnevezés és leírás megadása
    	//A navigáló gombok oda vissza mozgást engednek
    	if (tab.getSelectedIndex() == 0) {
            btnPrev.setText("Vissza");
            btnNext.setText("Létrehozás");
            tab.setSelectedIndex(1);
            
            //A máásodik oldal egyben az utolsó innen 
        } else if (tab.getSelectedIndex() == 1) {

        	int[] n = new int[model.getRowCount()];
            if (n.length < 2) {
            	JOptionPane.showMessageDialog(App.getInstance(), "A hálózat létrehozásához minimálisan 2 réteg szükséges, egy bemeneti, és egy kimeneti réteg!","Figyelem!",JOptionPane.WARNING_MESSAGE);
            	return;
            }
        	
        	//Neurális hálózat példányosítása
            NeuralNetwork nn = NeuralNetwork.create();
            nn.setName(txfMegnevezes.getText());
            nn.setDescription(txaLeiras.getText());

            Double[] b = new Double[n.length];
            Neuron.Activation[] a = new Neuron.Activation[model.getRowCount()];
            boolean[] bw = new boolean[n.length];
            for (int r = 0; r < model.getRowCount(); r++) {
                switch (model.getValueAt(r, 3).toString()) {
                    case ActivationModule.SIGMOID:
                        a[r] = Neuron.Activation.SIGMOID;
                        break;
                    case ActivationModule.HIPER:
                        a[r] = Neuron.Activation.TANH;
                        break;
                    case ActivationModule.RELU:
                        a[r] = Neuron.Activation.RELU;
                        break;
                    case ActivationModule.SOFTPLUS:
                        a[r] = Neuron.Activation.SOFTPLUS;
                        break;
                    case ActivationModule.LINEAR:
                    	a[r] = Neuron.Activation.LINEAR;
                    	break;
                    case ActivationModule.NO_ACTIVATION:
                    	a[r] = Neuron.Activation.NO_ACTIVATION;
                    break;
				default:
                    	a[r] = Neuron.Activation.NO_ACTIVATION;
                }

                n[r] = Integer.parseInt(model.getValueAt(r, 2).toString());
                bw[r] = model.getValueAt(r, 5).toString().equals("Igen")?true:false;
            }
            int i = 0;
            for (Object o : biases) {
                if (o instanceof Double) {
                    b[i] = (Double) o;
                } else {
                    b[i] = null;
                }
                i++;
            }
            nn.build(n, a, b, bw);
            nn.initWeights();
           // NN.setInstance( nn );
            App.nnp.setVisible(true);
            setVisible(false);

        }

    }

    void btnPrevActionPerformed() {
        if (tab.getSelectedIndex() == 0) {
            setVisible(false);
        } else {
            btnNext.setText("Tovább");
            btnPrev.setText("Mégse");
            tab.setSelectedIndex(tab.getSelectedIndex() - 1);
        }
    }

    void showGraphActionPerformed() {
        DialogFunctionPlotter fp = new DialogFunctionPlotter(null, true);
        fp.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/graph.png")).getImage());
        switch (cmbActivator.getSelectedItem().toString()) {
            case ActivationModule.SIGMOID:
                fp.setTitle("Sigmoid függvény");
                fp.show(Neuron.Activation.SIGMOID);
                break;
            case ActivationModule.HIPER:
                fp.setTitle("Hiperbolikus tangens függvény");
                fp.show(Neuron.Activation.TANH);
                break;
            case ActivationModule.RELU:
                fp.setTitle("ReLU (rektifikált lineáris egység) függvény");
                fp.show(Neuron.Activation.RELU);
                break;
            case ActivationModule.SOFTPLUS:
                fp.setTitle("SoftPlus függvény");
                fp.show(Neuron.Activation.SOFTPLUS);
                break;
            case ActivationModule.LINEAR:
            	 fp.setTitle("Lineáris függvény");
                 fp.show(Neuron.Activation.LINEAR);
            	break;
            default:
                throw new RuntimeException("Ismeretlen aktivációs függvény");
        }
    }

  

    void chkBiasActionPerformed() {
        if (chkBias.isSelected()) {
            dblBias.setEnabled(true);
            chkBiasWeightable.setEnabled(true);
        } else {
            dblBias.setEnabled(false);
            dblBias.setText(null);
            chkBiasWeightable.setEnabled(false); 
        }
    } 

    void btnAddActionPerformed() { 
        Object[] row = new Object[6];
        if (nbrNeuron.getText().length() > 0) {
            if (model.getRowCount() == 0) {
                row[0] = 1;
                row[1] = "Bemeneti réteg";
                row[2] = nbrNeuron.getText();
                row[3] = "Nincs";
                row[4] = dblBias.getDoubleValue();
                row[5] = chkBiasWeightable.isSelected()?"Igen":"Nem";
                model.addRow(row);
            } else if (model.getRowCount() > 0) {
                row[0] = model.getRowCount() + 1;
                row[1] = "Kimeneti réteg";
                row[2] = nbrNeuron.getText();
                row[3] = cmbActivator.getSelectedItem();
                row[4] = dblBias.getDoubleValue();
                row[5] = chkBiasWeightable.isSelected()?"Igen":"Nem";
                model.addRow(row);
                if (model.getRowCount() > 2) {
                    model.setValueAt("Rejtett réteg", model.getRowCount() - 2, 1);
                }
            }
            nbrNeuron.setText(null);
            biases.add(dblBias.getDoubleValue());
            chkBias.setSelected(false);
            chkBiasWeightable.setSelected(false);
            dblBias.setEnabled(false);
            dblBias.setText(null);
            chkBias.setEnabled(true);
            cmbActivator.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Kérem legalább 1 neuront adjon meg", "Figyelem!", JOptionPane.WARNING_MESSAGE);
        }
    } 
}
