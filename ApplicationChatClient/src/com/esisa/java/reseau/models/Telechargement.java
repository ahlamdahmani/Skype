package com.esisa.java.reseau.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.esisa.java.reseau.view.ClientFrame;

public class Telechargement implements Runnable {
	private String adresse;
    private int port;
    private Socket socket;
    private FileInputStream is;
    private OutputStream ous;
    private File file;
    private ClientFrame clientFrame;
    
	public Telechargement(String adresse, int port, File file,
			ClientFrame clientFrame) {
		super();
		try {
			this.adresse = adresse;
			this.port = port;
			this.file = file;
			this.clientFrame = clientFrame;
			socket = new Socket(InetAddress.getByName(adresse),port);
			ous = socket.getOutputStream();
			is = new FileInputStream(file);
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {       
            byte[] buffer = new byte[1024];
            int count;
            while((count = is.read(buffer)) >= 0){
                ous.write(buffer, 0, count);
            }
            ous.flush();
			clientFrame.getText().append("Applcation : Téléchargement du fichier complet\n");
            if(is != null){ is.close(); }
            if(ous != null){ ous.close(); }
            if(socket != null){ socket.close(); }
        }
        catch (Exception ex) {
        	System.out.println("Erreur : " + ex.getMessage());
        }
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	

}
