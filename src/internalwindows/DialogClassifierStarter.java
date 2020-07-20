package internalwindows;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import application.App;
import framework.ColorCellRenderer;
import framework.DoubleField;
import math.LinearMath;
import neuralnetwork.NeuralNetwork;
import plotter.Range;

public class DialogClassifierStarter extends javax.swing.JDialog {

	private static final long serialVersionUID = -3132729526587755538L;
	private DoubleField doubleXTo;
	private DoubleField doubleYFrom;
	private JLabel lblYRange;
	private JLabel lblSeparator1;
	private DoubleField doubleXFrom;
	private JLabel lblXRange;
	private DoubleField doubleClassDensity;
	private JLabel lblOsztSur;
	private JButton btnCancel;
	private JLabel lblSeparator2;
	private DoubleField doubleYTo;
	private JScrollPane scrollGrid;
	private JPanel pnlSetup;
	private JButton btnStart;
	private JPanel pnlGombok;
	private JPanel pnlGrid;
	JPopupMenu pop;
	JTable gridColors;
	
	private Random rnd = new Random();

	public static enum dialogResult { okButton, cancelButton }
	public dialogResult action;
	
	
	/**
	 * Konstruktor példányosítása szülőablak referencia segítségével
	 * gyártófüggvény segítségével hívható
	 * @param frame szülőablak
	 * @see #showClassifier()
	 */
	private DialogClassifierStarter(JFrame frame) {
		super(frame);
		initGUI();
		initControls();
		
	}
	
	/**
	 * Kontrolok inicializációja
	 */
	private void initControls() {
		
		/* Start gomb eseménye */
		btnStart.addActionListener( (ActionListener)->{
			action = dialogResult.okButton; 
			if (doubleXFrom.getDoubleValue()!=null && doubleXTo.getDoubleValue()!=null && doubleXTo.getDoubleValue()>doubleXFrom.getDoubleValue()) {
				NeuralNetwork.getInstance().setClassXAxis(new Range(doubleXFrom.getDoubleValue(),doubleXTo.getDoubleValue()));
			} else {
				JOptionPane.showMessageDialog(App.getInstance(), "Hibás bevitel az X tengelyre!","Figyelem!",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if (doubleYFrom.getDoubleValue()!=null && doubleYTo.getDoubleValue()!=null && doubleYTo.getDoubleValue()>doubleYFrom.getDoubleValue()) {
				NeuralNetwork.getInstance().setClassYAxis(new Range(doubleYFrom.getDoubleValue(),doubleYTo.getDoubleValue()));				
			} else {
				JOptionPane.showMessageDialog(App.getInstance(), "Hibás bevitel az Y tengelyre!","Figyelem!",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			if (doubleClassDensity.getDoubleValue()!=null) {
				NeuralNetwork.getInstance().setClassMapDesnity(doubleClassDensity.getDoubleValue());
			}
			
			setVisible(false);
		});
		
		/* Mégse gomb eseménye */
		btnCancel.addActionListener( (ActionListener)->{
			action = dialogResult.cancelButton; 
			setVisible(false);
		});
		
		pop = new JPopupMenu();
		JMenuItem menC = new JMenuItem("Szín kiválasztása");
		menC.addActionListener(  (ActionListener)->{
			
			if (gridColors.getSelectedRow()>-1) {
				String token = gridColors.getValueAt(gridColors.getSelectedRow(), 0).toString();
				String sc  = gridColors.getValueAt(gridColors.getSelectedRow(), 1).toString();
				Color c = StringToColor(sc);
				c = JColorChooser.showDialog(App.getInstance(), "Kérem válassza ki az új színt", c);
				if (c!=null) {
					NeuralNetwork.getInstance().getClm().put(token, c);
					reloadColorModel();
				}
			}	
		} );
		
		pop.add(menC);
		 
		gridColors.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseReleased(MouseEvent e) {
		        int r = gridColors.rowAtPoint(e.getPoint());
		        if (r >= 0 && r < gridColors.getRowCount()) {
		        	gridColors.setRowSelectionInterval(r, r);
		        } else {
		        	gridColors.clearSelection();
		        }

		        int rowindex = gridColors.getSelectedRow();
		        if (rowindex < 0)
		            return;
		        if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
		             
		            pop.show(e.getComponent(), e.getX(), e.getY());
		        }
		    }
		});
	}
	
	/**
	 * Felületet leító metódus
	 */
	private void initGUI() {
		this.setSize(508, 306);
		{
			GridBagLayout thisLayout = new GridBagLayout();
			getContentPane().setLayout(thisLayout);
			{
				pnlGrid = new JPanel();
				GridBagLayout pnlGridLayout = new GridBagLayout();
				getContentPane().add(pnlGrid, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				pnlGrid.setLayout(pnlGridLayout);
				{
					scrollGrid = new JScrollPane();
					pnlGrid.add(scrollGrid, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
					{
						gridColors = new JTable() {
							
							 private static final long serialVersionUID = 1L;

						        @Override
								public boolean isCellEditable(int row, int column) {                
						                return false;               
						        }       
						};
						scrollGrid.setViewportView(gridColors);
						
						gridColors.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								if (gridColors.columnAtPoint(e.getPoint())==1 ) {
									if (gridColors.getSelectedRow()>-1) {
										String token = gridColors.getValueAt(gridColors.getSelectedRow(), 0).toString();
										String sc  = gridColors.getValueAt(gridColors.getSelectedRow(), 1).toString();
										Color c = StringToColor(sc);
										c = JColorChooser.showDialog(App.getInstance(), "Kérem válassza ki az új színt", c);
										if (c!=null) {
											NeuralNetwork.getInstance().getClm().put(token, c);
											reloadColorModel();
										}
									}
								}
							}
						});
					
					}
				}
			}
			{
				pnlGombok = new JPanel();
				GridBagLayout pnlGombokLayout = new GridBagLayout();
				getContentPane().add(pnlGombok, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				pnlGombok.setLayout(pnlGombokLayout);
				{
					btnStart = new JButton();
					pnlGombok.add(btnStart, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnStart.setText("Indítás");
				}
				{
					btnCancel = new JButton();
					pnlGombok.add(btnCancel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					btnCancel.setText("Mégse");
				}
			}
			{
				pnlSetup = new JPanel();
				GridBagLayout pnlSetupLayout = new GridBagLayout();
				getContentPane().add(pnlSetup, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				pnlSetup.setLayout(pnlSetupLayout);
				{
					doubleXFrom = new DoubleField();
					pnlSetup.add(doubleXFrom, new GridBagConstraints(1, -1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				}
				{
					lblXRange = new JLabel();
					pnlSetup.add(lblXRange, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
					lblXRange.setText("X-tengely értéktartomány");
				}
				{
					lblSeparator1 = new JLabel();
					pnlSetup.add(lblSeparator1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
					lblSeparator1.setText("-");
				}
				{
					doubleXTo = new DoubleField();
					pnlSetup.add(doubleXTo, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				}
				{
					lblYRange = new JLabel();
					pnlSetup.add(lblYRange, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
					lblYRange.setText("Y-tengely értéktartomány");
				}
				{
					doubleYFrom = new DoubleField();
					pnlSetup.add(doubleYFrom, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				}
				{
					doubleYTo = new DoubleField();
					pnlSetup.add(doubleYTo, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				}
				{
					lblSeparator2 = new JLabel();
					pnlSetup.add(lblSeparator2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
					lblSeparator2.setText("-");
				}
				{
					lblOsztSur = new JLabel();
					pnlSetup.add(lblOsztSur, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
					lblOsztSur.setText("Osztályozási s\u0171r\u0171ség");
				}
				{
					doubleClassDensity = new DoubleField();
					pnlSetup.add(doubleClassDensity, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				}
			}

		}
		

	}
	
	/**
	 * A megadott színt RGB adatokra bekódolja a tábláztat cella értékére
	 * @param color kiválasztott szín
	 * @return Kódolt szín adatok ;-vel elválasztva R;G;B
	 * @see #StringToColor(String)
	 * @see java.awt.Color
	 */
	public static String ColorToString(Color color) {
		return Integer.toString(color.getRed()) + ";" + Integer.toString(color.getGreen()) + ";" + Integer.toString(color.getBlue());
	}
	
	/**
	 * A megadott {R;G;B} konvenciók által összállítot szín szöveges leírását
	 * visszaalakítja Color színné.
	 * @param colorString A szín szöveges leírása R;G;B formátumban
	 * @return visszatér a kódólt színnel
	 * @see #ColorToString(Color)
	 * @see java.awt.Color
	 */
	public static Color StringToColor(String colorString) {
		if (colorString==null)return null;
		String[] fragments = colorString.split(";");
		if (fragments.length==3) {
			int r = Integer.parseInt(fragments[0]);
			int g = Integer.parseInt(fragments[1]);
			int b = Integer.parseInt(fragments[2]);
			return new Color(r,g,b);
		}
		return null;
	}
 
	/**
	 * Tábla model újratöltése
	 */
	public void reloadColorModel() {
		NeuralNetwork nn = NeuralNetwork.getInstance();
		if (nn != null) {
			DefaultTableModel model = new DefaultTableModel(new Object[] { "Tanító cél vektor", "Hozzárendelt szín" },0);
			String list = "";
			for (int i = 0; i < nn.getDataSet().size(); i++) {
				String token = LinearMath.vectorToString(nn.getDataSet().get(i).getTargetVector());
				Color c = null;
				try {
					c = nn.getClm().get(token);
				} catch (NullPointerException e) {
				}
				if (c == null) {
					c = new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
					nn.getClm().put(token, c);
				}
				String[] _list = list.split("#");
				boolean van = false;
				for (int r=0; r<_list.length; r++) {
					if (_list[r].equals(token)) {
						van = true;
					}
				}
				if (!van) {
					list+=token+"#";
					model.addRow(new Object[] { token, ColorToString(c) });
								
				}
			}

			gridColors.setModel(model);
			gridColors.getColumnModel().getColumn(1).setCellRenderer(new ColorCellRenderer());
		}
	}
	
	/**
	 * A függvényhívás, legyártja a megjelenítendő ablakot, és annak bezárásakor
	 * visszatér az eredménnyel a dialogResult okButton, cancelButton 
	 * @return dialogResult
	 * @see dialogResult
	 */
	public static dialogResult showClassifier() {
		NeuralNetwork nn = NeuralNetwork.getInstance();
		if (nn!=null) {
			DialogClassifierStarter d = new DialogClassifierStarter(App.getInstance());
			d.setLocation(App.getInstance().getLocation().x + App.getInstance().getWidth() / 2 - d.getWidth() / 2,
					App.getInstance().getLocation().y + App.getInstance().getHeight() / 2 - d.getHeight() / 2);

			if (NeuralNetwork.getInstance().getClassXAxis()==null) {
				//TODO min max keresést lehetne alkalmazni
				NeuralNetwork.getInstance().setClassXAxis(new Range(-2,2));
			}
			if (NeuralNetwork.getInstance().getClassYAxis()==null) {
				//TODO min max keresést lehetne alkalmazni
				NeuralNetwork.getInstance().setClassYAxis(new Range(-2,2));
			}
			
			d.doubleXFrom.setDouble(NeuralNetwork.getInstance().getClassXAxis().getFrom());
			d.doubleXTo.setDouble( NeuralNetwork.getInstance().getClassXAxis().getTo() );
			
			d.doubleYFrom.setDouble(NeuralNetwork.getInstance().getClassYAxis().getFrom());
			d.doubleYTo.setDouble( NeuralNetwork.getInstance().getClassYAxis().getTo() );
			
			d.reloadColorModel();
			
			d.doubleClassDensity.setDouble(nn.getClassMapDesnity());
			
			d.setModal(true);
			d.setVisible(true);
			if (d.action==dialogResult.cancelButton) {
				return dialogResult.cancelButton;
			} 
			return d.action;
		}	
		return dialogResult.cancelButton;
	}

}
