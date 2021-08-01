/* Name : Avi Patel
 * ID : 1143213 
 */
package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DictionaryService implements Runnable {

	Socket client = null;
	DictionaryServer server = null;
	String path = null;
	
	public DictionaryService(DictionaryServer server) {
		this.server = server;
		client = server.socket;
		this.path =server.dictionaryFile;
		System.out.println("Connection established with client "+client);
	}
	
	
//Add Word and Meaning to Dictionary
	public synchronized String add(String word, List<String> meanings) {
		try {
			File file = new File(path);	
			if(file.exists()) {
				Object obj = new JSONParser().parse(new FileReader(file));
				JSONObject jo = (JSONObject)obj;
				jo.put(word, meanings);
				ArrayList<String> list = search(word);
				if(list==null) {					
					PrintWriter pw = new PrintWriter(file);
					pw.write(jo.toJSONString());
					pw.flush();
					pw.close();
					System.out.println("New word is successfully added!!");
					return " New word is successfully added!!";
				}else {
					if(list.containsAll(meanings)) {
						System.out.println("Duplicate!! Word and meanings are alredy exist!!");
						return "Duplicate!! Word and meanings are alredy exist!!";
					}
					List<String> tempList = new ArrayList<String>(list) ;
					tempList.removeAll(meanings);
					
					meanings.addAll(tempList);
					jo.replace(word,meanings);
					PrintWriter pw = new PrintWriter(file);
					pw.write(jo.toJSONString());
					pw.flush();
					pw.close();
					System.out.println("Word alredy exist but more meanings are added!!");
					return "Word alredy exist but more meanings are added!!";
				}
				
			
			}else {
				file.createNewFile();
				PrintWriter pw = new PrintWriter(file);
				JSONObject jo = new JSONObject();
				jo.put(word, meanings);
				pw.write(jo.toJSONString());
				pw.flush();
				pw.close();
				System.out.println("New file created and New word is successfully Added!!");
				return "New file created and New word is successfully Added!!";
			}
		}catch(FileNotFoundException f) {
			System.out.println("File not found because it is used by another program"+f);
		}
		catch(IOException e) {
			System.out.println("IO exception in Add dictionary services"+e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return null;
	}

//Remove word from dictionary
	public synchronized String remove(String word) {
		try {
			File file = new File(path);
			Object obj = new JSONParser().parse(new FileReader(file));
			JSONObject jo = (JSONObject)obj;
			if(file.exists()) {
				ArrayList<String> list = search(word);
				if(list!=null) {
					jo.remove(word);
					PrintWriter pw = new PrintWriter(file);
					pw.write(jo.toJSONString());
					pw.flush();
					pw.close();
					System.out.println("Word is successfully removed from dictionary!!");
					return "Word is successfully removed from dictionary!!";
				}else {
					System.out.println("Word is not available in dictionary!!");
					return "Word is not available in dictionary!!";
				}	
			}else {
				file.createNewFile();
				System.out.println("Successfully new file created");
				return "Successfully new file created";
			}
		}catch(FileNotFoundException f) {
			System.out.println("File not found because it is used by another program"+f);
		}
		catch(IOException e) {
			System.out.println("IO exception in remove dictionary services"+e);
		} catch (ParseException e) {
			System.out.println("Error occured during parsing the file!");
			e.printStackTrace();
		}
				
		return null;	}

//Search word from dictionary
	public synchronized ArrayList<String> search(String word) {
		try {
			File file = new File(path);
			HashMap<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
			ArrayList<String> meanings = new ArrayList<String>();
			meanings.clear();
			if(file.exists()) { 
				Object obj = new JSONParser().parse(new FileReader(file));
				JSONObject jo = (JSONObject)obj;
				meanings = (ArrayList<String>) jo.get(word);			
				return meanings;
			}else{
				file.createNewFile();
				return meanings;
			}}catch(IOException e){
				System.out.println("IO exception in search dictionary services"+e);
			}catch (ParseException e) {
				System.out.println("Error occured during parsing the file!");
				e.printStackTrace();
			}
		
		return null;
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(client.getInputStream());
			
			ArrayList<String> message = (ArrayList<String>) is.readObject();
			if(message.get(0).equals("add")) {
				String word = message.get(1);
				List<String> meanings = message.subList(2, message.size());
				if(meanings.isEmpty()) {
					String mesage = "enter mening of word";
				}
				String response = add(word, meanings);
				ArrayList<String> serverMessage = new ArrayList<String>();
				serverMessage.add("add");
				serverMessage.add(word);
				serverMessage.add(response);
				os.writeObject(serverMessage);
			}
			else if(message.get(0).equals("remove")) {
				String word = message.get(1);
				String response = remove(word);
				ArrayList<String> serverMessage = new ArrayList<String>();
				serverMessage.add("remove");
				serverMessage.add(word);
				serverMessage.add(response);
				os.writeObject(serverMessage);
			}
			else if(message.get(0).equals("search")) {
					String word = message.get(1);
					List<String> meaning = search(word);
					ArrayList<String> serverMessage = new ArrayList<String>();
					serverMessage.add("search");
					serverMessage.add(word);
					if(meaning!=null) {
					for(String string:meaning) {
						serverMessage.add(string);
					}}else {
						serverMessage.add(" is not available in dictionary");
					}
					
					os.writeObject(serverMessage);
				}
			
		}catch (SocketException e) {
			System.out.println("Connection with this client is ended.");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException occured in dicrionaryServices");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class not found.");
			e.printStackTrace();
		}
		
	
		
	}
	

}
