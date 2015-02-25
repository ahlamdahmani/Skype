package com.esisa.java.reseau.models;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.esisa.java.reseau.view.ClientFrame;

public class Telecharger implements Runnable {
	private ServerSocket server;
    private Socket socket;
    private int port;
    private String sauvegarder = "";
    private InputStream is;
    private FileOutputStream ous;
    private ClientFrame clientFrame;
	
	public Telecharger(String sauvegarder, ClientFrame clientFrame) {
		try {
			this.server = new ServerSocket(0);
	        this.port = server.getLocalPort();
			this.sauvegarder = sauvegarder;
			this.clientFrame = clientFrame;
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
            socket = server.accept();
            System.out.println("Telechargé : " + socket.getRemoteSocketAddress());
            is = socket.getInputStream();
            ous = new FileOutputStream(sauvegarder);
            byte[] buffer = new byte[1024];
            int count;
            while((count = is.read(buffer)) >= 0){
                ous.write(buffer, 0, count);
            }
            ous.flush();
            clientFrame.getText().append("Application : Téléchargement Complet\n");
            if(ous != null){ ous.close(); }
            if(is != null){ is.close(); }
            if(socket != null){ socket.close(); }
        } 
        catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
