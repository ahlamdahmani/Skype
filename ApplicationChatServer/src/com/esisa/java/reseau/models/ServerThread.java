package com.esisa.java.reseau.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.esisa.java.reseau.business.SocketServer;
import com.esisa.java.reseau.view.ServerFrame;

public class ServerThread extends Thread {
	private SocketServer server = null;
	private Socket socket = null;
	private int ID = -1;
	private String username = "";
	private ObjectInputStream is = null;
	private ObjectOutputStream ous = null;
	private ServerFrame view;

    public ServerThread(SocketServer server, Socket socket){  
    	super();
        this.server = server;
        this.socket = socket;
        this.ID = socket.getPort();
        this.view = server.getView();
    }
    
    public void send(Object msg){
        try {
            ous.writeObject(msg);
            ous.flush();
        } 
        catch (IOException ex) {
        	ex.printStackTrace();
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
   
   
    public long getID() {
		return ID;
	}


	@SuppressWarnings("deprecation")
	public void run(){  
        while (true){  
    	    try{  
                Message msg = (Message) is.readObject();
    	    	server.traiter(ID, msg);
            }
            catch(Exception ioe){  
            	ioe.printStackTrace();
            	System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.supprimer(ID);
                stop();
            }
        }
    }
    
    public void open() throws IOException {  
        ous = new ObjectOutputStream(socket.getOutputStream());
        ous.flush();
        is = new ObjectInputStream(socket.getInputStream());
    }
    
    public void close() throws IOException {  
    	if (socket != null) socket.close();
        if (is != null) is.close();
        if (ous != null) ous.close();
    }

	public SocketServer getServer() {
		return server;
	}

	public void setServer(SocketServer server) {
		this.server = server;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ServerFrame getView() {
		return view;
	}

	public void setView(ServerFrame view) {
		this.view = view;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
