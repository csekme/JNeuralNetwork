package internalwindows;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import application.App;
import framework.DoubleField;
import framework.IntegerField;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Trainer;

/**
 * Neurális Hálózatot tanító algoritmust paraméterező ablak
 * beviteli képernyő
 * @author Csekme Krisztián | KSQFYZ
 * @see NeuralNetwork
 * @see Trainer
 */
public class DialogTrainStart extends javax.swing.JDialog {

	private static final long serialVersionUID = -1362749894182428436L;
	
	public static enum dialogResult { okButton, cancelButton }
	public dialogResult action;
		
	private JLabel lblSorrend;
	private JPanel pnlButtons;
	private JLabel lblVerbose;
	private JLabel lblTitle;
	private JButton btnCancel;
	private JButton btnTrain;
	private JCheckBox chkSorrend;
	private DoubleField dblStopCondition;
	private JLabel lblStopCondition;
	private JCheckBox chkVerbose;
	private IntegerField dblEpoch;
	private JLabel lblEpoch;
	private DoubleField dblBrave;
	private JLabel lblBraveFactor;
	
	//Taníító algoritmus
	private Trainer trainer;
	

	/**
	 * Elrejtett konstruktor showTrain függvény gyártja le és
	 * jeleníti meg
	 * @param frame szülőablak
	 * @see #showTrain(Trainer)
	 */
	private DialogTrainStart(JFrame frame) {
		super(frame);
		initGUI();
	}

	/**
	 * Generált felületet leíró metódus
	 */
	private void initGUI() {
		{
			GridBagLayout thisLayout = new GridBagLayout();
			getContentPane().setLayout(thisLayout);
			getContentPane().setBackground(new java.awt.Color(255,255,255));
			this.setTitle("Tanítás indítása");
			{
				lblBraveFactor = new JLabel();
				getContentPane().add(lblBraveFactor, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
				lblBraveFactor.setText("Bátorsági faktor");
			}
			{
				dblBrave = new DoubleField();
				getContentPane().add(dblBrave, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				dblBrave.setPreferredSize(new java.awt.Dimension(100, 26));
			}
			{
				lblEpoch = new JLabel();
				getContentPane().add(lblEpoch, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
				lblEpoch.setText("Epizódok száma");
			}
			{
				dblEpoch = new IntegerField();
				getContentPane().add(dblEpoch, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				dblEpoch.setPreferredSize(new java.awt.Dimension(100, 26));
			}
			{
				lblSorrend = new JLabel();
				getContentPane().add(lblSorrend, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
				lblSorrend.setText("Véletlen sorrend");
			}
			{
				chkSorrend = new JCheckBox();
				getContentPane().add(chkSorrend, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
			}
			{
				pnlButtons = new JPanel();
				GridBagLayout pnlButtonsLayout = new GridBagLayout();
				getContentPane().add(pnlButtons, new GridBagConstraints(0, 7, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	 
				pnlButtons.setLayout(pnlButtonsLayout);
				{
					btnCancel = new JButton();
					pnlButtons.add(btnCancel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
					btnCancel.setText("Mégse");
					btnCancel.setBounds(92, 76, 42, 16);
					btnCancel.addActionListener( (ActionListener)->{ action = dialogResult.cancelButton; setVisible(false); }  );
				}
				{
					btnTrain = new JButton();
					pnlButtons.add(btnTrain, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
					btnTrain.setText("Tanítás");
					btnTrain.addActionListener( (ActionListener)->{ 
						if (dblEpoch.getIntegerValue()!=null) {
						action = dialogResult.okButton; 
						trainer.setShutdownCondition(dblStopCondition.getDoubleValue());
						trainer.setEpoch(dblEpoch.getIntegerValue());
						trainer.setBraveFactor(dblBrave.getDoubleValue() );
						trainer.setRandomSequence(chkSorrend.isSelected());
						trainer.setVerbose(chkVerbose.isSelected());
						setVisible(false); 
						
						} else {
							JOptionPane.showMessageDialog(App.getInstance(), "Kérem adja meg az epizódok számát, minimum 1 értékkel!", "Figyelem!", JOptionPane.WARNING_MESSAGE);
						}
						
					}  );
				}
			}
			{
				lblTitle = new JLabel();
				getContentPane().add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 4, 16, 4), 0, 0));
				lblTitle.setText("Hálózat tanítása");
				lblTitle.setFont(new java.awt.Font("Segoe UI",0,16));
			}
			{
				lblVerbose = new JLabel();
				getContentPane().add(lblVerbose, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
				lblVerbose.setText("Log vezetés");
			}
			{
				chkVerbose = new JCheckBox();
				getContentPane().add(chkVerbose, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
			}
			{
				lblStopCondition = new JLabel();
				getContentPane().add(lblStopCondition, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 12, 2, 2), 0, 0));
				lblStopCondition.setText("Leállási feltétel");
			}
			{
				dblStopCondition = new DoubleField();
				getContentPane().add(dblStopCondition, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
			}
		}
		this.setSize(441, 242);

	}
	
	/**
	 * Tanítóalgoritmust előparaméterező indító képernyő
	 * @param nn Neurális Hálózat referencia
	 * @return
	 */
	public static dialogResult showTrain(Trainer trainer) {
		if (trainer != null) {
			DialogTrainStart d = new DialogTrainStart(App.getInstance());
			d.setLocation(App.getInstance().getLocation().x + App.getInstance().getWidth() / 2 - d.getWidth() / 2,
					App.getInstance().getLocation().y + App.getInstance().getHeight() / 2 - d.getHeight() / 2);

			d.setModal(true);
			d.trainer = trainer;
			d.dblBrave.setDouble(trainer.getBraveFactor());
			d.chkVerbose.setSelected(trainer.isVerbose());
			d.dblStopCondition.setDouble(trainer.getShutdownCondition());
			d.setVisible(true);
			if (d.action==dialogResult.cancelButton) {
				return dialogResult.cancelButton;
			}
			return d.action;
		}
		return dialogResult.cancelButton;
	}

}
