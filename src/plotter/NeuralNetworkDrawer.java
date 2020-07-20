package plotter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import neuralnetwork.NeuralNetwork;
import neuralnetwork.Neuron;

/**
 * Neurális hálózat vizuális megjelentése
 * @author Csekme Krisztián | KSQFYZ
 */
public class NeuralNetworkDrawer extends Component {

	private static final long serialVersionUID = 5031900078143847390L;

	//Neurális hálózat referencia
    private NeuralNetwork nn = null;
    
    //Szín és dimenzionális beállítások
    final private Color clBackground = Color.white;
    final private int pr_r = 30;
    final private int left_margin = 50;
    final private int top_margin = 80;
    final private int vertical_space = 50;
    final private int horizontal_space = 150;
    
    private int max_neuron = 0;
    private int number_of_layer = 0;
    
    //Szinapszisok súly tartománya
    private Range rangeSynapseWeights;
    //Neuronok színtartománya
    private Range rangeNeuronOutputValues;

    /**
     * Neurális network referencia alapján a hálózatot kirajzolását előkészítő
     * függvény.
     * @param nn 
     */
    public void draw(NeuralNetwork nn) {
        this.nn = nn;
        
        rangeSynapseWeights = determineSynapseRange();
        rangeNeuronOutputValues = determineNeuronRange();
        
        max_neuron = 0;
        number_of_layer = 0;
        if (nn != null) {
            number_of_layer = nn.getLayers().size();
            for (int l = 0; l < number_of_layer; l++) {
                if (nn.getLayers().get(l).getNeurons().size() > max_neuron) {
                    max_neuron = nn.getLayers().get(l).getNeurons().size();
                }
            }
            //Kirajzolandó terület meghatározása
            setMinimumSize(new Dimension(left_margin + (number_of_layer * horizontal_space) + (number_of_layer * pr_r), top_margin + (max_neuron * vertical_space) + (max_neuron * vertical_space)));
            setPreferredSize(new Dimension(left_margin + (number_of_layer * horizontal_space) + (number_of_layer * pr_r), top_margin + (max_neuron * vertical_space) + (max_neuron * vertical_space)));
            setSize(new Dimension(left_margin + (number_of_layer * horizontal_space) + (number_of_layer * pr_r), top_margin + (max_neuron * vertical_space) + (max_neuron * vertical_space)));
            getParent().setBackground(clBackground);
        }
        repaint();
    }

    /**
     * Tényleges kirajzolást végző metódus
     * TODO nagy neuronszám esetén jelentősen lassúl a kirajzolás
     * ezért célszerű lenne valamilyen formában korlátozni a kirajzoltatást
     * @param gr 
     */
    @SuppressWarnings("cast")
	@Override
    public void paint(Graphics gr) throws ArrayIndexOutOfBoundsException {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(clBackground);
        //élmosás engedélyezése némi performancia veszteséggel jár TODO:  megvizsgálandó hogy nagy neuronszám esetén esetleg
        // kikapcsolhatóvá tenni
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if (nn != null) {

            for (int l = 0; l < nn.getLayers().size(); l++) {
                int shift_vertical = (int) ((max_neuron - nn.getLayers().get(l).getNeurons().size()) / 2);
                for (int pr = 0; pr < nn.getLayers().get(l).getNeurons().size(); pr++) {
                    double max = 0;
                    for (int prc = 0; prc < nn.getLayers().get(l).getNeurons().size(); prc++) {
                        if (nn.getLayers().get(l).getNeurons().get(prc).y > max) {
                            max = nn.getLayers().get(l).getNeurons().get(prc).y;
                        }

                    }
                    Neuron p = nn.getLayers().get(l).getNeurons().get(pr);

                    if (l < nn.getLayers().size() - 1) {
                        int shift_vertical_2 = (int) ((max_neuron - nn.getLayers().get(l + 1).getNeurons().size()) / 2);

                        for (int pp = 0; pp < nn.getLayers().get(l + 1).getNeurons().size(); pp++) {
                            g.setColor(Color.RED);
                            g.setColor(getSynapseColor(nn.getLayers().get(l + 1).getNeurons().get(pp).weights[pr]));
                            g.setStroke(new BasicStroke(3.0f));
                            //Ha a súly nagyobb mint nulla
                            if (nn.getLayers().get(l + 1).getNeurons().get(pp).weights[pr] != 0.0) {
                                g.drawLine(left_margin + ((l) * pr_r) + l * horizontal_space + pr_r / 2, shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space + pr_r / 2,
                                        left_margin + ((l + 1) * pr_r) + (l + 1) * horizontal_space + pr_r / 2, shift_vertical_2 * (vertical_space + pr_r) + top_margin + pp * pr_r + pp * vertical_space + pr_r / 2);
                            }
                        }
                    }

                    

                    int __x = left_margin + l * pr_r + l * horizontal_space;
                    int __y = shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space;
                    
                    
                    if (p.getBias() != null) {
                        g.setColor(getNeuronColor(p.getBias()));
                        int __bx = left_margin + l * pr_r + l * horizontal_space - horizontal_space / 4;
                        int __by = shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space - 41;
                        
                        if (p.getBiasWeight()!=null && p.getBiasWeight()!=0) {
                            g.setColor(new Color(0.5f,0.5f,0.5f,0.8f));
                            g.setStroke(new BasicStroke(5.0f));
                            g.drawLine( __bx + 6 , __by + 4, __x - pr_r /2 + 14, __y + pr_r/2);
                        
                            g.setColor(getNeuronColor(p.getBiasWeight()));
                            g.setStroke(new BasicStroke(3.0f));
                            g.drawLine( __bx + 6 , __by + 4, __x - pr_r /2 + 14, __y + pr_r/2);
                        }
                        g.setColor(getNeuronColor(p.getBias()));
                        g.fillOval(__bx, __by, pr_r - 8, pr_r - 8);
                        g.setColor(clBackground);
                        g.setStroke(new BasicStroke(5.0f));
                        g.drawOval(left_margin + l * pr_r + l * horizontal_space - horizontal_space / 4, shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space - 41, pr_r - 8, pr_r - 8);
                        g.setStroke(new BasicStroke(0.1f));
                        g.setColor(Color.black);
                        g.drawOval(left_margin + l * pr_r + l * horizontal_space - horizontal_space / 4, shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space - 41, pr_r - 8, pr_r - 8);
                        
                        g.setColor(new Color(0.4f, 0.4f, 0.4f, 0.8f));
                        g.setFont(g.getFont().deriveFont(12f));
                        g.drawString("\u0185",8+ left_margin + l * pr_r + l * horizontal_space - horizontal_space / 4,17+ shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space - 41);
                        g.setColor(Color.white);
                        g.drawString("\u0185",7+ left_margin + l * pr_r + l * horizontal_space - horizontal_space / 4,16+ shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space - 41);
                        
                    }
                    g.setColor(getNeuronColor(p.y));
                    //Kimeneti réteg
                    if (l == 0 /*|| l == nn.getLayers().size() - 1*/) {
                        g.fillRect(__x, __y, pr_r, pr_r);
                        //Rejtett réteg    
                    } else {
                        g.fillRoundRect(__x - pr_r / 2, __y, pr_r * 2 + 14, pr_r, 30, 30);

                        g.setFont(g.getFont().deriveFont(18f));

                        g.setColor(new Color(0.4f, 0.4f, 0.4f, 0.8f));
                        g.drawString("\u03A3", 6 + __x - pr_r / 2 + 1, 22 + __y + 1);
                        g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.8f));
                        g.drawString("\u03A3", 6 + __x - pr_r / 2, 22 + __y);
                        g.setFont(g.getFont().deriveFont(12f).deriveFont(Font.BOLD));
                        g.setColor(new Color(0.4f, 0.4f, 0.4f, 0.8f));
                        switch (p.getActivation()) {
                            case RELU:
                                g.drawString("ReLU", 22 + __x - pr_r / 2 + 1, 20 + __y + 1);
                                break;
                            case SIGMOID:
                                g.drawString("Sigmoid", 22 + __x - pr_r / 2 + 1, 20 + __y + 1);
                                break;
                            case SOFTPLUS:
                                g.drawString("Soft+", 22 + __x - pr_r / 2 + 1, 20 + __y + 1);
                                break;
                            case TANH:
                                g.drawString("TanH", 22 + __x - pr_r / 2 + 1, 20 + __y + 1);
                                break;
                            case LINEAR:
                            	g.drawString("Lineáris", 22 + __x - pr_r / 2 + 1, 20 + __y + 1);
                                break;
						default:
							break;
                
                        }
                        g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.8f));
                        switch (p.getActivation()) {
                            case RELU:
                                g.drawString("ReLU", 22 + __x - pr_r / 2, 20 + __y);
                                break;
                            case SIGMOID:
                                g.drawString("Sigmoid", 22 + __x - pr_r / 2, 20 + __y);
                                break;
                            case SOFTPLUS:
                                g.drawString("Soft+", 22 + __x - pr_r / 2, 20 + __y);
                                break;
                            case TANH:
                                g.drawString("TanH", 22 + __x - pr_r / 2, 20 + __y);
                                break;
                            case LINEAR:
                            	g.drawString("Lineáris", 22 + __x - pr_r / 2, 20 + __y);
                                break;
						default:
							break;
                        }
                        g.setStroke(new BasicStroke(0.5f));
                        g.drawLine(__x + 4, __y, __x + 4, __y + pr_r);
                        g.setFont(g.getFont().deriveFont(12f));
                        g.setStroke(new BasicStroke(1.5f));
                        g.setColor(getNeuronColor(p.y).darker());
                        g.drawRoundRect(__x - pr_r / 2, __y, pr_r * 2 + 14, pr_r, 30, 30);
                    }

                    g.setColor(Color.black);
                    g.setFont(g.getFont().deriveFont(12f).deriveFont(Font.ITALIC));
                    g.drawString("\u03B3=" + String.format("%1$,.4f", p.y), left_margin + l * pr_r + l * horizontal_space - 5, -5 + shift_vertical * (vertical_space + pr_r) + top_margin + pr * pr_r + pr * vertical_space);



                    if (l == nn.getLayers().size() - 1) {
                        g.setColor(getNeuronColor(p.y));
                        g.setStroke(new BasicStroke(3.0f));
                        g.drawLine(__x + pr_r * 2, __y + pr_r / 2, __x + 120, __y + pr_r / 2);
                        g.fillOval(__x + 120, __y, pr_r, pr_r);
                    }
                }
            }
        }

    }

    /**
     * Szinapszis színének meghatározása
     * @param v
     * @return 
     */
    private Color getSynapseColor(double v) {
        double H = 0.4 * v;
        if (rangeSynapseWeights != null) {
            double t;
            double m = 0;
            if (rangeSynapseWeights.getFrom() < 0) {
                v += (-1 * rangeSynapseWeights.getFrom());
                t = rangeSynapseWeights.getTo() + (-1 * rangeSynapseWeights.getFrom());
                m = 0.4 / t;
            } else {
                t = rangeSynapseWeights.getTo() - rangeSynapseWeights.getFrom();
                m = 0.4 / t;
            }
            H = m * v;

        }
        double S = 0.9;
        double B = 0.9;
        return Color.getHSBColor((float) H, (float) S, (float) B);
    }

    /**
     * Neuron színének meghatározása
     * @param v
     * @return 
     */
    private Color getNeuronColor(double v) {
        double H = 0.4 * v;
        if (rangeNeuronOutputValues != null) {
            double t;
            double m = 0;
            if (rangeNeuronOutputValues.getFrom() < 0) {
                v += (-1 * rangeNeuronOutputValues.getFrom());
                t = rangeNeuronOutputValues.getTo() + (-1 * rangeNeuronOutputValues.getFrom());
                m = 0.4 / t;
            } else {
                t = rangeNeuronOutputValues.getTo() - rangeNeuronOutputValues.getFrom();
                m = 0.4 / t;
            }
            H = m * v;
        }
        double S = 0.9;
        double B = 0.9;
        return Color.getHSBColor((float) H, (float) S, (float) B);
    }

    private Range determineSynapseRange() {
        if (nn != null) {
            double max = 0 - Double.MAX_VALUE;
            double min = Double.MAX_VALUE;
            try {
                for (int l = 0; l < nn.getLayers().size(); l++) {
                    for (int p = 0; p < nn.getLayers().get(l).getNeurons().size(); p++) {
                        if (nn.getLayers().get(l).getNeurons().get(p).weights != null) {
                            for (int w = 0; w < nn.getLayers().get(l).getNeurons().get(p).weights.length; w++) {
                                if (max < nn.getLayers().get(l).getNeurons().get(p).weights[w]) {
                                    max = nn.getLayers().get(l).getNeurons().get(p).weights[w];
                                }
                                if (min > nn.getLayers().get(l).getNeurons().get(p).weights[w]) {
                                    min = nn.getLayers().get(l).getNeurons().get(p).weights[w];
                                }
                            }
                        }
                        if (nn.getLayers().get(l).getNeurons().get(p).getBiasWeight() != null) {
                            if (max < nn.getLayers().get(l).getNeurons().get(p).getBiasWeight()) {
                                max = nn.getLayers().get(l).getNeurons().get(p).getBiasWeight();
                            }
                            if (min > nn.getLayers().get(l).getNeurons().get(p).getBiasWeight()) {
                                min = nn.getLayers().get(l).getNeurons().get(p).getBiasWeight();
                            }
                        }
                    }
                }
            } catch (NullPointerException n) {
                return null;
            }
            return new Range(min, max);
        }
        return null;
    }

    /**
     * Neuron min max értékének meghatározása
     * @return 
     */
    private Range determineNeuronRange() {
        if (nn != null) {
            double max = 0 - Double.MAX_VALUE;
            double min = Double.MAX_VALUE;
            try {
                for (int l = 0; l < nn.getLayers().size(); l++) {
                    for (int p = 0; p < nn.getLayers().get(l).getNeurons().size(); p++) {

                        if (max < nn.getLayers().get(l).getNeurons().get(p).y) {
                            max = nn.getLayers().get(l).getNeurons().get(p).y;
                        }
                        if (min > nn.getLayers().get(l).getNeurons().get(p).y) {
                            min = nn.getLayers().get(l).getNeurons().get(p).y;
                        }

                        if (nn.getLayers().get(l).getNeurons().get(p).getBias() != null) {
                            if (max < nn.getLayers().get(l).getNeurons().get(p).getBias()) {
                                max = nn.getLayers().get(l).getNeurons().get(p).getBias();
                            }
                            if (min > nn.getLayers().get(l).getNeurons().get(p).getBias()) {
                                min = nn.getLayers().get(l).getNeurons().get(p).getBias();
                            }
                        }
                    }
                }
            } catch (NullPointerException n) {
                return null;
            }
            return new Range(min, max);
        }
        return null;
    }
}
