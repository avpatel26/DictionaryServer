/* Name : Avi Patel
 * ID : 1143213 
 */
package Client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class DictionaryClient {

	private JFrame frmDictionaryApp;
	private JTextField textField;
	private JTextField textField_1;
	private static JTextArea textArea ;
	private static Socket socket;
	private static int port;
	private static String path;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DictionaryClient window = new DictionaryClient();
					window.frmDictionaryApp.setVisible(true);
					
					if(args.length == 2) {
						port = Integer.parseInt(args[1]);
						path = args[0];
						try {
						socket = new Socket(path,port);
						}catch(ConnectException e) {
							System.out.println(" Connection is Refused by server or Server is not running.");
							textArea.setText(" Connection is Refused by server or Server is not running.");
						}
						}else {
						System.out.println(" Improper Argument Error!! Enter proper number of arguments");
						textArea.setText(" Improper Argument Error!! Enter proper number of arguments");
						System.exit(0);
					}
					System.out.println("Client is running...");
					
				}catch(NumberFormatException e) {
					textArea.setText(" Improper Argument Error!! Run again with proper argument format: java –jar DictionaryClient.jar <server-address> <server-port>");
					
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					textArea.setText("Network is not reachable or Unknown host or Server is not running.");
					
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					textArea.setText("Connection is Refused by server or Server is not running.");
					
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DictionaryClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDictionaryApp = new JFrame();
		frmDictionaryApp.setTitle("DIctionary App");
		frmDictionaryApp.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 9));
		frmDictionaryApp.setBounds(100, 100, 684, 493);
		frmDictionaryApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{157,314,30,40,-85, 0};
		gridBagLayout.rowHeights = new int[]{57,52,64,58,65,24, 125,0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmDictionaryApp.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		frmDictionaryApp.getContentPane().add(panel, gbc_panel);
		
		JLabel lblNewLabel = new JLabel("Dictionary");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Enter Word");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		frmDictionaryApp.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField = new JTextField();
		textField.setToolTipText("Enter word to be search,add or remove in dictionary");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		frmDictionaryApp.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Enter Meanings");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		frmDictionaryApp.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Enter meaning of word to add");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		frmDictionaryApp.getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JTextArea textArea0 = new JTextArea(5, 200);
		textArea0.setEditable(false);
		textArea0.setFont(new Font("Monospaced", Font.PLAIN, 11));
		textArea0.setLineWrap(true);
		textArea0.setWrapStyleWord(true);
		textArea0.setMinimumSize(new Dimension(40, 64));
		textArea0.setText("*Enter comma seperated values to add multiple meanings");
		textArea0.setTabSize(25);
		GridBagConstraints gbc_textArea0 = new GridBagConstraints();
		gbc_textArea0.fill = GridBagConstraints.BOTH;
		gbc_textArea0.gridwidth = 3;
		gbc_textArea0.insets = new Insets(0, 0, 5, 5);
		gbc_textArea0.gridx = 2;
		gbc_textArea0.gridy = 3;
		frmDictionaryApp.getContentPane().add(textArea0, gbc_textArea0);
		
		JButton btnNewButton = new JButton("Search Word");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = textField.getText();
				if(word.equals("")) {
					textArea.setText("Enter word to search!!");
				}else {
					ArrayList<String> message = new ArrayList<String>();
					message.add("search");
					message.add(word);
					sendReceivePacket(message);
				}
					}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 11));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		frmDictionaryApp.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add Word and Meaning");
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = textField.getText();
				String meaning = textField_1.getText();
				if(meaning.equals("")) {
					textArea.setText("Enter Meaning of word!!");
				}else if(word.equals("")) {
					textArea.setText("Enter word to add!!");
				}else {
					String[] meanings = meaning.split("\\s*,\\s*");
				
					ArrayList<String> message = new ArrayList<String>();
					message.add("add");
					message.add(word);
					for (String string : meanings) {
						message.add(string);
					}
					sendReceivePacket(message);
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 4;
		frmDictionaryApp.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Remove Word");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = textField.getText();
				if(word.equals("")) {
					textArea.setText("Enter word to remove!!");
				}else {
					ArrayList<String> message = new ArrayList<String>();
					message.add("remove");
					message.add(word);
					sendReceivePacket(message);
				}
			}
		});
		btnNewButton_2.setFont(new Font("Arial", Font.BOLD, 11));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 4;
		frmDictionaryApp.getContentPane().add(btnNewButton_2, gbc_btnNewButton_2);
		
		JLabel lblNewLabel_3 = new JLabel("Output");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 5;
		frmDictionaryApp.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textArea = new JTextArea(5, 200);
		textArea.setPreferredSize(new Dimension(1604, 94));
		textArea.setMaximumSize(new Dimension(314, 125));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setMinimumSize(new Dimension(314, 125));
		textArea.setTabSize(25);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 6;
		frmDictionaryApp.getContentPane().add(textArea, gbc_textArea);
		
		textArea.setEditable(false);
		
		JButton btnNewButton_3 = new JButton("Exit");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_3.gridx = 0;
		gbc_btnNewButton_3.gridy = 7;
		frmDictionaryApp.getContentPane().add(btnNewButton_3, gbc_btnNewButton_3);
	}
	private static void sendReceivePacket(ArrayList<String> message) {
		try {
			socket = new Socket(path,port);
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			os.writeObject(message);
			ArrayList<String> receive = (ArrayList<String>) is.readObject();
			if(message.get(0).equals("search")) {
				
				String output = "Meaning of word "+message.get(1)+" :";
				for (int i =2;i<receive.size();i++) {
					output= output+","+receive.get(i);
				}
				
				textArea.setText(output);
			}else {
				
				
				textArea.setText(receive.get(2));
				System.out.println("Meassage from server: "+receive.get(2));	
			}
		}catch (SocketException e ) {
			textArea.setText("Network is not reachable or Connection is Refused by server or Server is not running.");
		}
		catch (IOException e) {
			textArea.setText("Connection is Refused by server or Server is not running.");
		}
		catch(Exception e) {
			textArea.setText("Error occured!!");;
		}	
	}

}
