package gui;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import dbconn.DatabaseModificationOptions;

public class WarningWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JButton cancel;
	private JButton proceed;

	public WarningWindow(MenuSystem menu, int type, Integer row, String charOpt) {
		buildMainFrame();
		setFeatures(type, charOpt);
		buildActionListeners(menu, type, row, charOpt);
	}
	
	private void buildMainFrame() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void setFeatures(int type, String charOpt) {
		cancel = new JButton("Cancel");
		cancel.setBounds(38, 143, 117, 29);
		contentPane.add(cancel);
		
		proceed = new JButton("Proceed");
		proceed.setBounds(153, 143, 117, 29);
		contentPane.add(proceed);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 288, 122);
		contentPane.add(scrollPane);
		
		JTextPane txtpnWarningYouAre = new JTextPane();
		
		if (type == 1) {
			txtpnWarningYouAre.setText("WARNING: You are about to delete the entire Database. Do you want to proceed?");
		}
		else if (type == 2) {
			txtpnWarningYouAre.setText("WARNING: You are about to restore the Database. "
					+ "After you do this you cannot recover any words you may have added. Do you want to proceed?");
		}
		else if (type == 3) {
			txtpnWarningYouAre.setText("WARNING: You are about to delete the row for \"" + charOpt + "\" from "
					+ "the Database. Do you want to proceed?");
		}
		
		scrollPane.setViewportView(txtpnWarningYouAre);
	}
	
	private void buildActionListeners(MenuSystem menu, int type, Integer row, String charOpt) {
		this.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
            	setAlwaysOnTop(true);
            }
            public void windowDeactivated(WindowEvent e) {
                setAlwaysOnTop(false);
            }
        });
		
		proceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (type == 1) {
					DatabaseModificationOptions.deleteAllEntries();
					
					dispose();
					
					MessageWindow message = new MessageWindow("Database deleted. If you need to restore use the \"Restore\" option");
					message.setVisible(true);
					message.setAlwaysOnTop(true);
				}
				else if (type == 2) {
					DatabaseModificationOptions.deleteAllEntries();
					DatabaseModificationOptions.restoreDB();
					
					dispose();
					
					MessageWindow message = new MessageWindow("Database restored");
					message.setVisible(true);
					message.setAlwaysOnTop(true);
				}
				else if (type == 3) {
					DatabaseModificationOptions.deleteEntry(charOpt);
					
					if (row != null) {
						menu.dataTable.deleteRow(row);
						menu.searchResultTable.setModel(menu.dataTable);
						
						menu.searchResultTable.clearSelection();
					}
					
					dispose();
					
					MessageWindow message = new MessageWindow("\"" + charOpt + "\" deleted");
					message.setVisible(true);
					message.setAlwaysOnTop(true);
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
}
