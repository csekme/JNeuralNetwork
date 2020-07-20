package internalwindows;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import application.App;
import framework.BottomPanel;
import framework.F;
import framework.TableCell;
import internalwindows.DialogInputForTrainer.Mode;
import internalwindows.DialogTrainStart.dialogResult;
import neuralnetwork.DataSet;
import neuralnetwork.GradiantTrainer;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Trainer;

/**
 * Tanítás végrehajtását elősegítő ablak
 * @author Csekme Krisztián | KSQFYZ
 * @see Trainer
 */
public class IFrameTrainer extends javax.swing.JInternalFrame {

	private static final long serialVersionUID = 5332904751166589922L;
	
	/** File logger */
	Logger flog = LogManager.getLogger("NeuralNetwork");
	Logger log = LogManager.getLogger(IFrameTrainer.class.getName());

	// Kontrollok
	JMenuBar mainMenu;
	BottomPanel bottomPanel;
	JButton btnClear;
	JMenuItem menuItemInsertTrainLine;
	JMenu menEdit;
	JMenuItem menuItemSaveTrainSet;
	JMenuItem menuItemOpenTrainSet;
	JMenu menFile;
	JTable gridTrainSet;
	JScrollPane scrollTable;
	JProgressBar progressBar;
	JButton btnTrain;
	JPanel pnlButtons;
	JPanel pnlBase;
	JButton btnInsertTrainLine;
	JToolBar toolBar;

	// Tanító algoritmus
	Trainer job;
	
	// Tanító készlet
	DefaultTableModel 	model;
	
    /**
     * Konstruktor
     */
    public IFrameTrainer() {
        Win10InternalFrameUI ui = new Win10InternalFrameUI(this);
        setUI(ui);
        initGUI();
        bottomPanel.setParent(this);
    }

    /**
     * Felülvezérelt megjelenítő metódus
     */
    @Override
    public void setVisible(boolean value) {
    	
    	//Ha a neurális háló példánya nem null akkor a táblázat oszlopait beállítjuk
		if (NeuralNetwork.getInstance() != null && model==null && value == true) {
			int numberOfInputs = NeuralNetwork.getInstance().getInputUnitNumber();
			int numberOfOutputs = NeuralNetwork.getInstance().getOutputUnitNumber();
			String[] columns = new String[ numberOfInputs + numberOfOutputs ];
			for (int i = 0; i < columns.length; i++) {
				if (i < numberOfInputs) {
					columns[i] = "Bemenet#" + (i);
				} else {
					columns[i] = "Kimenet#" + (i - numberOfInputs);
				}
			}
			model = new DefaultTableModel(columns, 0);
			for (int i = 0; i < gridTrainSet.getColumnCount(); i++) {
				gridTrainSet.getColumnModel().getColumn(i).setCellRenderer(new TableCell(TableCell.CENTER));
			}
			gridTrainSet.setModel(model);
			gridTrainSet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			setLocation( App.getInstance().getWidth()/2 - getWidth()/2 , App.getInstance().getRibbonBar().getHeight() + App.getInstance().getDesktop().getHeight()/2-getHeight());
			if (getLocation().y<0) {
				setLocation( getLocation().x ,10 );				
			}
		}
		
		super.setVisible(value);		
    }

	/**
	 * Képernyő felületet leíró metódus
	 */
	@SuppressWarnings("cast")
	private void initGUI() {
		{
			this.setClosable(true);
			this.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
			this.setMaximizable(true);
			this.setResizable(true);
			
			this.setPreferredSize(new java.awt.Dimension(804, 460));
			this.setBounds(0, 0, 804, 460);
			this.setTitle("Hálózat tanítása");
			this.setFrameIcon(new ImageIcon(getClass().getResource("/resources/images/learn.png"))); 
			
			BorderLayout layout = new BorderLayout();
			getContentPane().setLayout(layout);

			{
				toolBar = new JToolBar();
				getContentPane().add(toolBar, BorderLayout.NORTH);
				toolBar.setRollover(true);
				toolBar.setPreferredSize(new java.awt.Dimension(6,28));
				{
					btnInsertTrainLine = new javax.swing.JButton();
					toolBar.add(btnInsertTrainLine);
					btnInsertTrainLine.setIcon(new ImageIcon(getClass().getResource("/resources/images/add-row.png"))); 
					btnInsertTrainLine.setToolTipText("Tanítósor felvitele");
					btnInsertTrainLine.addActionListener((ActionEvent evt) -> btnInsertForTrainActionPerformed());
				}
				{
					btnClear = new JButton();
					toolBar.add(btnClear);
					btnClear.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/images/clear16.png")));
					btnClear.setToolTipText("Tanító adatok törlése");
					btnClear.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if (model != null) {
								while (model.getRowCount() > 0) {
									model.removeRow(model.getRowCount() - 1);
								}
							}
							
						}
					});
				}
			}
			{
				bottomPanel = new BottomPanel();
				GroupLayout bottomPanel1Layout = new GroupLayout((JComponent)bottomPanel);
				getContentPane().add(bottomPanel, BorderLayout.SOUTH);
				bottomPanel.setParent(this);
				bottomPanel.setPreferredSize(new java.awt.Dimension(0,24));
				bottomPanel.setLayout(bottomPanel1Layout);
				bottomPanel1Layout.setVerticalGroup(bottomPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGap(0, 24, Short.MAX_VALUE));
				bottomPanel1Layout.setHorizontalGroup(bottomPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGap(0, 0, Short.MAX_VALUE));
			}
			{
				pnlBase = new JPanel();
				getContentPane().add(pnlBase, BorderLayout.CENTER);
				GridBagLayout pnlBaseLayout = new GridBagLayout();
				
				pnlBase.setLayout(pnlBaseLayout);
				{
					pnlButtons = new javax.swing.JPanel();
					pnlBase.add(pnlButtons, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 2, 0, 2), 0, 0));
					pnlButtons.setLayout(new GridBagLayout());
					pnlButtons.setPreferredSize(new Dimension(87,30));
					{
						btnTrain = new javax.swing.JButton();
						pnlButtons.add(btnTrain, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
						btnTrain.setText("Tanítás");
						btnTrain.addActionListener((ActionEvent evt) -> btnTrainActionPerformed());
					}
					{
						progressBar = new javax.swing.JProgressBar();
						pnlButtons.add(progressBar, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 625, 9));
						progressBar.setStringPainted(true);
					}
				}
				{
					scrollTable = new javax.swing.JScrollPane();
					pnlBase.add(scrollTable, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 2, 0, 2), 0, 0));
					{
						gridTrainSet = new javax.swing.JTable();
						scrollTable.setViewportView(gridTrainSet);
						gridTrainSet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					}
				}
			}
		}
		mainMenu = new javax.swing.JMenuBar();
		GridBagConstraints gridBagConstraints;
		{
			gridBagConstraints = new java.awt.GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints.ipadx = 625;
			gridBagConstraints.ipady = 9;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
		}
		setJMenuBar(mainMenu);
		{
			menFile = new JMenu();
			mainMenu.add(menFile);
			menFile.setText("Fájl");
			{
				menuItemOpenTrainSet = new JMenuItem();
				menFile.add(menuItemOpenTrainSet);
				menuItemOpenTrainSet.setText("Tanítókészlet megnyitása");
				menuItemOpenTrainSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/open16.png")));
				menuItemOpenTrainSet.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						openTrainDataSetFromFile();
					}
				});
			}
			{
				menuItemSaveTrainSet = new JMenuItem();
				menFile.add(menuItemSaveTrainSet);
				menuItemSaveTrainSet.setText("Tanítókészlet mentése");
				menuItemSaveTrainSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/save16.png")));
				menuItemSaveTrainSet.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						saveTrainDataSetToFile();						
					}
				} );
			}
		}
		{
			menEdit = new JMenu();
			mainMenu.add(menEdit);
			menEdit.setText("Szerkesztés");
			menEdit.setToolTipText("Sor felvitele");
			{
				menuItemInsertTrainLine = new JMenuItem();
				menEdit.add(menuItemInsertTrainLine);
				menuItemInsertTrainLine.setText("Tanítósor felvitele");
				menuItemInsertTrainLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/add-row.png")));
				menuItemInsertTrainLine.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						inputVector();
					}
				});
			}
		}

		pack();
	}
 

    public void inputVector() {
        
        double[] be = DialogInputForTrainer.showInput(Mode.TRAIN);
    	
        if (be != null && be.length==NeuralNetwork.getInstance().getInputUnitNumber() + NeuralNetwork.getInstance().getOutputUnitNumber()) {
            Object[] row = new Object[ be.length ];
            try {
                for (int i = 0; i < be.length; i++) {
                    row[i] = be[i];
                }
                model.addRow(row);
            } catch (Exception e) {
            	JOptionPane.showMessageDialog(App.getInstance(), "Hibás bevitel, kérem ellenőrizze a vektorok dimenzióját!", "Figyelem!", JOptionPane.WARNING_MESSAGE);
    			throw new RuntimeException(e.getMessage());
            }
            
        } else {
        	JOptionPane.showMessageDialog(App.getInstance(), "Hibás bevitel, kérem ellenőrizze a vektorok dimenzióját!", "Figyelem!", JOptionPane.WARNING_MESSAGE);
        	
        }
    }

    

    private void btnTrainActionPerformed() { 

		if (job == null || !job.isRun()) {
			job = new GradiantTrainer();
			if (model != null && model.getRowCount() > 0) {
				dialogResult res = DialogTrainStart.showTrain(job);
				if (res == dialogResult.okButton) {
					NeuralNetwork.getInstance().setTrainer(job);
					NeuralNetwork.getInstance().setVerbose(job.isVerbose());

					for (int r = 0; r < model.getRowCount(); r++) {
						double[] trainSet = new double[NeuralNetwork.getInstance().getInputUnitNumber()];
						double[] targetSet = new double[NeuralNetwork.getInstance().getOutputUnitNumber()];
						for (int c = 0; c < trainSet.length; c++) {
							System.err.println(c);
							trainSet[c] = (double) model.getValueAt(r, c);
						}
						for (int c = trainSet.length; c < model.getColumnCount(); c++) {
							targetSet[c - trainSet.length] = (double) model.getValueAt(r, c);
						}
						job.getDataSet().add(DataSet.create(trainSet, targetSet));
					}

					new Thread(job).start();
				}

			} else {
				JOptionPane.showMessageDialog(App.getInstance(),
						"Kérem adjon meg tanító adatot, addig a tanítás nem indítható!", "Figyelem!",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(App.getInstance(), "Amíg a tanítás nem ér véget addig nem indítható új!",
					"Figyelem!", JOptionPane.WARNING_MESSAGE);
		}
    }

    private void btnInsertForTrainActionPerformed() { 
        inputVector();
    }
  
    /**
     * Tanító adatok kimentése fájlba
     */
	void saveTrainDataSetToFile() {
		JFileChooser fileChooser = new JFileChooser(F.getDesktopPath());
		fileChooser.setDialogTitle("Tanító adatkészlet mentése");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Mesterséges neurális hálózat tanító adat file","dat"));
		int res = fileChooser.showSaveDialog(App.getInstance());
		int trainSize = NeuralNetwork.getInstance().getInputUnitNumber();
		int targetSize = NeuralNetwork.getInstance().getOutputUnitNumber();
		if (res == JFileChooser.APPROVE_OPTION) {
			@SuppressWarnings("resource")
			FileOutputStream fos = null;
			try {
				String filename  = fileChooser.getSelectedFile().toString();
            	if (!filename.endsWith("dat")) {
            		filename+=".dat";
            	}
                File fileToSave = new File(filename);
				fos = new FileOutputStream(fileToSave);
				@SuppressWarnings("resource")
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				
				for (int r = 0; r < model.getRowCount(); r++) {
					String line = "";
					for (int c = 0; c < trainSize + targetSize; c++) {

						line += model.getValueAt(r, c);
						
						if (c<trainSize-1 || c>trainSize) {
							line+=",";
						} else {
							line+=";";
						}

					}
					
					if (line.length() > 0) {
						line = line.substring(0, line.length() - 1);
					}
					osw.write(line+"\r\n");
				}
				osw.flush();
				osw.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(App.getInstance(), "A megadott fájlhoz nincs hozzáférési engedélye!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(App.getInstance(), "A mentés nem sikerült! A megadott fájlhoz nincs írási engedélye!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
						
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {				
						log.warn(e.getMessage());
					}
				}
			}
		}
	}

    /**
     * Tanító adatok megnyitása és importálása
     */
    void openTrainDataSetFromFile() { 
        JFileChooser fileChooser = new JFileChooser(F.getDesktopPath());
        fileChooser.setDialogTitle("Tanító adatkészlet megnyitása");
        int res = fileChooser.showOpenDialog(App.getInstance());
        model = null;
        String error = null;
        int trainSize = 0;
        int targetSize = 0;
        if (res == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                for (String line; (line = br.readLine()) != null;) {

                    String[] parts = line.split(";");
                    if (parts.length > 0) {
                        try {
                            String[] trainSet = parts[0].split(",");
                            String[] targetSet = new String[] {}; 
                            if (parts.length == 2) {   
                            	targetSet = parts[1].split(",");
                            }
                            Object[] row;
                            if (model == null) {
                                String[] header = new String[NeuralNetwork.getInstance().getInputUnitNumber() + NeuralNetwork.getInstance().getOutputUnitNumber()];
                                for (int h = 0; h < NeuralNetwork.getInstance().getInputUnitNumber(); h++) {
                                    header[h] = "Bemenet #" + (h);
                                }
                                if (NeuralNetwork.getInstance().getOutputUnitNumber()==targetSet.length) {
                                for (int h = 0; h < NeuralNetwork.getInstance().getOutputUnitNumber(); h++) {
                                    header[trainSet.length + h ] = "Cél #" + (h);
                                }
                                }
                                model = new DefaultTableModel(header, 0);
                                trainSize = trainSet.length;
                                targetSize = targetSet.length;
                            }
                            row = new Object[model.getColumnCount()];

                            if (NeuralNetwork.getInstance().getInputUnitNumber() == trainSet.length) {
                            	for (int h = 0; h < trainSize; h++) {
                            		row[h] = Double.parseDouble(trainSet[h]);
                            	}
                            }
                            if (NeuralNetwork.getInstance().getOutputUnitNumber() == targetSet.length) {                                
                            	for (int h = 0; h < targetSize; h++) {
                            		row[trainSet.length + h] = Double.parseDouble(targetSet[h]);
                            	}
                            }
                            
                            String hiba = "";
                            if (NeuralNetwork.getInstance().getInputUnitNumber() != trainSet.length) {
                                hiba += "Bemeneti vektor dimenziója nem egyezik a neurális hálózat bemenetével";
                            }
                             
                            if (hiba.length() == 0) {
                                hiba = "Ok";
                            }
                            if (targetSet.length==0) {
                            	hiba = "Teszt";
                            }
                            model.addRow(row);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            error = "<html>Hiba történt a feldolgozás során, nem sikerült <br> minden sort betölteni kérem ellenőrizze a készletet!</html>";
                        }
                    }  

                }
                if (error != null) {
                    JOptionPane.showMessageDialog(App.getInstance(), error, "Hiba történt", JOptionPane.ERROR_MESSAGE);
                }
                gridTrainSet.setModel(model);
                gridTrainSet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                // line is not visible here.
            } catch (FileNotFoundException ex) {
                log.warn(ex);
            } catch (IOException ex) {
                log.warn(ex);
            }
        }


    }
    
     

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public Trainer getJob() {
		return job;
	}

	public void setJob(Trainer job) {
		this.job = job;
	}

	
   
}
