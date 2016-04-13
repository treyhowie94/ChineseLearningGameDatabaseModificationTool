package gui;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class MessageWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JButton okButton;
	private JScrollPane scrollPane;
	private JTextPane textPane;

	public MessageWindow(String message) {
		buildMainFrame();
		buildFeatures(message);
		buildActionListeners();
	}
	
	private void buildMainFrame() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void buildFeatures(String message) {
		okButton = new JButton("OK");
		okButton.setBounds(91, 138, 117, 29);
		contentPane.add(okButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 288, 120);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setText(message);
		scrollPane.setViewportView(textPane);
	}
	
	private void buildActionListeners() {
		this.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
            	setAlwaysOnTop(true);
            }
            public void windowDeactivated(WindowEvent e) {
                setAlwaysOnTop(false);
            }
        });
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

}
