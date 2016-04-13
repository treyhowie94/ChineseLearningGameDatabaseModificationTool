package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.JButton;

import dbconn.DBConnection;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final char[] CORRECT_USER = {'C', 'h', 'i', 'n', 'e', 's', 'e', 'G', 'a', 'm', 'e'};
	private final char[] CORRECT_PASS = {'2', 'e', 'Y', '$', '9', 'o', 'a', '!'};
	
	private JPanel contentPane;
	private JTextField passwordField;
	private JTextField usernameField;
	private JButton loginButton;
	
	public LoginWindow() {
		buildMainFrame();
		buildFeatures();
		addActionListeners();
	}
	
	public void buildMainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	public void buildFeatures() {
		JLabel lblNewLabel = new JLabel("Learning Game Database Modifier");
		lblNewLabel.setFont(new Font("Lantinghei SC", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(81, 6, 284, 49);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please enter the username and password below...");
		lblNewLabel_1.setBounds(67, 60, 324, 29);
		contentPane.add(lblNewLabel_1);
		
		usernameField = new JTextField();
		usernameField.setBounds(129, 116, 193, 28);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Username:");
		lblNewLabel_2.setBounds(48, 122, 69, 16);
		contentPane.add(lblNewLabel_2);
		
		passwordField = new JTextField();
		passwordField.setBounds(129, 168, 193, 28);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(48, 174, 69, 16);
		contentPane.add(lblPassword);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(168, 225, 117, 29);
		loginButton.setEnabled(false);
		contentPane.add(loginButton);
	}
	
	public void addActionListeners() {
		this.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
            	setAlwaysOnTop(true);
            }
            public void windowDeactivated(WindowEvent e) {
                setAlwaysOnTop(false);
            }
        });
		
		passwordField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  enableLoginButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  enableLoginButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  enableLoginButton();
			}
		});
		
		usernameField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  enableLoginButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  enableLoginButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  enableLoginButton();
			}
		});
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				authenticateUser();
			}
		});
	}
	
	private void authenticateUser() {
		char[] username = usernameField.getText().toCharArray();
		char[] password = passwordField.getText().toCharArray();

		if (Arrays.equals(username, CORRECT_USER) && Arrays.equals(password, CORRECT_PASS)) {
			DBConnection.connectDB();
			
			MenuSystem frame = new MenuSystem();
			frame.setVisible(true);
			frame.setAlwaysOnTop(true);
			dispose();
		} 
		else if (!Arrays.equals(username, CORRECT_USER) && Arrays.equals(password, CORRECT_PASS)) {
			MessageWindow message = new MessageWindow("An incorrect username was entered. Please try again.");
			message.setVisible(true);
			message.setAlwaysOnTop(true);		
		} 
		else if (Arrays.equals(username, CORRECT_USER) && !Arrays.equals(password, CORRECT_PASS)) {
			MessageWindow message = new MessageWindow("An incorrect password was entered. Please try again.");
			message.setVisible(true);
			message.setAlwaysOnTop(true);		
		} 
		else {
			MessageWindow message = new MessageWindow("An incorrect username and password was entered. Please try again.");
			message.setVisible(true);
			message.setAlwaysOnTop(true);	
		}
	}
	
	private void enableLoginButton() {
		if (!passwordField.getText().equals("") && !usernameField.getText().equals("")) {
			loginButton.setEnabled(true);
		}
		else {
			loginButton.setEnabled(false);
		}
	}
	
}
