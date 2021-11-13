package darth.jminas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelSuperior extends JPanel {
    private static final long serialVersionUID = 2473191468363778297L;

    private JMinasMain minasMain;
    private static JLabel lblTime, lblMinas, lblStart;
    private JPanel pCronometro, pMinas;

    public PanelSuperior(JMinasMain minasMain) {
    	this.minasMain = minasMain;
        setBorder(BorderFactory.createRaisedBevelBorder());
        setLayout(new GridLayout(1,5,3,3));

        initComponents();
        addComponents();
    }

    private void initComponents() {
        pCronometro = new JPanel();
        pCronometro.setBackground(Color.black);
        pCronometro.setLayout(new BorderLayout());
        pMinas = new JPanel();
        pMinas.setBackground(Color.black);
        pMinas.setLayout(new BorderLayout());

        lblTime = new JLabel("00:00", JLabel.CENTER);
        lblTime.setBackground(Color.black);
        lblTime.setForeground(Color.green);
        lblTime.setFont(new Font("Serif", Font.BOLD, 16));
        pCronometro.add(lblTime, BorderLayout.CENTER);

        lblMinas = new JLabel("00", JLabel.CENTER);
        lblMinas.setBackground(Color.black);
        lblMinas.setForeground(Color.green);
        lblMinas.setFont(new Font("Serif", Font.BOLD, 16));
        pMinas.add(lblMinas, BorderLayout.CENTER);

        lblStart = new JLabel("", JLabel.CENTER);
        lblStart.setBorder(BorderFactory.createRaisedBevelBorder());
        lblStart.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
                lblStart.setBorder(BorderFactory.createRaisedBevelBorder());
            }
            public void mousePressed(MouseEvent e) {
                lblStart.setBorder(BorderFactory.createLoweredBevelBorder());
            }
            public void mouseExited(MouseEvent e) {
                if(lblStart.getIcon() != null)
                    lblStart.setIcon(icon);
            }
            public void mouseEntered(MouseEvent e) {
                if(lblStart.getIcon() != null) {
                    icon = lblStart.getIcon();
                    lblStart.setIcon(new ImageIcon(getClass().getResource(Variables.pathRiendo)));
                }
            }

            public void mouseClicked(MouseEvent e) {
            	minasMain.RestartGame();
            }
        });
    }

    private static Icon icon;

    private void addComponents() {
        try {
            URL url = this.getClass().getResource(Variables.pathIcoCronometro);
            ImageIcon icon = new ImageIcon(url);
            add(new JLabel(icon, JLabel.RIGHT));
        }catch(NullPointerException e) {
            add(new JLabel("tiempo ", JLabel.RIGHT));
        }

        add(pCronometro);
        add(lblStart);
        add(pMinas);

        try {
            URL url = this.getClass().getResource(Variables.pathIcoMinas);  
            ImageIcon icon = new ImageIcon(url);
            add(new JLabel(icon, JLabel.LEFT));
        } catch(NullPointerException e) {
            add(new JLabel(" minas", JLabel.LEFT));
        }
    }

    public void restart() {
        lblTime.setForeground(Color.green);
        lblTime.setText("00:00");
        lblMinas.setForeground(Color.green);
    }

    public static void UpdateTime(int min, int seg) {
        if(min == 99 && seg == 59){
            JMinasMain.StopChron();
            lblTime.setForeground(Color.red);
            lblTime.setText("--:--");
            return;
        }

        String strMin, strSeg;
        if(seg > 9) strSeg = ""+seg;
        else strSeg = "0"+seg;

        if(min<10) strMin = "0"+min;
        else strMin = ""+min;

        lblTime.setText(strMin+":"+strSeg);
    }

    public static void UpdateMinas(int num) {
        if(num < 0)
            lblMinas.setForeground(Color.red);
        else
            lblMinas.setForeground(Color.green);
        lblMinas.setText(""+num);
    }

    public static void UpdateIconStart(ImageIcon icon, String text) {
        if(icon != null)
            lblStart.setIcon(icon);
        else {
            lblStart.setIcon(null);
            lblStart.setText(text);
        }
    }
}
