package darth.jminas;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelInferior extends JPanel {
	private static final long serialVersionUID = 2473191468363778297L;

	public PanelInferior() {
		setBorder(BorderFactory.createRaisedBevelBorder());
		setLayout(new BorderLayout());
		add(new JLabel("JMinas v0.02"), BorderLayout.EAST);
	}
}