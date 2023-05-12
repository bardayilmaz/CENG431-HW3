package test;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SphereWindow extends JFrame implements ActionListener{

	private JTextField radiusIn, volumeOut, areaOut;
	private DecimalFormat f3 = new DecimalFormat("0.000");
	
	public SphereWindow() {
		super("Sphegres");
		JPanel view = new JPanel();
		view.setLayout(new GridLayout(6,2,10,10));
		view.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		view.add(new JLabel("Radius = ", SwingConstants.RIGHT));
		radiusIn = new JTextField(8);
		radiusIn.setBackground(Color.yellow);
		radiusIn.addActionListener(this);
		view.add(radiusIn);
		
		view.add(new JLabel("Volume = ", SwingConstants.RIGHT));
		volumeOut = new JTextField(8);
		volumeOut.setBackground(Color.yellow);
		volumeOut.addActionListener(this);
		view.add(volumeOut);
		
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
