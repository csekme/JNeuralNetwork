package internalwindows;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import application.App;
import framework.DoubleVectorField;
import neuralnetwork.NeuralNetwork;

/**
 * A Neurális Hálózatot tanító adat felvitelét segítő képernyő 
 * @author Csekme krisztián | KSQFYZ
 * @see NeuralNetwork
 * @see Trainer
 */
public class DialogInputForTrainer extends javax.swing.JDialog {
 
	private static final long serialVersionUID = -7204736923674314601L;

	boolean handled = false;
	boolean okButton = false;
	
	public static enum Mode {
		TRAIN, TEST
	}

	private JLabel lblInput;
	private JButton btnMegse;
	private JButton btnFelvitel;
	private JPanel pnlButton;
	private JLabel lblTitle;
	private JLabel lblDescription;
	private DoubleVectorField vecTarget;
	private JLabel lblTargetVektor;
	private DoubleVectorField vecInput;

	private DialogInputForTrainer(JFrame frame) {
		super(frame);
		initGUI();
	}

	public static double[] showInput(Mode mode) {

		DialogInputForTrainer d = new DialogInputForTrainer(App.getInstance());
		d.setModal(true);
		if (mode == Mode.TEST) {
			d.lblTitle.setText("Teszt adat felvitele");
			d.vecTarget.setEnabled(false);
		} else {
			d.lblTitle.setText("Tanító adat felvitele");			
		}
		// Középre helyezés
		d.setLocation(App.getInstance().getLocation().x + App.getInstance().getWidth() / 2 - d.getWidth() / 2,
				App.getInstance().getLocation().y + App.getInstance().getHeight() / 2 - d.getHeight() / 2);
		d.setVisible(true);
		
		while (!d.handled) {
			d.setVisible(true);
			
		}
		
		if (!d.okButton) {
			return null;
		}

		if (mode == Mode.TRAIN && d.vecInput.getText().length()==0 && d.vecTarget.getText().length()==0) {
			JOptionPane.showMessageDialog(App.getInstance(), "Hibás bevitel, kérem ellenőrizze a vektorok dimenzióját!", "Figyelem!", JOptionPane.WARNING_MESSAGE);
			throw new RuntimeException("Nem adott meg minden adatot!");
		} else if (mode == Mode.TEST && d.vecInput.getText().length()==0) {
			JOptionPane.showMessageDialog(App.getInstance(), "Hibás bevitel, kérem ellenőrizze a vektorok dimenzióját!", "Figyelem!", JOptionPane.WARNING_MESSAGE);		
			throw new RuntimeException("Nem adott meg minden adatot!");
		}

		Double[] i = d.vecInput.getDoubleVector();
		Double[] t = d.vecTarget.getDoubleVector();

		if (mode == Mode.TRAIN) {
			double res[] = new double[i.length + t.length];
			for (int in = 0; in < res.length; in++) {
				if (in < i.length) {
					res[in] = i[in];
				} else {
					res[in] = t[in - i.length];
				}
			}
			return res;
		}
		double res[] = new double[i.length];
		for (int in = 0; in < res.length; in++) {
			res[in] = i[in];
		}
		return res;

	}

	/**
	 * Generált függvény a felület leírására
	 */
	private void initGUI() {
		this.setSize(586, 207);

		GridBagLayout thisLayout = new GridBagLayout();
		getContentPane().setLayout(thisLayout);

		getContentPane().setBackground(new java.awt.Color(255, 255, 255));

		this.setResizable(false);
		{
			lblInput = new JLabel();
			getContentPane().add(lblInput, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
			lblInput.setText("Bemeneti vektor");
		}
		{
			vecInput = new DoubleVectorField();
			getContentPane().add(vecInput, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 4), 0, 0));
		}
		{
			lblTargetVektor = new JLabel();
			getContentPane().add(lblTargetVektor, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(2, 4, 2, 2), 0, 0));
			lblTargetVektor.setText("Cél vektor");
		}
		{
			vecTarget = new DoubleVectorField();
			getContentPane().add(vecTarget, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 4), 0, 0));
		}
		{
			lblDescription = new JLabel();
			getContentPane().add(lblDescription, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, new Insets(2, 4, 2, 4), 0, 0));
			lblDescription.setText(
					"<html>A vektor megadási konvenció a lebeg\u0151pontos számok .-al írandók, az elemek elválasztása pedig ,-vel történik. Minta a bevitelre: <b>3,-5.1,2.06,4,-1</b></html>");
		}
		{
			lblTitle = new JLabel();
			getContentPane().add(lblTitle, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(8, 4, 8, 4), 0, 0));
			lblTitle.setText("Tanító, vagy tesztadat felvitele");
			lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 16));
		}
		{
			pnlButton = new JPanel();
			GridBagLayout pnlButtonLayout = new GridBagLayout();
			getContentPane().add(pnlButton, new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			pnlButton.setLayout(pnlButtonLayout);
			{
				btnFelvitel = new JButton();
				pnlButton.add(btnFelvitel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
				btnFelvitel.setText("Felvitel");
				btnFelvitel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						btnFelvitelActionPerformed();
					}
				});
			}
			{
				btnMegse = new JButton();
				pnlButton.add(btnMegse, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				btnMegse.setText("Mégse");
				btnMegse.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						btnMegseActionPerformed();
					}
				});
			}
		}

	}
	
	void btnFelvitelActionPerformed() {
		
		okButton = true;
		handled = true;
		setVisible(false);
	}
	
	void btnMegseActionPerformed() {
		handled = true;
		setVisible(false);
	}

}
