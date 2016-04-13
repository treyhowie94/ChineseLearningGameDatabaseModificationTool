package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;

import dbconn.DatabaseModificationOptions;

public class RowEditorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField pinyinField;
	private JTextField definitionField;
	private JTextField levelField;
	private JButton adjustButton;
	private JButton cancelButton;
	
	private MenuSystem mainMenu;
	private int editableRow;
	private String[] originalInfo;
	private String character;

	public RowEditorWindow(MenuSystem mainMenu, int editableRow, String character, String[] currInfo) {
		this.editableRow  = editableRow;
		this.mainMenu     = mainMenu;
		this.originalInfo = currInfo;
		this.character    = character;
		
		buildMainFrame();
		buildFeatures();
		buildActionListeners();
		
	}
	
	private void buildMainFrame() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void buildFeatures() {
		adjustButton = new JButton("Adjust Info\n");
		adjustButton.setBounds(241, 214, 161, 29);
		contentPane.add(adjustButton);
		
		JLabel label_1 = new JLabel("Pinyin:");
		label_1.setBounds(37, 84, 61, 16);
		contentPane.add(label_1);
		
		pinyinField = new JTextField();
		pinyinField.setText(originalInfo[0]);
		pinyinField.setColumns(10);
		pinyinField.setBounds(110, 84, 265, 28);
		contentPane.add(pinyinField);
		
		JLabel label_2 = new JLabel("Definition:");
		label_2.setBounds(37, 124, 77, 16);
		contentPane.add(label_2);
		
		definitionField = new JTextField();
		definitionField.setText(originalInfo[1]);
		definitionField.setColumns(10);
		definitionField.setBounds(110, 124, 265, 28);
		contentPane.add(definitionField);
		
		JLabel label_3 = new JLabel("Level:");
		label_3.setBounds(37, 163, 61, 16);
		contentPane.add(label_3);
		
		levelField = new JTextField();
		levelField.setText(originalInfo[2]);
		levelField.setColumns(10);
		levelField.setBounds(110, 164, 265, 28);
		contentPane.add(levelField);
		
		JLabel lblAdjustTheInformation = new JLabel("Adjust the information for " + character);
		lblAdjustTheInformation.setBounds(98, 34, 279, 29);
		contentPane.add(lblAdjustTheInformation);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(56, 214, 161, 29);
		contentPane.add(cancelButton);
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
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		adjustButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] info = new String[originalInfo.length];
				info[0] = pinyinField.getText();
				info[1] = definitionField.getText();
				info[2] = levelField.getText();
				
				if (infoChanged(info)) {
					if (StringUtils.isNumeric(info[2])) {	
						DatabaseModificationOptions.updateRowInformation(character, info);
						for (int i=0; i<info.length; i++) {
							mainMenu.dataTable.setValueAt(info[i], editableRow, i+1);
						}
						mainMenu.searchResultTable.setModel(mainMenu.dataTable);
						dispose();
					}
					else {
						MessageWindow message = new MessageWindow("Please enter a numeric value for the \"level\" field");
						message.setVisible(true);
						message.setAlwaysOnTop(true);
						
						levelField.setText("");
					}
				}
				else {
					MessageWindow message = new MessageWindow("No information was modified. Please change the information");
					message.setVisible(true);
					message.setAlwaysOnTop(true);
				}
			}
		});
		
	}
	
	private boolean infoChanged(String[] info) {
		for (int i=0; i<originalInfo.length; i++) {
			if (!originalInfo[i].equals(info[i])) {
				return true;
			}
		}
		return false;
	}
	
}
