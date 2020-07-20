package internalwindows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import application.App;
import neuralnetwork.ActivationModule;
import neuralnetwork.Linear;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Neuron;
import neuralnetwork.ReLU;
import neuralnetwork.Sigmoid;
import neuralnetwork.SoftPlus;
import neuralnetwork.TanH;

/**
 * Veldolgozó egység (Neuron) szerkesztő
 * @author Csekme Krisztián | KSQFYZ
 */
public class IFrameProcessingUnitEditor extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 7068416999319212465L;
     
	javax.swing.JButton btnSet;
    javax.swing.JButton btnValidate;
    javax.swing.JCheckBox chkBias;
    javax.swing.JComboBox<String> cmbActivations;
    javax.swing.JComboBox<String> cmbLayers;
    javax.swing.JComboBox<String> cmbUnit;
    framework.DoubleField dblBias;
    framework.DoubleField dblBiasWeight;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel lblAktivateFunction;
    javax.swing.JLabel lblSelectLayer;
    javax.swing.JLabel lblSelectNeuron;
    javax.swing.JPanel pnlButtons;
    javax.swing.JPanel pnlParameters;
    javax.swing.JPanel pnlSelector;
    javax.swing.JScrollPane scrollweights;
    javax.swing.JTextArea txfWeights;

	Neuron selectedUnit = null;
    double[] weights = null;

    
    /**
     * Konstruktor
     */
    public IFrameProcessingUnitEditor() {
        initGUI();
        Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
        setUI(ui);   
        
        txfWeights.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				btnSet.setEnabled(false);
				btnValidate.setEnabled(true);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
        
        cmbLayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbLayers.getSelectedIndex()>0) {
                    cmbUnit.setEnabled(true);
                    @SuppressWarnings("unused")
					DefaultComboBoxModel<String> units = new DefaultComboBoxModel<String>();
                    units.addElement("kérem válasszon");
                    for (int i=0; i<NeuralNetwork.getInstance().getLayers().get(cmbLayers.getSelectedIndex()-1).getNeurons().size(); i++) {
                        units.addElement(i+1 +". egység");
                    }
                    cmbUnit.setModel(units);
                           
                } else {
                    cmbUnit.setEnabled(false);
                    cmbUnit.removeAllItems();
                }
            }
        });
        cmbUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbUnit.getSelectedIndex()>0) {
         
                    selectedUnit = NeuralNetwork.getInstance().getLayers().get(cmbLayers.getSelectedIndex()-1).getNeurons().get(cmbUnit.getSelectedIndex()-1);
					switch (selectedUnit.getActivation()) {
						case SIGMOID:
							cmbActivations.setSelectedIndex(0);
							break;
						case TANH:
							cmbActivations.setSelectedIndex(1);
							break;
						case RELU:
							cmbActivations.setSelectedIndex(2);
							break;
						case SOFTPLUS:
							cmbActivations.setSelectedIndex(3);
							break;
						case LINEAR:
							cmbActivations.setSelectedIndex(4);
							break;
						default:
					}
                    
                    txfWeights.setText( selectedUnit.getWeightsAsString() );
                    if (selectedUnit.getBias()!=null) {
                        chkBias.setSelected(true);
                        dblBias.setDouble(selectedUnit.getBias());
                        dblBiasWeight.setDouble(selectedUnit.getBiasWeight());
                        dblBias.setEnabled(true);
                        dblBiasWeight.setEnabled(true);
                    } else {
                        chkBias.setSelected(false);
                        dblBias.setDouble(null);
                        dblBiasWeight.setDouble(null);
                        dblBias.setEnabled(false);
                        dblBiasWeight.setEnabled(false);
                    }
                }
            }
        });
    }

    /**
     * Paraméterek inicializációja
     */
    public void initParams() {
    	clearFields();
    	{ //disable controlls
	    	cmbUnit.setEnabled(false);
	        //btnSet.setEnabled(false);
	        cmbActivations.setEnabled(false);
	        txfWeights.setEnabled(false);
	        chkBias.setEnabled(false);
	        dblBias.setEnabled(false);
	        dblBiasWeight.setEnabled(false);
	        btnValidate.setEnabled(false);
    	}

        @SuppressWarnings("unused")
		DefaultComboBoxModel<String> layers = new DefaultComboBoxModel<String>();
        layers.addElement("kérem válasszon");
        
        int l = NeuralNetwork.getInstance().getNumberOfLayers();
        for (int i = 0; i < l; i++) {
            if (i == 0) {
                layers.addElement("Bemeneti réteg");
            } else if (i < l - 1) {
                layers.addElement((i) + ". rejtett réteg");

            } else {
                layers.addElement("Kimeneti réteg");
            }
        }        
        cmbLayers.setModel(layers);    
    }

    /**
     * Grafikus szerkesztő inicializáló metódusa
     */
    @SuppressWarnings("unused")
	private void initGUI() {
    	
    	{
    		setClosable(true);
			setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
			setTitle("Processzáló egység szerkesztő");
			setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/editor.png")));
    	}

        new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        pnlSelector = new javax.swing.JPanel();
        lblSelectLayer = new javax.swing.JLabel();
        cmbLayers = new javax.swing.JComboBox<>();
        lblSelectNeuron = new javax.swing.JLabel();
        cmbUnit = new javax.swing.JComboBox<>();
        pnlParameters = new javax.swing.JPanel();
        lblAktivateFunction = new javax.swing.JLabel();
        cmbActivations = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnValidate = new javax.swing.JButton();
        chkBias = new javax.swing.JCheckBox();
        dblBias = new framework.DoubleField();
        jLabel2 = new javax.swing.JLabel();
        dblBiasWeight = new framework.DoubleField();
        scrollweights = new javax.swing.JScrollPane();
        txfWeights = new javax.swing.JTextArea();
        pnlButtons = new javax.swing.JPanel();
        btnSet = new javax.swing.JButton();

       

        pnlSelector.setBorder(javax.swing.BorderFactory.createTitledBorder("Egység kiválasztása"));

        lblSelectLayer.setText("Válassza ki a réteget");

        cmbLayers.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLayersActionPerformed();
            }
        });

        lblSelectNeuron.setText("Válassza ki az egységet");

        cmbUnit.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUnitActionPerformed();
            }
        });

		javax.swing.GroupLayout pnlSelectorLayout = new javax.swing.GroupLayout(pnlSelector);
		pnlSelector.setLayout(pnlSelectorLayout);
		pnlSelectorLayout.setHorizontalGroup(pnlSelectorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlSelectorLayout.createSequentialGroup().addContainerGap().addGroup(pnlSelectorLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						.addGroup(pnlSelectorLayout.createSequentialGroup().addComponent(lblSelectNeuron)
								.addGap(18, 18, 18)
								.addComponent(cmbUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(pnlSelectorLayout.createSequentialGroup().addComponent(lblSelectLayer)
								.addGap(33, 33, 33).addComponent(cmbLayers, javax.swing.GroupLayout.PREFERRED_SIZE, 183,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		pnlSelectorLayout.setVerticalGroup(pnlSelectorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlSelectorLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(pnlSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(lblSelectLayer).addComponent(cmbLayers,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(pnlSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(lblSelectNeuron).addComponent(cmbUnit,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(3, 3, 3)));

        pnlParameters.setBorder(javax.swing.BorderFactory.createTitledBorder("Paraméterek"));

        lblAktivateFunction.setText("Aktiváló függvény");

        cmbActivations.setModel(new javax.swing.DefaultComboBoxModel<>( ActivationModule.activationNames ));

        jLabel1.setText("Súlyvektor");

        btnValidate.setText("validál");
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed();
            }
        });

        chkBias.setText("Bias");
        chkBias.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBias.setIconTextGap(12);
        chkBias.setMargin(new java.awt.Insets(2, 0, 2, 2));
        chkBias.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBiasActionPerformed();
            }
        });

        jLabel2.setText("Bias súly");

        txfWeights.setColumns(20);
        txfWeights.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        txfWeights.setLineWrap(true);
        txfWeights.setRows(5);
        scrollweights.setViewportView(txfWeights);

        javax.swing.GroupLayout pnlParametersLayout = new javax.swing.GroupLayout(pnlParameters);
        pnlParameters.setLayout(pnlParametersLayout);
        pnlParametersLayout.setHorizontalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAktivateFunction)
                    .addComponent(jLabel1)
                    .addComponent(chkBias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlParametersLayout.createSequentialGroup()
                        .addComponent(cmbActivations, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlParametersLayout.createSequentialGroup()
                        .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlParametersLayout.createSequentialGroup()
                                .addComponent(dblBias, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dblBiasWeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(scrollweights, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnValidate)))
                .addContainerGap())
        );
        pnlParametersLayout.setVerticalGroup(
            pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAktivateFunction)
                    .addComponent(cmbActivations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnValidate)
                    .addComponent(scrollweights, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(pnlParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkBias)
                    .addComponent(dblBias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(dblBiasWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlButtons.setBackground(new java.awt.Color(255, 255, 255));

        btnSet.setText("Beállít");
        btnSet.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetActionPerformed();
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSet)
                .addContainerGap())
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSet)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSelector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlParameters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlParameters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }

    void btnValidateActionPerformed() {
        
        String w = txfWeights.getText();
        String[] wa = w.split(",");
        if (wa.length!=selectedUnit.weights.length) {
            JOptionPane.showMessageDialog(this, "A bemeneti súlyok száma nem megfelelő " + selectedUnit.weights.length +" helyett " + wa.length );
            return;
        }
        try {
        weights = new double[wa.length];
        for (int i=0; i<wa.length; i++) {
            weights[i] = Double.parseDouble(wa[i]);
        }
        btnSet.setEnabled(true);
        } catch(Exception e) {
            weights = null;
            btnSet.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Hibás formátum!");
        }
    }

    void btnSetActionPerformed() {
		if (selectedUnit != null && weights != null) {
			selectedUnit.weights = weights;
		}
			if (chkBias.isSelected()) {
				selectedUnit.setBias(dblBias.getDoubleValue());
				selectedUnit.setBiasWeight(dblBiasWeight.getDoubleValue());
			} else {
				selectedUnit.setBias(null);
				selectedUnit.setBiasWeight(null);

			}
			switch (cmbActivations.getSelectedItem().toString()) {
			case ActivationModule.SIGMOID:
				selectedUnit.setActivationModule(new Sigmoid());
				break;
			case ActivationModule.HIPER:
				selectedUnit.setActivationModule(new TanH());
				break;
			case ActivationModule.RELU:
				selectedUnit.setActivationModule(new ReLU());
				break;
			case ActivationModule.SOFTPLUS:
				selectedUnit.setActivationModule(new SoftPlus());
				break;
			case ActivationModule.LINEAR:
				selectedUnit.setActivationModule(new Linear());
				break;

			}
			cmbActivations.setSelectedIndex(0);
			//cmbUnit.setSelectedIndex(0);
			cmbLayers.setSelectedIndex(0);
			clearFields();
			initParams();
		
    }
    
    public void clearFields() {
    	dblBias.setText(null);
    	dblBiasWeight.setText(null);
    	chkBias.setSelected(false);
    	txfWeights.setText(null);
    	cmbActivations.setSelectedIndex(0);
    }
    
    void cmbUnitActionPerformed() {
    	
        if (cmbUnit.getSelectedIndex()>0) {
        	cmbActivations.setEnabled(true);
        	txfWeights.setEnabled(true);
        	chkBias.setEnabled(true);
        	//dblBias.setEnabled(true);
        	//dblBiasWeight.setEnabled(true);
        	btnSet.setEnabled(true);
        } else {
        	clearFields();
        	cmbActivations.setEnabled( false);
        	txfWeights.setEnabled( false);
        	chkBias.setEnabled( false );
        	dblBias.setEnabled(false );
        	dblBiasWeight.setEnabled( false );      
        	btnSet.setEnabled(false);
        }
        
    }
    
    
    void chkBiasActionPerformed() {
        if (chkBias.isSelected()) {
            dblBias.setEnabled(true);
            dblBiasWeight.setEnabled(true);
        } else {
            dblBias.setEnabled(false);
            dblBiasWeight.setEnabled(false);
            dblBias.setText(null);
            dblBiasWeight.setText(null);
        }
    }
    
    /**
     * Képernyő középre igazítása
     */
    public void setCenter() {
    	setLocation( App.getInstance().getWidth()/2 - getWidth()/2 , App.getInstance().getRibbonBar().getHeight() + App.getInstance().getDesktop().getHeight()/2-getHeight());
		if (getLocation().y<0) {
			setLocation( getLocation().x ,10 );				
		}
    }
    
    void cmbLayersActionPerformed() {
        if (cmbLayers.getSelectedIndex()>0) {
        	cmbUnit.setSelectedIndex(0);
        }
        cmbActivations.setSelectedIndex(0);
        txfWeights.setText(null);
        chkBias.setSelected(false);
        dblBias.setText(null);
        dblBiasWeight.setText(null);   
    }

    

    
}
