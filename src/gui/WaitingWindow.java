package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class WaitingWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	public WaitingWindow(String message) {
		buildMainFrame(message);
	}
	
	public void buildMainFrame(String message) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		
		buildFeatures(message);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	public void buildFeatures(String message) {
		JLabel lblNewLabel = new JLabel(message + " please wait...");
		lblNewLabel.setBounds(50, 35, 204, 52);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(WaitingWindow.class.getResource("/gui/resources/waiting_icon.gif")));
		lblNewLabel_1.setBounds(124, 111, 50, 52);
		contentPane.add(lblNewLabel_1);
	}
}
