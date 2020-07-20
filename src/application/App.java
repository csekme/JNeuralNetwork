package application;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import framework.BottomPanel;
import framework.BottomPanelElement;
import framework.F;
import framework.ribbonmenu.Button;
import framework.ribbonmenu.RibbonBar;
import framework.ribbonmenu.RibbonMenuItem;
import framework.ribbonmenu.Tab;
import internalwindows.IFrameClassifier;
import internalwindows.IFrameFunctionPlotter;
import internalwindows.IFrameInfo;
import internalwindows.IFrameLearnDiagram;
import internalwindows.IFrameNeuralNetworkPlotter;
import internalwindows.IFrameNeuralNetworkWizard;
import internalwindows.IFrameProcessingUnitEditor;
import internalwindows.IFrameRandomInput;
import internalwindows.IFrameTrainer;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Neuron;

/**
 * Az alkalmazás főablaka MDI szülőablak.
 * A program elindítását követően ez az ablak kerül a felhasználó elé
 * @author Csekme Krisztián | KSQFYZ
 */
public class App extends javax.swing.JFrame {

	private static final long serialVersionUID = -6711110562915031719L;
	
	/** File logger */
	static Logger flog = LogManager.getLogger("NeuralNetwork");
	static Logger log = LogManager.getLogger(App.class.getName());

	/** Főablak példánya globális eléréssel **/
	private static App app = null;

	final static Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    // Belső MDI gyermek ablakok
    public static IFrameNeuralNetworkWizard wizard = null;
    public static IFrameNeuralNetworkPlotter nnp = null;
    public static IFrameProcessingUnitEditor ueditor = null;
    public static IFrameRandomInput randomi = null;
    public static IFrameTrainer trainer = null;
    public static IFrameInfo info = null;
    public static IFrameClassifier diagram = null;
    public static IFrameLearnDiagram learningRate = null;

    //Lenti panel elemek 
    public BottomPanelElement bottomMessage;
    public BottomPanelElement bottomLayerNumber;
    public BottomPanelElement bottomNeronNumber;
    public BottomPanelElement bottomTrainPercent;

    private RibbonBar ribbonBar;
    
    //Package változók
    javax.swing.JDesktopPane desktop;
    BottomPanel bottomPanel;
    
    
    /**
     * Visszaadja a futó főablak pédányát Singletonként
     * első hívás alkalmával pédányosodik
     * @return Főablak
     * @see App#app
     */
    public static App getInstance() {
    	if (app== null) {
    		app = new App();
    	}
    	return app;
    }
    
    /**
     * Főablakot létrehozó konsruktor
     */
    @SuppressWarnings("resource")
	private App() {
        
    	initGUI();
    	
    	//MDI gyermek ablakok példányosítása
    	nnp = new IFrameNeuralNetworkPlotter();
        wizard = new IFrameNeuralNetworkWizard();
        randomi = new IFrameRandomInput();
        ueditor = new IFrameProcessingUnitEditor();
        trainer = new IFrameTrainer();
        info = new IFrameInfo();
        diagram = new IFrameClassifier();
        learningRate = new IFrameLearnDiagram();

        //MDI gyermek ablak példányok hozzáadása az asztalhoz
        {
	        desktop.add(nnp);
	        desktop.add(ueditor);
	        desktop.add(wizard);
	        desktop.add(randomi);
	        desktop.add(trainer);
	        desktop.add(info);
	        desktop.add(diagram);
	        desktop.add(learningRate);
        }
        //Ablak alján lévő panel inicializálása
        {
	        bottomMessage = bottomPanel.addLabel("Nincs létrehozva neurális hálózat...");
	        bottomLayerNumber = bottomPanel.addLabel("");
	        bottomPanel.addDivider();
	        bottomNeronNumber = bottomPanel.addLabel("");
	        bottomPanel.addDivider();
	        bottomTrainPercent = bottomPanel.addLabel("");
        }
        
        //Szalagmenü felépítése  
      
        ribbonBar.putColor(RibbonBar.color_tokens.ribbon_tab_foreground, Program.accentColor);
        ribbonBar.putColor(RibbonBar.color_tokens.ribbon_tab_selected_foreground, Program.accentColor);
        ribbonBar.putColor(RibbonBar.color_tokens.ribbon_tab_selected_strip_background, Program.accentColor);

        //Legfelsőbb szint
        Tab tbHome = ribbonBar.addTab("Kezdőlap");
        Tab tbNN = ribbonBar.addTab("Neurális hálózat");

        //Kiválasztjuk a home tabot alapértelmezetten kiválasztottnak
        tbHome.setSelected(true);

        { //Új Neurális hálózat létrehozása
	        Button btnNew = tbHome.addButton("Új");
	        btnNew.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/new.png")));
	        btnNew.addActionListener((ActionEvent e) -> {	        	 
	        	wizard.showWindow();
	        });
        }

		{ // Neurális hálózat megnyitása fájlból
			Button btnOpen = tbHome.addSlimButton("Megnyitás");
			btnOpen.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/open16.png")));
			btnOpen.addActionListener((ActionEvent e) -> {
				JFileChooser fileChooser = new JFileChooser(F.getDesktopPath());
				fileChooser.setFileFilter(new FileNameExtensionFilter("Mesterséges neurális hálózat file", "ann"));
				fileChooser.setDialogTitle("Neurális hálózat megnyitása");
				int res = fileChooser.showOpenDialog(this);
				if (res == JFileChooser.APPROVE_OPTION) {
					FileInputStream fis = null;
					try {
						File fileForOpen = fileChooser.getSelectedFile();
						fis = new FileInputStream(fileForOpen);
						ObjectInputStream ois = new ObjectInputStream(fis);
						NeuralNetwork.setInstance((NeuralNetwork) ois.readObject());
						ois.close();
						bottomMessage.setCaption("ANN: " + NeuralNetwork.getInstance().getName());
						App.nnp.setVisible(true);
						
					} catch (FileNotFoundException ex) {

						JOptionPane.showMessageDialog(this, "A kiválasztott fájl nem található!", "Figyelem!", JOptionPane.ERROR_MESSAGE);

					} catch (ClassCastException ex) {

						JOptionPane.showMessageDialog(this,	"A kiválasztott fájl elavult, nem kompatibilis a jelenlegi programverzóval!", "Figyelem!", JOptionPane.ERROR_MESSAGE);

					}  catch (ClassNotFoundException ex) {
						
						JOptionPane.showMessageDialog(this,	"A kiválasztott fájl ismeretlen formátumú nem nyitható meg!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
					
					} catch (IOException ex) {
						
						JOptionPane.showMessageDialog(this,	"A kiválasztott fájl nem nyitható meg, lehet hogy sérült!", "Figyelem!", JOptionPane.ERROR_MESSAGE);

					} finally {
						try {
							if (fis!=null) {
								fis.close();
							}
						} catch (IOException ex) {
							log.warn(ex.getMessage());
						}

					}
				}
			});
		}
		
		{ //Neurális hálózat megnyitása
	        Button btnSave = tbHome.addSlimButton("Mentés");
	        btnSave.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/save16.png")));
	        btnSave.addActionListener((ActionEvent e) -> {
	
	            JFileChooser fileChooser = new JFileChooser(F.getDesktopPath());
	            fileChooser.setDialogTitle("Neurális hálózat mentése");
	            fileChooser.setFileFilter(new FileNameExtensionFilter("Mesterséges neurális hálózat file","ann"));
	            
	            int res = fileChooser.showSaveDialog(this);
	            if (res == JFileChooser.APPROVE_OPTION) {
	                FileOutputStream fos = null;
	                try {
	                	String filename  = fileChooser.getSelectedFile().toString();
	                	if (!filename.endsWith("ann")) {
	                		filename+=".ann";
	                	}
	                    File fileToSave = new File(filename);
	                    fos = new FileOutputStream(fileToSave);
	                    ObjectOutputStream oos = new ObjectOutputStream(fos);
	                    oos.writeObject(NeuralNetwork.getInstance());
	                    oos.flush();
	                    oos.close();
	                } catch (FileNotFoundException ex) {
	            		JOptionPane.showMessageDialog(this, "A kiválasztott fájl nem menthető a megadott helyre!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
	                	
	                } catch (IOException ex) {
	                	ex.printStackTrace();
	             		JOptionPane.showMessageDialog(this, "Hiba történt a mentés során!", "Figyelem!", JOptionPane.ERROR_MESSAGE);

	                } finally {
	                    try {
	                    	if (fos!=null) {
	                    		fos.close();
	                    	}
	                    } catch (IOException ex) {
	                       log.warn(ex.getMessage());
	                    }
	                }
	            }
	
	        });
		}
		
        tbHome.setGroupName("Létrehozás");
        tbHome.addSeperator();
        
        { //Információs panel
	        Button btnInfo = tbHome.addButton("Info");
	        btnInfo.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/info.png")));
	        btnInfo.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					if (!isNeuralNetworkNull()) {
						info.setVisible( true );
					}

	            }
	        });
        }
        
        Button btnVector = tbNN.addButton("Bemeneti vektor");
        btnVector.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/vector.png")));
        btnVector.addActionListener((ActionEvent e) -> {
            if (!isNeuralNetworkNull()) {

                int be = NeuralNetwork.getInstance().getInputUnitNumber();
                String _input = JOptionPane.showInputDialog(App.this, "Kérem adjon meg egy bemeneti érték vektort a számjegyeket vesszővel ellátva");
                double[] input = new double[be];
                if (_input.split(",").length != be) {
                    JOptionPane.showMessageDialog(App.this, "A bemenet darabszáma nem egyezik a bemeneti réteg neuron számával!");

                } else {
                    String[] fragments = _input.split(",");

                    for (int i = 0; i < be; i++) {
                        input[i] = Double.parseDouble(fragments[i]);
                    }
                    try {
                        NeuralNetwork.getInstance().verbose = true;
                        NeuralNetwork.getInstance().stimulus(input);
                        NeuralNetwork.getInstance().verbose = false;             
                        log.trace( Arrays.toString(NeuralNetwork.getInstance().getY(NeuralNetwork.getInstance().getNumberOfLayers()-1)));                             
                        flog.trace("Kimenet {}" , Arrays.toString(NeuralNetwork.getInstance().getY(NeuralNetwork.getInstance().getNumberOfLayers()-1)));                                                     
                    } catch (NumberFormatException er) {
                        er.printStackTrace();
                        JOptionPane.showMessageDialog(App.this, "Hibás bevitel!");
                    }
                }

            }
        });
        Button btnWeights = tbNN.addSlimButton("Súlyok újrainicializálása");
        btnWeights.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/weights16.png")));
        btnWeights.addActionListener((ActionEvent e) -> {
            if (!isNeuralNetworkNull()) {
                NeuralNetwork.getInstance().initWeights();
            }
        });
        Button btnRandomInput = tbNN.addSlimButton("Véletlen értékek írása bemenetre");
        btnRandomInput.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/input16.png")));
        btnRandomInput.addActionListener((ActionEvent e) -> {
        	if (!isNeuralNetworkNull()) {
        		randomi.setVisible(true);
        	}

        });

        Button btnNEditor = tbNN.addButton("Neuron szerkesztő");
        btnNEditor.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/editor.png")));
        btnNEditor.addActionListener((ActionEvent e) -> {
        	if (!isNeuralNetworkNull()) {
                ueditor.initParams();
                ueditor.setVisible(true);
                ueditor.setCenter();
            }
        });

        tbNN.setGroupName("Szerkesztés");
        tbNN.addSeperator();

        Button btnDrawNN = tbNN.addButton("Hálózat kirajzolása");
        RibbonMenuItem itemDrawContinuisly = new RibbonMenuItem("Folytonos kirajzolás", true);
        itemDrawContinuisly.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                nnp.setDrawContinuously(itemDrawContinuisly.isSelected());
            });

        });
        btnDrawNN.addSubMenu(itemDrawContinuisly);
        btnDrawNN.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/nn.png")));
        btnDrawNN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!isNeuralNetworkNull()) {
            		  nnp.setVisible(true);
            	}   
            }
        });

        Button btnFunctions = tbNN.addButton("Aktivációs függvények");
        btnFunctions.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/sine.png")));

        btnFunctions.addSubMenu((ActionEvent e) -> {
            IFrameFunctionPlotter fp = new IFrameFunctionPlotter();
            desktop.add(fp);
            fp.setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/images/graph.png")));
            fp.setTitle("Sigmoid függvény");
            fp.show(Neuron.Activation.SIGMOID);
        }, "Sigmoid");

        btnFunctions.addSubMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IFrameFunctionPlotter fp = new IFrameFunctionPlotter();
                desktop.add(fp);
                fp.setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/images/graph.png")));
                fp.setTitle("Hiperbolikus tangens függvény");
                fp.show(Neuron.Activation.TANH);

            }
        }, "Hiperbolikus tangens");
        btnFunctions.addSubMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IFrameFunctionPlotter fp = new IFrameFunctionPlotter();
                desktop.add(fp);
                fp.setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/images/graph.png")));
                fp.setTitle("ReLU (rektifikált lineáris egység) függvény");
                fp.show(Neuron.Activation.RELU);

            }
        }, "ReLU");
        btnFunctions.addSubMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IFrameFunctionPlotter fp = new IFrameFunctionPlotter();
                desktop.add(fp);
                fp.setFrameIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/images/graph.png")));
                fp.setTitle("SoftPlus függvény");
                fp.show(Neuron.Activation.SOFTPLUS);

            }
        }, "SoftPlus");

        tbNN.addSeperator();
        Button btnTraining = tbNN.addButton("Hálózat tanítása");
        btnTraining.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/learn.png")));
        btnTraining.addActionListener((ActionEvent e) -> {
        	if (!isNeuralNetworkNull()) {
            trainer.setVisible(true);
        	}
        });
        
        Button btnLearningRate = tbNN.addButton("Tanulási görbe");
        btnLearningRate.setImage( new ImageIcon(getClass().getClassLoader().getResource("resources/images/learning_curve.png")) );
        btnLearningRate.addActionListener((ActionEvent e)->{ 
        	if (!isNeuralNetworkNull()) {
        	learningRate.setVisible(true);
        	}
        });
        
        Button btnResult = tbNN.addButton("Osztályozási térkép");
        btnResult.setImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/plot2.png")));
        btnResult.addActionListener(  (ActionEvent e)->{
        	if (!isNeuralNetworkNull()) {
        	diagram.setVisible(true);
        	}
        } );
        
        //Főablak ikon beállítása
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("resources/images/machine-learning.png")).getImage());
        
        //Időzítő háttér feladatok felületi információk frissítésére
        Timer timInfoPanel = new Timer(200, loopInfoPanel);
        timInfoPanel.start();
 
    }

    ActionListener loopInfoPanel = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (bottomMessage != null) {
                if (NeuralNetwork.getInstance() == null) {
                    bottomMessage.setCaption("Nincs létrehozva neurális hálózat...");
                    bottomLayerNumber.setCaption("");
                    bottomNeronNumber.setCaption("");
                } else {
                    bottomMessage.setCaption("ANN: " + NeuralNetwork.getInstance().getName());
                    bottomLayerNumber.setCaption("L:" + NeuralNetwork.getInstance().getNumberOfLayers());
                    bottomNeronNumber.setCaption("N:" + NeuralNetwork.getInstance().getSumOfUnits());

                }
            }
            bottomPanel.repaint();
        }

    };

    @Override
    public void setVisible(boolean value) {
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        super.setVisible(value);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
     private void initGUI() {

        desktop = new javax.swing.JDesktopPane() {

			private static final long serialVersionUID = 30827981250305159L;

			@Override
            public void paint(Graphics g) {
                g.setColor(Color.white);
                g.fillRect(0,0,getWidth(), getHeight());
                super.paint(g);
            }

        };
        ribbonBar = new  RibbonBar();
        bottomPanel = new  BottomPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mesterséges neurális hálózat keretrendszer | Verzió " + Program.VERSION + " Build " + Program.build );

        desktop.setDoubleBuffered(true);
        desktop.setOpaque(false);

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktop)
                .addGap(0, 0, 0))
            .addComponent(ribbonBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1548, Short.MAX_VALUE)
            .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ribbonBar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(desktop)
                .addGap(0, 0, 0)
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    } 

     
	/**
	 * A függvény leellenőrzi hogy van a Neurális hálózat példány ha nincs az esetbe
	 * egy figyelmeztető üzenetet is ad a felhasználó részére hogy hozzon létre
	 * egyet, vagy töltsön be fájlrendszerről 
	 * @return 
	 */
	public boolean isNeuralNetworkNull() {
		if (NeuralNetwork.getInstance() == null) {
			JOptionPane.showMessageDialog(this,
					"Nincsen létrehozva Neurális Hálózat, kérem hozzon létre vagy nyisson meg egyet!", "Figyelem!",
					JOptionPane.WARNING_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * Visszaadja a főablak MDI konténerét
	 * @return
	 */
	public javax.swing.JDesktopPane getDesktop() {
		return desktop;
	}

	/**
	 * Visszaadja a főablak szalag menü példányát
	 * @return
	 */
	public RibbonBar getRibbonBar() {
		return ribbonBar;
	}

 
}
