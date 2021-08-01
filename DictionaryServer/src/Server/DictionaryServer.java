/* Name : Avi Patel
 * ID : 1143213 
 */
package Server;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.Border;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class DictionaryServer extends JFrame 
	implements ActionListener {
		
		public static final int WIDTH = 400;
		public static final int HEIGHT = 400;
		private static JTextArea word;
		protected Socket socket;
		private static int port;
		protected static String dictionaryFile;
		static int portnumber = 0;
		
	    DictionaryServer(){
	    	 super("Dictionary Server");
	         setSize(WIDTH, HEIGHT);
	         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         setLayout(new GridLayout(2, 1));
	         
	         JTextArea wordPanel = new JTextArea(20, 10); 
	         wordPanel.setLayout(new BorderLayout( ));
	         wordPanel.setBackground(Color.WHITE); 

	         word = new JTextArea(8,8);
	         
	         wordPanel.add(word, BorderLayout.SOUTH);
	         Border border = BorderFactory.createLineBorder(Color.BLACK);
	         word.setBorder(BorderFactory.createCompoundBorder(border,
	                 BorderFactory.createEmptyBorder(10,10,10, 10)));
	         

	         JLabel nameLabel1 = new JLabel("Welcome to Server!");
	         wordPanel.add(nameLabel1, BorderLayout.CENTER);
	         add(wordPanel);
	       	         
	         JPanel buttonPanel = new JPanel( );  
	         
	         JButton infoButton = new JButton("Show Client"); 
	         infoButton.addActionListener(this);
	         buttonPanel.add(infoButton);  
	         
	         add(buttonPanel);
	     }

	 	   @Override
	 	   public void actionPerformed(ActionEvent e) 
	 	    {
	 	        String actionCommand = e.getActionCommand( );
	 	        	
	 	 	    if (actionCommand.equals("Show Client"))
	 	 	    	word.setText( "client with port number " + portnumber+" just connected!");
	 	    } 
		
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public void setPath(String dictionaryFile) {
		this.dictionaryFile = dictionaryFile;
	}
	
	public static void main(String[] args)  {
		
		DictionaryServer frame = new DictionaryServer();
        frame.setVisible(true);
		ServerSocket serverSocket = null;
		if(args.length == 2) {
			port = Integer.parseInt(args[0]);
			dictionaryFile = args[1];
		}else {
			System.out.println(" Improper Argument Error!! Enter proper number of arguments");
			System.exit(0);
		}
		
		File file = new File(dictionaryFile);
		if(!file.exists()) {
			createInitialDict(dictionaryFile);
		}	
		ExecutorService executor = Executors.newFixedThreadPool(5);				// Creating Thread Pool of size 5
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is running...");
			while(true) {						
					Socket socket = serverSocket.accept();						// Listens the request from client
					System.out.println("Connection is established");
					DictionaryServer server = new DictionaryServer();
					server.setSocket(socket);
					server.setPath(dictionaryFile);
					portnumber = socket.getPort();
					word.setText( "client with port number " + portnumber+" just connected!");
					Runnable worker = new DictionaryService(server);			// Allocate the service
					executor.execute(worker);									// Execute the service
			
			}
		}catch(IOException e) {
			System.out.println("I/O error occured");
		}finally {
			System.out.println("Server is closed..");
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
	
	
	}
	
	
	public static void createInitialDict(String path) {
		
		System.out.println("New Default Dictionary File is created..");
		JSONObject obj = new JSONObject();
		List<String> list = new ArrayList<String>();
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list.add("an electronic device for storing and processing data");
	    obj.put("computer", list);
	    list1.add("group or system of interconnected people or things.");
	    obj.put("network", list1);
	    list2.add("a computer or computer program which manages access to a centralized resource or service in a network.");
	    obj.put("server", list2);
	   
	    try (FileWriter file = new FileWriter(path)) {

	        file.write(obj.toJSONString());
	        file.flush();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

