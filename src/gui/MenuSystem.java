package gui;

import java.awt.Color;  
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

import org.apache.commons.lang3.StringUtils;

import dbconn.DatabaseModificationOptions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuSystem extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final int DELETE_WINDOW  = 1;
	private final int RESTORE_WINDOW = 2;
	
	protected SearchResultTable dataTable;
	protected JTable searchResultTable;
	
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	
	private JButton createNewWordButton;
	private JTextField addCharacterTextField;
	private JTextField addPinyinTextField;
	private JTextField addDefinitionTextField;
	private JTextField addLevelTextField;
	
	private JTextField searchTextField;
	private JButton deleteRowButton;
	private JButton adjustInfoButton;
	private JButton wordSearchButton;
	
	private JCheckBox restoreCheckBox;
	private JCheckBox deleteCheckBox;
	private JButton restoreButton;
	private JButton deleteButton;
	
	public MenuSystem() {
		createMainFrame();
		addMainTabbedPane();
		createAddTab();
		createDeleteEditTab();
		createHelpTab();
		createAdvancedTab();
		addComponentListeners();
	}
	
	private void createMainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void addMainTabbedPane() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 438, 266);
		contentPane.add(tabbedPane);
	}
	
	private void createAddTab() {
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Add", null, panel_5, null);
		panel_5.setLayout(null);
		
		createNewWordButton = new JButton("Add New Word");
		createNewWordButton.setEnabled(false);
		createNewWordButton.setBounds(134, 185, 161, 29);
		panel_5.add(createNewWordButton);
		
		JLabel lblCharacter = new JLabel("Character:");
		lblCharacter.setBounds(6, 48, 89, 16);
		panel_5.add(lblCharacter);
		
		addCharacterTextField = new JTextField();
		addCharacterTextField.setBounds(81, 48, 265, 28);
		panel_5.add(addCharacterTextField);
		addCharacterTextField.setColumns(10);
		
		JLabel lblPinyin = new JLabel("Pinyin:");
		lblPinyin.setBounds(6, 80, 61, 16);
		panel_5.add(lblPinyin);
		
		addPinyinTextField = new JTextField();
		addPinyinTextField.setBounds(81, 80, 265, 28);
		panel_5.add(addPinyinTextField);
		addPinyinTextField.setColumns(10);
		
		JLabel lblDefinition = new JLabel("Definition:");
		lblDefinition.setBounds(6, 112, 77, 16);
		panel_5.add(lblDefinition);
		
		addDefinitionTextField = new JTextField();
		addDefinitionTextField.setBounds(81, 112, 265, 28);
		panel_5.add(addDefinitionTextField);
		addDefinitionTextField.setColumns(10);
		
		JLabel lblLevel = new JLabel("Level:");
		lblLevel.setBounds(6, 146, 61, 16);
		panel_5.add(lblLevel);
		
		addLevelTextField = new JTextField();
		addLevelTextField.setBounds(81, 146, 265, 28);
		panel_5.add(addLevelTextField);
		addLevelTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter the Information for a New Character:");
		lblNewLabel.setBounds(67, 6, 279, 29);
		panel_5.add(lblNewLabel);
	}
	
	private void createDeleteEditTab() {
		JPanel panel = new JPanel();
		tabbedPane.addTab("Delete/Edit", null, panel, null);
		panel.setLayout(null);
		
		deleteRowButton = new JButton("Delete Row");
		deleteRowButton.setEnabled(false);
		deleteRowButton.setBounds(300, 0, 111, 66);
		panel.add(deleteRowButton);
		
		adjustInfoButton = new JButton("Adjust Info");
		adjustInfoButton.setEnabled(false);
		adjustInfoButton.setBounds(300, 64, 111, 66);
		panel.add(adjustInfoButton);
		
		wordSearchButton = new JButton("Search");
		wordSearchButton.setEnabled(false);
		wordSearchButton.setBounds(300, 189, 111, 29);
		panel.add(wordSearchButton);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(0, 188, 297, 28);
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		createNewSearchResultTable(initEmptyTable());
		
		JScrollPane scrollPane = new JScrollPane(searchResultTable);
		scrollPane.setBounds(0, 0, 297, 188);
		panel.add(scrollPane);
	}
	
	private void createHelpTab() {
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Help", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 6, 405, 208);
		panel_2.add(scrollPane_1);
		
		JTextPane txtpnHelpToSearch = new JTextPane();
		txtpnHelpToSearch.setText("\t\t\t\t\t\t\t\tHelp\n\nTo search for a character: Enter your desired madarin character or english word and hit the search button. A listing of the character(s) will appear in a table above the search bar, showing the character(s), pinyin value, definition, and level.\n\nTo delete a character: Click on the character you want to delete's row and hit the \"Delete Row\" button. You will prompted with a window that asks whether you really want to delete the character, click the option related to your intention. The character will be deleted from the database\n\nTo add a character: Click the \"Add Character\" button. You will be prompted to enter the corresponding information for the character, then hit \"Add\". The character will be added to the database.\n\nTo delete the database: Go to \"Advanced\" and switch to the delete tab. Check the checkbox to verify that you want to delete the database and click the delete button.\n\nTo restore the database: Go to \"Advanced\" and switch to the restore tab. Check the checkbox to verify that you want to restore the database and click the restore button.\n");
		scrollPane_1.setViewportView(txtpnHelpToSearch);
	}
	
	private void createAdvancedTab() {
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Advanced", null, panel_1, null);
		panel_1.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(6, 6, 405, 208);
		panel_1.add(tabbedPane_1);
		
		createAdvancedSubTabMenu(tabbedPane_1);
	}
	
	private void createAdvancedSubTabMenu(JTabbedPane tabbedPane_1) {
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Restore", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblRestoreDatabase = new JLabel("Restore Database:");
		lblRestoreDatabase.setBounds(6, 129, 125, 16);
		panel_3.add(lblRestoreDatabase);
		
		restoreCheckBox = new JCheckBox("Confirm");
		restoreCheckBox.setBounds(128, 125, 128, 23);
		panel_3.add(restoreCheckBox);
		
		restoreButton = new JButton("Restore");
		restoreButton.setEnabled(false);
		restoreButton.setBounds(232, 119, 146, 39);
		panel_3.add(restoreButton);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 6, 372, 110);
		panel_3.add(scrollPane_2);
		
		JTextPane txtpnRestoreThisAllows = new JTextPane();
		txtpnRestoreThisAllows.setText("This allows you to restore the database to its original state, deleting any non-original words you may have added and adding any original words you may have deleted. Click the check box below to accept that you want to restore the database to its original state. \n\nWARNING: This action is irreversible and you will lose any data you added on your own.\n");
		scrollPane_2.setViewportView(txtpnRestoreThisAllows);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Delete All Entries", null, panel_4, null);
		panel_4.setLayout(null);
		
		deleteButton = new JButton("Delete Entries");
		deleteButton.setEnabled(false);
		deleteButton.setBounds(241, 117, 137, 39);
		panel_4.add(deleteButton);
		
		JLabel lblByClickingThis = new JLabel("Delete database:\n\n");
		lblByClickingThis.setBounds(17, 127, 120, 16);
		panel_4.add(lblByClickingThis);
		
		deleteCheckBox = new JCheckBox("Confirm");
		deleteCheckBox.setBounds(138, 123, 128, 23);
		panel_4.add(deleteCheckBox);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(6, 6, 372, 109);
		panel_4.add(scrollPane_3);
		
		JTextPane txtpnAdvancedOptionsThis = new JTextPane();
		txtpnAdvancedOptionsThis.setText("This button is to delete all entries in the database. Click the check box below to activate the button. Once clicked you will prompted by a window. Click the option related to your intentions. \n\nWARNING: If you finalize deletion of all entries you can only restore the original version of the database using the restore option in \"Advanced\".");
		scrollPane_3.setViewportView(txtpnAdvancedOptionsThis);
	}
	
	private void createNewSearchResultTable(ArrayList<String[]> data) {
		dataTable = new SearchResultTable(data);
		searchResultTable = new JTable(dataTable) {
			private static final long serialVersionUID = 1L;

			@Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		        Component c = super.prepareRenderer(renderer, row, column);
		        c.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
		        return c;
		    }
			
		};
		
		searchResultTable.getColumnModel().getColumn(0).setPreferredWidth(5);
		searchResultTable.getColumnModel().getColumn(1).setPreferredWidth(10);
		searchResultTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		searchResultTable.getColumnModel().getColumn(3).setPreferredWidth(10);
	}
	
	private void addComponentListeners() {
		this.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
            	setAlwaysOnTop(true);
            }
            public void windowDeactivated(WindowEvent e) {
                setAlwaysOnTop(false);
            }
        });
		
		addAddTabListeners();
		addEditDeleteTabListeners();
		addAdvancedTabListeners();
		
	}
	
	private void addAddTabListeners() {
		activateWordCreatorButton();
		
		createNewWordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String character  = addCharacterTextField.getText();
				String pinyin     = addPinyinTextField.getText();
				String definition = addDefinitionTextField.getText();
				String level      = addLevelTextField.getText();
				
				if (StringUtils.isNumeric(level)) {
					if (DatabaseModificationOptions.charAlreadyExists(character).isEmpty()) {
						if (DatabaseModificationOptions.addNewEntry(true, pinyin, character, definition, level)) {
							MessageWindow successFrame = new MessageWindow("\"" + character + "\" was added to the database");
							successFrame.setVisible(true);
							successFrame.setAlwaysOnTop(true);
							
							addCharacterTextField.setText("");
							addPinyinTextField.setText("");
							addDefinitionTextField.setText("");
							addLevelTextField.setText("");
						}
					}
					else {
						MessageWindow reviewFrame = new MessageWindow("An entry for \"" + character + 
								"\" already exists. Please make an entry for a new character or go to "
								+ "Delete/Edit to adjust the information for this character");
						reviewFrame.setVisible(true);
						reviewFrame.setAlwaysOnTop(true);
						
						addCharacterTextField.setText("");
						addPinyinTextField.setText("");
						addDefinitionTextField.setText("");
						addLevelTextField.setText("");
					}
				}
				else {
					MessageWindow message = new MessageWindow("Please enter a number for the \"level\" field");
					message.setVisible(true);
					message.setAlwaysOnTop(true);
					
					addLevelTextField.setText("");
				}
			}
		});
	}
	
	private void addEditDeleteTabListeners() {
		searchResultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				int row = searchResultTable.getSelectedRow();
				if (row > -1 && !searchResultTable.getModel().getValueAt(row, 0).toString().equals("")) { 
					deleteRowButton.setEnabled(true);
					adjustInfoButton.setEnabled(true);
				}
				else {
					deleteRowButton.setEnabled(false);
					adjustInfoButton.setEnabled(false);
				}
			}
		});
		
		deleteRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = searchResultTable.getSelectedRow();
				if (row > -1 && !searchResultTable.getModel().getValueAt(row, 0).toString().equals("")) {
					String charToDelete = searchResultTable.getModel().getValueAt(row, 0).toString();
					
					WarningWindow warning = new WarningWindow(MenuSystem.this, 3, row, charToDelete);
					warning.setVisible(true);
					warning.setAlwaysOnTop(true);
					
					searchResultTable.clearSelection();
					deleteRowButton.setEnabled(false);
					adjustInfoButton.setEnabled(false);
				}
			}
		});
		
		adjustInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = searchResultTable.getSelectedRow();
				if (row > -1 && !searchResultTable.getModel().getValueAt(row, 0).toString().equals("")) {
					String[] info = new String[3];
					info[0] = searchResultTable.getModel().getValueAt(row, 1).toString();
					info[1] = searchResultTable.getModel().getValueAt(row, 2).toString();
					info[2] = searchResultTable.getModel().getValueAt(row, 3).toString();
					
					RowEditorWindow editor = new RowEditorWindow(MenuSystem.this, row,
							searchResultTable.getModel().getValueAt(row, 0).toString(), info);
					editor.setVisible(true);
					editor.setAlwaysOnTop(true);
					
					searchResultTable.clearSelection();
					deleteRowButton.setEnabled(false);
					adjustInfoButton.setEnabled(false);
				}
			}
		});
		
		wordSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRowButton.setEnabled(false);
				adjustInfoButton.setEnabled(false);
				
				ArrayList<String[]> searchResults = DatabaseModificationOptions.searchDB(searchTextField.getText());
				
				if (searchResults == null) {
					MessageWindow message = new MessageWindow("You entered an invalid search. Please try again.");
					message.setVisible(true);
					message.setAlwaysOnTop(true);
				}
				else if (searchResults.isEmpty()) {
					MessageWindow message = new MessageWindow("Your search returned no results from the database.");
					message.setVisible(true);
					message.setAlwaysOnTop(true);
				}
				else if (searchResults != null && !searchResults.isEmpty()) {
					searchResults = fillOutTable(searchResults);
					dataTable.resetTableData(searchResults);
					searchResultTable.setModel(dataTable);
				}
				
				searchTextField.setText("");
			}
		});
		
		searchTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				deleteRowButton.setEnabled(false);
				adjustInfoButton.setEnabled(false);
				searchResultTable.clearSelection();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// do nothing
			}
		});
		
		searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  enableSearchButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  enableSearchButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  enableSearchButton();
			}
		});
	}
	
	private void addAdvancedTabListeners() {
		restoreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (restoreButton.isEnabled()) {
					WarningWindow warning = new WarningWindow(null, RESTORE_WINDOW, null, null);
					warning.setVisible(true);
					warning.setAlwaysOnTop(true);
					
					restoreCheckBox.setSelected(false);
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (deleteRowButton.isEnabled()) {
					WarningWindow warning = new WarningWindow(null, DELETE_WINDOW, null, null);
					warning.setVisible(true);
					warning.setAlwaysOnTop(true);
					
					deleteCheckBox.setSelected(false);
				}
			}
		});
		
		restoreCheckBox.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		            restoreButton.setEnabled(true);
		        } 
		        else {
		        	restoreButton.setEnabled(false);
		        };
		    }
		});
		
		deleteCheckBox.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
		            deleteButton.setEnabled(true);
		        } 
		        else {
		        	deleteButton.setEnabled(false);
		        };
		    }
		});
	}
	
	private void activateWordCreatorButton() {
		addCharacterTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			}
		});
		
		addPinyinTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			}
		});
		
		addDefinitionTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			}
		});
		
		addLevelTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  enableWordCreatorButton();
			}
		});
	}
	
	private void enableWordCreatorButton() {
		if (!addCharacterTextField.getText().equals("") && !addPinyinTextField.getText().equals("") 
				&& !addDefinitionTextField.getText().equals("") 
				&& !addLevelTextField.getText().equals("")) {
			createNewWordButton.setEnabled(true);
		}
		else {
			createNewWordButton.setEnabled(false);
		}
	}
	
	private void enableSearchButton() {
		if (!searchTextField.getText().equals("")) {
			  wordSearchButton.setEnabled(true);
		  }
		  else {
			  wordSearchButton.setEnabled(false);
		  }
	}
	
	private ArrayList<String[]> initEmptyTable() {
		ArrayList<String[]> emptyData = new ArrayList<String[]>(0);
		for (int i=0; i<10; i++) {
			emptyData.add(new String[] {"", "", "", ""});
		}
		return emptyData;
	}
	
	private ArrayList<String[]> fillOutTable(ArrayList<String[]> searchResults) {
		if (searchResults.size() < 10) {
			for (int i=0; i<10-searchResults.size(); i++) {
				searchResults.add(new String[] {"", "", "", ""});
			}
		}
		return searchResults;
	}
	
}
